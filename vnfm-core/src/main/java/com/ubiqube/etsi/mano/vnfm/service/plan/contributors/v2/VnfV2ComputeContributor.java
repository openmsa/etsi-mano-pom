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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

import jakarta.annotation.Priority;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(100)
@Service
public class VnfV2ComputeContributor extends AbstractContributorV3Base<ComputeTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfV2ComputeContributor.class);

	private final ScalingStrategy scalingStrategy;

	public VnfV2ComputeContributor(final ScalingStrategy scalingStrategy,
			final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
		this.scalingStrategy = scalingStrategy;
	}

	private static Set<ScaleInfo> merge(final VnfBlueprint plan, final Instance vnfInstance) {
		final Set<ScaleInfo> tmp = vnfInstance.getInstantiatedVnfInfo().getScaleStatus().stream().filter(x -> notIn(x.getAspectId(), plan.getParameters().getScaleStatus())).map(x -> new ScaleInfo(x.getAspectId(), x.getScaleLevel())).collect(Collectors.toSet());
		tmp.addAll(plan.getParameters().getScaleStatus());
		return tmp;
	}

	private static boolean notIn(final String aspectId, final Set<? extends ScaleInfo> scaleInfos) {
		return scaleInfos.stream().noneMatch(x -> x.getAspectId().equals(aspectId));
	}

	@Override
	public List<SclableResources<ComputeTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final Set<ScaleInfo> scaling = merge(parameters, parameters.getInstance());
		return bundle.getVnfCompute().stream().map(x -> {
			final NumberOfCompute numInst = scalingStrategy.getNumberOfCompute(parameters, bundle, scaling, x, parameters.getVnfInstance());
			LOG.debug("{} -> {}", x.getToscaName(), numInst);
			final ComputeTask computeTask = createTask(ComputeTask::new);
			computeTask.setVnfCompute(x);
			computeTask.setType(ResourceTypeEnum.COMPUTE);
			computeTask.setScaleInfo(numInst.getScaleInfo());
			computeTask.setToscaName(x.getToscaName());
			return create(Compute.class, computeTask.getToscaName(), numInst.getWanted(), computeTask, parameters.getInstance());
		}).toList();
	}
}
