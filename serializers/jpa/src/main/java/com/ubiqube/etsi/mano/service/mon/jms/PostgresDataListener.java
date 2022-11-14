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
package com.ubiqube.etsi.mano.service.mon.jms;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.PmType;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.model.NotificationEvent;
import com.ubiqube.etsi.mano.mon.dao.AllHostMetrics;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorValue;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;
import com.ubiqube.etsi.mano.service.mon.repository.VnfIndicatorValueJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class PostgresDataListener {
	private static final Logger LOG = LoggerFactory.getLogger(PostgresDataListener.class);

	private final MonitoringDataJpa monitoringDataJpa;

	private final VnfIndicatorValueJpa vnfIndicatorValueJpa;
	
	private final EventManager eventManager;
	
	private VnfInstanceJpa vnfInstanceJpa;

	public PostgresDataListener(final MonitoringDataJpa monitoringDataJpa, final VnfIndicatorValueJpa vnfIndicatorValueJpa, final VnfInstanceJpa vnfInstanceJpa, final EventManager eventManager) {
		super();
		this.monitoringDataJpa = monitoringDataJpa;
		this.vnfIndicatorValueJpa = vnfIndicatorValueJpa;
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.eventManager = eventManager;
	}

	@JmsListener(destination = "mano.monitoring.gnocchi.data", subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "gnocchiDataFactory")
	public void onGnocchiData(final AllHostMetrics allHostMetrics) {
		if (allHostMetrics == null || allHostMetrics.getTelemetryMetricsResult() == null) {
			return;
		}
		if(allHostMetrics.getPmType().equals(PmType.VNF)) {
			Double averageValueByPercent = 0.0;
			Double totalValue = 0.0;
			final VnfInstance vnfInstance = vnfInstanceJpa.findById(allHostMetrics.getVnfInstanceId()).orElseThrow();
			Set<VnfCompute> computes = vnfInstance.getVnfPkg().getVnfCompute();
			if(allHostMetrics.getTelemetryMetricsResult().get(0).getKey().equals("cpu")) {
				Long noOfVirtualCpus = 0L;
				for(VnfCompute compute : computes) {
					noOfVirtualCpus = noOfVirtualCpus + compute.getVirtualCpu().getNumVirtualCpu();
				}
				for(TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
					LOG.info("Postgresql-Receive: {}", action);
					if(noOfVirtualCpus != 0) {
						Double usageInSeconds = action.getValue() / 10000000000L;
						totalValue = totalValue + usageInSeconds;
					}
				}
				averageValueByPercent = totalValue / (noOfVirtualCpus * 600);
			} else if(allHostMetrics.getTelemetryMetricsResult().get(0).getKey().equals("memory.usage")) {
				Long totalMemorySize = 0L;
				for(VnfCompute compute : computes) {
					totalMemorySize = totalMemorySize + compute.getVirtualMemory().getVirtualMemSize();
				}
				for(TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
					LOG.info("Postgresql-Receive: {}", action);
					Double bytesValue = action.getValue() * 1000000;
					totalValue = totalValue + bytesValue;
				}
				Double averageValue = totalValue / allHostMetrics.getTelemetryMetricsResult().size();
				if(totalMemorySize != 0) {
					averageValueByPercent = (averageValue / totalMemorySize) * 100;
				}
			} else {
				averageValueByPercent = 0.0;
			}
			
			VnfIndicatorValue existingVnfIndicatorValue = vnfIndicatorValueJpa.findByKeyAndVnfInstanceId(allHostMetrics.getMetricName(), allHostMetrics.getVnfInstanceId());
			if(existingVnfIndicatorValue != null && !existingVnfIndicatorValue.getValue().equals(averageValueByPercent)) {
					//vnf indicator value change notification should be sent.
				LOG.info("{} indicator value changed to {}", allHostMetrics.getVnfInstanceId()+":"+allHostMetrics.getMetricName(), averageValueByPercent);
				
				eventManager.sendNotification(NotificationEvent.VNF_INDICATOR_VALUE_CHANGED, allHostMetrics.getVnfInstanceId(), Map.of("vnfIndicatorId", allHostMetrics.getMetricName(), "value", String.valueOf(averageValueByPercent), "vnfInstanceId", allHostMetrics.getVnfInstanceId().toString(), "vnfdId", vnfInstance.getVnfdId()));
			}
			final VnfIndicatorValue vnfIndValue = new VnfIndicatorValue(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), averageValueByPercent, allHostMetrics.getVnfInstanceId());
			vnfIndicatorValueJpa.save(vnfIndValue);
			return;
		}
		for(TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
			LOG.info("Postgresql-Receive: {}", action);
			final MonitoringData entity = new MonitoringData(action.getKey(), action.getMasterJobId(), action.getTimestamp(), action.getValue(), action.getVnfcId(), action.isStatus());
			monitoringDataJpa.save(entity);
		}
	}
	
}
