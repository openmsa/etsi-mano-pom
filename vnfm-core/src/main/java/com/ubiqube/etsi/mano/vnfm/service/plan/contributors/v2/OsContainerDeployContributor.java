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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 * This one is in fact an Host with K8S+helm installed
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OsContainerDeployContributor extends AbstractContributorV3Base<OsContainerDeployableTask> {

	public OsContainerDeployContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
	}

	private static OsContainerDeployableTask createTask(final VnfBlueprint blueprint, final OsContainerDeployableUnit x) {
		final OsContainerDeployableTask t = createTask(OsContainerDeployableTask::new);
		t.setBlueprint(blueprint);
		t.setOsContainerDeployableUnit(x);
		t.setToscaName(x.getName());
		t.setType(ResourceTypeEnum.OS_CONTAINER_DEPLOYABLE);
		return t;
	}

	@Override
	public List<SclableResources<OsContainerDeployableTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final List<SclableResources<OsContainerDeployableTask>> ret = new ArrayList<>();
		bundle.getOsContainerDeployableUnits().forEach(x -> {
			final OsContainerDeployableTask t = createTask(parameters, x);
			ret.add(create(OsContainerDeployableNode.class, t.getToscaName(), 1, t, parameters.getInstance()));
		});
		return ret;
	}

}
