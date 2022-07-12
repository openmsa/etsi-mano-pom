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

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes information enabling the VNF instance to access the NFV-MANO
 * interfaces produced by the VNFM
 */
public class VnfmInterfaceInfo extends Root {
	/**
	 * Provides credential enabling access to the interface
	 */
	@Valid
	@JsonProperty("credentials")
	private Map<String, String> credentials;

	/**
	 * Identifies an interface produced by the VNFM.
	 */
	@Valid
	@NotNull
	@JsonProperty("interface_name")
	private String interfaceName;

	/**
	 * Provide additional data to access the interface endpoint
	 */
	@Valid
	@JsonProperty("details")
	private InterfaceDetails details;

	public Map<String, String> getCredentials() {
		return this.credentials;
	}

	public void setCredentials(final Map<String, String> credentials) {
		this.credentials = credentials;
	}

	@NotNull
	public String getInterfaceName() {
		return this.interfaceName;
	}

	public void setInterfaceName(@NotNull final String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public InterfaceDetails getDetails() {
		return this.details;
	}

	public void setDetails(final InterfaceDetails details) {
		this.details = details;
	}
}
