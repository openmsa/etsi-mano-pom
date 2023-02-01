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
import java.util.ArrayList;
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
import com.google.common.collect.Lists;
import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsVnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.FailureDetails;
import com.ubiqube.etsi.mano.dao.mano.config.ServerType;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.jpa.VnfIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.nfvo.controller.nslcm.NsInstanceControllerService;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.UowUtils;
import com.ubiqube.etsi.mano.nfvo.v261.model.nslcm.ScaleNsByStepsData;
import com.ubiqube.etsi.mano.nfvo.v261.model.nslcm.ScaleNsByStepsData.ScalingDirectionEnum;
import com.ubiqube.etsi.mano.nfvo.v261.model.nslcm.ScaleNsData;
import com.ubiqube.etsi.mano.nfvo.v261.model.nslcm.ScaleNsRequest;
import com.ubiqube.etsi.mano.service.VnfPackageServiceImpl;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.cond.ConditionService;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;

import ma.glasnost.orika.MapperFacade;

@Component
public class VnfIndicatorValueChangeNotificationImpl {

	private static final String ERROR_PARSING_THRESHOLD_VALUE = "error parsing threshold value";

	private static final Logger LOG = LoggerFactory.getLogger(VnfIndicatorValueChangeNotificationImpl.class);

	private final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa;

	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	private final NsBlueprintJpa nsBlueprintJpa;

	private final NsdInstanceJpa nsdInstanceJpa;

	private final NsdPackageJpa nsdPackageJpa;

	private final NsInstanceControllerService nsInstanceControllerService;

	private final VnfPackageServiceImpl vnfPackageServiceImpl;

	private final VnfmInterface vnfm;

	private Properties props;

	private final ServersJpa serversJpa;

	private final BiFunction<Servers, UUID, VnfBlueprint> func;

	private final BiFunction<Servers, UUID, List<VnfBlueprint>> func2;

	private final MapperFacade mapper;

	private final ConditionService conditionService;
	private final Random rand = new Random();

	public VnfIndicatorValueChangeNotificationImpl(final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa,
			final VnfPackageServiceImpl vnfPackageServiceImpl, final ServersJpa serversJpa,
			final VnfmInterface vnfm, final VnfInstanceLcm vnfLcmOpOccsService,
			final NsLiveInstanceJpa nsLiveInstanceJpa, final NsdPackageJpa nsdPackageJpa, final NsdInstanceJpa nsdInstanceJpa,
			final NsInstanceControllerService nsInstanceControllerService, final MapperFacade mapper, final NsBlueprintJpa nsBlueprintJpa,
			final ConditionService conditionService) {
		this.vnfIndValueNotificationJpa = vnfIndValueNotificationJpa;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.nsdInstanceJpa = nsdInstanceJpa;
		this.nsBlueprintJpa = nsBlueprintJpa;
		this.nsdPackageJpa = nsdPackageJpa;
		this.nsInstanceControllerService = nsInstanceControllerService;
		this.vnfPackageServiceImpl = vnfPackageServiceImpl;
		this.serversJpa = serversJpa;
		this.vnfm = vnfm;
		this.mapper = mapper;
		this.conditionService = conditionService;
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
		final List<VnfIndiValueChangeNotification> allNotifications = Lists.newArrayList(ite);
		vnfIndValueNotificationJpa.deleteAll(allNotifications);
		final Map<String, List<NSVnfNotification>> notificationsByNsInstanceId = new HashMap<>();
		final List<NSVnfNotification> nsVnfNotifications = new ArrayList<>();
		LOG.trace("Polling notification");
		for (final Map.Entry<String, List<VnfIndiValueChangeNotification>> entry : notificationsByInstanceId.entrySet()) {
			final List<NsLiveInstance> nsInstanceIds = nsLiveInstanceJpa.findByResourceId(entry.getKey());
			final Set<NsVnfIndicator> nsVnfIndicators = getNSIndicators(nsInstanceIds.get(0).getNsInstance().getId());
			if ((nsVnfIndicators != null) && nsVnfIndicators.iterator().next().getToscaName().equals("auto_scale")) {
				final UUID nsInstanceId = nsInstanceIds.get(0).getNsInstance().getId();
				LOG.info("NS instance of the vnf Instance {}:{}", entry.getKey(), nsInstanceId);
				final NSVnfNotification nsVnfNotification = new NSVnfNotification();
				nsVnfNotification.setVnfIndiValueChangeNotifications(entry.getValue());
				nsVnfNotification.setVnfInstanceId(entry.getKey());
				nsVnfNotification.setNsInstanceId(nsInstanceId.toString());
				if (notificationsByNsInstanceId.containsKey(nsInstanceId.toString())) {
					notificationsByNsInstanceId.get(nsInstanceId.toString()).add(nsVnfNotification);
				} else {
					nsVnfNotifications.add(nsVnfNotification);
					notificationsByNsInstanceId.put(nsInstanceId.toString(), nsVnfNotifications);
				}
			} else {
				LOG.info("NS instanceId for VNF instance not found");
				evaluateValueBasedOnCondition(null, entry.getKey(), entry.getValue());
			}
		}
		for (final Map.Entry<String, List<NSVnfNotification>> nsNotifications : notificationsByNsInstanceId.entrySet()) {
			final List<VnfIndiValueChangeNotification> notifications = getNSVnfNotifications(nsNotifications.getValue(), UUID.fromString(nsNotifications.getKey()));
			evaluateValueBasedOnCondition(nsNotifications.getKey(), null, notifications);
		}
	}

	public Set<NsVnfIndicator> getNSIndicators(final UUID nsInstanceId) {
		final NsdInstance nsdInstance = nsdInstanceJpa.findById(nsInstanceId).orElseThrow(() -> new GenericException("Could not find nsInstance: " + nsInstanceId));
		final NsdPackage nsdPackage = nsdPackageJpa.findByNsdId(nsdInstance.getNsdInfo().getNsdId()).orElseThrow(() -> new GenericException("Could not find nsdPackage: " + nsdInstance.getNsdInfo().getNsdId()));
		if ((nsdPackage.getNsVnfIndicator() != null) && !nsdPackage.getNsVnfIndicator().isEmpty()) {
			return nsdPackage.getNsVnfIndicator();
		}
		return null;
	}

	public List<VnfIndiValueChangeNotification> getNSVnfNotifications(final List<NSVnfNotification> nsVnfNotifications, final UUID nsInstanceId) {
		final NsdInstance nsdInstance = nsdInstanceJpa.findById(nsInstanceId).orElseThrow(() -> new GenericException("Could not find nsInstance: " + nsInstanceId));
		final NsdPackage nsdPackage = nsdPackageJpa.findByNsdId(nsdInstance.getNsdInfo().getNsdId()).orElseThrow(() -> new GenericException("Could not find nsdPackage: " + nsdInstance.getNsdInfo().getNsdId()));
		final Set<NsdPackageVnfPackage> nsVnfPackages = nsdPackage.getVnfPkgIds();
		final List<VnfIndiValueChangeNotification> allNsVnfNotifications = new ArrayList<>();
		for (final NSVnfNotification nsVnfNotification : nsVnfNotifications) {
			for (final VnfIndiValueChangeNotification vnfIndiValueChangeNotification : nsVnfNotification.getVnfIndiValueChangeNotifications()) {
				final Set<NsdPackageVnfPackage> vnfPackages = nsVnfPackages.stream().filter(x -> x.getVnfdId().equals(vnfIndiValueChangeNotification.getVnfdId())).collect(Collectors.toSet());
				final String vnfInstanceName = vnfPackages.iterator().next().getToscaName();
				final String vnfIndicatorId = vnfIndiValueChangeNotification.getVnfIndicatorId();
				LOG.info("NS vnf notification vnf indicator id : {} _ {}", vnfInstanceName, vnfIndicatorId);
				vnfIndiValueChangeNotification.setVnfIndicatorId(vnfInstanceName + "_" + vnfIndicatorId);
				allNsVnfNotifications.add(vnfIndiValueChangeNotification);
			}
		}
		return allNsVnfNotifications;
	}

	public void evaluateValueBasedOnCondition(final String nsInstanceId, final String vnfInstanceId, final List<VnfIndiValueChangeNotification> notifications) {
		if (nsInstanceId != null) {
			for (final NsVnfIndicator nsVnfIndicator : getNSIndicators(UUID.fromString(nsInstanceId))) {
				final Map<String, TriggerDefinition> nsTriggers = nsVnfIndicator.getTriggers();
				LOG.info("NS Trigger evaluating");
				evaluateTriggers(nsTriggers, nsInstanceId, null, null, notifications);
			}
		} else {
			final String vnfdId = notifications.get(0).getVnfdId();
			final VnfPackage vnfPackage = vnfPackageServiceImpl.findByVnfdId(UUID.fromString(vnfdId));
			final Set<VnfIndicator> vnfIndicators = vnfPackage.getVnfIndicator();
			for (final VnfIndicator vnfIndicator : vnfIndicators) {
				final Map<String, TriggerDefinition> vnfTriggers = vnfIndicator.getTriggers();
				LOG.info("VNF Trigger evaluating");
				evaluateTriggers(vnfTriggers, null, vnfInstanceId, vnfPackage, notifications);
			}
		}
	}

	public void evaluateTriggers(final Map<String, TriggerDefinition> triggers, final String nsInstanceId, final String vnfInstanceId, final VnfPackage vnfPackage, final List<VnfIndiValueChangeNotification> notifications) {
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
			final Node root = conditionService.parse(trigger.getCondition());
			final Context context = null;
			conditionService.evaluate(root, context);
			conditions: for (final JsonNode jsonNode : actualObj) {
				final Map<String, Object> condition = mapper.convertValue(jsonNode,
						new TypeReference<Map<String, Object>>() {
							//
						});
				final Map.Entry<String, Object> c = condition.entrySet().iterator().next();
				final String indicatorName = c.getKey();
				LOG.info("trigger indicator name {}", indicatorName);
				double vnfIndicatorValue;
				final Set<VnfIndiValueChangeNotification> not = notifications.stream().filter(x -> x.getVnfIndicatorId().equals(indicatorName)).collect(Collectors.toSet());
				if (not.isEmpty()) {
					LOG.info("Notification after filter is empty");
					break conditions;
				}
				if (not.size() > 1) {
					double totalValue = 0.0;
					Double value = 0.0;
					for (final VnfIndiValueChangeNotification vnfIndiValueChangeNotification : not) {
						try {
							value = Double.valueOf(vnfIndiValueChangeNotification.getValue());
						} catch (final NumberFormatException e) {
							LOG.error(ERROR_PARSING_THRESHOLD_VALUE, e);
						}
						totalValue = totalValue + value;
					}
					if (totalValue == 0.0) {
						return;
					}
					vnfIndicatorValue = totalValue / not.size();
				} else {
					final VnfIndiValueChangeNotification vnfIndiValueChangeNotification = not.iterator().next();
					try {
						vnfIndicatorValue = Double.valueOf(vnfIndiValueChangeNotification.getValue());
					} catch (final NumberFormatException e) {
						LOG.error(ERROR_PARSING_THRESHOLD_VALUE, e);
						break;
					}
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
								LOG.error(ERROR_PARSING_THRESHOLD_VALUE, e);
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
				if (vnfInstanceId != null) {
					final Servers server = selectServer(vnfPackage);
					if (!UowUtils.isVnfLcmRunning(UUID.fromString(vnfInstanceId), func2, server)) {
						LOG.info("calling VNF Action");
						callAction(vnfInstanceId, null, trigger.getAction(), server);
					}
				}
				if ((nsInstanceId != null) && !UowUtils.isNSLcmRunning(UUID.fromString(nsInstanceId), nsBlueprintJpa)) {
					LOG.info("calling NS Action");
					callAction(null, nsInstanceId, trigger.getAction(), null);
				}

			}
		}
	}

	public void callAction(final String vnfInstanceId, final String nsInstanceId, final String actionParameters, final Servers server) {
		final ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(actionParameters);
		} catch (final JsonProcessingException e) {
			throw new GenericException("Error parsing action", e);
		}
		Map<String, Object> action = new HashMap<>();
		for (final JsonNode jsonNode : actualObj) {
			action = mapper.convertValue(jsonNode,
					new TypeReference<Map<String, Object>>() {
						//
					});
		}
		if (action.isEmpty()) {
			return;
		}

		final Map.Entry<String, Object> a = action.entrySet().iterator().next();
		final Map<String, Object> b = (Map<String, Object>) a.getValue();
		final String operationName = (String) b.get("operation");
		final Map<String, Object> inputs = (Map<String, Object>) b.get("inputs");
		VnfBlueprint res = null;
		if ("Vnflcm.scale".equals(operationName)) {
			res = doVnfLcmScale(vnfInstanceId, server, inputs);
		} else if ("Vnflcm.heal".equals(operationName)) {
			final VnfHealRequest vnfHealRequest = new VnfHealRequest();
			for (final Map.Entry<String, Object> c : inputs.entrySet()) {
				final Map<String, String> d = (Map<String, String>) c.getValue();
				final Object value = d.entrySet().iterator().next().getValue();
				vnfHealRequest.setCause(value.toString());
			}
			LOG.info("VNF Heal with cause {} launched", vnfHealRequest.getCause());
			res = vnfm.vnfHeal(server, vnfInstanceId, vnfHealRequest);
		} else if ("Nslcm.scale".equals(operationName)) {
			final ScaleNsRequest scaleNSRequest = new ScaleNsRequest();
			scaleNSRequest.setScaleType(ScaleNsRequest.ScaleTypeEnum.NS);
			final ScaleNsData scaleNsData = new ScaleNsData();
			final ScaleNsByStepsData snbsd = new ScaleNsByStepsData();
			final Map<String, String> d = (Map<String, String>) inputs.get("scale_ns_by_steps_data");
			for (final Map.Entry<String, String> c : d.entrySet()) {
				final Object value = c.getValue();
				switch (c.getKey()) {
				case "scaling_direction":
					if ("scale_out".equals(value)) {
						snbsd.setScalingDirection(ScalingDirectionEnum.OUT);
					}
					if ("scale_in".equals(value)) {
						snbsd.setScalingDirection(ScalingDirectionEnum.IN);
					}
					break;
				case "aspect":
					snbsd.setAspectId(value.toString());
					break;
				case "number_of_steps":
					snbsd.setNumberOfSteps(Integer.parseInt(value.toString()));
					break;
				default:
					break;
				}
			}
			if (snbsd.getNumberOfSteps() == null) {
				snbsd.setNumberOfSteps(1);
			}
			scaleNsData.setScaleNsByStepsData(snbsd);
			scaleNSRequest.setScaleNsData(scaleNsData);
			final NsScale nsInst = this.mapper.map(scaleNSRequest, NsScale.class);
			LOG.info("NS Scale {} {} : {} launched", snbsd.getScalingDirection(), nsInstanceId, nsInst);
			final NsBlueprint nsBlueprint = nsInstanceControllerService.scale(UUID.fromString(nsInstanceId), nsInst);

			final NsBlueprint result = UowUtils.waitNSLcmCompletion(nsBlueprint, nsBlueprintJpa);
			if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
				final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
				throw new GenericException("NS LCM Failed: " + details + " With state:  " + result.getOperationStatus());
			}
		} else {
			LOG.error("operation name not valid");
		}

		if ("Vnflcm.scale".equals(operationName) || "Vnflcm.heal".equals(operationName)) {
			final VnfBlueprint result = UowUtils.waitLcmCompletion(res, func, server);
			if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
				final String details = Optional.ofNullable(result.getError()).map(FailureDetails::getDetail).orElse("[No content]");
				throw new GenericException("VNF LCM Failed: " + details + " With state:  " + result.getOperationStatus());
			}
		}
	}

	private VnfBlueprint doVnfLcmScale(final String vnfInstanceId, final Servers server, final Map<String, Object> inputs) {
		final VnfBlueprint res;
		final VnfScaleRequest vnfScaleRequest = new VnfScaleRequest();
		for (final Map.Entry<String, Object> c : inputs.entrySet()) {
			final Map<String, String> d = (Map<String, String>) c.getValue();
			final Object value = d.entrySet().iterator().next().getValue();
			switch (c.getKey()) {
			case "type":
				if ("scale_out".equals(value)) {
					vnfScaleRequest.setType(ScaleTypeEnum.OUT);
				}
				if ("scale_in".equals(value)) {
					vnfScaleRequest.setType(ScaleTypeEnum.IN);
				}
				break;
			case "aspect":
				vnfScaleRequest.setAspectId(value.toString());
				break;
			case "number_of_steps":
				vnfScaleRequest.setNumberOfSteps(Integer.parseInt(value.toString()));
				break;
			}
		}
		if (vnfScaleRequest.getNumberOfSteps() == null) {
			vnfScaleRequest.setNumberOfSteps(1);
		}
		LOG.info("VNF Scale {} launched", vnfScaleRequest.getType());
		return vnfm.vnfScale(server, vnfInstanceId, vnfScaleRequest);
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
