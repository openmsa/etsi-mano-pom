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
 * This type represents information about an VNF external VL.
 */
@Schema(description = "This type represents information about an VNF external VL.")
@Validated

public class ExtVirtualLinkInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("resourceHandle")
	private ResourceHandle resourceHandle = null;

	@JsonProperty("extLinkPorts")
	@Valid
	private List<ExtLinkPortInfo> extLinkPorts = null;

	@JsonProperty("extNetAttDefResource")
	@Valid
	private List<NetAttDefResourceInfo> extNetAttDefResource = null;

	@JsonProperty("currentVnfExtCpData")
	private List<VnfExtCpData> currentVnfExtCpData = null;

	public ExtVirtualLinkInfo id(final String id) {
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

	public ExtVirtualLinkInfo resourceHandle(final ResourceHandle resourceHandle) {
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

	public ExtVirtualLinkInfo extLinkPorts(final List<ExtLinkPortInfo> extLinkPorts) {
		this.extLinkPorts = extLinkPorts;
		return this;
	}

	public ExtVirtualLinkInfo addExtLinkPortsItem(final ExtLinkPortInfo extLinkPortsItem) {
		if (this.extLinkPorts == null) {
			this.extLinkPorts = new ArrayList<>();
		}
		this.extLinkPorts.add(extLinkPortsItem);
		return this;
	}

	/**
	 * Link ports of this VL.
	 *
	 * @return extLinkPorts
	 **/
	@Schema(description = "Link ports of this VL. ")
	@Valid
	public List<ExtLinkPortInfo> getExtLinkPorts() {
		return extLinkPorts;
	}

	public void setExtLinkPorts(final List<ExtLinkPortInfo> extLinkPorts) {
		this.extLinkPorts = extLinkPorts;
	}

	public ExtVirtualLinkInfo extNetAttDefResource(final List<NetAttDefResourceInfo> extNetAttDefResource) {
		this.extNetAttDefResource = extNetAttDefResource;
		return this;
	}

	public ExtVirtualLinkInfo addExtNetAttDefResourceItem(final NetAttDefResourceInfo extNetAttDefResourceItem) {
		if (this.extNetAttDefResource == null) {
			this.extNetAttDefResource = new ArrayList<>();
		}
		this.extNetAttDefResource.add(extNetAttDefResourceItem);
		return this;
	}

	/**
	 * Network attachment definition resources that provide the specification of the
	 * interface to attach connection points to this VL.
	 *
	 * @return extNetAttDefResource
	 **/
	@Schema(description = "Network attachment definition resources that provide the specification of the interface to attach connection points to this VL. ")
	@Valid
	public List<NetAttDefResourceInfo> getExtNetAttDefResource() {
		return extNetAttDefResource;
	}

	public void setExtNetAttDefResource(final List<NetAttDefResourceInfo> extNetAttDefResource) {
		this.extNetAttDefResource = extNetAttDefResource;
	}

	public ExtVirtualLinkInfo currentVnfExtCpData(final List<VnfExtCpData> currentVnfExtCpData) {
		this.currentVnfExtCpData = currentVnfExtCpData;
		return this;
	}

	/**
	 * Get currentVnfExtCpData
	 *
	 * @return currentVnfExtCpData
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public List<VnfExtCpData> getCurrentVnfExtCpData() {
		return currentVnfExtCpData;
	}

	public void setCurrentVnfExtCpData(final List<VnfExtCpData> currentVnfExtCpData) {
		this.currentVnfExtCpData = currentVnfExtCpData;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ExtVirtualLinkInfo extVirtualLinkInfo = (ExtVirtualLinkInfo) o;
		return Objects.equals(this.id, extVirtualLinkInfo.id) &&
				Objects.equals(this.resourceHandle, extVirtualLinkInfo.resourceHandle) &&
				Objects.equals(this.extLinkPorts, extVirtualLinkInfo.extLinkPorts) &&
				Objects.equals(this.extNetAttDefResource, extVirtualLinkInfo.extNetAttDefResource) &&
				Objects.equals(this.currentVnfExtCpData, extVirtualLinkInfo.currentVnfExtCpData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, resourceHandle, extLinkPorts, extNetAttDefResource, currentVnfExtCpData);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ExtVirtualLinkInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
		sb.append("    extLinkPorts: ").append(toIndentedString(extLinkPorts)).append("\n");
		sb.append("    extNetAttDefResource: ").append(toIndentedString(extNetAttDefResource)).append("\n");
		sb.append("    currentVnfExtCpData: ").append(toIndentedString(currentVnfExtCpData)).append("\n");
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
