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

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Indicates for a given Vdu.Compute in a given level the number of instances to
 * deploy
 */
public class VduLevel extends Root {
	/**
	 * Number of instances of VNFC based on this VDU to deploy for this level.
	 */
	@Valid
	@NotNull
	@JsonProperty("number_of_instances")
	@DecimalMin(value = "0", inclusive = true)
	private Integer numberOfInstances;

	@NotNull
	public Integer getNumberOfInstances() {
		return this.numberOfInstances;
	}

	public void setNumberOfInstances(@NotNull final Integer numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
}
