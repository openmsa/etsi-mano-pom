/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.notification.vnfind;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.NsdChangeType;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.FailureDetails;
import com.ubiqube.etsi.mano.dao.mano.config.ServerType;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.VnfIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.nfvo.service.graph.nfvo.UowUtils;
import com.ubiqube.etsi.mano.service.VnfPackageServiceImpl;
import com.ubiqube.etsi.mano.service.VnfmInterface;

@Component
public class VnfIndicatorValueChangeNotificationImpl {

	private static final Logger LOG = LoggerFactory.getLogger(VnfIndicatorValueChangeNotificationImpl.class);

	private final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa;

	private final VnfPackageServiceImpl vnfPackageServiceImpl;
	
	private final VnfmInterface vnfm;

	private Properties props;
	
	private final ServersJpa serversJpa;
	
	private final BiFunction<Servers, UUID, VnfBlueprint> func;
	
	private final BiFunction<Servers, UUID, List<VnfBlueprint>> func2;
	
	private final Random rand = new Random();

	public VnfIndicatorValueChangeNotificationImpl(final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa, final VnfPackageServiceImpl vnfPackageServiceImpl, final ServersJpa serversJpa, final VnfmInterface vnfm, final VnfInstanceLcm vnfLcmOpOccsService) {
		this.vnfIndValueNotificationJpa = vnfIndValueNotificationJpa;
		this.vnfPackageServiceImpl = vnfPackageServiceImpl;
		this.serversJpa = serversJpa;
		this.vnfm = vnfm;
		func = vnfLcmOpOccsService::vnfLcmOpOccsGet;
		func2 = vnfLcmOpOccsService::findByVnfInstanceId;
		try (InputStream mappting = this.getClass().getClassLoader().getResourceAsStream("gnocchi-mapping.properties")) {
			props = new Properties();
			props.load(mappting);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	@Scheduled(fixedRate = 60_000)
	public void run() {
		final Iterable<VnfIndiValueChangeNotification> ite = vnfIndValueNotificationJpa.findAll();
		final Set<VnfIndiValueChangeNotification> result = StreamSupport.stream(ite.spliterator(), false)
				.collect(Collectors.toSet());
		final Map<String, List<VnfIndiValueChangeNotification>> notificationsByInstanceId = result.stream()
				.collect(Collectors.groupingBy(VnfIndiValueChangeNotification::getVnfInstanceId));
		LOG.trace("Polling notification");
		for (final Map.Entry<String, List<VnfIndiValueChangeNotification>> entry : notificationsByInstanceId.entrySet()) {
			evaluateValueBasedOnCondition(entry.getKey(), entry.getValue());
		}
	}

	public void evaluateValueBasedOnCondition(final String vnfInstanceId, final List<VnfIndiValueChangeNotification> notifications) {
		vnfIndValueNotificationJpa.deleteAll(notifications);
		final String vnfdId = notifications.get(0).getVnfdId();
		final VnfPackage vnfPackage = vnfPackageServiceImpl.findByVnfdId(UUID.fromString(vnfdId));
		final Set<VnfIndicator> vnfIndicators = vnfPackage.getVnfIndicator();
		for (final VnfIndicator vnfIndicator : vnfIndicators) {
			final Map<String, TriggerDefinition> triggers = vnfIndicator.getTriggers();
			for (final TriggerDefinition trigger : triggers.values()) {
				final ObjectMapper mapper = new ObjectMapper();
				JsonNode actualObj = null;
				boolean thresholdCrossed = false;
				try {
					actualObj = mapper.readTree(trigger.getCondition());
				} catch (final JsonProcessingException e) {
					LOG.error("error parsing condition", e);
					break;
				}
				conditions: for (final JsonNode jsonNode : actualObj) {
					final Map<String, Object> condition = mapper.convertValue(jsonNode,
							new TypeReference<Map<String, Object>>() {
							});
					final Map.Entry<String, Object> c = condition.entrySet().iterator().next();
					final String indicatorName = c.getKey();
					Double vnfIndicatorValue;
					final Set<VnfIndiValueChangeNotification> not = notifications.stream().filter(x -> x.getVnfIndicatorId().equals(indicatorName)).collect(Collectors.toSet());
					if (not.isEmpty()) {
						break conditions;
					}
					final VnfIndiValueChangeNotification vnfIndiValueChangeNotification = not.iterator().next();
					try {
						vnfIndicatorValue = Double.valueOf(vnfIndiValueChangeNotification.getValue());
					} catch (final NumberFormatException e) {
						LOG.error("error parsing threshold value", e);
						break;
					}
					final List<Map<String, Object>> conList = (List<Map<String, Object>>) c.getValue();
					for (final Map<String, Object> m : conList) {
						for (final Map.Entry<String, Object> r : m.entrySet()) {
							final String conditionOperator = r.getKey();
							Double conditionValue;
							final String prop = props.getProperty(indicatorName + "_" + r.getValue().toString());
							if (prop != null) {
								conditionValue = Double.valueOf(prop);
							} else {
								try {
									conditionValue = Double.valueOf(r.getValue().toString());
								} catch (final NumberFormatException e) {
									LOG.error("error parsing threshold value", e);
									break;
								}
							}
							thresholdCrossed = switch (conditionOperator) {
							case "greater_or_equal" -> (vnfIndicatorValue >= conditionValue);
							case "less_than" -> (vnfIndicatorValue < conditionValue);
							case "greater_than" -> (vnfIndicatorValue > conditionValue);
							case "less_or_equal" -> (vnfIndicatorValue <= conditionValue);
							case "equal" -> (vnfIndicatorValue == conditionValue);
							default -> false;
							};
						}
						if (!thresholdCrossed) {
							break conditions;
						}
					}
				}
				if (thresholdCrossed) {
					LOG.info("Launching action");
					final Servers server = selectServer(vnfPackage);
					if(!UowUtils.isVnfLcmRunning(UUID.fromString(vnfInstanceId), func2, server)) {
						callAction(vnfInstanceId, trigger.getAction(), server);
					}
				}
			}
		}
	}

	public void callAction(final String vnfInstanceId, final String actionParameters, Servers server) {
		final ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(actionParameters);
		} catch (final JsonProcessingException e) {
			LOG.error("error parsing action", e);
		}
		Map<String, Object> action = new HashMap<>();
		for (JsonNode jsonNode : actualObj) {
			action = mapper.convertValue(jsonNode,
					new TypeReference<Map<String, Object>>() {
					});
		}
		if (action.isEmpty()) {
			return;
		}
		
		Map.Entry<String, Object> a = action.entrySet().iterator().next();
		Map<String, Object> b = (Map<String, Object>) a.getValue();
		String operationName = (String) b.get("operation");
		Map<String, Object> inputs = (Map<String, Object>) b.get("inputs");
		VnfBlueprint res = null;
		if (operationName.equals("Vnflcm.scale")) {
			VnfScaleRequest vnfScaleRequest = new VnfScaleRequest();
			for (Map.Entry<String, Object> c : inputs.entrySet()) {
				Map<String, String> d = (Map<String, String>) c.getValue();
				Object value = d.entrySet().iterator().next().getValue();
				switch (c.getKey()) {
				case "type":
					if (value.equals("scale_out"))
						vnfScaleRequest.setType(ScaleTypeEnum.OUT);
					if (value.equals("scale_in"))
						vnfScaleRequest.setType(ScaleTypeEnum.IN);
					break;
				case "aspect":
					vnfScaleRequest.setAspectId(value.toString());
					break;
				case "number_of_steps":
					vnfScaleRequest.setNumberOfSteps(Integer.parseInt(value.toString()));
					break;
				}
			}
			LOG.info("VNF Scale {} launched", vnfScaleRequest.getType().toString());
			res = vnfm.vnfScale(server, vnfInstanceId, vnfScaleRequest);
		} else if (operationName.equals("Vnflcm.heal")) {
			VnfHealRequest vnfHealRequest = new VnfHealRequest();
			for (Map.Entry<String, Object> c : inputs.entrySet()) {
				Map<String, String> d = (Map<String, String>) c.getValue();
				Object value = d.entrySet().iterator().next().getValue();
				vnfHealRequest.setCause(value.toString());
			}
			LOG.info("VNF Heal with cause {} launched", vnfHealRequest.getCause());
			res = vnfm.vnfHeal(server, vnfInstanceId, vnfHealRequest);
		} else {
			LOG.error("operation name not valid");
		}

		final VnfBlueprint result = UowUtils.waitLcmCompletion(res, func, server);
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
			throw new GenericException("VNF LCM Failed: " + details + " With state:  " + result.getOperationStatus());
		}
	}
	
	private Servers selectServer(final VnfPackage vnfPackage) {
		final List<Servers> servers = serversJpa.findByServerTypeAndServerStatusIn(ServerType.VNFM, Arrays.asList(PlanStatusType.SUCCESS));
		if (servers.isEmpty()) {
			return null;
		}
		final Set<String> vnfmInfos = vnfPackage.getVnfmInfo();
		if (vnfmInfos.isEmpty()) {
			return servers.get(rand.nextInt(servers.size()));
		}
		final List<Servers> available = servers.stream()
				.filter(x -> {
					final List<String> s = x.getCapabilities().stream().filter(vnfmInfos::contains).toList();
					return !s.isEmpty();
				})
				.toList();
		if (available.isEmpty()) {
			throw new GenericException("No VNFM server found for the following requirements: " + vnfmInfos);
		}
		return available.get(rand.nextInt(available.size()));
	}
}
