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
package com.ubiqube.etsi.mano.vnfm.v431.model.grant;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents the scale level of a VNF instance related to a scaling
 * aspect.
 */
@Schema(description = "This type represents the scale level of a VNF instance related to a scaling aspect. ")
@Validated

public class ScaleInfo {
	@JsonProperty("aspectId")
	private String aspectId = null;

	@JsonProperty("vnfdId")
	private String vnfdId = null;

	@JsonProperty("scaleLevel")
	private int scaleLevel;

	public ScaleInfo aspectId(final String aspectId) {
		this.aspectId = aspectId;
		return this;
	}

	/**
	 * Get aspectId
	 *
	 * @return aspectId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getAspectId() {
		return aspectId;
	}

	public void setAspectId(final String aspectId) {
		this.aspectId = aspectId;
	}

	public ScaleInfo vnfdId(final String vnfdId) {
		this.vnfdId = vnfdId;
		return this;
	}

	/**
	 * Get vnfdId
	 *
	 * @return vnfdId
	 **/
	@Schema(description = "")

	public String getVnfdId() {
		return vnfdId;
	}

	public void setVnfdId(final String vnfdId) {
		this.vnfdId = vnfdId;
	}

	public ScaleInfo scaleLevel(final int scaleLevel) {
		this.scaleLevel = scaleLevel;
		return this;
	}

	/**
	 * Get scaleLevel
	 *
	 * @return scaleLevel
	 **/
	@Schema(description = "")

	public int getScaleLevel() {
		return scaleLevel;
	}

	public void setscaleLevel(final int scaleLevel) {
		this.scaleLevel = scaleLevel;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ScaleInfo scaleInfo = (ScaleInfo) o;
		return Objects.equals(this.aspectId, scaleInfo.aspectId) &&
				Objects.equals(this.vnfdId, scaleInfo.vnfdId) &&
				Objects.equals(this.scaleLevel, scaleInfo.scaleLevel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(aspectId, vnfdId, scaleLevel);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ScaleInfo {\n");

		sb.append("    aspectId: ").append(toIndentedString(aspectId)).append("\n");
		sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
		sb.append("    scaleLevel: ").append(toIndentedString(scaleLevel)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
