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
package com.ubiqube.etsi.mano.service.grant.executor;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.grant.ccm.CcmManager;
import com.ubiqube.etsi.mano.service.mapping.ConnectionMapping;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@Service
public class ContainerExecutor {
	private final CcmManager ccmManager;
	private final CirConnectionManager cirManager;
	private final VnfPackageService vnfPackageService;
	private final VimManager vimManager;
	private final ConnectionMapping connectionMapping;

	public ContainerExecutor(final CcmManager ccmManager, final CirConnectionManager cirManager, final VimManager vimManager, final VnfPackageService vnfPackageService) {
		this.ccmManager = ccmManager;
		this.cirManager = cirManager;
		this.vnfPackageService = vnfPackageService;
		this.vimManager = vimManager;
		this.connectionMapping = Mappers.getMapper(ConnectionMapping.class);
	}

	public void addCirConnection(final GrantResponse grants) {
		final Map<String, ConnectionInformation> cirMap = StreamSupport.stream(cirManager.findAll().spliterator(), false)
				.collect(Collectors.toMap(ConnectionInformation::getName, x -> x));
		grants.setCirConnectionInfo(cirMap);
	}

	public void removeK8sCluster(final GrantResponse grants) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(grants.getVnfdId());
		if (!haveCni(vnfPackage)) {
			return;
		}
		ccmManager.getTerminateCluster(grants.getVnfInstanceId());
	}

	public void addOrCreateK8sVim(final VimConnectionInformation vci, final GrantResponse grants) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(grants.getVnfdId());
		if (!haveCni(vnfPackage)) {
			return;
		}
		VimConnectionInformation vimc = ccmManager.getVimConnection(vci, grants, vnfPackage);
		vimc = storeIfNeeded(vimc);
		final Set vims = new LinkedHashSet<>(grants.getVimConnections());
		vims.add(vimc);
		grants.setVimConnections(vims);
	}

	private VimConnectionInformation storeIfNeeded(final VimConnectionInformation vimc) {
		final Optional<VimConnectionInformation> res = vimManager.findOptionalVimByVimId(vimc.getVimId());
		if (res.isPresent()) {
			return res.get();
		}
		return vimManager.save(vimc);
	}

	private static boolean haveCni(final VnfPackage vnfPackage) {
		return (haveOsContainer(vnfPackage) || haveDeloyableUnit(vnfPackage));
	}

	private static boolean haveDeloyableUnit(final VnfPackage vnfPackage) {
		return ((null != vnfPackage.getOsContainerDesc()) && !vnfPackage.getOsContainerDesc().isEmpty()) || ((null != vnfPackage.getMciops()) && !vnfPackage.getMciops().isEmpty());
	}

	private static boolean haveOsContainer(final VnfPackage vnfPackage) {
		return ((null != vnfPackage.getOsContainer()) && !vnfPackage.getOsContainer().isEmpty()) || ((null != vnfPackage.getOsContainerDeployableUnits()) && !vnfPackage.getOsContainerDeployableUnits().isEmpty());
	}

}
