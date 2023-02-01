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
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.LinkBitrateRequirements;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VirtualLinkToInstantiationLevelMapping type is a policy type describing
 * the bitrate requirements of a VL at each NS instantiation level of the
 * composite NS, as defined in ETSI GS NFV-IFA 014
 * [2]tosca.nodes.nfv.NsVirtualLink
 */
public class VirtualLinkToInstantiationLevelMapping extends Root {
	/**
	 * Bitrate requirements of a VL for each NS instantiation level.
	 */
	@Valid
	@NotNull
	@JsonProperty("bit_rate_requirements")
	@Size(min = 1)
	private Map<String, LinkBitrateRequirements> bitRateRequirements;

	@Valid
	private List<String> targets;

	@NotNull
	public Map<String, LinkBitrateRequirements> getBitRateRequirements() {
		return this.bitRateRequirements;
	}

	public void setBitRateRequirements(
			@NotNull final Map<String, LinkBitrateRequirements> bitRateRequirements) {
		this.bitRateRequirements = bitRateRequirements;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
