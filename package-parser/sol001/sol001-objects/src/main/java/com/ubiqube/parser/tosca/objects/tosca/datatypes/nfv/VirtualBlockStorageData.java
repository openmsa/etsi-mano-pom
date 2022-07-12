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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;
import com.ubiqube.parser.tosca.scalar.Size;

/**
 * VirtualBlockStorageData describes block storage requirements associated with
 * compute resources in a particular VDU, either as a local disk or as virtual
 * attached storage
 */
public class VirtualBlockStorageData extends Root {
	/**
	 * The hardware platform specific storage requirements. A map of strings that
	 * contains a set of key-value pairs that represents the hardware platform
	 * specific storage deployment requirements
	 */
	@Valid
	@JsonProperty("vdu_storage_requirements")
	private Map<String, String> vduStorageRequirements;

	/**
	 * Size of virtualised storage resource
	 */
	@Valid
	@NotNull
	@JsonProperty("size_of_storage")
	private Size sizeOfStorage;

	/**
	 * Indicates if the storage support RDMA
	 */
	@Valid
	@NotNull
	@JsonProperty("rdma_enabled")
	private Boolean rdmaEnabled = false;

	public Map<String, String> getVduStorageRequirements() {
		return this.vduStorageRequirements;
	}

	public void setVduStorageRequirements(final Map<String, String> vduStorageRequirements) {
		this.vduStorageRequirements = vduStorageRequirements;
	}

	@NotNull
	public Size getSizeOfStorage() {
		return this.sizeOfStorage;
	}

	public void setSizeOfStorage(@NotNull final Size sizeOfStorage) {
		this.sizeOfStorage = sizeOfStorage;
	}

	@NotNull
	public Boolean getRdmaEnabled() {
		return this.rdmaEnabled;
	}

	public void setRdmaEnabled(@NotNull final Boolean rdmaEnabled) {
		this.rdmaEnabled = rdmaEnabled;
	}
}
