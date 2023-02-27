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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the details of an aspect used for horizontal scaling
 */
public class NsScalingAspect extends Root {
	/**
	 * Human readable name of the aspect
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * Human readable description of the aspect
	 */
	@Valid
	@NotNull
	@JsonProperty("description")
	private String description;

	/**
	 * Description of the NS levels for this scaling aspect.
	 */
	@Valid
	@NotNull
	@JsonProperty("ns_scale_levels")
	private Map<String, NsLevels> nsScaleLevels;

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	@NotNull
	public Map<String, NsLevels> getNsScaleLevels() {
		return this.nsScaleLevels;
	}

	public void setNsScaleLevels(@NotNull final Map<String, NsLevels> nsScaleLevels) {
		this.nsScaleLevels = nsScaleLevels;
	}
}
