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
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualLinkBitrateLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VirtualLinkBitrateScalingAspectDeltas type is a policy type representing the VnfVirtualLink detail of an aspect deltas used for horizontal scaling, as defined in ETSI GS NFV-IFA 011 [1].tosca.nodes.nfv.VnfVirtualLink
 */
public class VirtualLinkBitrateScalingAspectDeltas extends Root {
	/**
	 * Represents the scaling aspect to which this policy applies.
	 */
	@Valid
	@NotNull
	@JsonProperty("aspect")
	private String aspect;

	/**
	 * Describes the VnfVirtualLink scaling deltas to be applied for every scaling steps of a particular aspect.
	 */
	@Valid
	@NotNull
	@JsonProperty("deltas")
	@Size(
			min = 1
	)
	private Map<String, VirtualLinkBitrateLevel> deltas;

	@Valid
	private List<String> targets;

	@NotNull
	public String getAspect() {
		return this.aspect;
	}

	public void setAspect(@NotNull final String aspect) {
		this.aspect = aspect;
	}

	@NotNull
	public Map<String, VirtualLinkBitrateLevel> getDeltas() {
		return this.deltas;
	}

	public void setDeltas(@NotNull final Map<String, VirtualLinkBitrateLevel> deltas) {
		this.deltas = deltas;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
