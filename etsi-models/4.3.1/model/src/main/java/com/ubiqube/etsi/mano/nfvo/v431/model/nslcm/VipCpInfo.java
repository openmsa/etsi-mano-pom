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
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type provides information related to virtual IP (VIP) CP. It shall
 * comply with the provisions defined in table 6.5.3.97 1. NOTE: It is possible
 * that there is no associated VnfcCp because the VIP CP is available but not
 * associated yet.
 */
@Schema(description = "This type provides information related to virtual IP (VIP) CP. It shall comply with the provisions defined in table 6.5.3.97 1. NOTE:   It is possible that there is no associated VnfcCp because the VIP CP is available but not associated yet. ")
@Validated

public class VipCpInfo {
	@JsonProperty("cpInstanceId")
	private String cpInstanceId = null;

	@JsonProperty("cpdId")
	private String cpdId = null;

	@JsonProperty("vnfdId")
	private String vnfdId = null;

	@JsonProperty("vnfExtCpId")
	private String vnfExtCpId = null;

	@JsonProperty("cpProtocolInfo")
	@Valid
	private List<CpProtocolInfo> cpProtocolInfo = null;

	@JsonProperty("associatedVnfcCpIds")
	@Valid
	private List<String> associatedVnfcCpIds = null;

	@JsonProperty("vnfLinkPortId")
	private String vnfLinkPortId = null;

	@JsonProperty("metadata")
	@Valid
	private Map<String, String> metadata = null;

	public VipCpInfo cpInstanceId(final String cpInstanceId) {
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

	public VipCpInfo cpdId(final String cpdId) {
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

	public VipCpInfo vnfdId(final String vnfdId) {
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

	public VipCpInfo vnfExtCpId(final String vnfExtCpId) {
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

	public VipCpInfo cpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
		return this;
	}

	public VipCpInfo addCpProtocolInfoItem(final CpProtocolInfo cpProtocolInfoItem) {
		if (this.cpProtocolInfo == null) {
			this.cpProtocolInfo = new ArrayList<>();
		}
		this.cpProtocolInfo.add(cpProtocolInfoItem);
		return this;
	}

	/**
	 * Protocol information for this CP. There shall be one cpProtocolInfo for layer
	 * 3. There may be one cpProtocolInfo for layer 2.
	 *
	 * @return cpProtocolInfo
	 **/
	@Schema(description = "Protocol information for this CP. There shall be one cpProtocolInfo for layer 3. There may be one cpProtocolInfo for layer 2. ")
	@Valid
	public List<CpProtocolInfo> getCpProtocolInfo() {
		return cpProtocolInfo;
	}

	public void setCpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
	}

	public VipCpInfo associatedVnfcCpIds(final List<String> associatedVnfcCpIds) {
		this.associatedVnfcCpIds = associatedVnfcCpIds;
		return this;
	}

	public VipCpInfo addAssociatedVnfcCpIdsItem(final String associatedVnfcCpIdsItem) {
		if (this.associatedVnfcCpIds == null) {
			this.associatedVnfcCpIds = new ArrayList<>();
		}
		this.associatedVnfcCpIds.add(associatedVnfcCpIdsItem);
		return this;
	}

	/**
	 * Identifiers of the VnfcCps that share the virtual IP addresse allocated to
	 * the VIP CP instance. See note.
	 *
	 * @return associatedVnfcCpIds
	 **/
	@Schema(description = "Identifiers of the VnfcCps that share the virtual IP addresse allocated to the VIP CP instance. See note. ")

	public List<String> getAssociatedVnfcCpIds() {
		return associatedVnfcCpIds;
	}

	public void setAssociatedVnfcCpIds(final List<String> associatedVnfcCpIds) {
		this.associatedVnfcCpIds = associatedVnfcCpIds;
	}

	public VipCpInfo vnfLinkPortId(final String vnfLinkPortId) {
		this.vnfLinkPortId = vnfLinkPortId;
		return this;
	}

	/**
	 * Get vnfLinkPortId
	 *
	 * @return vnfLinkPortId
	 **/
	@Schema(description = "")

	public String getVnfLinkPortId() {
		return vnfLinkPortId;
	}

	public void setVnfLinkPortId(final String vnfLinkPortId) {
		this.vnfLinkPortId = vnfLinkPortId;
	}

	public VipCpInfo metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Allows the OSS/BSS to provide additional parameter(s) to the termination
	 * process at the NS level.
	 *
	 * @return metadata
	 **/
	@Schema(description = "Allows the OSS/BSS to provide additional parameter(s) to the termination process at the NS level. ")
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
		final VipCpInfo vipCpInfo = (VipCpInfo) o;
		return Objects.equals(this.cpInstanceId, vipCpInfo.cpInstanceId) &&
				Objects.equals(this.cpdId, vipCpInfo.cpdId) &&
				Objects.equals(this.vnfdId, vipCpInfo.vnfdId) &&
				Objects.equals(this.vnfExtCpId, vipCpInfo.vnfExtCpId) &&
				Objects.equals(this.cpProtocolInfo, vipCpInfo.cpProtocolInfo) &&
				Objects.equals(this.associatedVnfcCpIds, vipCpInfo.associatedVnfcCpIds) &&
				Objects.equals(this.vnfLinkPortId, vipCpInfo.vnfLinkPortId) &&
				Objects.equals(this.metadata, vipCpInfo.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpInstanceId, cpdId, vnfdId, vnfExtCpId, cpProtocolInfo, associatedVnfcCpIds, vnfLinkPortId, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VipCpInfo {\n");

		sb.append("    cpInstanceId: ").append(toIndentedString(cpInstanceId)).append("\n");
		sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
		sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
		sb.append("    vnfExtCpId: ").append(toIndentedString(vnfExtCpId)).append("\n");
		sb.append("    cpProtocolInfo: ").append(toIndentedString(cpProtocolInfo)).append("\n");
		sb.append("    associatedVnfcCpIds: ").append(toIndentedString(associatedVnfcCpIds)).append("\n");
		sb.append("    vnfLinkPortId: ").append(toIndentedString(vnfLinkPortId)).append("\n");
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
