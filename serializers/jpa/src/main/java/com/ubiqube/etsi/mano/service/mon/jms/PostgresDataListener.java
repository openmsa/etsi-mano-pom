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
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.PmType;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.mon.dao.AllHostMetrics;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorMonitoringData;
import com.ubiqube.etsi.mano.service.mon.model.VnfIndicatorValue;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;
import com.ubiqube.etsi.mano.service.mon.repository.VnfIndicatorMonitoringDataJpa;
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

	private final VnfIndicatorMonitoringDataJpa vnfIndicatorMonitoringDataJpa;

	private final EventManager eventManager;

	private final VnfInstanceJpa vnfInstanceJpa;

	public PostgresDataListener(final MonitoringDataJpa monitoringDataJpa, final VnfIndicatorValueJpa vnfIndicatorValueJpa, final VnfInstanceJpa vnfInstanceJpa, final VnfIndicatorMonitoringDataJpa vnfIndicatorMonitoringDataJpa, final EventManager eventManager) {
		this.monitoringDataJpa = monitoringDataJpa;
		this.vnfIndicatorValueJpa = vnfIndicatorValueJpa;
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.vnfIndicatorMonitoringDataJpa = vnfIndicatorMonitoringDataJpa;
		this.eventManager = eventManager;
	}

	@JmsListener(destination = "mano.monitoring.gnocchi.data", subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "gnocchiDataFactory")
	public void onGnocchiData(final AllHostMetrics allHostMetrics) {
		if ((allHostMetrics == null) || (allHostMetrics.getTelemetryMetricsResult() == null)) {
			return;
		}
		if (PmType.VNF.equals(allHostMetrics.getPmType())) {
			Double averageValueByPercent = 0.0;
			Double totalValue = 0.0;
			final VnfInstance vnfInstance = vnfInstanceJpa.findById(allHostMetrics.getVnfInstanceId()).orElseThrow();
			final Set<VnfCompute> computes = vnfInstance.getVnfPkg().getVnfCompute();
			if ("cpu".equals(allHostMetrics.getTelemetryMetricsResult().get(0).getKey())) {
				Long noOfVirtualCpus = 0L;
				Boolean isMetricsUpdated = false;
				for (final VnfCompute compute : computes) {
					noOfVirtualCpus = noOfVirtualCpus + compute.getVirtualCpu().getNumVirtualCpu();
				}
				for (final TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
					LOG.trace("Postgresql-Receive: {}", action);
					final VnfIndicatorMonitoringData data = vnfIndicatorMonitoringDataJpa.findByKeyAndVnfcId(allHostMetrics.getMetricName(), UUID.fromString(action.getVnfcId()));
					if (data != null) {
						final Double deltaCpuUsage = action.getValue() - data.getValue();
						if (deltaCpuUsage != 0.0) {
							isMetricsUpdated = true;
						} else {
							isMetricsUpdated = false;
						}
						final Double usageInSeconds = deltaCpuUsage / 1000000000L;
						totalValue = totalValue + usageInSeconds;
					}
					final VnfIndicatorMonitoringData data2 = new VnfIndicatorMonitoringData(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), action.getValue(), UUID.fromString(action.getVnfcId()));
					vnfIndicatorMonitoringDataJpa.save(data2);
				}
				if (!isMetricsUpdated) {
					return;
				}
				averageValueByPercent = (totalValue / (noOfVirtualCpus * 600)) * 100;
			} else if ("memory.usage".equals(allHostMetrics.getTelemetryMetricsResult().get(0).getKey())) {
				Long totalMemorySize = 0L;
				for (final VnfCompute compute : computes) {
					totalMemorySize = totalMemorySize + compute.getVirtualMemory().getVirtualMemSize();
				}
				for (final TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
					LOG.trace("Postgresql-Receive: {}", action);
					final Double bytesValue = action.getValue() * 1000000;
					totalValue = totalValue + bytesValue;
				}
				final Double averageValue = totalValue / allHostMetrics.getTelemetryMetricsResult().size();
				if (totalMemorySize != 0) {
					averageValueByPercent = (averageValue / totalMemorySize) * 100;
				}
			} else {
				averageValueByPercent = 0.0;
			}

			final VnfIndicatorValue existingVnfIndicatorValue = vnfIndicatorValueJpa.findByKeyAndVnfInstanceId(allHostMetrics.getMetricName(), allHostMetrics.getVnfInstanceId());
			if ((existingVnfIndicatorValue != null) && !existingVnfIndicatorValue.getValue().equals(averageValueByPercent)) {
				// vnf indicator value change notification should be sent.
				LOG.info("{} indicator value changed to {}", allHostMetrics.getVnfInstanceId() + ":" + allHostMetrics.getMetricName(), averageValueByPercent);

				eventManager.sendNotification(NotificationEvent.VNF_INDICATOR_VALUE_CHANGED, allHostMetrics.getVnfInstanceId(), Map.of("vnfIndicatorId", allHostMetrics.getMetricName(), "value", String.valueOf(averageValueByPercent), "vnfInstanceId", allHostMetrics.getVnfInstanceId().toString(), "vnfdId", vnfInstance.getVnfdId()));
			}
			final VnfIndicatorValue vnfIndValue = new VnfIndicatorValue(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), averageValueByPercent, allHostMetrics.getVnfInstanceId());
			vnfIndicatorValueJpa.save(vnfIndValue);
			return;
		}
		for (final TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
			LOG.info("Postgresql-Receive: {}", action);
			final MonitoringData entity = new MonitoringData(action.getKey(), action.getMasterJobId(), action.getTimestamp(), action.getValue(), action.getVnfcId(), action.isStatus());
			monitoringDataJpa.save(entity);
		}
	}

}
