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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.ResourceHandle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents the information related to a network attachment
 * definition resource that provides the specification of the interface to be
 * used to connect one or multiple connection points to a secondary container
 * cluster network realizing a VL.
 */
@Schema(description = "This type represents the information related to a network attachment definition resource that provides the specification of the interface to be used to connect one or multiple connection points to a secondary container cluster network  realizing a VL. ")
@Validated

public class NetAttDefResourceData {
	@JsonProperty("netAttDefResourceId")
	private String netAttDefResourceId = null;

	@JsonProperty("resourceHandle")
	private ResourceHandle resourceHandle = null;

	public NetAttDefResourceData netAttDefResourceId(final String netAttDefResourceId) {
		this.netAttDefResourceId = netAttDefResourceId;
		return this;
	}

	/**
	 * Get netAttDefResourceId
	 *
	 * @return netAttDefResourceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getNetAttDefResourceId() {
		return netAttDefResourceId;
	}

	public void setNetAttDefResourceId(final String netAttDefResourceId) {
		this.netAttDefResourceId = netAttDefResourceId;
	}

	public NetAttDefResourceData resourceHandle(final ResourceHandle resourceHandle) {
		this.resourceHandle = resourceHandle;
		return this;
	}

	/**
	 * Get resourceHandle
	 *
	 * @return resourceHandle
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ResourceHandle getResourceHandle() {
		return resourceHandle;
	}

	public void setResourceHandle(final ResourceHandle resourceHandle) {
		this.resourceHandle = resourceHandle;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NetAttDefResourceData netAttDefResourceData = (NetAttDefResourceData) o;
		return Objects.equals(this.netAttDefResourceId, netAttDefResourceData.netAttDefResourceId) &&
				Objects.equals(this.resourceHandle, netAttDefResourceData.resourceHandle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(netAttDefResourceId, resourceHandle);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NetAttDefResourceData {\n");

		sb.append("    netAttDefResourceId: ").append(toIndentedString(netAttDefResourceId)).append("\n");
		sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
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
