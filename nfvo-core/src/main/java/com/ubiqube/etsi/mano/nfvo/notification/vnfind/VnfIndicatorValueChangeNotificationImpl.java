/**
 *     Copyright (C) 2019-2024 Ubiqube.
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

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsVnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.jpa.VnfIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.UowUtils;
import com.ubiqube.etsi.mano.service.VnfPackageServiceImpl;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;
import com.ubiqube.etsi.mano.service.cond.ConditionService;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

@Component
public class VnfIndicatorValueChangeNotificationImpl {

	private static final String ERROR_PARSING_THRESHOLD_VALUE = "error parsing threshold value";

	private static final Logger LOG = LoggerFactory.getLogger(VnfIndicatorValueChangeNotificationImpl.class);

	private final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa;

	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	private final NsBlueprintJpa nsBlueprintJpa;

	private final NsdInstanceJpa nsdInstanceJpa;

	private final NsdPackageJpa nsdPackageJpa;

	private final VnfPackageServiceImpl vnfPackageServiceImpl;

	private Properties props;

	private final ServersJpa serversJpa;

	private final VnfLcmInterface vnfLcmInterface;

	private final NsLcmInterface nsLcmInterface;

	private final ConditionService conditionService;

	private final Random rand = new Random();

	private final VnfInstanceLcm vnfLcmOpOccsService;

	public VnfIndicatorValueChangeNotificationImpl(final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa,
			final VnfPackageServiceImpl vnfPackageServiceImpl, final ServersJpa serversJpa,
			final VnfInstanceLcm vnfLcmOpOccsService, final VnfLcmInterface vnfLcmInterface,
			final NsLiveInstanceJpa nsLiveInstanceJpa, final NsdPackageJpa nsdPackageJpa, final NsdInstanceJpa nsdInstanceJpa,
			final NsBlueprintJpa nsBlueprintJpa, final NsLcmInterface nsLcmInterface,
			final ConditionService conditionService) {
		this.vnfIndValueNotificationJpa = vnfIndValueNotificationJpa;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.nsdInstanceJpa = nsdInstanceJpa;
		this.nsBlueprintJpa = nsBlueprintJpa;
		this.nsdPackageJpa = nsdPackageJpa;
		this.vnfPackageServiceImpl = vnfPackageServiceImpl;
		this.serversJpa = serversJpa;
		this.nsLcmInterface = nsLcmInterface;
		this.conditionService = conditionService;
		this.vnfLcmInterface = vnfLcmInterface;
		this.vnfLcmOpOccsService = vnfLcmOpOccsService;
		try (InputStream mappting = this.getClass().getClassLoader().getResourceAsStream("gnocchi-mapping.properties")) {
			props = new Properties();
			props.load(mappting);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	@Scheduled(fixedRate = 60_000)
	public void run() {
		final Set<VnfIndiValueChangeNotification> result = findAllNotifications();
		final Map<String, List<VnfIndiValueChangeNotification>> notificationsByInstanceId = result.stream()
				.collect(Collectors.groupingBy(VnfIndiValueChangeNotification::getVnfInstanceId));
		vnfIndValueNotificationJpa.deleteAll(result);
		final MultiValueMap<UUID, NSVnfNotification> notificationsByNsInstanceId = new LinkedMultiValueMap<>();
		final List<NSVnfNotification> nsVnfNotifications = new ArrayList<>();
		LOG.trace("Polling notification");
		for (final Map.Entry<String, List<VnfIndiValueChangeNotification>> entry : notificationsByInstanceId.entrySet()) {
			final NsLiveInstance nsInstanceIds = findSingleLiveInstanceByResourceId(entry.getKey());
			final Set<NsVnfIndicator> nsVnfIndicators = getNSIndicators(nsInstanceIds.getNsInstance().getId());
			if (nsVnfIndicators.iterator().next().getToscaName().equals("auto_scale")) {
				final UUID nsInstanceId = nsInstanceIds.getNsInstance().getId();
				LOG.info("NS instance of the vnf Instance {}:{}", entry.getKey(), nsInstanceId);
				final NSVnfNotification nsVnfNotification = new NSVnfNotification();
				nsVnfNotification.setVnfIndiValueChangeNotifications(entry.getValue());
				nsVnfNotification.setVnfInstanceId(entry.getKey());
				nsVnfNotification.setNsInstanceId(nsInstanceId.toString());
				notificationsByNsInstanceId.put(nsInstanceId, nsVnfNotifications);
			} else {
				LOG.info("NS instanceId for VNF instance not found");
				evaluateValueBasedOnCondition(null, getSafeUUID(entry.getKey()), entry.getValue());
			}
		}
		for (final Map.Entry<UUID, List<NSVnfNotification>> nsNotifications : notificationsByNsInstanceId.entrySet()) {
			final List<VnfIndiValueChangeNotification> notifications = getNSVnfNotifications(nsNotifications.getValue(), nsNotifications.getKey());
			evaluateValueBasedOnCondition(nsNotifications.getKey(), null, notifications);
		}
	}

	private NsLiveInstance findSingleLiveInstanceByResourceId(final @Nonnull String resourceId) {
		final List<NsLiveInstance> res = nsLiveInstanceJpa.findByResourceId(resourceId);
		if (res.size() != 1) {
			throw new GenericException("Trying to select a single resource with id: " + resourceId + ", but got: " + res.size());
		}
		return res.getFirst();
	}

	private Set<VnfIndiValueChangeNotification> findAllNotifications() {
		final Iterable<VnfIndiValueChangeNotification> ite = vnfIndValueNotificationJpa.findAll();
		return StreamSupport.stream(ite.spliterator(), false)
				.collect(Collectors.toSet());
	}

	@NotNull
	public Set<NsVnfIndicator> getNSIndicators(final UUID nsInstanceId) {
		final NsdInstance nsdInstance = nsdInstanceJpa.findById(nsInstanceId).orElseThrow(() -> new GenericException("Could not find nsInstance: " + nsInstanceId));
		final NsdPackage nsdPackage = nsdPackageJpa.findByNsdId(nsdInstance.getNsdInfo().getNsdId()).orElseThrow(() -> new GenericException("Could not find nsdPackage: " + nsdInstance.getNsdInfo().getNsdId()));
		if ((nsdPackage.getNsVnfIndicator() != null) && !nsdPackage.getNsVnfIndicator().isEmpty()) {
			return nsdPackage.getNsVnfIndicator();
		}
		return Set.of();
	}

	public List<VnfIndiValueChangeNotification> getNSVnfNotifications(final List<NSVnfNotification> nsVnfNotifications, final UUID nsInstanceId) {
		final NsdInstance nsdInstance = nsdInstanceJpa.findById(nsInstanceId).orElseThrow(() -> new GenericException("Could not find nsInstance: " + nsInstanceId));
		final NsdPackage nsdPackage = nsdPackageJpa.findByNsdId(nsdInstance.getNsdInfo().getNsdId()).orElseThrow(() -> new GenericException("Could not find nsdPackage: " + nsdInstance.getNsdInfo().getNsdId()));
		final Set<NsdPackageVnfPackage> nsVnfPackages = nsdPackage.getVnfPkgIds();
		return nsVnfNotifications.stream()
				.flatMap(x -> x.getVnfIndiValueChangeNotifications().stream())
				.map(vnfIndiValueChangeNotification -> {
					final NsdPackageVnfPackage vnfPackages = nsVnfPackages.stream().filter(x -> x.getVnfdId().equals(vnfIndiValueChangeNotification.getVnfdId())).findFirst().orElseThrow();
					final String vnfInstanceName = vnfPackages.getToscaName();
					final String vnfIndicatorId = vnfIndiValueChangeNotification.getVnfIndicatorId();
					LOG.info("NS vnf notification vnf indicator id : {}_{}", vnfInstanceName, vnfIndicatorId);
					vnfIndiValueChangeNotification.setVnfIndicatorId(vnfInstanceName + "_" + vnfIndicatorId);
					return vnfIndiValueChangeNotification;
				})
				.toList();
	}

	public void evaluateValueBasedOnCondition(final UUID nsInstanceId, final UUID vnfInstanceId, final List<VnfIndiValueChangeNotification> notifications) {
		if (nsInstanceId != null) {
			for (final NsVnfIndicator nsVnfIndicator : getNSIndicators(nsInstanceId)) {
				final Map<String, TriggerDefinition> nsTriggers = nsVnfIndicator.getTriggers();
				LOG.info("NS Trigger evaluating");
				evaluateTriggers(nsTriggers, nsInstanceId, null, null, notifications);
			}
		} else {
			final String vnfdId = notifications.getFirst().getVnfdId();
			final VnfPackage vnfPackage = vnfPackageServiceImpl.findByVnfdId(vnfdId);
			final Set<VnfIndicator> vnfIndicators = vnfPackage.getVnfIndicator();
			for (final VnfIndicator vnfIndicator : vnfIndicators) {
				final Map<String, TriggerDefinition> vnfTriggers = vnfIndicator.getTriggers();
				LOG.info("VNF Trigger evaluating");
				evaluateTriggers(vnfTriggers, null, vnfInstanceId, vnfPackage, notifications);
			}
		}
	}

	public void evaluateTriggers(final Map<String, TriggerDefinition> triggers, final UUID nsInstanceId, final UUID vnfInstanceId, final VnfPackage vnfPackage, final List<VnfIndiValueChangeNotification> notifications) {
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
			// XXX: Use the new evaluator.
			final Context context = null;
			conditionService.evaluate(root, context);
			conditions: for (final JsonNode jsonNode : actualObj) {
				final Map<String, Object> condition = mapper.convertValue(jsonNode,
                        new TypeReference<>() {
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
						vnfIndicatorValue = Double.parseDouble(vnfIndiValueChangeNotification.getValue());
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
					if (!UowUtils.isVnfLcmRunning(vnfInstanceId, vnfLcmOpOccsService::findByVnfInstanceId, server)) {
						LOG.info("calling VNF Action");
						callAction(vnfInstanceId, trigger.getAction(), server);
					}
				}
				if ((nsInstanceId != null) && !UowUtils.isNSLcmRunning(nsInstanceId, nsBlueprintJpa)) {
					LOG.info("calling NS Action");
					callAction(nsInstanceId, trigger.getAction(), null);
				}

			}
		}
	}

	public void callAction(final UUID instanceId, final String actionParameters, final Servers server) {
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
                    new TypeReference<>() {
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
        switch (operationName) {
            case "Vnflcm.scale" -> vnfLcmInterface.vnfLcmScaleAction(instanceId, server, inputs);
            case "Vnflcm.heal" -> vnfLcmInterface.vnfLcmHealAction(instanceId, server, inputs);
            case "Nslcm.scale" -> nsLcmInterface.nsLcmScaleAction(instanceId, inputs);
            case null, default -> LOG.error("operation name not valid");
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
