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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;

/**
 *
 * @author olivier
 *
 */
@Service
public class NsdNsdContributorV3 extends AbstractNsdContributorV3<Object> {
	private final NsScaleStrategyV3 nsScaleStrategy;

	protected NsdNsdContributorV3(final NsLiveInstanceJpa nsLiveInstanceJpa, final NsScaleStrategyV3 nsScaleStrategy) {
		super(nsLiveInstanceJpa);
		this.nsScaleStrategy = nsScaleStrategy;
	}

	@Override
	public List<SclableResources<Object>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
        final List<SclableResources<Object>> ret = new ArrayList<>();
		bundle.getNestedNsdInfoIds().forEach(x -> {
			final NsdTask task = createVnfTask(x);
			final int numInst = nsScaleStrategy.getNumberOfInstances(x, parameters);
			ret.add(create(VnfCreateNode.class, task.getClass(), x.getToscaName(), numInst, task, parameters.getInstance(), parameters));
			final NsdInstantiateTask taskInst = createVnfInstantiateTask(x);
			ret.add(create(VnfInstantiateNode.class, taskInst.getClass(), x.getToscaName(), numInst, taskInst, parameters.getInstance(), parameters));
			final NsdExtractorTask taskExt = createVnfExtractTask(x);
			ret.add(create(VnfExtractorNode.class, taskExt.getClass(), x.getToscaName(), numInst, taskExt, parameters.getInstance(), parameters));
		});
		return ret;
	}

	private static NsdExtractorTask createVnfExtractTask(final NsdPackageNsdPackage x) {
		final NsdExtractorTask task = createTask(NsdExtractorTask::new);
		task.setType(ResourceTypeEnum.NSD_EXTRACTOR);
		task.setToscaName(x.getToscaName());
		return task;
	}

	private static NsdInstantiateTask createVnfInstantiateTask(final NsdPackageNsdPackage x) {
		final NsdInstantiateTask task = createTask(NsdInstantiateTask::new);
		task.setType(ResourceTypeEnum.NSD_INSTANTIATE);
		task.setToscaName(x.getToscaName());
		return task;
	}

	private static NsdTask createVnfTask(final NsdPackageNsdPackage x) {
		final NsdTask task = createTask(NsdTask::new);
		task.setType(ResourceTypeEnum.NSD_CREATE);
		task.setToscaName(x.getToscaName());
		task.setNsdParam(x);
		return task;
	}

}
