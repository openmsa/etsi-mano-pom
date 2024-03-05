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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;

import jakarta.annotation.Nullable;

/**
 *
 * @author olivier
 *
 */
@Service
public class NsdVnfContributorV3 extends AbstractNsdContributorV3<Object> {
	private final NsScaleStrategyV3 nsScaleStrategy;

	private final ServersJpa serversJpa;

	private final VnfPackageService vnfPackageService;

	private final Random rand = new Random();

	protected NsdVnfContributorV3(final NsLiveInstanceJpa nsLiveInstanceJpa, final NsScaleStrategyV3 nsScaleStrategy, final ServersJpa serversJpa, final VnfPackageService vnfPackageService) {
		super(nsLiveInstanceJpa);
		this.nsScaleStrategy = nsScaleStrategy;
		this.serversJpa = serversJpa;
		this.vnfPackageService = vnfPackageService;
	}

	@Override
	public List<SclableResources<Object>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
		final Set<NsdVnfPackageCopy> vnfsCopy = parameters.getInstance().getVnfPkgIds();
		final List<VnfPackage> vnfs = vnfsCopy.stream().map(x -> vnfPackageService.findByVnfdId(x.getVnfdId()).orElseThrow()).toList();
		final List<SclableResources<Object>> ret = new ArrayList<>();
		vnfs.forEach(x -> {
			final NsdVnfPackageCopy nsPackageVnfPackage = find(x, vnfsCopy);
			final NsVnfTask task = createVnfCreateTask(nsPackageVnfPackage, x);
			final int numInst = nsScaleStrategy.getNumberOfInstances(nsPackageVnfPackage, parameters);
			ret.add(create(VnfCreateNode.class, task.getClass(), nsPackageVnfPackage.getToscaName(), numInst, task, parameters.getInstance(), parameters));
			final NsVnfInstantiateTask taskInst = createVnfInstantiateTask(nsPackageVnfPackage, x);
			ret.add(create(VnfInstantiateNode.class, taskInst.getClass(), nsPackageVnfPackage.getToscaName(), numInst, taskInst, parameters.getInstance(), parameters));
			final NsVnfExtractorTask taskExtractor = createVnfExtractTask(nsPackageVnfPackage, x, bundle.getId());
			ret.add(create(VnfExtractorNode.class, taskExtractor.getClass(), nsPackageVnfPackage.getToscaName(), numInst, taskExtractor, parameters.getInstance(), parameters));

		});
		return ret;
	}

	private NsVnfExtractorTask createVnfExtractTask(final NsdVnfPackageCopy param, final VnfPackage vnfPkg, final UUID nsdId) {
		final NsVnfExtractorTask task = createTask(NsVnfExtractorTask::new);
		task.setType(ResourceTypeEnum.VNF_EXTRACTOR);
		task.setToscaName(param.getToscaName());
		final Servers server = selectServer(vnfPkg);
		task.setServer(server);
		task.setNsdId(nsdId.toString());
		return task;
	}

	private NsVnfInstantiateTask createVnfInstantiateTask(final NsdVnfPackageCopy param, final VnfPackage vnfPkg) {
		final NsVnfInstantiateTask task = createTask(NsVnfInstantiateTask::new);
		task.setType(ResourceTypeEnum.VNF_INSTANTIATE);
		task.setToscaName(param.getToscaName());
		final Servers server = selectServer(vnfPkg);
		task.setServer(server);
		task.setVnfInstanceName(param.getToscaName());
		task.setFlavourId("flavour");
		final Set<String> nstworks = rebuildVl(param);
		task.setVlInstances(nstworks);
		task.setParam(param);
		return task;
	}

	private NsVnfTask createVnfCreateTask(final NsdVnfPackageCopy param, final VnfPackage vnfPkg) {
		final NsVnfTask task = createTask(NsVnfTask::new);
		task.setType(ResourceTypeEnum.VNF_CREATE);
		task.setToscaName(param.getToscaName());
		task.setNsPackageVnfPackage(param);
		task.setVnfdId(vnfPkg.getVnfdId());
		final Servers server = selectServer(vnfPkg);
		task.setServer(server);
		return task;
	}

	private static Set<String> rebuildVl(final NsdVnfPackageCopy nsPackageVnfPackage) {
		return nsPackageVnfPackage.getVirtualLinks().stream()
				.map(x -> mapToVl(nsPackageVnfPackage, x))
				.collect(Collectors.toSet());
	}

	private static String mapToVl(final NsdVnfPackageCopy nsPackageVnfPackage, final ListKeyPair lp) {
		return nsPackageVnfPackage.getForwardMapping().stream()
				.filter(x -> x.getForwardingName().equals(lp.getValue()))
				.findFirst()
				.map(ForwarderMapping::getVlName)
				.orElse(lp.getValue());
	}

	private @Nullable Servers selectServer(final VnfPackage vnfPackage) {
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

	private static NsdVnfPackageCopy find(final VnfPackage vnfPackage, final Set<NsdVnfPackageCopy> vnfPkgIds) {
		return vnfPkgIds.stream()
				.filter(x -> x.getVnfdId().equals(vnfPackage.getVnfdId()))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("VNF Package not found: " + vnfPackage.getId()));
	}

}
