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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.ResourceHandle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type provides information about an externally-managed internal virtual
 * link for VNFs
 */
@Schema(description = "This type provides information about an externally-managed internal virtual link for VNFs ")
@Validated

public class ExtManagedVirtualLinkInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("vnfdId")
	private String vnfdId = null;

	@JsonProperty("vnfVirtualLinkDescId")
	private String vnfVirtualLinkDescId = null;

	@JsonProperty("networkResource")
	@Valid
	private ResourceHandle networkResource;

	@JsonProperty("vnfLinkPorts")
	@Valid
	private List<VnfLinkPortInfo> vnfLinkPorts = null;

	@JsonProperty("vnfNetAttDefResource")
	private List<NetAttDefResourceInfo> vnfNetAttDefResource = null;

	@JsonProperty("extManagedMultisiteVirtualLinkId")
	private String extManagedMultisiteVirtualLinkId = null;

	public ExtManagedVirtualLinkInfo id(final String id) {
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

	public ExtManagedVirtualLinkInfo vnfdId(final String vnfdId) {
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

	public ExtManagedVirtualLinkInfo vnfVirtualLinkDescId(final String vnfVirtualLinkDescId) {
		this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
		return this;
	}

	/**
	 * Get vnfVirtualLinkDescId
	 *
	 * @return vnfVirtualLinkDescId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVnfVirtualLinkDescId() {
		return vnfVirtualLinkDescId;
	}

	public void setVnfVirtualLinkDescId(final String vnfVirtualLinkDescId) {
		this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
	}

	public ExtManagedVirtualLinkInfo networkResource(final ResourceHandle networkResource) {
		this.networkResource = networkResource;
		return this;
	}

	/**
	 * Reference to the VirtualNetwork resource or multi-site connectivity service
	 * providing this VL.
	 *
	 * @return networkResource
	 **/
	@Schema(required = true, description = "Reference to the VirtualNetwork resource or multi-site connectivity service providing this VL. ")
	@NotNull
	@Valid
	public ResourceHandle getNetworkResource() {
		return networkResource;
	}

	public void setNetworkResource(final ResourceHandle networkResource) {
		this.networkResource = networkResource;
	}

	public ExtManagedVirtualLinkInfo vnfLinkPorts(final List<VnfLinkPortInfo> vnfLinkPorts) {
		this.vnfLinkPorts = vnfLinkPorts;
		return this;
	}

	public ExtManagedVirtualLinkInfo addVnfLinkPortsItem(final VnfLinkPortInfo vnfLinkPortsItem) {
		if (this.vnfLinkPorts == null) {
			this.vnfLinkPorts = new ArrayList<>();
		}
		this.vnfLinkPorts.add(vnfLinkPortsItem);
		return this;
	}

	/**
	 * Link ports of this VL.
	 *
	 * @return vnfLinkPorts
	 **/
	@Schema(description = "Link ports of this VL. ")
	@Valid
	public List<VnfLinkPortInfo> getVnfLinkPorts() {
		return vnfLinkPorts;
	}

	public void setVnfLinkPorts(final List<VnfLinkPortInfo> vnfLinkPorts) {
		this.vnfLinkPorts = vnfLinkPorts;
	}

	public ExtManagedVirtualLinkInfo vnfNetAttDefResource(final List<NetAttDefResourceInfo> vnfNetAttDefResource) {
		this.vnfNetAttDefResource = vnfNetAttDefResource;
		return this;
	}

	/**
	 * Get vnfNetAttDefResource
	 *
	 * @return vnfNetAttDefResource
	 **/
	@Schema(description = "")

	@Valid
	public List<NetAttDefResourceInfo> getVnfNetAttDefResource() {
		return vnfNetAttDefResource;
	}

	public void setVnfNetAttDefResource(final List<NetAttDefResourceInfo> vnfNetAttDefResource) {
		this.vnfNetAttDefResource = vnfNetAttDefResource;
	}

	public ExtManagedVirtualLinkInfo extManagedMultisiteVirtualLinkId(final String extManagedMultisiteVirtualLinkId) {
		this.extManagedMultisiteVirtualLinkId = extManagedMultisiteVirtualLinkId;
		return this;
	}

	/**
	 * Get extManagedMultisiteVirtualLinkId
	 *
	 * @return extManagedMultisiteVirtualLinkId
	 **/
	@Schema(description = "")

	public String getExtManagedMultisiteVirtualLinkId() {
		return extManagedMultisiteVirtualLinkId;
	}

	public void setExtManagedMultisiteVirtualLinkId(final String extManagedMultisiteVirtualLinkId) {
		this.extManagedMultisiteVirtualLinkId = extManagedMultisiteVirtualLinkId;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ExtManagedVirtualLinkInfo extManagedVirtualLinkInfo = (ExtManagedVirtualLinkInfo) o;
		return Objects.equals(this.id, extManagedVirtualLinkInfo.id) &&
				Objects.equals(this.vnfdId, extManagedVirtualLinkInfo.vnfdId) &&
				Objects.equals(this.vnfVirtualLinkDescId, extManagedVirtualLinkInfo.vnfVirtualLinkDescId) &&
				Objects.equals(this.networkResource, extManagedVirtualLinkInfo.networkResource) &&
				Objects.equals(this.vnfLinkPorts, extManagedVirtualLinkInfo.vnfLinkPorts) &&
				Objects.equals(this.vnfNetAttDefResource, extManagedVirtualLinkInfo.vnfNetAttDefResource) &&
				Objects.equals(this.extManagedMultisiteVirtualLinkId, extManagedVirtualLinkInfo.extManagedMultisiteVirtualLinkId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, vnfdId, vnfVirtualLinkDescId, networkResource, vnfLinkPorts, vnfNetAttDefResource, extManagedMultisiteVirtualLinkId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ExtManagedVirtualLinkInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
		sb.append("    vnfVirtualLinkDescId: ").append(toIndentedString(vnfVirtualLinkDescId)).append("\n");
		sb.append("    networkResource: ").append(toIndentedString(networkResource)).append("\n");
		sb.append("    vnfLinkPorts: ").append(toIndentedString(vnfLinkPorts)).append("\n");
		sb.append("    vnfNetAttDefResource: ").append(toIndentedString(vnfNetAttDefResource)).append("\n");
		sb.append("    extManagedMultisiteVirtualLinkId: ").append(toIndentedString(extManagedMultisiteVirtualLinkId)).append("\n");
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
