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

import javax.annotation.Priority;
import javax.validation.constraints.NotNull;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPortPairVt;
import com.ubiqube.etsi.mano.orchestrator.OrchestratorNamingStrategy;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;

/**
 *
 * @author olivier
 *
 */
@Priority(150)
@Service
public class VnffgPortPairContributor extends AbstractNsContributor<VnffgPortPairTask, NsVnffgPortPairVt> {
	private final NsLiveInstanceJpa nsLiveInstanceJpa;
	private final OrchestratorNamingStrategy namingtrategy;

	public VnffgPortPairContributor(final NsLiveInstanceJpa nsLiveInstanceJpa, final OrchestratorNamingStrategy namingtrategy) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.namingtrategy = namingtrategy;
	}

	@Override
	public Class<? extends Node> getNode() {
		return PortPairNode.class;
	}

	@Override
	protected List<NsVnffgPortPairVt> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return blueprint.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.VNF)
				.map(NsVnfTask.class::cast)
				.flatMap(x -> {
					if (x.getChangeType() == ChangeType.REMOVED) {
						return remove(x, blueprint.getInstance(), blueprint).stream();
					}
					if (x.getChangeType() == ChangeType.ADDED) {
						return addForScaling(x, blueprint.getInstance(), bundle).stream();
					}
					throw new GenericException("Unknown change type: " + x.getChangeType());
				})
				.filter(Objects::nonNull)
				.toList();
	}

	/**
	 * We need to find the associated pair port of a given VNF.
	 *
	 * @param vnfTask
	 * @param instance
	 * @param bundle
	 * @return
	 */
	private List<NsVnffgPortPairVt> addForScaling(final NsVnfTask vnfTask, final NsdInstance instance, final NsBundleAdapter bundle) {
		return bundle.nsPackage().getVnffgs().stream()
				.flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream())
				.filter(x -> x.getVnf().equals(vnfTask.getToscaName()))
				.filter(x -> !exist(vnfTask.getAlias(), instance))
				.map(x -> add(vnfTask, bundle, 1))
				.flatMap(List::stream)
				.toList();
	}

	private List<NsVnffgPortPairVt> addForScalingTmp(final NsVnfTask vnfTask, final NsdInstance instance, final NsBundleAdapter bundle) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, VnffgPortPairTask.class.getSimpleName());
		if (insts.isEmpty()) {
			return add(vnfTask, bundle, 1);
		}
		final List<@NonNull VnffgPortPairTask> res = insts.stream()
				.map(VnffgPortPairTask.class::cast)
				.filter(x -> x.getVnfAlias().equals(vnfTask.getAlias()))
				.toList();
		return res.stream().map(x -> copyForDelete(x, instance)).toList();
	}

	private boolean exist(final String alias, final NsdInstance instance) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, VnffgPortPairTask.class.getSimpleName());
		return insts.stream()
				.map(NsLiveInstance::getNsTask)
				.map(VnffgPortPairTask.class::cast)
				.anyMatch(x -> x.getVnfAlias().equals(alias));
	}

	private @NonNull NsVnffgPortPairVt copyForDelete(@NonNull final VnffgPortPairTask x, final NsdInstance instance) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(instance, x.getToscaName(), VnffgPortPairTask.class.getSimpleName());
		final NsLiveInstance nsLiveInstance = insts.stream().filter(y -> y.getNsTask().getAlias().equals(x.getAlias())).findFirst().orElseThrow(() -> new GenericException("Unable to find a matching pair port " + x.getAlias()));
		final VnffgPortPairTask nt = createDeleteTask(VnffgPortPairTask::new, nsLiveInstance);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(x.getAlias());
		nt.setToscaName(x.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
		return new NsVnffgPortPairVt(nt);
	}

	@NotNull
	private List<@NonNull NsVnffgPortPairVt> add(final NsVnfTask vnfTask, final NsBundleAdapter bundle, final int i) {
		final NsdPackageVnfPackage vnf = findVnf(bundle.nsPackage(), vnfTask.getToscaName());
		final List<CpPair> elements = find(bundle.nsPackage().getVnffgs(), vnf.getToscaName());
		if (elements.isEmpty()) {
			throw new GenericException("Could not find a VNFFG element of name " + vnf.getToscaName());
		}
		final AtomicInteger ii = new AtomicInteger(i);
		return elements.stream().map(x -> {
			final VnffgPortPairTask task = createTask(VnffgPortPairTask::new);
			task.setToscaName(x.getToscaName());
			task.setRank(ii.get());
			task.setAlias(namingtrategy.dimensional("pp", x.getToscaName(), ii.getAndIncrement()));
			task.setVnf(vnf);
			task.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
			task.setVnfToscaName(vnf.getToscaName());
			task.setVnfAlias(vnfTask.getAlias());
			task.setCpPair(x);
			return new NsVnffgPortPairVt(task);
		})
				.toList();
	}

	private static List<CpPair> find(final Set<VnffgDescriptor> vnffgd, final String vnfToscaName) {
		return vnffgd.stream()
				.flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream())
				.filter(x -> x.getVnf().equals(vnfToscaName))
				.toList();
	}

	private List<NsVnffgPortPairVt> remove(@NonNull final NsVnfTask task, final NsdInstance instance, final NsBlueprint blueprint) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(instance, task.getToscaName(), VnffgPortPairTask.class.getSimpleName());
		final List<NsTask> listPlan = blueprint.getTasks().stream().filter(x -> x.getToscaName().equals(task.getToscaName())).toList();
		final boolean remove = (insts.size() - listPlan.size()) != 0;
		// We can use the toscanme here, are there is always a single instance.
		final NsLiveInstance nsLiveInstance = insts.stream()
				.filter(y -> y.getNsTask().getAlias().equals(task.getAlias()))
				.findFirst()
				.orElseThrow(() -> new GenericException("Unable to find a matching pair port " + task.getAlias()));
		final VnffgPortPairTask nt = createDeleteTask(VnffgPortPairTask::new, nsLiveInstance);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(task.getAlias());
		nt.setToscaName(task.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
		return List.of(new NsVnffgPortPairVt(nt));
	}

	private static NsVnffgPortPairVt remove1(final NsTask task, final NsLiveInstance nsLiveInstance) {
		final VnffgPortPairTask nt = createDeleteTask(VnffgPortPairTask::new, nsLiveInstance);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setAlias(task.getAlias());
		nt.setToscaName(task.getToscaName());
		nt.setType(ResourceTypeEnum.VNFFG_PORT_PAIR);
		return new NsVnffgPortPairVt(nt);
	}

	@Override
	protected List<NsVnffgPortPairVt> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return blueprint.getTasks().stream()
				.filter(x -> x.getType() == ResourceTypeEnum.VNF)
				.map(NsVnfTask.class::cast)
				.flatMap(x -> add(x, bundle, 1).stream())
				.toList();
	}

	private static NsdPackageVnfPackage findVnf(final NsdPackage nsPackage, final String toscaName) {
		return nsPackage.getVnfPkgIds().stream().filter(x -> x.getToscaName().equals(toscaName)).findFirst().orElseThrow(() -> new GenericException("Unable to find VNF named: " + toscaName));
	}

	@Override
	protected List<NsVnffgPortPairVt> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return List.of();
	}

	@Override
	protected List<NsVnffgPortPairVt> onTerminate(final NsdInstance instance) {
		final List<NsVnffgPortPairVt> ret = new ArrayList<>();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, VnffgPortPairTask.class.getSimpleName());
		for (final NsLiveInstance nsLiveInstance : insts) {
			final VnffgPortPairTask task = (VnffgPortPairTask) nsLiveInstance.getNsTask();
			final NsVnffgPortPairVt nt = remove1(task, nsLiveInstance);
			ret.add(nt);
		}
		return ret;
	}

}
