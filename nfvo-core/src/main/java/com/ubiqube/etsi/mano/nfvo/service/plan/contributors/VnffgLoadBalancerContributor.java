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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Priority;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.VnffgLoadbalancerVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;

/**
 * Create a pairGroup in SFC. Have many port pair.
 *
 * @author olivier
 *
 */
@Priority(200)
@Service
public class VnffgLoadBalancerContributor extends AbstractNsContributor<VnffgLoadbalancerTask, VnffgLoadbalancerVt> {
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	public VnffgLoadBalancerContributor(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	@Override
	public Class<? extends Node> getNode() {
		return VnffgLoadbalancerNode.class;
	}

	@Override
	protected List<VnffgLoadbalancerVt> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return blueprint.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.VNFFG_PORT_PAIR)
				.map(VnffgPortPairTask.class::cast)
				.flatMap(x -> {
					if (x.getChangeType() == ChangeType.REMOVED) {
						return remove(x, blueprint).stream();
					}
					if (x.getChangeType() == ChangeType.ADDED) {
						return add(x, bundle, blueprint).stream();
					}
					throw new GenericException("");
				})
				.filter(Objects::nonNull)
				.toList();
	}

	private List<VnffgLoadbalancerVt> add(final VnffgPortPairTask task, final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final NsdInstance instance = blueprint.getInstance();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(instance, task.getToscaName(), VnffgLoadbalancerTask.class.getSimpleName());
		if (!insts.isEmpty()) {
			return List.of();
		}
		final NfpDescriptor vnf = find(bundle.nsPackage().getVnffgs(), task.getToscaName());
		final VnffgLoadbalancerTask nt = createTask(VnffgLoadbalancerTask::new);
		nt.setToscaName(vnf.getToscaName());
		nt.setAlias(vnf.getToscaName());
		return List.of(new VnffgLoadbalancerVt(nt));
	}

	private List<VnffgLoadbalancerVt> remove(final VnffgPortPairTask task, final NsBlueprint blueprint) {
		final NsdInstance instance = blueprint.getInstance();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(instance, task.getToscaName(), VnffgPortPairTask.class.getSimpleName());
		final List<NsTask> listPlan = blueprint.getTasks().stream().filter(x -> x.getToscaName().equals(task.getToscaName())).toList();
		final boolean remove = (insts.size() - listPlan.size()) != 0;
		final NsLiveInstance nsLiveInstance = insts.stream()
				.filter(y -> y.getNsTask().getToscaName().equals(task.getToscaName()))
				.findFirst()
				.orElseThrow(() -> new GenericException("Unable to find a matching loadbalancer " + task.getAlias()));
		final VnffgLoadbalancerTask nt = createDeleteTask(VnffgLoadbalancerTask::new, nsLiveInstance);
		final List<NsLiveInstance> live2 = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(instance, task.getToscaName(), VnffgLoadbalancerTask.class.getSimpleName());
		final NsTask t = live2.get(0).getNsTask();
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(t.getAlias());
		nt.setToscaName(t.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_LOADBALANCER);
		nt.setRemove(remove);
		final AtomicInteger i = new AtomicInteger();
		final Set<ListKeyPair> constituant = new LinkedHashSet<>(insts.stream()
				.map(NsLiveInstance::getNsTask)
				.map(NsTask::getAlias)
				.map(x -> new ListKeyPair(x, i.getAndIncrement()))
				.toList());
		constituant.addAll(listPlan.stream().map(NsTask::getAlias).map(x -> new ListKeyPair(x, i.getAndIncrement())).collect(Collectors.toSet()));
		nt.setConstituant(constituant);
		return List.of(new VnffgLoadbalancerVt(nt));
	}

	private static VnffgLoadbalancerVt remove1(final VnffgLoadbalancerTask task, final NsLiveInstance nsLiveInstance) {
		final VnffgLoadbalancerTask nt = createDeleteTask(VnffgLoadbalancerTask::new, nsLiveInstance);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(task.getAlias());
		nt.setToscaName(task.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
		return new VnffgLoadbalancerVt(nt);
	}

	@SuppressWarnings("null")
	@Override
	protected List<VnffgLoadbalancerVt> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final NsdPackage pkg = bundle.nsPackage();
		if ((null == pkg.getVnffgs()) || pkg.getVnffgs().isEmpty()) {
			return List.of();
		}
		return bundle.nsPackage().getVnffgs().stream()
				.flatMap(x -> x.getNfpd().stream()
						.flatMap(y -> y.getInstances().stream()
								.map(yy -> {
									final AtomicInteger i = new AtomicInteger();
									final Set<ListKeyPair> constituant = yy.getPairs().stream().map(z -> ListKeyPair.of(z.getToscaName(), i.getAndIncrement())).collect(Collectors.toSet());
									final VnffgLoadbalancerTask t = createTask(VnffgLoadbalancerTask::new);
									t.setInstances(y);
									t.setToscaName(yy.getToscaName());
									t.setAlias(yy.getToscaName());
									t.setConstituant(constituant);
									return new VnffgLoadbalancerVt(t);
								}).toList().stream()))
				.toList();
	}

	private static NfpDescriptor find(final Set<VnffgDescriptor> vnffgs, final String vnfName) {
		return vnffgs.stream()
				.flatMap(x -> x.getNfpd().stream())
				.filter(x -> x.getInstances().stream()
						.flatMap(y -> y.getPairs().stream())
						.anyMatch(y -> y.getToscaName().equals(vnfName)))
				.findFirst()
				.orElseThrow();

	}

	@Override
	protected List<VnffgLoadbalancerVt> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return List.of();
	}

	@Override
	protected List<VnffgLoadbalancerVt> onTerminate(final NsdInstance instance) {
		final List<VnffgLoadbalancerVt> ret = new ArrayList<>();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, VnffgLoadbalancerTask.class.getSimpleName());
		for (final NsLiveInstance nsLiveInstance : insts) {
			final VnffgLoadbalancerTask task = (VnffgLoadbalancerTask) nsLiveInstance.getNsTask();
			final VnffgLoadbalancerVt nt = remove1(task, nsLiveInstance);
			ret.add(nt);
		}
		return ret;
	}

}
