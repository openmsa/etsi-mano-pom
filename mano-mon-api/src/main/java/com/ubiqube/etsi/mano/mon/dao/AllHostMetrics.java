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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.mon.dao;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllHostMetrics {

	private List<TelemetryMetricsResult> telemetryMetricsResult;

	private String metricName;

	private UUID vnfInstanceId;

	private UUID masterJobId;

	public AllHostMetrics(final List<TelemetryMetricsResult> telemetryMetricsResult, final String metricName, final UUID vnfInstanceId, final UUID masterJobId) {
		this.telemetryMetricsResult = telemetryMetricsResult;
		this.metricName = metricName;
		this.vnfInstanceId = vnfInstanceId;
		this.masterJobId = masterJobId;
	}
}
