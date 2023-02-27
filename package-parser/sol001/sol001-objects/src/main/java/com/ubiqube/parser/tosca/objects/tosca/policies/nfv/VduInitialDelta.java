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
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VduInitialDelta type is a policy type representing the Vdu.Compute detail
 * of an initial delta used for horizontal scaling, as defined in ETSI GS
 * NFV-IFA 011
 * [1].tosca.nodes.nfv.Vdu.Computetosca.nodes.nfv.Vdu.OsContainerDeployableUnit
 */
public class VduInitialDelta extends Root {
	/**
	 * Represents the initial minimum size of the VNF.
	 */
	@Valid
	@NotNull
	@JsonProperty("initial_delta")
	private VduLevel initialDelta;

	@Valid
	private List<String> targets;

	@NotNull
	public VduLevel getInitialDelta() {
		return this.initialDelta;
	}

	public void setInitialDelta(@NotNull final VduLevel initialDelta) {
		this.initialDelta = initialDelta;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
