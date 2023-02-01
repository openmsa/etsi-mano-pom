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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

import jakarta.annotation.Priority;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(200)
@Service
public class MonitoringContributor extends AbstractContributorV3Base<MonitoringTask> {

	public MonitoringContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<MonitoringTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final List<SclableResources<MonitoringTask>> ret = new ArrayList<>();
		bundle.getVnfCompute().stream()
				.forEach(x -> x.getMonitoringParameters().stream().forEach(y -> {
					final MonitoringTask t = createMonitoringTask(y);
					ret.add(create(Monitoring.class, x.getToscaName(), 1, t, parameters.getInstance()));
				}));
		bundle.getVnfIndicator().stream()
				.forEach(x -> x.getMonitoringParameters().stream().forEach(y -> {
					final MonitoringTask t = createMonitoringTask(y);
					t.setVnfInstance(parameters.getInstance());
					final String toscaName = parameters.getInstance().getId().toString() + "-vnf_indicator";
					ret.add(create(Monitoring.class, toscaName, 1, t, parameters.getInstance()));
				}));
		return ret;
	}

	private static MonitoringTask createMonitoringTask(final MonitoringParams param) {
		final MonitoringTask task = new MonitoringTask();
		task.setType(ResourceTypeEnum.MONITORING);
		task.setToscaName(param.getName());
		// task.setParentAlias(param.getVnfComputeName());
		task.setMonitoringParams(param);
		// task.setVnfCompute(param.getVnfCompute());
		return task;
	}

}
