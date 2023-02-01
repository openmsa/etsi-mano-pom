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
package com.ubiqube.etsi.mano.em.v431.model.vnflcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type provides information related to a virtual CP instance of a VNF.
 * NOTE: A consumer of the VNF LCM interface can learn the actual VNFC instances
 * implementing the service accessible via the virtual CP instance by querying
 * the \&quot;vnfcResourceInfo\&quot; from the \&quot;InstantiatedVnfInfo\&quot;
 * and filtering by corresponding \&quot;vduIds\&quot; values.
 */
@Schema(description = "This type provides information related to a virtual CP instance of a VNF. NOTE: A consumer of the VNF LCM interface can learn the actual VNFC instances implementing the service        accessible via the virtual CP instance by querying the \"vnfcResourceInfo\" from the \"InstantiatedVnfInfo\"        and filtering by corresponding \"vduIds\" values. ")
@Validated

public class VirtualCpInfo {
	@JsonProperty("cpInstanceId")
	private String cpInstanceId = null;

	@JsonProperty("cpdId")
	private String cpdId = null;

	@JsonProperty("resourceHandle")
	private ResourceHandle resourceHandle = null;

	@JsonProperty("vnfExtCpId")
	private String vnfExtCpId = null;

	@JsonProperty("cpProtocolInfo")
	@Valid
	private List<CpProtocolInfo> cpProtocolInfo = null;

	@JsonProperty("vduIds")
	@Valid
	private List<String> vduIds = new ArrayList<>();

	@JsonProperty("additionalServiceInfo")
	@Valid
	private List<AdditionalServiceInfo> additionalServiceInfo = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	public VirtualCpInfo cpInstanceId(final String cpInstanceId) {
		this.cpInstanceId = cpInstanceId;
		return this;
	}

	/**
	 * Get cpInstanceId
	 *
	 * @return cpInstanceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getCpInstanceId() {
		return cpInstanceId;
	}

	public void setCpInstanceId(final String cpInstanceId) {
		this.cpInstanceId = cpInstanceId;
	}

	public VirtualCpInfo cpdId(final String cpdId) {
		this.cpdId = cpdId;
		return this;
	}

	/**
	 * Get cpdId
	 *
	 * @return cpdId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getCpdId() {
		return cpdId;
	}

	public void setCpdId(final String cpdId) {
		this.cpdId = cpdId;
	}

	public VirtualCpInfo resourceHandle(final ResourceHandle resourceHandle) {
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

	public VirtualCpInfo vnfExtCpId(final String vnfExtCpId) {
		this.vnfExtCpId = vnfExtCpId;
		return this;
	}

	/**
	 * Get vnfExtCpId
	 *
	 * @return vnfExtCpId
	 **/
	@Schema(description = "")

	public String getVnfExtCpId() {
		return vnfExtCpId;
	}

	public void setVnfExtCpId(final String vnfExtCpId) {
		this.vnfExtCpId = vnfExtCpId;
	}

	public VirtualCpInfo cpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
		return this;
	}

	public VirtualCpInfo addCpProtocolInfoItem(final CpProtocolInfo cpProtocolInfoItem) {
		if (this.cpProtocolInfo == null) {
			this.cpProtocolInfo = new ArrayList<>();
		}
		this.cpProtocolInfo.add(cpProtocolInfoItem);
		return this;
	}

	/**
	 * Protocol information for this CP. There shall be one cpProtocolInfo for each
	 * layer protocol supported.
	 *
	 * @return cpProtocolInfo
	 **/
	@Schema(description = "Protocol information for this CP. There shall be one cpProtocolInfo for each layer protocol supported. ")
	@Valid
	public List<CpProtocolInfo> getCpProtocolInfo() {
		return cpProtocolInfo;
	}

	public void setCpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
	}

	public VirtualCpInfo vduIds(final List<String> vduIds) {
		this.vduIds = vduIds;
		return this;
	}

	public VirtualCpInfo addVduIdsItem(final String vduIdsItem) {
		this.vduIds.add(vduIdsItem);
		return this;
	}

	/**
	 * Reference to the VDU(s) which implement the service accessible via the
	 * virtual CP instance. See note.
	 *
	 * @return vduIds
	 **/
	@Schema(required = true, description = "Reference to the VDU(s) which implement the service accessible via the virtual CP instance. See note. ")
	@NotNull

	@Size(min = 1)
	public List<String> getVduIds() {
		return vduIds;
	}

	public void setVduIds(final List<String> vduIds) {
		this.vduIds = vduIds;
	}

	public VirtualCpInfo additionalServiceInfo(final List<AdditionalServiceInfo> additionalServiceInfo) {
		this.additionalServiceInfo = additionalServiceInfo;
		return this;
	}

	public VirtualCpInfo addAdditionalServiceInfoItem(final AdditionalServiceInfo additionalServiceInfoItem) {
		if (this.additionalServiceInfo == null) {
			this.additionalServiceInfo = new ArrayList<>();
		}
		this.additionalServiceInfo.add(additionalServiceInfoItem);
		return this;
	}

	/**
	 * Additional service identification information of the virtual CP instance.
	 *
	 * @return additionalServiceInfo
	 **/
	@Schema(description = "Additional service identification information of the virtual CP instance. ")
	@Valid
	public List<AdditionalServiceInfo> getAdditionalServiceInfo() {
		return additionalServiceInfo;
	}

	public void setAdditionalServiceInfo(final List<AdditionalServiceInfo> additionalServiceInfo) {
		this.additionalServiceInfo = additionalServiceInfo;
	}

	public VirtualCpInfo metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Get metadata
	 *
	 * @return metadata
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VirtualCpInfo virtualCpInfo = (VirtualCpInfo) o;
		return Objects.equals(this.cpInstanceId, virtualCpInfo.cpInstanceId) &&
				Objects.equals(this.cpdId, virtualCpInfo.cpdId) &&
				Objects.equals(this.resourceHandle, virtualCpInfo.resourceHandle) &&
				Objects.equals(this.vnfExtCpId, virtualCpInfo.vnfExtCpId) &&
				Objects.equals(this.cpProtocolInfo, virtualCpInfo.cpProtocolInfo) &&
				Objects.equals(this.vduIds, virtualCpInfo.vduIds) &&
				Objects.equals(this.additionalServiceInfo, virtualCpInfo.additionalServiceInfo) &&
				Objects.equals(this.metadata, virtualCpInfo.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpInstanceId, cpdId, resourceHandle, vnfExtCpId, cpProtocolInfo, vduIds, additionalServiceInfo, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VirtualCpInfo {\n");

		sb.append("    cpInstanceId: ").append(toIndentedString(cpInstanceId)).append("\n");
		sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
		sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
		sb.append("    vnfExtCpId: ").append(toIndentedString(vnfExtCpId)).append("\n");
		sb.append("    cpProtocolInfo: ").append(toIndentedString(cpProtocolInfo)).append("\n");
		sb.append("    vduIds: ").append(toIndentedString(vduIds)).append("\n");
		sb.append("    additionalServiceInfo: ").append(toIndentedString(additionalServiceInfo)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
