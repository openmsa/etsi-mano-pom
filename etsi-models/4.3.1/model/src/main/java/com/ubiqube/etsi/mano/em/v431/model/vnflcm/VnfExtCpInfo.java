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

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents information about an external CP of a VNF. NOTE 1: The
 * attributes \&quot;associatedVnfcCpId\&quot;, \&quot;associatedVipCpId\&quot;,
 * \&quot;associatedVirtualCpId\&quot; and
 * \&quot;associatedVnfVirtualLinkId\&quot; are mutually exclusive. Exactly one
 * shall be present. NOTE 2: An external CP instance is not associated to a link
 * port in the cases indicated for the “extLinkPorts” attribute in clause
 * 4.4.1.11. NOTE 3: Cardinality greater than 1 is only applicable for specific
 * cases where more than one network attachment definition resource is needed to
 * fulfil the connectivity requirements of the external CP, e.g. to build a link
 * redundant mated pair in SR-IOV cases. NOTE 4: When more than one
 * netAttDefResourceId is indicated, all shall belong to the same namespace.
 */
@Schema(description = "This type represents information about an external CP of a VNF. NOTE 1: The attributes \"associatedVnfcCpId\", \"associatedVipCpId\", \"associatedVirtualCpId\" and          \"associatedVnfVirtualLinkId\" are mutually exclusive. Exactly one shall be present. NOTE 2: An external CP instance is not associated to a link port in the cases indicated for the          “extLinkPorts” attribute in clause 4.4.1.11. NOTE 3: Cardinality greater than 1 is only applicable for specific cases where more than one network          attachment definition resource is needed to fulfil the connectivity requirements of the external          CP, e.g. to build a link redundant mated pair in SR-IOV cases. NOTE 4: When more than one netAttDefResourceId is indicated, all shall belong to the same namespace. ")
@Validated

public class VnfExtCpInfo implements OneOfVnfExtCpInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("cpdId")
	private String cpdId = null;

	@JsonProperty("cpConfigId")
	private String cpConfigId = null;

	@JsonProperty("vnfdId")
	private String vnfdId = null;

	@JsonProperty("cpProtocolInfo")
	@Valid
	private List<CpProtocolInfo> cpProtocolInfo = new ArrayList<>();

	@JsonProperty("extLinkPortId")
	private String extLinkPortId = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	@JsonProperty("associatedVnfcCpId")
	private String associatedVnfcCpId = null;

	@JsonProperty("associatedVipCpId")
	private String associatedVipCpId = null;

	@JsonProperty("associatedVirtualCpId")
	private String associatedVirtualCpId = null;

	@JsonProperty("associatedVnfVirtualLinkId")
	private String associatedVnfVirtualLinkId = null;

	@JsonProperty("netAttDefResourceId")
	@Valid
	private List<String> netAttDefResourceId = null;

	public VnfExtCpInfo id(final String id) {
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

	public VnfExtCpInfo cpdId(final String cpdId) {
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

	public VnfExtCpInfo cpConfigId(final String cpConfigId) {
		this.cpConfigId = cpConfigId;
		return this;
	}

	/**
	 * Get cpConfigId
	 *
	 * @return cpConfigId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getCpConfigId() {
		return cpConfigId;
	}

	public void setCpConfigId(final String cpConfigId) {
		this.cpConfigId = cpConfigId;
	}

	public VnfExtCpInfo vnfdId(final String vnfdId) {
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

	public VnfExtCpInfo cpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
		return this;
	}

	public VnfExtCpInfo addCpProtocolInfoItem(final CpProtocolInfo cpProtocolInfoItem) {
		this.cpProtocolInfo.add(cpProtocolInfoItem);
		return this;
	}

	/**
	 * Network protocol information for this CP.
	 *
	 * @return cpProtocolInfo
	 **/
	@Schema(required = true, description = "Network protocol information for this CP. ")
	@NotNull
	@Valid
	public List<CpProtocolInfo> getCpProtocolInfo() {
		return cpProtocolInfo;
	}

	public void setCpProtocolInfo(final List<CpProtocolInfo> cpProtocolInfo) {
		this.cpProtocolInfo = cpProtocolInfo;
	}

	public VnfExtCpInfo extLinkPortId(final String extLinkPortId) {
		this.extLinkPortId = extLinkPortId;
		return this;
	}

	/**
	 * Get extLinkPortId
	 *
	 * @return extLinkPortId
	 **/
	@Schema(description = "")

	public String getExtLinkPortId() {
		return extLinkPortId;
	}

	public void setExtLinkPortId(final String extLinkPortId) {
		this.extLinkPortId = extLinkPortId;
	}

	public VnfExtCpInfo metadata(final Map<String, String> metadata) {
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

	public VnfExtCpInfo associatedVnfcCpId(final String associatedVnfcCpId) {
		this.associatedVnfcCpId = associatedVnfcCpId;
		return this;
	}

	/**
	 * Get associatedVnfcCpId
	 *
	 * @return associatedVnfcCpId
	 **/
	@Schema(description = "")

	public String getAssociatedVnfcCpId() {
		return associatedVnfcCpId;
	}

	public void setAssociatedVnfcCpId(final String associatedVnfcCpId) {
		this.associatedVnfcCpId = associatedVnfcCpId;
	}

	public VnfExtCpInfo associatedVipCpId(final String associatedVipCpId) {
		this.associatedVipCpId = associatedVipCpId;
		return this;
	}

	/**
	 * Get associatedVipCpId
	 *
	 * @return associatedVipCpId
	 **/
	@Schema(description = "")

	public String getAssociatedVipCpId() {
		return associatedVipCpId;
	}

	public void setAssociatedVipCpId(final String associatedVipCpId) {
		this.associatedVipCpId = associatedVipCpId;
	}

	public VnfExtCpInfo associatedVirtualCpId(final String associatedVirtualCpId) {
		this.associatedVirtualCpId = associatedVirtualCpId;
		return this;
	}

	/**
	 * Get associatedVirtualCpId
	 *
	 * @return associatedVirtualCpId
	 **/
	@Schema(description = "")

	public String getAssociatedVirtualCpId() {
		return associatedVirtualCpId;
	}

	public void setAssociatedVirtualCpId(final String associatedVirtualCpId) {
		this.associatedVirtualCpId = associatedVirtualCpId;
	}

	public VnfExtCpInfo associatedVnfVirtualLinkId(final String associatedVnfVirtualLinkId) {
		this.associatedVnfVirtualLinkId = associatedVnfVirtualLinkId;
		return this;
	}

	/**
	 * Get associatedVnfVirtualLinkId
	 *
	 * @return associatedVnfVirtualLinkId
	 **/
	@Schema(description = "")

	public String getAssociatedVnfVirtualLinkId() {
		return associatedVnfVirtualLinkId;
	}

	public void setAssociatedVnfVirtualLinkId(final String associatedVnfVirtualLinkId) {
		this.associatedVnfVirtualLinkId = associatedVnfVirtualLinkId;
	}

	public VnfExtCpInfo netAttDefResourceId(final List<String> netAttDefResourceId) {
		this.netAttDefResourceId = netAttDefResourceId;
		return this;
	}

	public VnfExtCpInfo addNetAttDefResourceIdItem(final String netAttDefResourceIdItem) {
		if (this.netAttDefResourceId == null) {
			this.netAttDefResourceId = new ArrayList<>();
		}
		this.netAttDefResourceId.add(netAttDefResourceIdItem);
		return this;
	}

	/**
	 * Identifier of the “NetAttDefResourceInfo” structure that provides the
	 * specification of the interface to attach the connection point to a secondary
	 * container cluster network. See notes 3 and 4. It shall be present if the
	 * external CP is associated to a VNFC realized by one or a set of OS containers
	 * and is connected to a secondary container cluster network. It shall not be
	 * present otherwise.
	 *
	 * @return netAttDefResourceId
	 **/
	@Schema(description = "Identifier of the “NetAttDefResourceInfo” structure that provides the specification of the interface to attach the connection point to a secondary container cluster network. See notes 3 and 4. It shall be present if the external CP is associated to a VNFC realized by one or a set of OS containers and is connected to a secondary container cluster network. It shall not be present otherwise. ")

	public List<String> getNetAttDefResourceId() {
		return netAttDefResourceId;
	}

	public void setNetAttDefResourceId(final List<String> netAttDefResourceId) {
		this.netAttDefResourceId = netAttDefResourceId;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfExtCpInfo vnfExtCpInfo = (VnfExtCpInfo) o;
		return Objects.equals(this.id, vnfExtCpInfo.id) &&
				Objects.equals(this.cpdId, vnfExtCpInfo.cpdId) &&
				Objects.equals(this.cpConfigId, vnfExtCpInfo.cpConfigId) &&
				Objects.equals(this.vnfdId, vnfExtCpInfo.vnfdId) &&
				Objects.equals(this.cpProtocolInfo, vnfExtCpInfo.cpProtocolInfo) &&
				Objects.equals(this.extLinkPortId, vnfExtCpInfo.extLinkPortId) &&
				Objects.equals(this.metadata, vnfExtCpInfo.metadata) &&
				Objects.equals(this.associatedVnfcCpId, vnfExtCpInfo.associatedVnfcCpId) &&
				Objects.equals(this.associatedVipCpId, vnfExtCpInfo.associatedVipCpId) &&
				Objects.equals(this.associatedVirtualCpId, vnfExtCpInfo.associatedVirtualCpId) &&
				Objects.equals(this.associatedVnfVirtualLinkId, vnfExtCpInfo.associatedVnfVirtualLinkId) &&
				Objects.equals(this.netAttDefResourceId, vnfExtCpInfo.netAttDefResourceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cpdId, cpConfigId, vnfdId, cpProtocolInfo, extLinkPortId, metadata, associatedVnfcCpId, associatedVipCpId, associatedVirtualCpId, associatedVnfVirtualLinkId, netAttDefResourceId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfExtCpInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
		sb.append("    cpConfigId: ").append(toIndentedString(cpConfigId)).append("\n");
		sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
		sb.append("    cpProtocolInfo: ").append(toIndentedString(cpProtocolInfo)).append("\n");
		sb.append("    extLinkPortId: ").append(toIndentedString(extLinkPortId)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
		sb.append("    associatedVnfcCpId: ").append(toIndentedString(associatedVnfcCpId)).append("\n");
		sb.append("    associatedVipCpId: ").append(toIndentedString(associatedVipCpId)).append("\n");
		sb.append("    associatedVirtualCpId: ").append(toIndentedString(associatedVirtualCpId)).append("\n");
		sb.append("    associatedVnfVirtualLinkId: ").append(toIndentedString(associatedVnfVirtualLinkId)).append("\n");
		sb.append("    netAttDefResourceId: ").append(toIndentedString(netAttDefResourceId)).append("\n");
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
