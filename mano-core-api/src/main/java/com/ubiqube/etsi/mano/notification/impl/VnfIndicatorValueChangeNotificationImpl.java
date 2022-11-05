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
package com.ubiqube.etsi.mano.notification.impl;

import java.io.IOException;
import java.io.InputStream;
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
import com.ubiqube.etsi.mano.dao.mano.TriggerDefinition;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.VnfIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfPackageServiceImpl;

@Component
public class VnfIndicatorValueChangeNotificationImpl {

	private static final Logger LOG = LoggerFactory.getLogger(VnfIndicatorValueChangeNotificationImpl.class);

	private final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa;

	private final VnfPackageServiceImpl vnfPackageServiceImpl;

	private final VnfInstanceJpa vnfInstanceJpa;

	private Properties props;

	public VnfIndicatorValueChangeNotificationImpl(final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa, final VnfPackageServiceImpl vnfPackageServiceImpl, final VnfInstanceJpa vnfInstanceJpa) {
		this.vnfIndValueNotificationJpa = vnfIndValueNotificationJpa;
		this.vnfPackageServiceImpl = vnfPackageServiceImpl;
		this.vnfInstanceJpa = vnfInstanceJpa;
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
					callAction(vnfInstanceId, trigger.getAction());
				}
			}
		}
		vnfIndValueNotificationJpa.deleteAll(notifications);
	}

	public void callAction(final String vnfInstanceId, final String actionParameters) {
		// TODO
	}
}
