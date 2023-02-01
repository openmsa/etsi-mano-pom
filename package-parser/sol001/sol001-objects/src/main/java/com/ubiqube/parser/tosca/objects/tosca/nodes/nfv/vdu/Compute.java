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

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualBindable;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualCompute;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.BootData;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduProfile;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VnfcMonitoringParameter;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Describes the virtual compute part of a VDU which is a construct supporting
 * the description of the deployment and operational behavior of a VNFC
 */
public class Compute extends Root {
	/**
	 * Contains the information used to customize a virtualised compute resource at
	 * boot time. The bootData may contain variable parts that are replaced by
	 * deployment specific values before being sent to the VIM.
	 */
	@Valid
	@JsonProperty("boot_data")
	private BootData bootData;

	/**
	 * Human readable name of the VDU
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * Describes constraints on the NFVI for the VNFC instance(s) created from this
	 * VDU. This property is reserved for future use in the present document.
	 */
	@Valid
	@JsonProperty("nfvi_constraints")
	private Map<String, String> nfviConstraints;

	/**
	 * Describes monitoring parameters applicable to a VNFC instantiated from this
	 * VDU
	 */
	@Valid
	@JsonProperty("monitoring_parameters")
	private Map<String, VnfcMonitoringParameter> monitoringParameters;

	/**
	 * Human readable description of the VDU
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	/**
	 * indicates whether the order of the virtual_storage requirements is used as
	 * the boot index (the first requirement represents the lowest index and defines
	 * highest boot priority)
	 */
	@Valid
	@NotNull
	@JsonProperty("boot_order")
	private Boolean bootOrder = false;

	/**
	 * Defines additional instantiation data for the VDU.Compute node
	 */
	@Valid
	@NotNull
	@JsonProperty("vdu_profile")
	private VduProfile vduProfile;

	/**
	 * Caps.
	 */
	private VirtualCompute virtualCompute;

	/**
	 * Caps.
	 */
	private VirtualBindable virtualBinding;

	@Occurence({ "0", "UNBOUNDED" })
	@Capability("tosca.capabilities.nfv.VirtualStorage")
	@Relationship("tosca.relationships.nfv.AttachesTo")
	@JsonProperty("virtual_storage")
	private List<String> virtualStorageReq;

	public BootData getBootData() {
		return this.bootData;
	}

	public void setBootData(final BootData bootData) {
		this.bootData = bootData;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public Map<String, String> getNfviConstraints() {
		return this.nfviConstraints;
	}

	public void setNfviConstraints(final Map<String, String> nfviConstraints) {
		this.nfviConstraints = nfviConstraints;
	}

	public Map<String, VnfcMonitoringParameter> getMonitoringParameters() {
		return this.monitoringParameters;
	}

	public void setMonitoringParameters(
			final Map<String, VnfcMonitoringParameter> monitoringParameters) {
		this.monitoringParameters = monitoringParameters;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	@NotNull
	public Boolean getBootOrder() {
		return this.bootOrder;
	}

	public void setBootOrder(@NotNull final Boolean bootOrder) {
		this.bootOrder = bootOrder;
	}

	@NotNull
	public VduProfile getVduProfile() {
		return this.vduProfile;
	}

	public void setVduProfile(@NotNull final VduProfile vduProfile) {
		this.vduProfile = vduProfile;
	}

	public VirtualCompute getVirtualCompute() {
		return this.virtualCompute;
	}

	public void setVirtualCompute(final VirtualCompute virtualCompute) {
		this.virtualCompute = virtualCompute;
	}

	public VirtualBindable getVirtualBinding() {
		return this.virtualBinding;
	}

	public void setVirtualBinding(final VirtualBindable virtualBinding) {
		this.virtualBinding = virtualBinding;
	}

	public List<String> getVirtualStorageReq() {
		return this.virtualStorageReq;
	}

	public void setVirtualStorageReq(final List<String> virtualStorageReq) {
		this.virtualStorageReq = virtualStorageReq;
	}
}
