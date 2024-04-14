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
package com.ubiqube.etsi.mano.service;

import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.BlueZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ZoneInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;

public class TestBluePrint extends VnfBlueprint {

	private final Set<TestTask> task = Set.of();
	private Set<VimConnectionInformation> vimConn;
	private Set<TestTask> tasks;
	private TestInstance instance;
	private UUID id;
	private BlueprintParameters parameters = new BlueprintParameters();

	@Override
	public BlueprintParameters getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(final BlueprintParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setVimConnections(final Set<VimConnectionInformation> vimConnections) {
		vimConn = vimConnections;

	}

	@Override
	public void setZoneGroups(final Set<BlueZoneGroupInformation> mapAsSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setZones(final Set<ZoneInfoEntity> zones) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGrantsRequestId(final String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExtManagedVirtualLinks(final Set<ExtManagedVirtualLinkDataEntity> extManagedVirtualLinks) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<VimConnectionInformation> getVimConnections() {
		return vimConn;
	}

	public void setInstance(final TestInstance instance) {
		this.instance = instance;
	}

	@Override
	public void addVimConnection(final VimConnectionInformation vimConnection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExtVirtualLinks(final Set<ExtVirtualLinkDataEntity> extVirtualLinks) {
		// TODO Auto-generated method stub

	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void setId(final UUID id) {
		this.id = id;
	}

}
