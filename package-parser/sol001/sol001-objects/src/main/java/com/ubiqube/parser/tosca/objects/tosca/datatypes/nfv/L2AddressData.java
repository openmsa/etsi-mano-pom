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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes the information on the MAC addresses to be assigned to a connection
 * point.
 */
public class L2AddressData extends Root {
	/**
	 * Specifies if the address assignment is the responsibility of management and
	 * orchestration function or not. If it is set to True, it is the management and
	 * orchestration function responsibility
	 */
	@Valid
	@NotNull
	@JsonProperty("mac_address_assignment")
	private Boolean macAddressAssignment;

	@NotNull
	public Boolean getMacAddressAssignment() {
		return this.macAddressAssignment;
	}

	public void setMacAddressAssignment(@NotNull final Boolean macAddressAssignment) {
		this.macAddressAssignment = macAddressAssignment;
	}
}
