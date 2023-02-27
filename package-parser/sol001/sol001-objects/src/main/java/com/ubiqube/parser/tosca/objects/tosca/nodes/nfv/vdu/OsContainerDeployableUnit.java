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

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.AssociableVdu;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualBindable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.LogicalNodeData;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.RequestedAdditionalCapability;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduProfile;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Describes the aggregate of container of a VDU (when realized as OS
 * containers) which supporting the description of the deployment and
 * operational behavior of a VNFC
 */
public class OsContainerDeployableUnit extends Root {
	/**
	 * Describes additional capability for a particular OS container
	 */
	@Valid
	@JsonProperty("requested_additional_capabilities")
	private Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities;

	/**
	 * Defines the parameter names for constraints expected to be assigned to MCIOs
	 * realizing this Vdu.OsContainerDeployableUnit. The value specifies the
	 * standardized semantical context of the MCIO constraints.
	 */
	@Valid
	@JsonProperty("mcio_constraint_params")
	private List<String> mcioConstraintParams;

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
	 * Human readable description of the VDU
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	/**
	 * Describes the Logical Node requirements
	 */
	@Valid
	@JsonProperty("logical_node")
	private Map<String, LogicalNodeData> logicalNode;

	/**
	 * Defines additional instantiation data for the Vdu.OsContainerDeployableUnit
	 * node
	 */
	@Valid
	@NotNull
	@JsonProperty("vdu_profile")
	private VduProfile vduProfile;

	/**
	 * Caps.
	 */
	private VirtualBindable virtualBinding;

	/**
	 * Caps.
	 */
	private AssociableVdu associable;

	@Occurence({ "1", "UNBOUNDED" })
	@Capability("tosca.capabilities.nfv.ContainerDeployable")
	@Relationship("tosca.relationships.nfv.DeploysTo")
	@JsonProperty("container")
	private List<String> containerReq;

	@Occurence({ "0", "UNBOUNDED" })
	@Capability("tosca.capabilities.nfv.VirtualStorage")
	@Relationship("tosca.relationships.nfv.AttachesTo")
	@JsonProperty("virtual_storage")
	private List<String> virtualStorageReq;

	public Map<String, RequestedAdditionalCapability> getRequestedAdditionalCapabilities() {
		return this.requestedAdditionalCapabilities;
	}

	public void setRequestedAdditionalCapabilities(
			final Map<String, RequestedAdditionalCapability> requestedAdditionalCapabilities) {
		this.requestedAdditionalCapabilities = requestedAdditionalCapabilities;
	}

	public List<String> getMcioConstraintParams() {
		return this.mcioConstraintParams;
	}

	public void setMcioConstraintParams(final List<String> mcioConstraintParams) {
		this.mcioConstraintParams = mcioConstraintParams;
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

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	public Map<String, LogicalNodeData> getLogicalNode() {
		return this.logicalNode;
	}

	public void setLogicalNode(final Map<String, LogicalNodeData> logicalNode) {
		this.logicalNode = logicalNode;
	}

	@NotNull
	public VduProfile getVduProfile() {
		return this.vduProfile;
	}

	public void setVduProfile(@NotNull final VduProfile vduProfile) {
		this.vduProfile = vduProfile;
	}

	public VirtualBindable getVirtualBinding() {
		return this.virtualBinding;
	}

	public void setVirtualBinding(final VirtualBindable virtualBinding) {
		this.virtualBinding = virtualBinding;
	}

	public AssociableVdu getAssociable() {
		return this.associable;
	}

	public void setAssociable(final AssociableVdu associable) {
		this.associable = associable;
	}

	public List<String> getContainerReq() {
		return this.containerReq;
	}

	public void setContainerReq(final List<String> containerReq) {
		this.containerReq = containerReq;
	}

	public List<String> getVirtualStorageReq() {
		return this.virtualStorageReq;
	}

	public void setVirtualStorageReq(final List<String> virtualStorageReq) {
		this.virtualStorageReq = virtualStorageReq;
	}
}
