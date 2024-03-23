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
package com.ubiqube.etsi.mano.vnfm.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.CpProtocolInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.CpProtocolInfoEntity.LayerProtocolEnum;
import com.ubiqube.etsi.mano.dao.mano.ExtCpInfo;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressDataAddressRangeEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressDataIpAddressesEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressInfoEntity.TypeEnum;
import com.ubiqube.etsi.mano.dao.mano.IpType;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VimResource;
import com.ubiqube.etsi.mano.dao.mano.VirtualLinkInfo;
import com.ubiqube.etsi.mano.dao.mano.VirtualStorageResourceInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfMonitoringParameter;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoVnfcCpInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.v2.IpSubnet;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.IpPool;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.graph.DefaultVduNamingStrategy;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfInstanceServiceVnfm implements VnfInstanceGatewayService {

	private static final String VIRTUAL_LINK = "virtual_link";

	private final VnfInstanceJpa vnfInstanceJpa;

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	private final MapperFacade mapper;

	private final DefaultVduNamingStrategy namingStrategy;

	private final VnfPackageJpa vnfPackageJpa;

	public VnfInstanceServiceVnfm(final VnfInstanceJpa vnfInstanceJpa, final VnfLiveInstanceJpa vnfLiveInstanceJpa, final MapperFacade mapper,
			final DefaultVduNamingStrategy namingStrategy, final VnfPackageJpa vnfPackageJpa) {
		this.vnfInstanceJpa = vnfInstanceJpa;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.mapper = mapper;
		this.namingStrategy = namingStrategy;
		this.vnfPackageJpa = vnfPackageJpa;
	}

	@Override
	public VnfInstance findById(final UUID id) {
		final VnfInstance inst = vnfInstanceJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find VNF instance: " + id));
		final BlueprintParameters vnfInfo = Objects.requireNonNull(inst.getInstantiatedVnfInfo());
		final List<VnfLiveInstance> vli = vnfLiveInstanceJpa.findByVnfInstance(inst);
		extractCompute(vnfInfo, vli);
		extractExtCp(vnfInfo, vli, inst);
		extractStorage(vnfInfo, vli);
		extractVl(vnfInfo, vli);
		// This pne will crash if a router is present at terminate time.
		// extractExtVl vnfInfo, vli
		extractMonitoring(vnfInfo, vli);
		extractExtVirtualLinkInfo(vnfInfo, vli);
		inst.setInstantiationState(isLive(id));
		return inst;
	}

	private static void extractExtVirtualLinkInfo(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream().filter(x -> x.getTask() instanceof VnfPortTask).toList();
		final Set<ExtVirtualLinkDataEntity> obj = vli.stream().map(x -> {
			final VnfPortTask vpt = (VnfPortTask) x.getTask();
			final ExtVirtualLinkDataEntity elie = new ExtVirtualLinkDataEntity();
			elie.setId(x.getId());
			elie.setResourceId(x.getResourceId());
			elie.setResourceProviderId(vpt.getResourceProviderId());
			elie.setVimConnectionId(x.getVimConnectionId());
			elie.setVnfInstance(x.getVnfInstance());
			return elie;
		}).collect(Collectors.toSet());
		vnfInfo.setExtVirtualLinkInfo(obj);
	}

	private static void extractMonitoring(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream()
				.filter(x -> x.getTask() instanceof MonitoringTask)
				.toList();
		final Set<VnfMonitoringParameter> obj = vli.stream()
				.map(x -> {
					final VnfMonitoringParameter mon = new VnfMonitoringParameter();
					final MonitoringTask mt = (MonitoringTask) x.getTask();
					mon.setId(x.getId());
					mon.setName(mt.getMonitoringParams().getName());
					mon.setPerformanceMetric(mt.getMonitoringParams().getPerformanceMetric());
					return mon;
				}).collect(Collectors.toSet());
		vnfInfo.setVnfMonitoringParameter(obj);
	}

	private static void extractExtVl(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream().filter(x -> x.getTask() instanceof ExternalCpTask).toList();
		final Set<ExtManagedVirtualLinkDataEntity> obj = vli.stream().map(x -> {
			final ExtManagedVirtualLinkDataEntity ret = new ExtManagedVirtualLinkDataEntity();
			final ExternalCpTask ect = (ExternalCpTask) x.getTask();
			ret.setId(x.getId());
			ret.setResourceId(x.getResourceId());
			ret.setResourceProviderId(ect.getResourceProviderId());
			return ret;
		}).collect(Collectors.toSet());
		vnfInfo.setExtManagedVirtualLinks(obj);
	}

	private static void extractVl(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream()
				.filter(x -> x.getTask() instanceof NetworkTask).toList();
		final Set<VirtualLinkInfo> obj = vli.stream()
				.map(x -> {
					final NetworkTask nt = (NetworkTask) x.getTask();
					final VirtualLinkInfo ret = new VirtualLinkInfo();
					ret.setId(x.getId());
					ret.setVnfVirtualLinkDescId(nt.getToscaName());
					final VimResource vimResource = new VimResource();
					vimResource.setResourceId(x.getResourceId());
					vimResource.setResourceProviderId(nt.getResourceProviderId());
					vimResource.setVimConnectionId(x.getVimConnectionId());
					vimResource.setVimLevelResourceType(null);
					ret.setNetworkResource(vimResource);
					ret.setVnfLinkPorts(null);
					return ret;
				}).collect(Collectors.toSet());
		vnfInfo.setVirtualLinkResourceInfo(obj);
	}

	private static void extractStorage(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vli) {
		final List<VnfLiveInstance> storageVli = vli.stream().filter(x -> x.getTask() instanceof StorageTask).toList();
		final Set<VirtualStorageResourceInfo> storages = storageVli.stream()
				.map(VnfInstanceServiceVnfm::createVirtualStorageResourceInfo)
				.collect(Collectors.toSet());
		vnfInfo.setVirtualStorageResourceInfo(storages);
	}

	private static VirtualStorageResourceInfo createVirtualStorageResourceInfo(final VnfLiveInstance x) {
		final VirtualStorageResourceInfo ret = new VirtualStorageResourceInfo();
		ret.setReservationId(null);
		final VimResource vimResource = new VimResource();
		vimResource.setResourceId(x.getResourceId());
		vimResource.setVimConnectionId(x.getVimConnectionId());
		vimResource.setResourceProviderId(x.getTask().getResourceProviderId());
		ret.setId(x.getId());
		ret.setStorageResource(vimResource);
		ret.setVirtualStorageDescId(x.getTask().getToscaName());
		ret.setVnfdId(null);
		ret.setZoneId(null);
		return ret;
	}

	private void extractExtCp(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vli, final VnfInstance inst) {
		final List<VnfLiveInstance> portVli = vli.stream().filter(x -> x.getTask() instanceof VnfPortTask).toList();
		final Set<ExtCpInfo> extCp = portVli.stream()
				.filter(x -> isPointingOut(x, inst.getVnfPkg().getVirtualLinks()))
				.map(x -> createExtCpInfo(inst, portVli, x)).collect(Collectors.toSet());
		vnfInfo.setExtCpInfo(extCp);
	}

	private ExtCpInfo createExtCpInfo(final VnfInstance inst, final List<VnfLiveInstance> portVli, final VnfLiveInstance vli) {
		final VnfPortTask vpt = (VnfPortTask) vli.getTask();
		final VnfLinkPort vlp = vpt.getVnfLinkPort();
		final ExtCpInfo ret = new ExtCpInfo();
		ret.setId(vli.getId());
		ret.setAssociatedVnfcCpId(null); // This is one
		ret.setAssociatedVnfVirtualLinkId(getPort(portVli, vlp.getToscaName()));
		ret.setCpConfigId(null);
		if (null == vlp.getVirtualLink()) {
			final VnfPackage vnfPkg = vnfPackageJpa.findById(inst.getVnfPkg().getId()).orElseThrow();
			setFromExtCp(vnfPkg, ret, vlp.getToscaName());
		} else {
			ret.setCpdId(vlp.getVirtualLink());
		}
		ret.setCpProtocolInfo(null);
		ret.setExtLinkPortId(null);
		return ret;
	}

	private static boolean isPointingOut(final VnfLiveInstance vli, final Set<ListKeyPair> extVls) {
		final VnfPortTask task = (VnfPortTask) vli.getTask();
		return extVls.stream().anyMatch(x -> x.getValue().equals(task.getToscaName()));
	}

	private static void setFromExtCp(final VnfPackage vnfPkg, final ExtCpInfo ret, final String toscaName) {
		vnfPkg.getVirtualLinks().stream()
				.filter(x -> x.getValue().equals(toscaName))
				.findFirst()
				.ifPresent(x -> ret.setCpdId(buildVlName(x)));
	}

	private static String buildVlName(final ListKeyPair x) {
		if (x.getIdx() == 0) {
			return VIRTUAL_LINK;
		}
		return VIRTUAL_LINK + "_" + x.getIdx();
	}

	private static @Nullable String getPort(final List<VnfLiveInstance> portVli, final String toscaName) {
		return portVli.stream()
				.filter(x -> toscaName.equals(((VnfPortTask) x.getTask()).getVnfLinkPort().getToscaName()))
				.findFirst()
				.map(x -> x.getId().toString())
				.orElse(null);
	}

	private void extractCompute(final BlueprintParameters vnfInfo, final List<VnfLiveInstance> vli) {
		final List<VnfLiveInstance> computeVli = vli.stream()
				.filter(x -> x.getTask() instanceof ComputeTask)
				.toList();
		final Set<VnfcResourceInfoEntity> vnfcResourceInfo = computeVli.stream()
				.map(x -> createVnfcResourceInfoEntity(vli, x)).collect(Collectors.toSet());
		vnfInfo.setVnfcResourceInfo(vnfcResourceInfo);
	}

	private VnfcResourceInfoEntity createVnfcResourceInfoEntity(final List<VnfLiveInstance> vli, final VnfLiveInstance x) {
		final ComputeTask ct = (ComputeTask) x.getTask();
		final VnfcResourceInfoEntity ret = mapper.map(x, VnfcResourceInfoEntity.class);
		ret.setComputeResource(mapper.map(x.getTask(), VimResource.class));
		ret.setId(x.getId().toString());
		ret.setStorageResourceIds(ct.getVnfCompute().getStorages());
		ret.setVduId(ct.getToscaName());
		ret.setZoneId(ct.getZoneId());
		ret.getComputeResource().setResourceId(x.getResourceId());
		final Set<VnfcResourceInfoVnfcCpInfoEntity> cpInfo = extractCpInfo(ct, vli);
		ret.setVnfcCpInfo(cpInfo);
		return ret;
	}

	private Set<VnfcResourceInfoVnfcCpInfoEntity> extractCpInfo(final ComputeTask ct, final List<VnfLiveInstance> vli) {
		return ct.getVnfCompute().getPorts().stream()
				.map(x -> createVnfcResourceInfoVnfcCpInfoEntity(ct, vli, x))
				.collect(Collectors.toSet());
	}

	private VnfcResourceInfoVnfcCpInfoEntity createVnfcResourceInfoVnfcCpInfoEntity(final ComputeTask ct, final List<VnfLiveInstance> vli, final VnfLinkPort x) {
		final String livePortName = namingStrategy.nameConnectionPort(x, ct);
		final VnfLiveInstance vp = findPort(vli, x.getToscaName(), ct.getRank());
		final VnfcResourceInfoVnfcCpInfoEntity ret = new VnfcResourceInfoVnfcCpInfoEntity();
		final VnfPortTask vpt = (VnfPortTask) vp.getTask();
		ret.setCpdId(vpt.getVnfLinkPort().getToscaName());
		ret.setCpProtocolInfo(createCpInfo(vli, vpt));
		ret.setId(vp.getId().toString());
		ret.setParentCpId(null);
		final String vt = vpt.getVnfLinkPort().getVirtualLink();
		if ((null != vt) && vt.startsWith(VIRTUAL_LINK)) {
			ret.setVnfExtCpId(vp.getId().toString());
		} else {
			ret.setVnfLinkPortId(vp.getId().toString());
		}
		return ret;
	}

	private static List<CpProtocolInfoEntity> createCpInfo(final List<VnfLiveInstance> vli, final VnfPortTask vp) {
		final CpProtocolInfoEntity ret = new CpProtocolInfoEntity();
		final IpOverEthernetAddressInfoEntity ioe = new IpOverEthernetAddressInfoEntity();
		ioe.setAddressRange(null);
		final List<IpOverEthernetAddressDataIpAddressesEntity> addrs = mapIps(vli, vp.getIpSubnet());
		ioe.setIpAddresses(addrs);
		ioe.setIsDynamic(false);
		ioe.setMacAddress(vp.getMacAddress());
		ioe.setSegmentationId(null);
		ioe.setType(TypeEnum.PV4);
		//
		ret.setIpOverEthernet(ioe);
		ret.setLayerProtocol(LayerProtocolEnum.IP_OVER_ETHERNET);
		ret.setVirtualCpAddress(null);
		return List.of(ret);
	}

	@SuppressWarnings("null")
	private static List<IpOverEthernetAddressDataIpAddressesEntity> mapIps(final List<VnfLiveInstance> vli, final Set<IpSubnet> ips) {
		return ips.stream().map(x -> {
			final IpOverEthernetAddressDataIpAddressesEntity ret = new IpOverEthernetAddressDataIpAddressesEntity();
			ret.setId(x.getId());
			ret.setAddresses(List.of(x.getIp()));
			ret.setType(IpType.IPV4);
			ret.setSubnetId(x.getSubnetId());
			final Optional<SubNetworkTask> sntOpt = findSubnetworkTaskByResourceId(vli, x.getSubnetId());
			if (sntOpt.isPresent()) {
				final SubNetworkTask snt = sntOpt.get();
				final IpPool pool = snt.getIpPool();
				final IpOverEthernetAddressDataAddressRangeEntity range = new IpOverEthernetAddressDataAddressRangeEntity();
				range.setMinAddress(pool.getStartIpAddress());
				range.setMaxAddress(pool.getEndIpAddress());
				ret.setAddressRange(range);
				ret.setNumDynamicAddresses(1);
			}
			return ret;
		}).toList();
	}

	@SuppressWarnings("null")
	private static Optional<SubNetworkTask> findSubnetworkTaskByResourceId(final List<VnfLiveInstance> vli, final String subnetId) {
		return vli.stream()
				.filter(x -> subnetId.equals(x.getResourceId()))
				.findFirst()
				.map(VnfLiveInstance::getTask)
				.map(SubNetworkTask.class::cast);
	}

	private static VnfLiveInstance findPort(final List<VnfLiveInstance> vli, final String toscaName, final int rank) {
		return vli.stream()
				.filter(x -> (x.getTask() instanceof VnfPortTask))
				.filter(x -> x.getTask().getToscaName().equals(toscaName))
				.filter(x -> x.getTask().getRank() == rank)
				.findFirst()
				.orElseThrow(() -> new GenericException("Could not find port " + toscaName + " with rank: " + String.format("%04d", rank)));
	}

	private InstantiationState isLive(final UUID id) {
		return vnfLiveInstanceJpa.findByVnfInstanceId(id).isEmpty() ? InstantiationState.NOT_INSTANTIATED : InstantiationState.INSTANTIATED;
	}

}
