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
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Represents geographical information on the location where a PNF is deployed.
 */
public class LocationInfo extends Root {
	/**
	 * Country code
	 */
	@Valid
	@NotNull
	@JsonProperty("country_code")
	private String countryCode;

	/**
	 * Geographic coordinates (e.g. Altitude, Longitude, Latitude) where the PNF is deployed.
	 */
	@Valid
	@JsonProperty("geographic_coordinates")
	private GeographicCoordinates geographicCoordinates;

	/**
	 * Elements composing the civic address where the PNF is deployed.
	 */
	@Valid
	@JsonProperty("civic_address_element")
	private List<CivicAddressElement> civicAddressElement;

	@NotNull
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(@NotNull final String countryCode) {
		this.countryCode = countryCode;
	}

	public GeographicCoordinates getGeographicCoordinates() {
		return this.geographicCoordinates;
	}

	public void setGeographicCoordinates(final GeographicCoordinates geographicCoordinates) {
		this.geographicCoordinates = geographicCoordinates;
	}

	public List<CivicAddressElement> getCivicAddressElement() {
		return this.civicAddressElement;
	}

	public void setCivicAddressElement(final List<CivicAddressElement> civicAddressElement) {
		this.civicAddressElement = civicAddressElement;
	}
}
