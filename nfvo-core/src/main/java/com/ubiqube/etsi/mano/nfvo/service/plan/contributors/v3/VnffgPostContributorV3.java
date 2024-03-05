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
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;

/**
 *
 * @author olivier
 *
 */
@Service
public class VnffgPostContributorV3 extends AbstractNsdContributorV3<VnffgPostTask> {

	protected VnffgPostContributorV3(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		super(nsLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<VnffgPostTask>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
		final NsdPackage nsd = bundle;
		return nsd.getVnffgs().stream()
				.map(x -> {
					final VnffgPostTask task = createTask(VnffgPostTask::new);
					task.setType(ResourceTypeEnum.VNFFG_POST);
					task.setToscaName(x.getName());
					task.setChain(createChain(x));
					task.setClassifier(x.getClassifier());
					task.setVnffg(x);
					task.setSrcPort(findPort(nsd, task.getClassifier().getLogicalSourcePort()));
					task.setDstPort(findPort(nsd, task.getClassifier().getLogicalDestinationPort()));
					return create(VnffgPostNode.class, task.getClass(), x.getName(), 1, task, parameters.getInstance(), parameters);
				})
				.toList();
	}

	private static String findPort(final NsdPackage nsd, final String port) {
		return nsd.getVnfPkgIds().stream()
				.flatMap(x -> x.getForwardMapping().stream())
				.filter(x -> x.getForwardingName().equals(port))
				.map(ForwarderMapping::getVduName)
				.findFirst()
				.orElseThrow(() -> new GenericException("Could not find "));
	}

	private static Set<ListKeyPair> createChain(final VnffgDescriptor vnffgd) {
		final AtomicInteger i = new AtomicInteger(0);
		return vnffgd.getNfpd().stream()
				.flatMap(x -> x.getInstances().stream())
				.map(x -> ListKeyPair.of(x.getToscaName(), i.getAndIncrement()))
				.collect(Collectors.toSet());
	}

}
