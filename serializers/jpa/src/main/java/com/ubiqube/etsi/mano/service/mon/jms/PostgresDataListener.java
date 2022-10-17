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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.pm.PmType;
import com.ubiqube.etsi.mano.mon.dao.AllHostMetrics;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
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

	public PostgresDataListener(final MonitoringDataJpa monitoringDataJpa, final VnfIndicatorValueJpa vnfIndicatorValueJpa) {
		super();
		this.monitoringDataJpa = monitoringDataJpa;
		this.vnfIndicatorValueJpa = vnfIndicatorValueJpa;
	}

	@JmsListener(destination = "mano.monitoring.gnocchi.data", subscription = "mano.monitoring.gnocchi.data", concurrency = "1", containerFactory = "gnocchiDataFactory")
	public void onGnocchiData(final AllHostMetrics allHostMetrics) {
		if (allHostMetrics == null || allHostMetrics.getTelemetryMetricsResult() == null) {
			return;
		}
		if(allHostMetrics.getPmType().equals(PmType.VNF)) {
			Double averageValue;
			Double totalValue = 0.0;
			for(TelemetryMetricsResult action : allHostMetrics.getTelemetryMetricsResult()) {
				LOG.info("Postgresql-Receive: {}", action);
				totalValue = totalValue + action.getValue();
			}
			averageValue = totalValue / allHostMetrics.getTelemetryMetricsResult().size();
			VnfIndicatorValue existingVnfIndicatorValue = vnfIndicatorValueJpa.findByKeyAndVnfInstanceId(allHostMetrics.getMetricName(), allHostMetrics.getVnfInstanceId());
			if(existingVnfIndicatorValue != null && !existingVnfIndicatorValue.getValue().equals(averageValue)) {
					//vnf indicator value change notification should be sent.
				LOG.info("{} indicator value changed to {}", allHostMetrics.getVnfInstanceId()+":"+allHostMetrics.getMetricName(), averageValue);
			}
			final VnfIndicatorValue vnfIndValue = new VnfIndicatorValue(allHostMetrics.getMetricName(), allHostMetrics.getMasterJobId(), averageValue, allHostMetrics.getVnfInstanceId());
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
