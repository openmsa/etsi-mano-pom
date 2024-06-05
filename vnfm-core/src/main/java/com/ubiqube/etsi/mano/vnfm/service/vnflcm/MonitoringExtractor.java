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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfMonitoringParameter;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;

@Service
public class MonitoringExtractor implements VnfLcmExtractor {

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream()
				.filter(x -> x.getTask() instanceof MonitoringTask)
				.toList();
		final Set<VnfMonitoringParameter> obj = vli.stream()
				.map(x -> {
					final MonitoringTask mt = (MonitoringTask) x.getTask();
					return createMonitoring(x, mt);
				}).collect(Collectors.toSet());
		params.setVnfMonitoringParameter(obj);
	}

	private static VnfMonitoringParameter createMonitoring(final VnfLiveInstance vli, final MonitoringTask mt) {
		final VnfMonitoringParameter mon = new VnfMonitoringParameter();
		mon.setId(vli.getId());
		mon.setName(mt.getMonitoringParams().getName());
		mon.setPerformanceMetric(mt.getMonitoringParams().getPerformanceMetric());
		return mon;
	}

}
