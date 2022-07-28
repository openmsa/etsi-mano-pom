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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Priority;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPostVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;

/**
 *
 * @author olivier
 *
 */
@Priority(200)
@Service
public class VnffgPostContributor extends AbstractNsContributor<VnffgPostTask, NsVnffgPostVt> {
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	public VnffgPostContributor(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	@Override
	public Class<? extends Node> getNode() {
		return VnffgPostNode.class;
	}

	@SuppressWarnings("null")
	@Override
	protected List<NsVnffgPostVt> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		// Need to keep scale0 principle.
		return bundle.nsPackage().getVnffgs().stream()
				.map(x -> {
					final List<NsLiveInstance> lives = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(blueprint.getInstance(), x.getName(), null);
					if (!lives.isEmpty()) {
						return null;
					}
					final NsVnffgPostVt vt = create(x, bundle.nsPackage(), blueprint);
					final VnffgPostTask t = vt.getParameters();
					t.setClassifier(x.getClassifier());
					t.setChain(createChain(x));
					t.setVnffg(x);
					return new NsVnffgPostVt(t);
				})
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	protected List<NsVnffgPostVt> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return bundle.nsPackage().getVnffgs().stream().map(x -> create(x, bundle.nsPackage(), blueprint)).toList();
	}

	@NonNull
	private static NsVnffgPostVt create(final VnffgDescriptor task, final NsdPackage nsd, final NsBlueprint blueprint) {
		final VnffgPostTask t = createTask(VnffgPostTask::new);
		t.setToscaName(task.getName());
		t.setAlias(task.getName());
		t.setClassifier(task.getClassifier());
		t.setChain(createChain(task));
		t.setVnffg(task);
		t.setSrcPort(findPort(nsd, blueprint, task.getClassifier().getLogicalSourcePort()));
		t.setDstPort(findPort(nsd, blueprint, task.getClassifier().getLogicalDestinationPort()));
		return new NsVnffgPostVt(t);
	}

	private static String findPort(final NsdPackage nsd, final NsBlueprint blueprint, final String port) {
		final String srcPort = convert(nsd, blueprint, port);
		return blueprint.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.VNF)
				.map(NsVnfTask.class::cast)
				.filter(x -> x.getAlias().equals(srcPort))
				.map(NsVnfTask::getAlias)
				.findFirst()
				.orElseThrow();
	}

	private static String convert(final NsdPackage nsd, final NsBlueprint blueprint, final String logicalSourcePort) {
		return nsd.getVnfPkgIds().stream()
				.flatMap(x -> x.getForwardMapping().stream())
				.filter(x -> x.getForwardingName().equals(logicalSourcePort))
				.map(ForwarderMapping::getVduName)
				.flatMap(x -> blueprint.getTasks().stream()
						.filter(y -> y.getToscaName().equals(x))
						.map(NsTask::getAlias))
				.findFirst()
				.orElseThrow();
	}

	private static Set<ListKeyPair> createChain(final VnffgDescriptor vnffgd) {
		final AtomicInteger i = new AtomicInteger(0);
		return vnffgd.getNfpd().stream()
				.flatMap(x -> x.getInstances().stream())
				.map(x -> ListKeyPair.of(x.getToscaName(), i.getAndIncrement()))
				.collect(Collectors.toSet());
	}

	@Override
	protected List<NsVnffgPostVt> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return List.of();
	}

	@Override
	protected List<NsVnffgPostVt> onTerminate(final NsdInstance instance) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, VnffgPostTask.class.getSimpleName());
		final List<NsVnffgPostVt> ret = new ArrayList<>();
		for (final NsLiveInstance nsLiveInstance : insts) {
			final VnffgPostTask task = (VnffgPostTask) nsLiveInstance.getNsTask();
			final NsVnffgPostVt nt = remove(task, nsLiveInstance);
			ret.add(nt);
		}
		return ret;
	}

	private static NsVnffgPostVt remove(final VnffgPostTask task, final NsLiveInstance nsLiveInstance) {
		final VnffgPostTask nt = createDeleteTask(VnffgPostTask::new, nsLiveInstance);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(task.getAlias());
		nt.setToscaName(task.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_POST);
		nt.setVnffg(null);
		return new NsVnffgPostVt(nt);
	}

}
