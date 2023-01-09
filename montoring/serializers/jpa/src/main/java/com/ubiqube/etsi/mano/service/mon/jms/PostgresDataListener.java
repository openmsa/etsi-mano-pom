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

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCpu;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualMemory;
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
	private static final String POSTGRESQL_RECEIVE = "Postgresql-Receive: {}";

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

	@JmsListener(destination = "${spring.application.name:none}.mano.monitoring.gnocchi.data", subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "gnocchiDataFactory")
	public void onGnocchiData(final AllHostMetrics allHostMetrics) {
		if ((allHostMetrics == null) || (allHostMetrics.getTelemetryMetricsResult() == null) || allHostMetrics.getTelemetryMetricsResult().isEmpty()) {
			return;
		}
		if (PmType.VNF.equals(allHostMetrics.getPmType())) {
			doVnfIndicator(allHostMetrics);
			return;
		}
		for (final TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
			LOG.info(POSTGRESQL_RECEIVE, action);
			final MonitoringData entity = new MonitoringData(action.getKey(), action.getMasterJobId(), action.getTimestamp(), action.getValue(), action.getVnfcId(), action.isStatus());
			monitoringDataJpa.save(entity);
		}
	}

	private void doVnfIndicator(final AllHostMetrics allHostMetrics) {
		MetricValue mv;
		final VnfInstance vnfInstance = vnfInstanceJpa.findById(allHostMetrics.getVnfInstanceId()).orElseThrow();
		final Set<VnfCompute> computes = vnfInstance.getVnfPkg().getVnfCompute();
		final VnfIndicatorValue existingVnfIndicatorValue = vnfIndicatorValueJpa.findByKeyAndVnfInstanceId(allHostMetrics.getMetricName(), allHostMetrics.getVnfInstanceId());
		if ("cpu".equals(allHostMetrics.getTelemetryMetricsResult().get(0).getKey())) {
			OffsetDateTime metricsUpdatedTime = OffsetDateTime.now();
			double totalValue = 0.0;
			boolean isMetricsUpdated = false;
			long deltaSeconds = 0;
			final long noOfVirtualCpus = computes.stream()
					.map(VnfCompute::getVirtualCpu)
					.mapToLong(VirtualCpu::getNumVirtualCpu)
					.sum();
			for (final TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
				if (action.getValue() == 0.0) {
					continue;
				}
				LOG.trace(POSTGRESQL_RECEIVE, action);
				final VnfIndicatorMonitoringData data = vnfIndicatorMonitoringDataJpa.findByKeyAndVnfcId(allHostMetrics.getMetricName(), UUID.fromString(action.getVnfcId()));
				if ((data != null) && (existingVnfIndicatorValue != null)) {
					final double deltaCpuUsage = action.getValue() - data.getValue();
					if (deltaCpuUsage != 0.0) {
						isMetricsUpdated = true;
						metricsUpdatedTime = action.getTimestamp();
						final Duration duration = Duration.between(existingVnfIndicatorValue.getTime(), action.getTimestamp());
						deltaSeconds = duration.getSeconds();
					} else {
						isMetricsUpdated = false;
					}
					final double usageInSeconds = deltaCpuUsage / 1_000_000_000L;
					totalValue = totalValue + usageInSeconds;
				} else {
					isMetricsUpdated = true;
					final double deltaCpuUsage = action.getValue();
					metricsUpdatedTime = action.getTimestamp().truncatedTo(ChronoUnit.MINUTES);
					deltaSeconds = 600;
					final double usageInSeconds = deltaCpuUsage / 1_000_000_000L;
					totalValue = totalValue + usageInSeconds;
				}
				final VnfIndicatorMonitoringData data2 = new VnfIndicatorMonitoringData(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), action.getValue(), UUID.fromString(action.getVnfcId()));
				vnfIndicatorMonitoringDataJpa.save(data2);
			}
			if (!isMetricsUpdated || (totalValue == 0.0) || (noOfVirtualCpus == 0) || (deltaSeconds == 0)) {
				return;
			}
			final double averageValueByPercent = (totalValue / (noOfVirtualCpus * deltaSeconds)) * 100;
			mv = new MetricValue(averageValueByPercent, metricsUpdatedTime);
		} else if ("memory.usage".equals(allHostMetrics.getTelemetryMetricsResult().get(0).getKey())) {
			double averageValueByPercent;
			final OffsetDateTime metricsUpdatedTime = OffsetDateTime.now();
			final long totalMemorySize = computes.stream()
					.map(VnfCompute::getVirtualMemory)
					.mapToLong(VirtualMemory::getVirtualMemSize)
					.sum();
			final double totalValue = allHostMetrics.getTelemetryMetricsResult().stream()
					.mapToDouble(TelemetryMetricsResult::getValue)
					.filter(x -> x != 0.0d)
					.map(this::toMiB)
					.sum();
			if ((totalMemorySize == 0) || (totalValue == 0)) {
				return;
			}
			averageValueByPercent = (totalValue / totalMemorySize) * 100;
			mv = new MetricValue(averageValueByPercent, metricsUpdatedTime);
		} else {
			mv = new MetricValue(0.0, OffsetDateTime.now());
		}
		eventIfNeeded(allHostMetrics, mv, vnfInstance, existingVnfIndicatorValue);
		storeMetric(allHostMetrics, mv);
	}

	private void storeMetric(final AllHostMetrics allHostMetrics, final MetricValue mv) {
		final VnfIndicatorValue vnfIndValue = new VnfIndicatorValue(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), mv.metricsUpdatedTime(), mv.averageValueByPercent(), allHostMetrics.getVnfInstanceId());
		vnfIndicatorValueJpa.save(vnfIndValue);
	}

	private void eventIfNeeded(final AllHostMetrics allHostMetrics, final MetricValue mv, final VnfInstance vnfInstance, final VnfIndicatorValue existingVnfIndicatorValue) {
		if (metricHaveChanged(mv, existingVnfIndicatorValue)) {
			// vnf indicator value change notification should be sent.
			LOG.info("{}:{} indicator value changed to {}", allHostMetrics.getVnfInstanceId(), allHostMetrics.getMetricName(), mv.averageValueByPercent());
			eventManager.sendNotification(NotificationEvent.VNF_INDICATOR_VALUE_CHANGED, allHostMetrics.getVnfInstanceId(), Map.of("vnfIndicatorId", allHostMetrics.getMetricName(),
					"value", String.valueOf(mv.averageValueByPercent()),
					"vnfInstanceId", allHostMetrics.getVnfInstanceId().toString(),
					"vnfdId", vnfInstance.getVnfdId()));
		}
	}

	private boolean metricHaveChanged(final MetricValue mv, final VnfIndicatorValue existingVnfIndicatorValue) {
		return (existingVnfIndicatorValue != null) && !existingVnfIndicatorValue.getValue().equals(mv.averageValueByPercent());
	}

	private Double toMiB(final double x) {
		return x * 1_048_576; // Mib
	}

	record MetricValue(double averageValueByPercent, OffsetDateTime metricsUpdatedTime) {
		//
	}
}
