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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.SecurityGroupTask;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.ResourceTypeConverter;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.ComputeVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsHostVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsZoneVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.MciopUserVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.MonitoringVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.NetWorkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerDeployableVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsK8sClusterVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SecurityGroupVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.StorageVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SubNetworkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfExtCpVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfIndicatorVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfPortVt;

@Service
public class ResourceTypeConverterVnfm implements ResourceTypeConverter<VnfTask> {
	private final Map<ResourceTypeEnum, Function<VnfTask, VirtualTaskV3<? extends VnfTask>>> vts;
	private final Set<ResourceHolder<VnfTask>> set = new LinkedHashSet<>();
	private final Map<Class<? extends Node>, ResourceHolder<VnfTask>> classToHolder;
	private final Map<ResourceTypeEnum, ResourceHolder<VnfTask>> resourceToHolder;

	public ResourceTypeConverterVnfm() {
		set.add(new ResourceHolder<>(ResourceTypeEnum.VL, x -> new NetWorkVt((NetworkTask) x), Network.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.SUBNETWORK, x -> new SubNetworkVt((SubNetworkTask) x), SubNetwork.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.COMPUTE, x -> new ComputeVt((ComputeTask) x), Compute.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.LINKPORT, x -> new VnfPortVt((VnfPortTask) x), VnfPortNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.VNF_EXTCP, x -> new VnfExtCpVt((ExternalCpTask) x), VnfExtCp.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.SECURITY_GROUP, x -> new SecurityGroupVt((SecurityGroupTask) x), SecurityGroupNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.STORAGE, x -> new StorageVt((StorageTask) x), Storage.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.DNSZONE, x -> new DnsZoneVt((DnsZoneTask) x), DnsZone.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.DNSHOST, x -> new DnsHostVt((DnsHostTask) x), DnsHost.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.OS_CONTAINER, x -> new OsContainerVt((OsContainerTask) x), OsContainerNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.OS_CONTAINER_INFO, x -> new OsK8sClusterVt((K8sInformationsTask) x), OsK8sInformationsNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.OS_CONTAINER_DEPLOYABLE, x -> new OsContainerDeployableVt((OsContainerDeployableTask) x), OsContainerDeployableNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.MCIOP_USER, x -> new MciopUserVt((MciopUserTask) x), MciopUser.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.HELM, x -> new HelmVt((HelmTask) x), HelmNode.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.MONITORING, x -> new MonitoringVt((MonitoringTask) x), Monitoring.class));
		set.add(new ResourceHolder<>(ResourceTypeEnum.VNF_INDICATOR, x -> new VnfIndicatorVt((VnfIndicatorTask) x), VnfIndicator.class));
		vts = set.stream()
				.collect(Collectors.toMap(ResourceHolder::res, ResourceHolder::createVt));
		classToHolder = set.stream()
				.collect(Collectors.toMap(ResourceHolder::node, x -> x));
		resourceToHolder = set.stream()
				.collect(Collectors.toMap(ResourceHolder::res, x -> x));
	}

	@Override
	public Function<VnfTask, VirtualTaskV3<? extends VnfTask>> toVt(final ResourceTypeEnum resource) {
		return vts.get(resource);
	}

	@Override
	public Optional<ResourceHolder<VnfTask>> toResourceHolder(final ResourceTypeEnum resource) {
		return Optional.ofNullable(resourceToHolder.get(resource));
	}

}
