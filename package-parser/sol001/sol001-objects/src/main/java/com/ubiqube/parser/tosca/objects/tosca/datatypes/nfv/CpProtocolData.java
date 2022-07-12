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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes and associates the protocol layer that a CP uses together with
 * other protocol and connection point information
 */
public class CpProtocolData extends Root {
	/**
	 * One of the values of the property layer_protocols of the CP
	 */
	@Valid
	@NotNull
	@JsonProperty("associated_layer_protocol")
	private String associatedLayerProtocol;

	/**
	 * Provides information on the addresses to be assigned to the CP
	 */
	@Valid
	@JsonProperty("address_data")
	private List<AddressData> addressData;

	@NotNull
	public String getAssociatedLayerProtocol() {
		return this.associatedLayerProtocol;
	}

	public void setAssociatedLayerProtocol(@NotNull final String associatedLayerProtocol) {
		this.associatedLayerProtocol = associatedLayerProtocol;
	}

	public List<AddressData> getAddressData() {
		return this.addressData;
	}

	public void setAddressData(final List<AddressData> addressData) {
		this.addressData = addressData;
	}
}
