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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Represents an element of a geographic coordinate location as specified in IETF RFC?6225.
 */
public class GeographicCoordinates extends Root {
	/**
	 * Altitude value as per RFC 6225
	 */
	@Valid
	@NotNull
	@JsonProperty("altitude")
	private String altitude;

	/**
	 * LatUnc as per RFC 6225
	 */
	@Valid
	@JsonProperty("latitude_uncertainty")
	private String latitudeUncertainty;

	/**
	 * LongUnc as per RFC 6225
	 */
	@Valid
	@JsonProperty("longitude_uncertainty")
	private String longitudeUncertainty;

	/**
	 * Latitude value as per RFC 6225
	 */
	@Valid
	@NotNull
	@JsonProperty("latitude")
	private String latitude;

	/**
	 * AType value as per RFC 6225
	 */
	@Valid
	@NotNull
	@JsonProperty("altitude_type")
	private String altitudeType;

	/**
	 * AltUnc as per RFC 6225
	 */
	@Valid
	@JsonProperty("altitude_uncertainty")
	private String altitudeUncertainty;

	/**
	 * Longitude value as per RFC 6225
	 */
	@Valid
	@NotNull
	@JsonProperty("longitude")
	private String longitude;

	@NotNull
	public String getAltitude() {
		return this.altitude;
	}

	public void setAltitude(@NotNull final String altitude) {
		this.altitude = altitude;
	}

	public String getLatitudeUncertainty() {
		return this.latitudeUncertainty;
	}

	public void setLatitudeUncertainty(final String latitudeUncertainty) {
		this.latitudeUncertainty = latitudeUncertainty;
	}

	public String getLongitudeUncertainty() {
		return this.longitudeUncertainty;
	}

	public void setLongitudeUncertainty(final String longitudeUncertainty) {
		this.longitudeUncertainty = longitudeUncertainty;
	}

	@NotNull
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(@NotNull final String latitude) {
		this.latitude = latitude;
	}

	@NotNull
	public String getAltitudeType() {
		return this.altitudeType;
	}

	public void setAltitudeType(@NotNull final String altitudeType) {
		this.altitudeType = altitudeType;
	}

	public String getAltitudeUncertainty() {
		return this.altitudeUncertainty;
	}

	public void setAltitudeUncertainty(final String altitudeUncertainty) {
		this.altitudeUncertainty = altitudeUncertainty;
	}

	@NotNull
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(@NotNull final String longitude) {
		this.longitude = longitude;
	}
}
