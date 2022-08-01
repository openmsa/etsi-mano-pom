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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Priority;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.config.ServerType;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.ExternalPortRecord;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfCreateVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.service.NsScaleStrategy;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.graph.vt.NsVtBase;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(50)
@Service
@Transactional
public class VnfContributor extends AbstractNsContributor<NsVnfTask, NsVtBase<NsVnfTask>> {
	private static final Logger LOG = LoggerFactory.getLogger(VnfContributor.class);

	private final NsInstanceService nsInstanceService;
	private final NsLiveInstanceJpa nsLiveInstanceJpa;
	private final ServersJpa serversJpa;
	private final NsScaleStrategy nsScaleStrategy;
	private final VnfPackageService vnfPackageService;
	private final Random rand = new Random();

	public VnfContributor(final NsInstanceService nsInstanceService, final NsLiveInstanceJpa nsLiveInstanceJpa, final ServersJpa serversJpa, final NsScaleStrategy nsScaleStrategy, final VnfPackageService vnfPackageService) {
		this.nsInstanceService = nsInstanceService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.serversJpa = serversJpa;
		this.nsScaleStrategy = nsScaleStrategy;
		this.vnfPackageService = vnfPackageService;
	}

	private static Set<ExternalPortRecord> getNetworks(final VnfPackage vnfPackage) {
		final Set<ExternalPortRecord> ret = new HashSet<>();
		final Set<ExternalPortRecord> n = vnfPackage.getVnfCompute().stream()
				.flatMap(y -> y.getNetworks().stream())
				.map(y -> new ExternalPortRecord(y, null, null))
				.collect(Collectors.toSet());
		ret.addAll(n);
		return ret;
	}

	private static List<CpPair> getForwarder(final Set<VnffgDescriptor> vnffg) {
		final Set<ExternalPortRecord> ret = new HashSet<>();
		vnffg.stream()
				.flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream()).forEach(x -> {
					if (null != x.getEgressVl()) {
						ret.add(new ExternalPortRecord(x.getEgressVl(), null, x.getEgressVl()));
					}
					if (null != x.getIngressVl()) {
						ret.add(new ExternalPortRecord(x.getIngressVl(), null, x.getIngressVl()));
					}
				});
		return List.of();
	}

	@Override
	protected List<NsVtBase<NsVnfTask>> onTerminate(final NsdInstance instance) {
		final List<NsVtBase<NsVnfTask>> ret = new ArrayList<>();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, NsVnfTask.class.getSimpleName());
		int i = 0;
		for (final NsLiveInstance nsLiveInstance : insts) {
			final NsVnfTask task = (NsVnfTask) nsLiveInstance.getNsTask();
			final List<NsLiveInstance> ress = nsLiveInstanceJpa.findByResourceId(nsLiveInstance.getResourceId());
			if (ress.size() > 1) {
				nsLiveInstanceJpa.delete(nsLiveInstance);
				continue;
			}
			final String toscaName = getToscaName(nsLiveInstance.getNsTask().getAlias(), i);
			final String aliasName = getToscaName(insts.get(i).getNsTask().getToscaName(), i);
			final NsVnfTask nt = createVnfDeleteTask(nsLiveInstance, task, instance, toscaName, aliasName);
			i++;
			ret.add(new NsVnfCreateVt(nt));
		}
		return ret;
	}

	public static NsVnfTask createVnfDeleteTask(final NsLiveInstance nsLiveInstance, final NsVnfTask task, final NsdInstance instance, final String toscaName, final String aliasName) {
		final NsVnfTask nt = createDeleteTask(NsVnfTask::new, nsLiveInstance);
		final Set<ExternalPortRecord> nets = task.getNsPackageVnfPackage().getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.map(x -> new ExternalPortRecord(x.getValue(), null, null))
				.collect(Collectors.toSet());
		nt.setExternalNetworks(nets);
		nt.setVimResourceId(nsLiveInstance.getResourceId());
		nt.setServer(task.getServer());
		nt.setAlias(toscaName);
		nt.setToscaName(aliasName);
		nt.setType(ResourceTypeEnum.VNF);
		nt.setNsdId(instance.getNsdInfo().getId());
		nt.setNsPackageVnfPackage(task.getNsPackageVnfPackage());
		nt.setVnfdId(task.getVnfdId());
		nt.setVlInstances(task.getVlInstances());
		return nt;
	}

	private static NsdPackageVnfPackage find(final VnfPackage vnfPackage, final Set<NsdPackageVnfPackage> vnfPkgIds) {
		return vnfPkgIds.stream()
				.filter(x -> x.getVnfPackage().getId().compareTo(vnfPackage.getId()) == 0)
				.findFirst().orElseThrow(() -> new NotFoundException("VNF Package not found: " + vnfPackage.getId()));
	}

	@Override
	public Class<? extends Node> getNode() {
		return VnfInstantiateNode.class;
	}

	private void remove(final int cnt, final NsdInstance instance, final List<NsVtBase<NsVnfTask>> ret) {
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, NsVnfTask.class.getSimpleName());
		for (int i = 0; i < cnt; i++) {
			final NsVnfTask task = (NsVnfTask) insts.get(i).getNsTask();
			final NsLiveInstance linst = insts.get(i);
			final String toscaName = getToscaName(linst.getNsTask().getToscaName(), i);
			final String aliasName = getToscaName(linst.getNsTask().getToscaName(), i);
			final NsVnfTask nt = createDeleteTask(instance, linst, task, toscaName, aliasName);
			ret.add(new NsVnfCreateVt(nt));
		}
	}

	private static NsVnfTask createDeleteTask(final NsdInstance instance, final NsLiveInstance insts, final NsVnfTask task, final String toscaName, final String aliasName) {
		final NsVnfTask nt = createDeleteTask(NsVnfTask::new, insts);
		final Set<ExternalPortRecord> nets = getNetworks(task.getNsPackageVnfPackage().getVnfPackage());
		nt.setExternalNetworks(nets);
		nt.setVimResourceId(insts.getResourceId());
		nt.setToscaName(toscaName);
		nt.setAlias(aliasName);
		nt.setServer(task.getServer());
		nt.setNsdId(instance.getNsdInfo().getId());
		nt.setVlInstances(task.getVlInstances());
		return nt;
	}

	private void add(final int curr, final int cnt, final VnfPackage vnfPkg, final NsdPackageVnfPackage nsPackageVnfPackage, final List<NsVtBase<NsVnfTask>> ret, final UUID nsdId, final NsBlueprint blueprint) {
		for (int i = curr; i < cnt; i++) {
			final String newName = getToscaName(nsPackageVnfPackage.getToscaName(), i);
			LOG.debug("VNF inst Creating: {}", newName);
			final NsVnfTask vnf = createTask(vnfPkg, nsPackageVnfPackage, nsdId, newName, blueprint);
			ret.add(new NsVnfCreateVt(vnf));
		}
	}

	private NsVnfTask createTask(final VnfPackage vnfPkg, final NsdPackageVnfPackage nsPackageVnfPackage, final UUID nsdId, final String newName, final NsBlueprint blueprint) {
		final NsVnfTask vnf = createTask(NsVnfTask::new);
		vnf.setChangeType(ChangeType.ADDED);
		final Set<ExternalPortRecord> nets = getNetworks(vnfPkg);
		nets.addAll(getVl(nsPackageVnfPackage));
		vnf.setExternalNetworks(nets);
		vnf.setNsPackageVnfPackage(nsPackageVnfPackage);
		final Servers server = selectServer(vnfPkg);
		vnf.setServer(server);
		vnf.setAlias(newName);
		final Set<String> nstworks = rebuildVl(nsPackageVnfPackage, blueprint.getTasks());
		vnf.setVlInstances(nstworks);
		vnf.setToscaName(nsPackageVnfPackage.getToscaName());
		vnf.setFlavourId("flavour");
		vnf.setVnfdId(nsPackageVnfPackage.getVnfPackage().getVnfdId());
		vnf.setType(ResourceTypeEnum.VNF);
		vnf.setNsdId(nsdId);
		return vnf;
	}

	private static Set<String> rebuildVl(final NsdPackageVnfPackage nsPackageVnfPackage, final Set<NsTask> tasks) {
		return nsPackageVnfPackage.getVirtualLinks().stream()
				.map(x -> mapToVl(nsPackageVnfPackage, x))
				.map(ForwarderMapping::getVlName)
				.flatMap(x -> tasks.stream().filter(y -> y.getToscaName().equals(x)).collect(Collectors.toSet()).stream())
				.map(NsTask::getAlias)
				.collect(Collectors.toSet());
	}

	private static ForwarderMapping mapToVl(final NsdPackageVnfPackage nsPackageVnfPackage, final ListKeyPair lp) {
		return nsPackageVnfPackage.getForwardMapping().stream().filter(x -> x.getForwardingName().equals(lp.getValue())).findFirst().orElseThrow();
	}

	private static Set<ExternalPortRecord> getVl(final NsdPackageVnfPackage nsPackageVnfPackage) {
		return nsPackageVnfPackage.getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.map(x -> new ExternalPortRecord(x.getValue(), getVlName(x), null))
				.collect(Collectors.toSet());
	}

	private static String getVlName(final ListKeyPair x) {
		if (x.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + x.getIdx();
	}

	private static String getToscaName(final String nsPackageVnfPackage, final int instanceNumber) {
		return nsPackageVnfPackage + "-" + String.format("%04d", instanceNumber);
	}

	private Servers selectServer(final VnfPackage vnfPackage) {
		final List<Servers> servers = serversJpa.findByServerTypeAndServerStatusIn(ServerType.VNFM, Arrays.asList(PlanStatusType.SUCCESS));
		if (servers.isEmpty()) {
			return null;
		}
		final Set<String> vnfmInfos = vnfPackage.getVnfmInfo();
		if (vnfmInfos.isEmpty()) {
			return servers.get(rand.nextInt(servers.size()));
		}
		final List<Servers> available = servers.stream()
				.filter(x -> {
					final List<String> s = x.getCapabilities().stream().filter(vnfmInfos::contains).toList();
					return !s.isEmpty();
				})
				.toList();
		if (available.isEmpty()) {
			throw new GenericException("No VNFM server found for the following requirements: " + vnfmInfos);
		}
		return available.get(rand.nextInt(available.size()));
	}

	@Override
	protected List<NsVtBase<NsVnfTask>> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

	@Override
	protected List<NsVtBase<NsVnfTask>> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final Set<NsdVnfPackageCopy> vnfsCopy = blueprint.getInstance().getVnfPkgIds();
		final List<VnfPackage> vnfs = vnfsCopy.stream().map(x -> vnfPackageService.findByDescriptorId(x.getVnfdId()).orElseThrow()).toList();
		final List<NsVtBase<NsVnfTask>> ret = new ArrayList<>();
		vnfs.stream()
				.forEach(x -> {
					final NsdPackageVnfPackage nsPackageVnfPackage = find(x, bundle.nsPackage().getVnfPkgIds());
					final int curr = nsInstanceService.countLiveInstanceOfVnf(blueprint.getNsInstance(), nsPackageVnfPackage.getToscaName() + "%");
					final int inst = nsScaleStrategy.getNumberOfInstances(nsPackageVnfPackage, blueprint);
					LOG.info("VNF curr: {} <=> inst: {}", curr, inst);
					if (curr > inst) {
						remove(curr - inst, blueprint.getInstance(), ret);
					} else if (curr < inst) {
						add(curr, inst, x, nsPackageVnfPackage, ret, bundle.nsPackage().getId(), blueprint);
					}
				});
		return ret;
	}

	@Override
	protected List<NsVtBase<NsVnfTask>> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

	@Override
	protected List<NsVtBase<NsVnfTask>> onUpdate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final BlueprintParameters p = blueprint.getParameters();
		final NsdInstance inst = blueprint.getInstance();
		final UpdateRequest updData = p.getUpdData();
		if (updData.getUpdateType() != UpdateTypeEnum.REMOVE_VNF) {
			return List.of();
		}
		final List<NsVtBase<NsVnfTask>> ret = new ArrayList<>();
		p.getUpdData().getRealVnfInstanceToRemove().stream()
				.forEach(x -> {
					final NsLiveInstance linst = nsLiveInstanceJpa.findById(x).orElseThrow();
					final String toscaName = getToscaName(linst.getNsTask().getToscaName(), 0);
					final String aliasName = getToscaName(linst.getNsTask().getToscaName(), 0);
					final NsVnfTask t = createDeleteTask(inst, linst, (NsVnfTask) linst.getNsTask(), toscaName, aliasName);
					ret.add(new NsVnfCreateVt(t));
				});
		return ret;
	}

}
