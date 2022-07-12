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
 * Describes information about the addressing scheme and parameters applicable
 * to a CP
 */
public class AddressData extends Root {
	/**
	 * Describes the type of the address to be assigned to a connection point. The
	 * content type shall be aligned with the address type supported by the
	 * layerProtocol property of the connection point
	 */
	@Valid
	@NotNull
	@JsonProperty("address_type")
	private String addressType;

	/**
	 * Provides the information on the MAC addresses to be assigned to a connection
	 * point.
	 */
	@Valid
	@JsonProperty("l2_address_data")
	private L2AddressData l2AddressData;

	/**
	 * Provides the information on the IP addresses to be assigned to a connection
	 * point
	 */
	@Valid
	@JsonProperty("l3_address_data")
	private L3AddressData l3AddressData;

	@NotNull
	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(@NotNull final String addressType) {
		this.addressType = addressType;
	}

	public L2AddressData getL2AddressData() {
		return this.l2AddressData;
	}

	public void setL2AddressData(final L2AddressData l2AddressData) {
		this.l2AddressData = l2AddressData;
	}

	public L3AddressData getL3AddressData() {
		return this.l3AddressData;
	}

	public void setL3AddressData(final L3AddressData l3AddressData) {
		this.l3AddressData = l3AddressData;
	}
}
