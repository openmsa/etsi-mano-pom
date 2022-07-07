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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Boolean;
import java.lang.String;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes requirements on a virtual network interface
 */
public class VirtualNetworkInterfaceRequirements extends Root {
	/**
	 * The network interface requirements. A map of strings that contain a set of key-value pairs that describes the hardware platform specific  network interface deployment requirements.
	 */
	@Valid
	@NotNull
	@JsonProperty("network_interface_requirements")
	private Map<String, String> networkInterfaceRequirements;

	/**
	 * Provides a human readable name for the requirement.
	 */
	@Valid
	@JsonProperty("name")
	private String name;

	/**
	 * Indicates whether fulfilling the constraint is mandatory (TRUE) for successful operation or desirable (FALSE).
	 */
	@Valid
	@NotNull
	@JsonProperty("support_mandatory")
	private Boolean supportMandatory;

	/**
	 * Provides a human readable description of the requirement.
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	/**
	 * references (couples) the CP with any logical node I/O requirements (for network devices) that may have been created. Linking these attributes is necessary so that so that I/O requirements that need to be articulated at the logical node level can be associated with the network interface requirements associated with the CP.
	 */
	@Valid
	@JsonProperty("nic_io_requirements")
	private LogicalNodeData nicIoRequirements;

	@NotNull
	public Map<String, String> getNetworkInterfaceRequirements() {
		return this.networkInterfaceRequirements;
	}

	public void setNetworkInterfaceRequirements(
			@NotNull final Map<String, String> networkInterfaceRequirements) {
		this.networkInterfaceRequirements = networkInterfaceRequirements;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Boolean getSupportMandatory() {
		return this.supportMandatory;
	}

	public void setSupportMandatory(@NotNull final Boolean supportMandatory) {
		this.supportMandatory = supportMandatory;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LogicalNodeData getNicIoRequirements() {
		return this.nicIoRequirements;
	}

	public void setNicIoRequirements(final LogicalNodeData nicIoRequirements) {
		this.nicIoRequirements = nicIoRequirements;
	}
}
