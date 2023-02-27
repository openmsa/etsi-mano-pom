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
package com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Node;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.LogicalNodeData;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.RequestedAdditionalCapability;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualBlockStorageData;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualCpu;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualMemory;

/**
 * Describes the capabilities related to virtual compute resources
 */
public class VirtualCompute extends Node {
	/**
	 * Describes virtual memory of the virtualized compute
	 */
	@Valid
	@NotNull
	@JsonProperty("virtual_memory")
	private VirtualMemory virtualMemory;

	/**
	 * A list of virtual system disks created and destroyed as part of the VM
	 * lifecycle
	 */
	@Valid
	@JsonProperty("virtual_local_storage")
	private List<VirtualBlockStorageData> virtualLocalStorage;

	/**
	 * Describes additional capability for a particular VDU
	 */
	@Valid
	@JsonProperty("requested_additional_capabilities")
	private Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities;

	@Valid
	@JsonProperty("compute_requirements")
	private Map<String, String> computeRequirements;

	/**
	 * Describes the Logical Node requirements
	 */
	@Valid
	@JsonProperty("logical_node")
	private Map<String, LogicalNodeData> logicalNode;

	/**
	 * Describes virtual CPU(s) of the virtualized compute
	 */
	@Valid
	@NotNull
	@JsonProperty("virtual_cpu")
	private VirtualCpu virtualCpu;

	@NotNull
	public VirtualMemory getVirtualMemory() {
		return this.virtualMemory;
	}

	public void setVirtualMemory(@NotNull final VirtualMemory virtualMemory) {
		this.virtualMemory = virtualMemory;
	}

	public List<VirtualBlockStorageData> getVirtualLocalStorage() {
		return this.virtualLocalStorage;
	}

	public void setVirtualLocalStorage(final List<VirtualBlockStorageData> virtualLocalStorage) {
		this.virtualLocalStorage = virtualLocalStorage;
	}

	public Map<String, RequestedAdditionalCapability> getRequestedAdditionalCapabilities() {
		return this.requestedAdditionalCapabilities;
	}

	public void setRequestedAdditionalCapabilities(
			final Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities) {
		this.requestedAdditionalCapabilities = requestedAdditionalCapabilities;
	}

	public Map<String, String> getComputeRequirements() {
		return this.computeRequirements;
	}

	public void setComputeRequirements(final Map<String, String> computeRequirements) {
		this.computeRequirements = computeRequirements;
	}

	public Map<String, LogicalNodeData> getLogicalNode() {
		return this.logicalNode;
	}

	public void setLogicalNode(final Map<String, LogicalNodeData> logicalNode) {
		this.logicalNode = logicalNode;
	}

	@NotNull
	public VirtualCpu getVirtualCpu() {
		return this.virtualCpu;
	}

	public void setVirtualCpu(@NotNull final VirtualCpu virtualCpu) {
		this.virtualCpu = virtualCpu;
	}
}
