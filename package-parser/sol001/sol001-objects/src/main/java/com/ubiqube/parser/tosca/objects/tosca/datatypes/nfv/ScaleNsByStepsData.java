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

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the information needed to scale an NS instance by one or more
 * scaling steps, with respect to a particular NS scaling aspect
 */
public class ScaleNsByStepsData extends Root {
	/**
	 * Indicates the type of the scale operation requested.
	 */
	@Valid
	@NotNull
	@JsonProperty("scaling_direction")
	private String scalingDirection;

	/**
	 * Identifier of the scaling aspect.
	 */
	@Valid
	@NotNull
	@JsonProperty("aspect")
	private String aspect;

	/**
	 * Number of scaling steps to be executed.
	 */
	@Valid
	@NotNull
	@JsonProperty("number_of_steps")
	@DecimalMin("0")
	private Integer numberOfSteps = 1;

	@NotNull
	public String getScalingDirection() {
		return this.scalingDirection;
	}

	public void setScalingDirection(@NotNull final String scalingDirection) {
		this.scalingDirection = scalingDirection;
	}

	@NotNull
	public String getAspect() {
		return this.aspect;
	}

	public void setAspect(@NotNull final String aspect) {
		this.aspect = aspect;
	}

	@NotNull
	public Integer getNumberOfSteps() {
		return this.numberOfSteps;
	}

	public void setNumberOfSteps(@NotNull final Integer numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
}
