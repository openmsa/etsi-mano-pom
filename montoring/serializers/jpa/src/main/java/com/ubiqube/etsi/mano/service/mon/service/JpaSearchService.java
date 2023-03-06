/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.mon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.api.SearchApi;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.jms.MonitoringDataMapping;
import com.ubiqube.etsi.mano.service.mon.model.MonitoringData;
import com.ubiqube.etsi.mano.service.mon.repository.MonitoringDataJpa;

@Service
public class JpaSearchService implements SearchApi {

	private final MonitoringDataJpa monitoringDataJpa;
	private final MonitoringDataMapping mapper;

	public JpaSearchService(final MonitoringDataJpa monitoringDataJpa) {
		this.monitoringDataJpa = monitoringDataJpa;
		this.mapper = MonitoringDataMapping.INSTANCE;
	}

	@Override
	public TelemetryMetricsResult search(final String instance, final String object) {
		final List<MonitoringData> ress = monitoringDataJpa.getLastMetrics(instance, object);
		final MonitoringData res = ress.get(0);
		return mapper.fromDto(res);
	}
}
