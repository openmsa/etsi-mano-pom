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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Size;
import java.lang.Integer;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.ContainerDeployable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ExtendedResourceData;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.Hugepages;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Describes the resources of a single OS container within a VDU
 */
public class OsContainer extends Root {
	/**
	 * Size of ephemeral storage resources the OS container can maximum use (e.g. in GB).
	 */
	@Valid
	@JsonProperty("ephemeral_storage_resource_limit")
	private Size ephemeralStorageResourceLimit;

	/**
	 * Amount of memory resources requested for the OS container (e.g. in MB).
	 */
	@Valid
	@JsonProperty("requested_memory_resources")
	private Size requestedMemoryResources;

	/**
	 * Amount of memory resources the OS container can maximum use (e.g. in MB).
	 */
	@Valid
	@JsonProperty("memory_resource_limit")
	private Size memoryResourceLimit;

	/**
	 * Number of CPU resources the OS container can maximally use (e.g. in milli-CPU).
	 */
	@Valid
	@JsonProperty("cpu_resource_limit")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer cpuResourceLimit;

	/**
	 * The requirement for huge pages resources. Each element in the list indicates a hugepage size and the total memory requested for hugepages of that size.
	 */
	@Valid
	@JsonProperty("huge_pages_resources")
	private List<Hugepages> hugePagesResources;

	/**
	 * Human readable name of the OS container
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * Number of CPU resources requested for the OS container (e.g. in milli-CPU-s).
	 */
	@Valid
	@JsonProperty("requested_cpu_resources")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer requestedCpuResources;

	/**
	 * Human readable description of the OS container
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	/**
	 * Extended resources and their respective amount required by the container.
	 */
	@Valid
	@JsonProperty("extended_resource_requests")
	@javax.validation.constraints.Size(
			min = 1
	)
	private List<ExtendedResourceData> extendedResourceRequests;

	/**
	 * Size of ephemeral storage resources requested for the OS container (e.g. in GB).
	 */
	@Valid
	@JsonProperty("requested_ephemeral_storage_resources")
	private Size requestedEphemeralStorageResources;

	/**
	 * Caps.
	 */
	private ContainerDeployable containerDeployable;

	public Size getEphemeralStorageResourceLimit() {
		return this.ephemeralStorageResourceLimit;
	}

	public void setEphemeralStorageResourceLimit(final Size ephemeralStorageResourceLimit) {
		this.ephemeralStorageResourceLimit = ephemeralStorageResourceLimit;
	}

	public Size getRequestedMemoryResources() {
		return this.requestedMemoryResources;
	}

	public void setRequestedMemoryResources(final Size requestedMemoryResources) {
		this.requestedMemoryResources = requestedMemoryResources;
	}

	public Size getMemoryResourceLimit() {
		return this.memoryResourceLimit;
	}

	public void setMemoryResourceLimit(final Size memoryResourceLimit) {
		this.memoryResourceLimit = memoryResourceLimit;
	}

	public Integer getCpuResourceLimit() {
		return this.cpuResourceLimit;
	}

	public void setCpuResourceLimit(final Integer cpuResourceLimit) {
		this.cpuResourceLimit = cpuResourceLimit;
	}

	public List<Hugepages> getHugePagesResources() {
		return this.hugePagesResources;
	}

	public void setHugePagesResources(final List<Hugepages> hugePagesResources) {
		this.hugePagesResources = hugePagesResources;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public Integer getRequestedCpuResources() {
		return this.requestedCpuResources;
	}

	public void setRequestedCpuResources(final Integer requestedCpuResources) {
		this.requestedCpuResources = requestedCpuResources;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	public List<ExtendedResourceData> getExtendedResourceRequests() {
		return this.extendedResourceRequests;
	}

	public void setExtendedResourceRequests(
			final List<ExtendedResourceData> extendedResourceRequests) {
		this.extendedResourceRequests = extendedResourceRequests;
	}

	public Size getRequestedEphemeralStorageResources() {
		return this.requestedEphemeralStorageResources;
	}

	public void setRequestedEphemeralStorageResources(final Size requestedEphemeralStorageResources) {
		this.requestedEphemeralStorageResources = requestedEphemeralStorageResources;
	}

	public ContainerDeployable getContainerDeployable() {
		return this.containerDeployable;
	}

	public void setContainerDeployable(final ContainerDeployable containerDeployable) {
		this.containerDeployable = containerDeployable;
	}
}
