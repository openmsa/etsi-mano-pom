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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.CpProtocolInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.CpProtocolInfoEntity.LayerProtocolEnum;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressDataAddressRangeEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressDataIpAddressesEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.IpOverEthernetAddressInfoEntity.TypeEnum;
import com.ubiqube.etsi.mano.dao.mano.IpType;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoVnfcCpInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.IpSubnet;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.IpPool;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.vim.SubNetwork;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.mapping.VnfcResourceInfoEntityMapping;

@Service
public class ComputeExtractor implements VnfLcmExtractor {
	private static final String VIRTUAL_LINK = "virtual_link";
	private final VnfcResourceInfoEntityMapping mapper;
	private final VimManager vimManager;

	public ComputeExtractor(final VnfcResourceInfoEntityMapping mapper, final VimManager vimManager) {
		this.mapper = mapper;
		this.vimManager = vimManager;
	}

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vli) {
		final List<VnfLiveInstance> computeVli = vli.stream()
				.filter(x -> x.getTask() instanceof ComputeTask)
				.toList();
		final Set<VnfcResourceInfoEntity> vnfcResourceInfo = computeVli.stream()
				.map(x -> createVnfcResourceInfoEntity(vli, x)).collect(Collectors.toSet());
		params.setVnfcResourceInfo(vnfcResourceInfo);
	}

	private VnfcResourceInfoEntity createVnfcResourceInfoEntity(final List<VnfLiveInstance> vlis, final VnfLiveInstance vli) {
		final ComputeTask ct = (ComputeTask) vli.getTask();
		final VnfcResourceInfoEntity ret = mapper.map(vli);
		// Probably done previously.
		ret.setComputeResource(mapper.map(vli.getTask()));
		ret.setId(vli.getId().toString());
		ret.setStorageResourceIds(ct.getVnfCompute().getStorages());
		ret.setVduId(ct.getToscaName());
		ret.setZoneId(ct.getZoneId());
		ret.getComputeResource().setResourceId(vli.getResourceId());
		final Set<VnfcResourceInfoVnfcCpInfoEntity> cpInfo = extractCpInfo(ct, vlis);
		ret.setVnfcCpInfo(cpInfo);
		return ret;
	}

	@SuppressWarnings("null")
	private Set<VnfcResourceInfoVnfcCpInfoEntity> extractCpInfo(final ComputeTask ct, final List<VnfLiveInstance> vli) {
		return ct.getVnfCompute().getPorts().stream()
				.map(x -> createVnfcResourceInfoVnfcCpInfoEntity(ct, vli, x))
				.collect(Collectors.toSet());
	}

	private VnfcResourceInfoVnfcCpInfoEntity createVnfcResourceInfoVnfcCpInfoEntity(final ComputeTask ct, final List<VnfLiveInstance> vli, final VnfLinkPort linkPort) {
		final VnfLiveInstance vp = findPortByNameAndRank(vli, linkPort.getToscaName(), ct.getRank());
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

	@SuppressWarnings("null")
	private static VnfLiveInstance findPortByNameAndRank(final List<VnfLiveInstance> vli, final String toscaName, final int rank) {
		return vli.stream()
				.filter(x -> (x.getTask() instanceof VnfPortTask))
				.filter(x -> x.getTask().getToscaName().equals(toscaName))
				.filter(x -> x.getTask().getRank() == rank)
				.findFirst()
				.orElseThrow(() -> new GenericException("Could not find port " + toscaName + " with rank: " + String.format("%04d", rank)));
	}

	private List<CpProtocolInfoEntity> createCpInfo(final List<VnfLiveInstance> vli, final VnfPortTask vp) {
		final CpProtocolInfoEntity ret = new CpProtocolInfoEntity();
		final List<IpOverEthernetAddressDataIpAddressesEntity> addrs = mapIps(vli, vp);
		final IpOverEthernetAddressInfoEntity ioe = createIoe(vp, addrs);
		ret.setIpOverEthernet(ioe);
		ret.setLayerProtocol(LayerProtocolEnum.IP_OVER_ETHERNET);
		ret.setVirtualCpAddress(null);
		return List.of(ret);
	}

	private static IpOverEthernetAddressInfoEntity createIoe(final VnfPortTask vp, final List<IpOverEthernetAddressDataIpAddressesEntity> addrs) {
		final IpOverEthernetAddressInfoEntity ioe = new IpOverEthernetAddressInfoEntity();
		ioe.setAddressRange(null);
		ioe.setIpAddresses(addrs);
		ioe.setIsDynamic(false);
		ioe.setMacAddress(vp.getMacAddress());
		ioe.setSegmentationId(null);
		ioe.setType(TypeEnum.PV4);
		return ioe;
	}

	@SuppressWarnings("null")
	private List<IpOverEthernetAddressDataIpAddressesEntity> mapIps(final List<VnfLiveInstance> vli, final VnfPortTask vpt) {
		final Set<IpSubnet> vp = vpt.getIpSubnet();
		return vp.stream().map(x -> {
			final IpOverEthernetAddressDataIpAddressesEntity ret = createIpOverEthernetAddressDataIpAddressesEntity(x);
			final Optional<SubNetworkTask> sntOpt = findSubnetworkTaskByResourceId(vli, x.getSubnetId());
			if (sntOpt.isPresent()) {
				final SubNetworkTask snt = sntOpt.get();
				final IpPool pool = snt.getIpPool();
				final IpOverEthernetAddressDataAddressRangeEntity range = mapPoolToRange(pool);
				ret.setAddressRange(range);
			} else {
				findOsSubNetInfo(ret, vpt, x.getSubnetId());
			}
			return ret;
		}).toList();
	}

	private void findOsSubNetInfo(final IpOverEthernetAddressDataIpAddressesEntity ret, final VnfPortTask vpt, final String subnetId) {
		vpt.getVimConnectionId();
		final VimConnectionInformation vimConn = vimManager.findVimByVimId(vpt.getVimConnectionId());
		final Vim vim = vimManager.getVimById(vimConn.getId());
		final SubNetwork res = vim.network(vimConn).findSubNetworkById(subnetId);
		ret.setAddressRange(poolToRange(res.getPools()));
	}

	private IpOverEthernetAddressDataAddressRangeEntity poolToRange(final List<IpPool> pools) {
		final IpOverEthernetAddressDataAddressRangeEntity range = new IpOverEthernetAddressDataAddressRangeEntity();
		final IpPool p = pools.get(0);
		range.setMinAddress(p.getStartIpAddress());
		range.setMaxAddress(p.getEndIpAddress());
		return range;
	}

	private static IpOverEthernetAddressDataAddressRangeEntity mapPoolToRange(final IpPool pool) {
		final IpOverEthernetAddressDataAddressRangeEntity range = new IpOverEthernetAddressDataAddressRangeEntity();
		range.setMinAddress(pool.getStartIpAddress());
		range.setMaxAddress(pool.getEndIpAddress());
		return range;
	}

	private static IpOverEthernetAddressDataIpAddressesEntity createIpOverEthernetAddressDataIpAddressesEntity(final IpSubnet ipSubnet) {
		final IpOverEthernetAddressDataIpAddressesEntity ret = new IpOverEthernetAddressDataIpAddressesEntity();
		ret.setId(ipSubnet.getId());
		ret.setAddresses(List.of(ipSubnet.getIp()));
		ret.setType(IpType.IPV4);
		ret.setSubnetId(ipSubnet.getSubnetId());
		return ret;
	}

	@SuppressWarnings("null")
	private static Optional<SubNetworkTask> findSubnetworkTaskByResourceId(final List<VnfLiveInstance> vli, final String subnetId) {
		return vli.stream()
				.filter(x -> subnetId.equals(x.getResourceId()))
				.findFirst()
				.map(VnfLiveInstance::getTask)
				.map(SubNetworkTask.class::cast);
	}

}
