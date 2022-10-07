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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The VnfIndicator policy type is a base policy type for defining VNF indicator
 * specific policies that define the conditions to assess and the action to
 * perform when a VNF indicator changes value as defined in ETSI GS NFV-IFA 011
 * [1].tosca.nodes.nfv.VNF
 */
public class VnfIndicator extends Root {
	/**
	 * Describe the source of the indicator.
	 */
	@Valid
	@NotNull
	@JsonProperty("source")
	private String source;

	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	@Valid
	@JsonProperty("targets")
	private List<String> targets;

	@NotNull
	public String getSource() {
		return this.source;
	}

	public void setSource(@NotNull final String source) {
		this.source = source;
	}
	
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(@NotNull String name) {
		this.name = name;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
	
	
}
