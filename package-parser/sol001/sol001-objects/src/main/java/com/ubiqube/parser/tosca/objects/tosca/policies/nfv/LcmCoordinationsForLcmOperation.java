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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The LcmCoordinationsForLcmOperation type is a policy type representing
 * supported LCM coordination actions associated to a VNF LCM operation. This
 * policy concerns the whole VNF (deployment flavour) represented by the
 * topology_template and thus has no explicit target list.
 */
public class LcmCoordinationsForLcmOperation extends Root {
	/**
	 * List of names of coordination actions not specified within this VNFD as a
	 * TOSCA policy of a type derived from tosca.policies.nfv.LcmCoordinationAction.
	 */
	@Valid
	@JsonProperty("referenced_coordination_actions")
	private List<String> referencedCoordinationActions;

	/**
	 * The VNF LCM operation the LCM coordination actions are associated with.
	 */
	@Valid
	@NotNull
	@JsonProperty("vnf_lcm_operation")
	private String vnfLcmOperation;

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
	public String getVnfLcmOperation() {
		return this.vnfLcmOperation;
	}

	public void setVnfLcmOperation(@NotNull final String vnfLcmOperation) {
		this.vnfLcmOperation = vnfLcmOperation;
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
