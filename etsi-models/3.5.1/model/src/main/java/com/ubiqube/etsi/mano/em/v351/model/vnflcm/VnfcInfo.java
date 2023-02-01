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
package com.ubiqube.etsi.mano.em.v351.model.vnflcm;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents the information about a VNFC instance that is part of a
 * VNF instance. * NOTE: This allows to represent the error condition that a
 * VNFC instance has lost its resources.
 */
@Schema(description = "This type represents the information about a VNFC instance that is part of a VNF instance. * NOTE: This allows to represent the error condition that a VNFC instance has lost its resources. ")
@Validated

public class VnfcInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("vduId")
	private String vduId = null;

	@JsonProperty("vnfcResourceInfoId")
	private String vnfcResourceInfoId = null;

	/**
	 * State of the VNFC instance. Permitted values: • STARTED: The VNFC instance is
	 * up and running. • STOPPED: The VNFC instance has been shut down
	 */
	public enum VnfcStateEnum {
		STARTED("STARTED"),

		STOPPED("STOPPED");

		private final String value;

		VnfcStateEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static VnfcStateEnum fromValue(final String text) {
			for (final VnfcStateEnum b : VnfcStateEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("vnfcState")
	private VnfcStateEnum vnfcState = null;

	@JsonProperty("vnfcConfigurableProperties")
	private Map<String, String> vnfcConfigurableProperties = null;

	public VnfcInfo id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public VnfcInfo vduId(final String vduId) {
		this.vduId = vduId;
		return this;
	}

	/**
	 * Get vduId
	 *
	 * @return vduId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVduId() {
		return vduId;
	}

	public void setVduId(final String vduId) {
		this.vduId = vduId;
	}

	public VnfcInfo vnfcResourceInfoId(final String vnfcResourceInfoId) {
		this.vnfcResourceInfoId = vnfcResourceInfoId;
		return this;
	}

	/**
	 * Get vnfcResourceInfoId
	 *
	 * @return vnfcResourceInfoId
	 **/
	@Schema(description = "")

	public String getVnfcResourceInfoId() {
		return vnfcResourceInfoId;
	}

	public void setVnfcResourceInfoId(final String vnfcResourceInfoId) {
		this.vnfcResourceInfoId = vnfcResourceInfoId;
	}

	public VnfcInfo vnfcState(final VnfcStateEnum vnfcState) {
		this.vnfcState = vnfcState;
		return this;
	}

	/**
	 * Get vnfcState
	 *
	 * @return vnfcState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public VnfcStateEnum getVnfcState() {
		return vnfcState;
	}

	public void setVnfcState(final VnfcStateEnum vnfcState) {
		this.vnfcState = vnfcState;
	}

	public VnfcInfo vnfcConfigurableProperties(final Map<String, String> vnfcConfigurableProperties) {
		this.vnfcConfigurableProperties = vnfcConfigurableProperties;
		return this;
	}

	/**
	 * Get vnfcConfigurableProperties
	 *
	 * @return vnfcConfigurableProperties
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getVnfcConfigurableProperties() {
		return vnfcConfigurableProperties;
	}

	public void setVnfcConfigurableProperties(final Map<String, String> vnfcConfigurableProperties) {
		this.vnfcConfigurableProperties = vnfcConfigurableProperties;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfcInfo vnfcInfo = (VnfcInfo) o;
		return Objects.equals(this.id, vnfcInfo.id) &&
				Objects.equals(this.vduId, vnfcInfo.vduId) &&
				Objects.equals(this.vnfcResourceInfoId, vnfcInfo.vnfcResourceInfoId) &&
				Objects.equals(this.vnfcState, vnfcInfo.vnfcState) &&
				Objects.equals(this.vnfcConfigurableProperties, vnfcInfo.vnfcConfigurableProperties);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, vduId, vnfcResourceInfoId, vnfcState, vnfcConfigurableProperties);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfcInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    vduId: ").append(toIndentedString(vduId)).append("\n");
		sb.append("    vnfcResourceInfoId: ").append(toIndentedString(vnfcResourceInfoId)).append("\n");
		sb.append("    vnfcState: ").append(toIndentedString(vnfcState)).append("\n");
		sb.append("    vnfcConfigurableProperties: ").append(toIndentedString(vnfcConfigurableProperties)).append("\n");
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
