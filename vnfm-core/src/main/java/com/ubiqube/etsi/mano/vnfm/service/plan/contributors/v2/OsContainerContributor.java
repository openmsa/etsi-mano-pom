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

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.graph.VduNamingStrategy;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OsContainerContributor extends AbstractContributorV3Base<OsContainerTask> {
	private final VduNamingStrategy vduNamingStrategy;

	public OsContainerContributor(final VduNamingStrategy vduNamingStrategy, final VnfLiveInstanceJpa vnfLiveInstace) {
		super(vnfLiveInstace);
		this.vduNamingStrategy = vduNamingStrategy;
	}

	@Override
	public List<SclableResources<OsContainerTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final List<SclableResources<OsContainerTask>> ret = new ArrayList<>();
		bundle.getOsContainer().forEach(x -> {
			final OsContainerTask t = createTask(OsContainerTask::new);
			t.setAlias(vduNamingStrategy.osContainerName(parameters.getInstance(), x.getName()));
			t.setToscaName(x.getName());
			t.setBlueprint(parameters);
			t.setType(ResourceTypeEnum.CNF);
			t.setChangeType(ChangeType.ADDED);
			t.setOsContainer(x);
			ret.add(create(OsContainerNode.class, t.getToscaName(), 1, t, parameters.getInstance()));
		});
		return ret;
	}

}
