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
 * supports the specification of requirements related to virtual memory of a
 * virtual compute resource
 */
public class VirtualMemory extends Root {
	/**
	 * It specifies the memory allocation to be cognisant of the relevant
	 * process/core allocation.
	 */
	@Valid
	@NotNull
	@JsonProperty("numa_enabled")
	private Boolean numaEnabled = false;

	/**
	 * Amount of virtual memory.
	 */
	@Valid
	@NotNull
	@JsonProperty("virtual_mem_size")
	private Size virtualMemSize;

	/**
	 * The hardware platform specific VDU memory requirements. A map of strings that
	 * contains a set of key-value pairs that describes hardware platform specific
	 * VDU memory requirements.
	 */
	@Valid
	@JsonProperty("vdu_mem_requirements")
	private Map<String, String> vduMemRequirements;

	/**
	 * The memory core oversubscription policy in terms of virtual memory to
	 * physical memory on the platform.
	 */
	@Valid
	@JsonProperty("virtual_mem_oversubscription_policy")
	private String virtualMemOversubscriptionPolicy;

	@NotNull
	public Boolean getNumaEnabled() {
		return this.numaEnabled;
	}

	public void setNumaEnabled(@NotNull final Boolean numaEnabled) {
		this.numaEnabled = numaEnabled;
	}

	@NotNull
	public Size getVirtualMemSize() {
		return this.virtualMemSize;
	}

	public void setVirtualMemSize(@NotNull final Size virtualMemSize) {
		this.virtualMemSize = virtualMemSize;
	}

	public Map<String, String> getVduMemRequirements() {
		return this.vduMemRequirements;
	}

	public void setVduMemRequirements(final Map<String, String> vduMemRequirements) {
		this.vduMemRequirements = vduMemRequirements;
	}

	public String getVirtualMemOversubscriptionPolicy() {
		return this.virtualMemOversubscriptionPolicy;
	}

	public void setVirtualMemOversubscriptionPolicy(final String virtualMemOversubscriptionPolicy) {
		this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
	}
}
