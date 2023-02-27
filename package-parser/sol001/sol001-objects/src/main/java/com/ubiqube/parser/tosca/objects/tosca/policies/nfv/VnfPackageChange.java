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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VnfPackageChangeComponentMapping;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VnfPackageChangeSelector;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * policy type specifying the processes and rules to be used for performing the
 * resource related tasks, to change VNF instance to a different VNF Package
 * (destination package)tosca.nodes.nfv.VNF
 */
public class VnfPackageChange extends Root {
	/**
	 * List of names of coordination actions not specified within this VNFD as a
	 * TOSCA policy of a type derived from tosca.policies.nfv.LcmCoordinationAction.
	 */
	@Valid
	@JsonProperty("referenced_coordination_actions")
	private List<String> referencedCoordinationActions;

	/**
	 * Identifies the deployment flavour in the destination VNF package for which
	 * this change applies. The flavour ID is defined in the destination VNF
	 * package.
	 */
	@Valid
	@NotNull
	@JsonProperty("destination_flavour_id")
	private String destinationFlavourId;

	/**
	 * Mapping information related to identifiers of components in source VNFD and
	 * destination VNFD that concern to the change process.
	 */
	@Valid
	@JsonProperty("component_mappings")
	private List<VnfPackageChangeComponentMapping> componentMappings;

	/**
	 * Additional information to qualify further the change between the two
	 * versions.
	 */
	@Valid
	@JsonProperty("additional_modification_description")
	private String additionalModificationDescription;

	/**
	 * Information to identify the source and destination VNFD for the change, and
	 * the related deployment flavours.
	 */
	@Valid
	@NotNull
	@JsonProperty("selector")
	@Size(min = 1)
	private List<VnfPackageChangeSelector> selector;

	/**
	 * Specifies the type of modification resulting from transitioning from
	 * srcVnfdId to dstVnfdId. The possible values are UP indicating that the
	 * destination VNF version is newer than the source version, DOWN indicating
	 * that the destination VNF version is older than the source version.
	 */
	@Valid
	@NotNull
	@JsonProperty("modification_qualifier")
	private String modificationQualifier;

	/**
	 * List of applicable supported LCM coordination action names (action_name)
	 * specified in this VNFD as a TOSCA policy of a type derived from
	 * tosca.policies.nfv.LcmCoordinationAction.
	 */
	@Valid
	@JsonProperty("actions")
	private List<String> actions;

	@Valid
	private List<String> targets;

	public List<String> getReferencedCoordinationActions() {
		return this.referencedCoordinationActions;
	}

	public void setReferencedCoordinationActions(final List<String> referencedCoordinationActions) {
		this.referencedCoordinationActions = referencedCoordinationActions;
	}

	@NotNull
	public String getDestinationFlavourId() {
		return this.destinationFlavourId;
	}

	public void setDestinationFlavourId(@NotNull final String destinationFlavourId) {
		this.destinationFlavourId = destinationFlavourId;
	}

	public List<VnfPackageChangeComponentMapping> getComponentMappings() {
		return this.componentMappings;
	}

	public void setComponentMappings(final List<VnfPackageChangeComponentMapping> componentMappings) {
		this.componentMappings = componentMappings;
	}

	public String getAdditionalModificationDescription() {
		return this.additionalModificationDescription;
	}

	public void setAdditionalModificationDescription(final String additionalModificationDescription) {
		this.additionalModificationDescription = additionalModificationDescription;
	}

	@NotNull
	public List<VnfPackageChangeSelector> getSelector() {
		return this.selector;
	}

	public void setSelector(@NotNull final List<VnfPackageChangeSelector> selector) {
		this.selector = selector;
	}

	@NotNull
	public String getModificationQualifier() {
		return this.modificationQualifier;
	}

	public void setModificationQualifier(@NotNull final String modificationQualifier) {
		this.modificationQualifier = modificationQualifier;
	}

	public List<String> getActions() {
		return this.actions;
	}

	public void setActions(final List<String> actions) {
		this.actions = actions;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
