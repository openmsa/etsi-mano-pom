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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the details of an aspect used for horizontal scaling
 */
public class ScalingAspect extends Root {
	/**
	 * List of scaling deltas to be applied for the different subsequent scaling
	 * steps of this aspect. The first entry in the array shall correspond to the
	 * first scaling step (between scale levels 0 to 1) and the last entry in the
	 * array shall correspond to the last scaling step (between maxScaleLevel-1 and
	 * maxScaleLevel)
	 */
	@Valid
	@JsonProperty("step_deltas")
	private List<String> stepDeltas;

	/**
	 * Total number of scaling steps that can be applied w.r.t. this aspect. The
	 * value of this property corresponds to the number of scaling steps can be
	 * applied to this aspect when scaling it from the minimum scale level (i.e. 0)
	 * to the maximum scale level defined by this property
	 */
	@Valid
	@NotNull
	@JsonProperty("max_scale_level")
	@DecimalMin(value = "0", inclusive = true)
	private Integer maxScaleLevel;

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

	public List<String> getStepDeltas() {
		return this.stepDeltas;
	}

	public void setStepDeltas(final List<String> stepDeltas) {
		this.stepDeltas = stepDeltas;
	}

	@NotNull
	public Integer getMaxScaleLevel() {
		return this.maxScaleLevel;
	}

	public void setMaxScaleLevel(@NotNull final Integer maxScaleLevel) {
		this.maxScaleLevel = maxScaleLevel;
	}

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
}
