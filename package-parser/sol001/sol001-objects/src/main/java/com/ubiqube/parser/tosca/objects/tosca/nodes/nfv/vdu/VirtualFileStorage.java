/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualStorage;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NfviMaintenanceInfo;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualFileStorageData;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * This node type describes the specifications of requirements related to
 * virtual file storage resources
 */
public class VirtualFileStorage extends Root {
	/**
	 * Indicates whether the virtual storage descriptor shall be instantiated per
	 * VNFC instance.
	 */
	@Valid
	@NotNull
	@JsonProperty("per_vnfc_instance")
	private Boolean perVnfcInstance = true;

	/**
	 * Describes the file storage characteristics.
	 */
	@Valid
	@NotNull
	@JsonProperty("virtual_file_storage_data")
	private VirtualFileStorageData virtualFileStorageData;

	/**
	 * Provides information on the rules to be observed when an instance based on
	 * this VirtualFileStorage is impacted during NFVI operation and maintenance
	 * (e.g. NFVI resource upgrades).
	 */
	@Valid
	@JsonProperty("nfvi_maintenance_info")
	private NfviMaintenanceInfo nfviMaintenanceInfo;

	/**
	 * Caps.
	 */
	private VirtualStorage virtualStorage;

	@Occurence({ "1", "1" })
	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	@NotNull
	public Boolean getPerVnfcInstance() {
		return this.perVnfcInstance;
	}

	public void setPerVnfcInstance(@NotNull final Boolean perVnfcInstance) {
		this.perVnfcInstance = perVnfcInstance;
	}

	@NotNull
	public VirtualFileStorageData getVirtualFileStorageData() {
		return this.virtualFileStorageData;
	}

	public void setVirtualFileStorageData(
			@NotNull final VirtualFileStorageData virtualFileStorageData) {
		this.virtualFileStorageData = virtualFileStorageData;
	}

	public NfviMaintenanceInfo getNfviMaintenanceInfo() {
		return this.nfviMaintenanceInfo;
	}

	public void setNfviMaintenanceInfo(final NfviMaintenanceInfo nfviMaintenanceInfo) {
		this.nfviMaintenanceInfo = nfviMaintenanceInfo;
	}

	public VirtualStorage getVirtualStorage() {
		return this.virtualStorage;
	}

	public void setVirtualStorage(final VirtualStorage virtualStorage) {
		this.virtualStorage = virtualStorage;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}
}
