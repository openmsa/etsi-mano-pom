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
import com.ubiqube.etsi.mano.jpa.VnfIndiValueChangeNotificationJpa;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.mon.MonGenericException;
import com.ubiqube.etsi.mano.service.VnfPackageServiceImpl;


@Component
public class VnfIndicatorValueChangeNotificationImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(VnfIndicatorValueChangeNotificationImpl.class);
	
	private final VnfIndiValueChangeNotificationJpa vnfIndValueNotificationJpa;
	
	private final VnfPackageServiceImpl	vnfPackageServiceImpl;
	
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
			throw new MonGenericException(e);
		}
	}

	@Scheduled(fixedRate = 60_000)
	public void run() {
		final Iterable<VnfIndiValueChangeNotification> ite = vnfIndValueNotificationJpa.findAll();
		Set<VnfIndiValueChangeNotification> result = 
				  StreamSupport.stream(ite.spliterator(), false)
				    .collect(Collectors.toSet());
		Map<String, List<VnfIndiValueChangeNotification>> notificationsByInstanceId = result.stream()
				  .collect(Collectors.groupingBy(VnfIndiValueChangeNotification::getVnfInstanceId));
		LOG.trace("Polling notification");
		for (Map.Entry<String, List<VnfIndiValueChangeNotification>> entry : notificationsByInstanceId.entrySet()) {
			evaluateValueBasedOnCondition(entry.getKey(), entry.getValue());
		}
	}
	
	public void evaluateValueBasedOnCondition(String vnfInstanceId, List<VnfIndiValueChangeNotification> notifications) {
		final String vnfdId = notifications.get(0).getVnfdId();
		VnfPackage vnfPackage = vnfPackageServiceImpl.findByVnfdId(UUID.fromString(vnfdId));
		Set<VnfIndicator> vnfIndicators = vnfPackage.getVnfIndicator();
		for (VnfIndicator vnfIndicator : vnfIndicators) {
			Map<String, TriggerDefinition> triggers = vnfIndicator.getTriggers();
			for (TriggerDefinition trigger : triggers.values()) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode actualObj = null;
				boolean thresholdCrossed = false;
				try {
					actualObj = mapper.readTree(trigger.getCondition());
				} catch (JsonProcessingException e) {
					LOG.error("error parsing condition", e);
					break;
				}
				conditions:
				for (JsonNode jsonNode : actualObj) {
					Map<String, Object> condition = mapper.convertValue(jsonNode,
							new TypeReference<Map<String, Object>>() {
							});
					Map.Entry<String, Object> c = condition.entrySet().iterator().next();
						String indicatorName = c.getKey();
						Double vnfIndicatorValue;
						Set<VnfIndiValueChangeNotification> not = notifications.stream().filter(x -> x.getVnfIndicatorId().equals(indicatorName)).collect(Collectors.toSet());
						if(not.isEmpty()) break conditions;
						VnfIndiValueChangeNotification vnfIndiValueChangeNotification = not.iterator().next();
						try {
							vnfIndicatorValue = Double.valueOf(vnfIndiValueChangeNotification.getValue());
						} catch (NumberFormatException e) {
							LOG.error("error parsing threshold value", e);
							break;
						}
						List<Map<String, Object>> conList = (List<Map<String, Object>>) c.getValue();
						for (Map<String, Object> m : conList) {
							for (Map.Entry<String, Object> r : m.entrySet()) {
								String conditionOperator = r.getKey();
								Double conditionValue;
								final String prop = props.getProperty(indicatorName +"_"+r.getValue().toString());
								if (prop != null) {
									conditionValue = Double.valueOf(prop);
								}
								else {
									try {
										conditionValue = Double.valueOf(r.getValue().toString());
									} catch (NumberFormatException e) {
										LOG.error("error parsing threshold value", e);
										break;
									}
								}
								switch (conditionOperator) {
								case "greater_or_equal":
									thresholdCrossed = (vnfIndicatorValue >= conditionValue);
									break;
								case "less_than":
									thresholdCrossed = (vnfIndicatorValue < conditionValue);
									break;
								case "greater_than":
									thresholdCrossed = (vnfIndicatorValue > conditionValue);
									break;
								case "less_or_equal":
									thresholdCrossed = (vnfIndicatorValue <= conditionValue);
									break;
								case "equal":
									thresholdCrossed = (vnfIndicatorValue == conditionValue);
									break;
								default:
									thresholdCrossed = false;
								}
							}
							if(!thresholdCrossed) {
								break conditions;
							}
						}
				}
				if(thresholdCrossed) {
					LOG.info("Launching action");
					callAction(vnfInstanceId, trigger.getAction());
				}
			}
		}
		vnfIndValueNotificationJpa.deleteAll(notifications);
	}
	
	public void callAction(String vnfInstanceId, String actionParameters) {
		//TODO
	}
}
