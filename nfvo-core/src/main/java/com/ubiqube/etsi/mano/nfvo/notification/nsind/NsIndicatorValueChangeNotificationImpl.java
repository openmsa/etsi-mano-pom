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
package com.ubiqube.etsi.mano.nfvo.notification.nsind;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
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
import com.ubiqube.etsi.mano.dao.mano.NsVnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.ind.NsIndiValueChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsByStepsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleType;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScalingDirectionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.controller.nslcm.NsInstanceControllerService;

@Component
public class NsIndicatorValueChangeNotificationImpl {
	private static final Logger LOG = LoggerFactory.getLogger(NsIndicatorValueChangeNotificationImpl.class);

	private final NsIndiValueChangeNotificationJpa nsIndValueNotificationJpa;

	private final NsdPackageJpa nsdPackageJpa;

	private final NsInstanceControllerService nsInstanceControllerService;

	private Properties props;

	public NsIndicatorValueChangeNotificationImpl(final NsIndiValueChangeNotificationJpa nsIndValueNotificationJpa, final NsdPackageJpa nsdPackageJpa, final NsInstanceControllerService nsInstanceControllerService) {
		this.nsIndValueNotificationJpa = nsIndValueNotificationJpa;
		this.nsdPackageJpa = nsdPackageJpa;
		this.nsInstanceControllerService = nsInstanceControllerService;
		try (InputStream mappting = this.getClass().getClassLoader().getResourceAsStream("gnocchi-mapping.properties")) {
			props = new Properties();
			props.load(mappting);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	@Scheduled(fixedRate = 60_000)
	public void run() {
		final Iterable<NsIndiValueChangeNotification> ite = nsIndValueNotificationJpa.findAll();
		final Set<NsIndiValueChangeNotification> result = StreamSupport.stream(ite.spliterator(), false)
				.collect(Collectors.toSet());
		final Map<String, List<NsIndiValueChangeNotification>> notificationsByInstanceId = result.stream()
				.collect(Collectors.groupingBy(NsIndiValueChangeNotification::getNsInstanceId));
		LOG.trace("Polling notification");
		for (final Map.Entry<String, List<NsIndiValueChangeNotification>> entry : notificationsByInstanceId.entrySet()) {
			evaluateValueBasedOnCondition(entry.getKey(), entry.getValue());
		}
	}

	public void evaluateValueBasedOnCondition(final String nsInstanceId, final List<NsIndiValueChangeNotification> notifications) {
		nsIndValueNotificationJpa.deleteAll(notifications);
		final String nsdId = notifications.getFirst().getNsdId();
		final NsdPackage nsdPackage = nsdPackageJpa.findByNsdIdAndNsdOnboardingState(nsdId, OnboardingStateType.ONBOARDED);
		final Set<NsVnfIndicator> nsVnfIndicators = nsdPackage.getNsVnfIndicator();
		for (final NsVnfIndicator nsVnfIndicator : nsVnfIndicators) {
			final Map<String, TriggerDefinition> triggers = nsVnfIndicator.getTriggers();
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
                            new TypeReference<>() {
                                //
                            });
					final Map.Entry<String, Object> c = condition.entrySet().iterator().next();
					final String indicatorName = c.getKey();
					Double nsVnfIndicatorValue;
					final Set<NsIndiValueChangeNotification> not = notifications.stream().filter(x -> x.getNsIndicatorId().equals(indicatorName)).collect(Collectors.toSet());
					if (not.isEmpty()) {
						break conditions;
					}
					final NsIndiValueChangeNotification nsIndiValueChangeNotification = not.iterator().next();
					try {
						nsVnfIndicatorValue = Double.valueOf(nsIndiValueChangeNotification.getValue());
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
							case "greater_or_equal" -> (nsVnfIndicatorValue >= conditionValue);
							case "less_than" -> (nsVnfIndicatorValue < conditionValue);
							case "greater_than" -> (nsVnfIndicatorValue > conditionValue);
							case "less_or_equal" -> (nsVnfIndicatorValue <= conditionValue);
							case "equal" -> (nsVnfIndicatorValue.equals(conditionValue));
							default -> false;
							};
						}
						if (!thresholdCrossed) {
							break conditions;
						}
					}
				}
				if (thresholdCrossed) {
					LOG.info("Launching NSD action");
					callAction(nsInstanceId, trigger.getAction());
				}
			}
		}
	}

	public void callAction(final String nsInstanceId, final String actionParameters) {
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
		if ("Nslcm.scale".equals(operationName)) {
			final NsScale nsInst = createScaleRequest(inputs);
			LOG.info("NS Scale {} : {} launched", nsInstanceId, nsInst);
			nsInstanceControllerService.scale(UUID.fromString(nsInstanceId), nsInst);
		} else {
			LOG.error("operation name not valid");
		}

	}

	private static NsScale createScaleRequest(final Map<String, Object> inputs) {
		final NsScale nsInst = new NsScale();
		nsInst.setScaleType(ScaleType.NS);
		final ScaleNsByStepsData snbsd = new ScaleNsByStepsData();
		for (final Map.Entry<String, Object> c : inputs.entrySet()) {
			final Map<String, String> d = (Map<String, String>) c.getValue();
			final Object value = d.entrySet().iterator().next().getValue();
			switch (c.getKey()) {
			case "type":
				if ("scale_out".equals(value)) {
					snbsd.setScalingDirection(ScalingDirectionType.OUT);
				}
				if ("scale_in".equals(value)) {
					snbsd.setScalingDirection(ScalingDirectionType.IN);
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
		final ScaleNsData snd = new ScaleNsData();
		snd.setScaleNsByStepsData(snbsd);
		nsInst.setScaleNsData(snd);
		return nsInst;
	}
}
