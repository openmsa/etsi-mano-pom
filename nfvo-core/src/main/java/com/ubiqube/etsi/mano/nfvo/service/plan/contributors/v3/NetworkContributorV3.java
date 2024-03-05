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

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;

/**
 *
 * @author olivier
 *
 */
@Service
public class NetworkContributorV3 extends AbstractNsdContributorV3<NsVirtualLinkTask> {

	protected NetworkContributorV3(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		super(nsLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<NsVirtualLinkTask>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
		final NsdPackage nsd = bundle;
		return nsd.getNsVirtualLinks().stream().map(x -> {
			final NsVirtualLinkTask task = createTask(NsVirtualLinkTask::new);
			task.setType(ResourceTypeEnum.VL);
			task.setToscaName(x.getToscaName());
			task.setNsVirtualLink(x);
			return create(Network.class, task.getClass(), x.getToscaName(), 1, task, parameters.getInstance(), parameters);
		})
				.toList();
	}

}
