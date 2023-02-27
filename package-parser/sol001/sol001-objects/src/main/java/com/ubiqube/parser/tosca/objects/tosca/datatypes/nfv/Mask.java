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

public class Mask extends Root {
	/**
	 * Indicates the number of bits to be matched.
	 */
	@Valid
	@NotNull
	@JsonProperty("length")
	@DecimalMin(value = "1", inclusive = true)
	private Integer length;

	/**
	 * Provide the sequence of bit values to be matched.
	 */
	@Valid
	@NotNull
	@JsonProperty("value")
	private String value;

	/**
	 * Indicates the offset between the last bit of the source mac address and the
	 * first bit of the sequence of bits to be matched.
	 */
	@Valid
	@NotNull
	@JsonProperty("starting_point")
	@DecimalMin(value = "1", inclusive = true)
	private Integer startingPoint;

	@NotNull
	public Integer getLength() {
		return this.length;
	}

	public void setLength(@NotNull final Integer length) {
		this.length = length;
	}

	@NotNull
	public String getValue() {
		return this.value;
	}

	public void setValue(@NotNull final String value) {
		this.value = value;
	}

	@NotNull
	public Integer getStartingPoint() {
		return this.startingPoint;
	}

	public void setStartingPoint(@NotNull final Integer startingPoint) {
		this.startingPoint = startingPoint;
	}
}
