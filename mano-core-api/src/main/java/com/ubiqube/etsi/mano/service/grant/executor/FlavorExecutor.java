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

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.event.flavor.FlavorManager;
import com.ubiqube.etsi.mano.service.grant.GrantSupport;

@Service
public class FlavorExecutor {
	private final FlavorManager flavorManager;
	private final GrantSupport grantSupport;

	public FlavorExecutor(final FlavorManager flavorManager, final GrantSupport grantSupport) {
		this.flavorManager = flavorManager;
		this.grantSupport = grantSupport;
	}

	public List<VimComputeResourceFlavourEntity> getFlavors(final VimConnectionInformation vimConnectionInformation, final UUID id) {
		final Set<VnfCompute> comp = grantSupport.getVnfCompute(id);
		return flavorManager.getFlavors(vimConnectionInformation, comp);
	}

}
