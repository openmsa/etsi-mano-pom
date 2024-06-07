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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class NetworkContributor extends AbstractVnfmContributor<Object> {

	protected NetworkContributor(final VnfLiveInstanceJpa vnfInstanceJpa) {
		super(vnfInstanceJpa);
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
        final List<SclableResources<Object>> ret = new ArrayList<>();
		bundle.getVnfVl().stream().forEach(x -> {
			final NetworkTask networkTask = createTask(NetworkTask::new);
			networkTask.setToscaName(x.getToscaName());
			networkTask.setType(ResourceTypeEnum.VL);
			networkTask.setVnfVl(x);
			ret.add(create(Network.class, networkTask.getClass(), x.getToscaName(), 1, networkTask, parameters.getInstance(), parameters));
			x.getVlProfileEntity().getVirtualLinkProtocolData().stream().forEach(y -> y.getIpAllocationPools().forEach(z -> {
				final SubNetworkTask sn = createTask(SubNetworkTask::new);
				sn.setToscaName(x.getToscaName() + "-" + y.getL2ProtocolData().getName());
				sn.setType(ResourceTypeEnum.SUBNETWORK);
				sn.setParentName(x.getToscaName());
				sn.setL3Data(y.getL3ProtocolData());
				sn.setIpPool(z);
				ret.add(create(SubNetwork.class, sn.getClass(), sn.getToscaName(), 1, sn, parameters.getInstance(), parameters));
			}));
		});
		return ret;
	}
}
