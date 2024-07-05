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

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.Vim;

@Service
public class ExecutorFacade {

	private final ContainerExecutor containerExecutor;
	private final ZoneExecutor zoneExecutor;
	private final ServerGroupExecutor serverGroupExecutor;
	private final SoftwareImageExecutor softwareImageExecutor;
	private final FlavorExecutor flavorExecutor;
	private final NetworkExecutor networkExecutor;

	public ExecutorFacade(final ContainerExecutor containerExecutor, final ZoneExecutor zoneExecutor, final NetworkExecutor networkExecutor, final ServerGroupExecutor serverGroupExecutor, final SoftwareImageExecutor softwareImageExecutor, final FlavorExecutor flavorExecutor) {
		this.containerExecutor = containerExecutor;
		this.zoneExecutor = zoneExecutor;
		this.serverGroupExecutor = serverGroupExecutor;
		this.softwareImageExecutor = softwareImageExecutor;
		this.flavorExecutor = flavorExecutor;
		this.networkExecutor = networkExecutor;
	}

	public void addOrCreateK8sVim(final VimConnectionInformation<OpenstackV3InterfaceInfo, KeystoneAuthV3> vimInfo, final GrantResponse grants) {
		containerExecutor.addOrCreateK8sVim(vimInfo, grants);
	}

	public void addCirConnection(final GrantResponse grants) {
		containerExecutor.addCirConnection(grants);
	}

	public void removeK8sCluster(final GrantResponse grants) {
		containerExecutor.removeK8sCluster(grants);
	}

	public Callable<String> getZone(final VimConnectionInformation vimInfo, final GrantResponse grants) {
		return zoneExecutor.getZone(vimInfo, grants);
	}

	public Callable<ZoneGroupInformation> getServerGroup(final GrantResponse grants) {
		return serverGroupExecutor.getServerGroup(grants);
	}

	public Set<VimSoftwareImageEntity> getSoftwareImageSafe(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		return softwareImageExecutor.getSoftwareImageSafe(vimInfo, vim, grants);
	}

	public Collection<? extends VimComputeResourceFlavourEntity> getFlavors(final VimConnectionInformation vimInfo, final UUID id) {
		return flavorExecutor.getFlavors(vimInfo, id);
	}

	public void extractNetwork(final GrantResponse grants, final VimConnectionInformation vimInfo, final Vim vim) {
		networkExecutor.extractNetwork(grants, vimInfo, vim);
	}

}
