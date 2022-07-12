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
 * Represents an element of a civic location as specified in IETF RFC 4776 [11].
 */
public class CivicAddressElement extends Root {
	/**
	 * caValue as per RFC4776.
	 */
	@Valid
	@NotNull
	@JsonProperty("ca_value")
	private String caValue;

	/**
	 * caType as per RFC4776
	 */
	@Valid
	@NotNull
	@JsonProperty("ca_type")
	private String caType;

	@NotNull
	public String getCaValue() {
		return this.caValue;
	}

	public void setCaValue(@NotNull final String caValue) {
		this.caValue = caValue;
	}

	@NotNull
	public String getCaType() {
		return this.caType;
	}

	public void setCaType(@NotNull final String caType) {
		this.caType = caType;
	}
}
