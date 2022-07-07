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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualLinkBitrateLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VirtualLinkBitrateInitialDelta type is a policy type representing the VnfVirtualLink detail of an initial deltas used for horizontal scaling, as defined in ETSI GS NFV-IFA 011 [1].tosca.nodes.nfv.VnfVirtualLink
 */
public class VirtualLinkBitrateInitialDelta extends Root {
	/**
	 * Represents the initial minimum size of the VNF.
	 */
	@Valid
	@NotNull
	@JsonProperty("initial_delta")
	private VirtualLinkBitrateLevel initialDelta;

	@Valid
	private List<String> targets;

	@NotNull
	public VirtualLinkBitrateLevel getInitialDelta() {
		return this.initialDelta;
	}

	public void setInitialDelta(@NotNull final VirtualLinkBitrateLevel initialDelta) {
		this.initialDelta = initialDelta;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
