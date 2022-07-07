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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Integer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Specifies the minimum number of instances of a given Vdu.Compute node or VnfVirtualLink node which need to be preserved simultaneously.
 */
public class MinNumberOfPreservedInstances extends Root {
	/**
	 * Determines the size of the group for which the min_number_of_preserved_instances is specified. If not present the size is not limited.
	 */
	@Valid
	@JsonProperty("group_size")
	private Integer groupSize;

	/**
	 * The minimum number of instances which need to be preserved simultaneously within the group of the specified size.
	 */
	@Valid
	@NotNull
	@JsonProperty("min_number_of_preserved_instances")
	private Integer minNumberOfPreservedInstances;

	public Integer getGroupSize() {
		return this.groupSize;
	}

	public void setGroupSize(final Integer groupSize) {
		this.groupSize = groupSize;
	}

	@NotNull
	public Integer getMinNumberOfPreservedInstances() {
		return this.minNumberOfPreservedInstances;
	}

	public void setMinNumberOfPreservedInstances(
			@NotNull final Integer minNumberOfPreservedInstances) {
		this.minNumberOfPreservedInstances = minNumberOfPreservedInstances;
	}
}
