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
import javax.validation.constraints.DecimalMin;

/**
 * describes QoS data for a given VL used in a VNF deployment flavour
 */
public class NsVirtualLinkQos extends Qos {
	/**
	 * Specifies the priority level in case of congestion on the underlying physical links
	 */
	@Valid
	@JsonProperty("priority")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer priority;

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(final Integer priority) {
		this.priority = priority;
	}
}
