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
package com.ubiqube.parser.tosca.objects.tosca.capabilities;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Scalable extends Root {
	@Valid
	@NotNull
	@JsonProperty("min_instances")
	private Integer minInstances = 1;

	@Valid
	@NotNull
	@JsonProperty("max_instances")
	private Integer maxInstances = 1;

	@Valid
	@JsonProperty("default_instances")
	private Integer defaultInstances;

	@NotNull
	public Integer getMinInstances() {
		return this.minInstances;
	}

	public void setMinInstances(@NotNull final Integer minInstances) {
		this.minInstances = minInstances;
	}

	@NotNull
	public Integer getMaxInstances() {
		return this.maxInstances;
	}

	public void setMaxInstances(@NotNull final Integer maxInstances) {
		this.maxInstances = maxInstances;
	}

	public Integer getDefaultInstances() {
		return this.defaultInstances;
	}

	public void setDefaultInstances(final Integer defaultInstances) {
		this.defaultInstances = defaultInstances;
	}
}
