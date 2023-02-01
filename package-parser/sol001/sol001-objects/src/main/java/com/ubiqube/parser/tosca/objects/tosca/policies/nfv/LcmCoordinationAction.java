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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The LcmCoordinationAction type is a policy type representing the LCM
 * coordination actions supported by a VNF and/or expected to be supported by
 * its EM for a particular VNF LCM operation. This policy concerns the whole VNF
 * (deployment flavour) represented by the topology_template and thus has no
 * explicit target list.
 */
public class LcmCoordinationAction extends Root {
	/**
	 * Coordination action name.
	 */
	@Valid
	@NotNull
	@JsonProperty("action_name")
	private String actionName;

	@Valid
	private List<String> targets;

	@NotNull
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(@NotNull final String actionName) {
		this.actionName = actionName;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
