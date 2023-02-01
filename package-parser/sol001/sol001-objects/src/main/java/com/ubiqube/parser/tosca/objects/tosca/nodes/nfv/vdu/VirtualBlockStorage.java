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
package com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualStorage;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NfviMaintenanceInfo;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualBlockStorageData;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * This node type describes the specifications of requirements related to
 * virtual block storage resources
 */
public class VirtualBlockStorage extends Root {
	/**
	 * Indicates whether the virtual storage descriptor shall be instantiated per
	 * VNFC instance.
	 */
	@Valid
	@NotNull
	@JsonProperty("per_vnfc_instance")
	private Boolean perVnfcInstance = true;

	/**
	 * Provides information on the rules to be observed when an instance based on
	 * this VirtualBlockStorage is impacted during NFVI operation and maintenance
	 * (e.g. NFVI resource upgrades).
	 */
	@Valid
	@JsonProperty("nfvi_maintenance_info")
	private NfviMaintenanceInfo nfviMaintenanceInfo;

	/**
	 * Describes the block storage characteristics.
	 */
	@Valid
	@NotNull
	@JsonProperty("virtual_block_storage_data")
	private VirtualBlockStorageData virtualBlockStorageData;

	/**
	 * Caps.Defines the capabilities of virtual_storage.
	 */
	private VirtualStorage virtualStorage;

	@NotNull
	public Boolean getPerVnfcInstance() {
		return this.perVnfcInstance;
	}

	public void setPerVnfcInstance(@NotNull final Boolean perVnfcInstance) {
		this.perVnfcInstance = perVnfcInstance;
	}

	public NfviMaintenanceInfo getNfviMaintenanceInfo() {
		return this.nfviMaintenanceInfo;
	}

	public void setNfviMaintenanceInfo(final NfviMaintenanceInfo nfviMaintenanceInfo) {
		this.nfviMaintenanceInfo = nfviMaintenanceInfo;
	}

	@NotNull
	public VirtualBlockStorageData getVirtualBlockStorageData() {
		return this.virtualBlockStorageData;
	}

	public void setVirtualBlockStorageData(
			@NotNull final VirtualBlockStorageData virtualBlockStorageData) {
		this.virtualBlockStorageData = virtualBlockStorageData;
	}

	public VirtualStorage getVirtualStorage() {
		return this.virtualStorage;
	}

	public void setVirtualStorage(final VirtualStorage virtualStorage) {
		this.virtualStorage = virtualStorage;
	}
}
