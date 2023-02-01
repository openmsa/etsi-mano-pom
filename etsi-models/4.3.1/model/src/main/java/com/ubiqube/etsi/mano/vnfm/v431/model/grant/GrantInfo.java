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
 * This type contains information about a Compute, storage or network resource
 * whose addition/update/deletion was granted.
 */
@Schema(description = "This type contains information about a Compute, storage or network resource whose addition/update/deletion was granted. ")
@Validated

public class GrantInfo {
	@JsonProperty("resourceDefinitionId")
	private String resourceDefinitionId = null;

	@JsonProperty("reservationId")
	private String reservationId = null;

	@JsonProperty("vimConnectionId")
	private String vimConnectionId = null;

	@JsonProperty("resourceProviderId")
	private String resourceProviderId = null;

	@JsonProperty("zoneId")
	private String zoneId = null;

	@JsonProperty("resourceGroupId")
	private String resourceGroupId = null;

	@JsonProperty("containerNamespace")
	private String containerNamespace = null;

	@JsonProperty("mcioConstraints")
	@Valid
	private List<Map<String, String>> mcioConstraints = null;

	public GrantInfo resourceDefinitionId(final String resourceDefinitionId) {
		this.resourceDefinitionId = resourceDefinitionId;
		return this;
	}

	/**
	 * Get resourceDefinitionId
	 *
	 * @return resourceDefinitionId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getResourceDefinitionId() {
		return resourceDefinitionId;
	}

	public void setResourceDefinitionId(final String resourceDefinitionId) {
		this.resourceDefinitionId = resourceDefinitionId;
	}

	public GrantInfo reservationId(final String reservationId) {
		this.reservationId = reservationId;
		return this;
	}

	/**
	 * Get reservationId
	 *
	 * @return reservationId
	 **/
	@Schema(description = "")

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(final String reservationId) {
		this.reservationId = reservationId;
	}

	public GrantInfo vimConnectionId(final String vimConnectionId) {
		this.vimConnectionId = vimConnectionId;
		return this;
	}

	/**
	 * Get vimConnectionId
	 *
	 * @return vimConnectionId
	 **/
	@Schema(description = "")

	public String getVimConnectionId() {
		return vimConnectionId;
	}

	public void setVimConnectionId(final String vimConnectionId) {
		this.vimConnectionId = vimConnectionId;
	}

	public GrantInfo resourceProviderId(final String resourceProviderId) {
		this.resourceProviderId = resourceProviderId;
		return this;
	}

	/**
	 * Get resourceProviderId
	 *
	 * @return resourceProviderId
	 **/
	@Schema(description = "")

	public String getResourceProviderId() {
		return resourceProviderId;
	}

	public void setResourceProviderId(final String resourceProviderId) {
		this.resourceProviderId = resourceProviderId;
	}

	public GrantInfo zoneId(final String zoneId) {
		this.zoneId = zoneId;
		return this;
	}

	/**
	 * Get zoneId
	 *
	 * @return zoneId
	 **/
	@Schema(description = "")

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(final String zoneId) {
		this.zoneId = zoneId;
	}

	public GrantInfo resourceGroupId(final String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
		return this;
	}

	/**
	 * Get resourceGroupId
	 *
	 * @return resourceGroupId
	 **/
	@Schema(description = "")

	public String getResourceGroupId() {
		return resourceGroupId;
	}

	public void setResourceGroupId(final String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}

	public GrantInfo containerNamespace(final String containerNamespace) {
		this.containerNamespace = containerNamespace;
		return this;
	}

	/**
	 * Get containerNamespace
	 *
	 * @return containerNamespace
	 **/
	@Schema(description = "")

	public String getContainerNamespace() {
		return containerNamespace;
	}

	public void setContainerNamespace(final String containerNamespace) {
		this.containerNamespace = containerNamespace;
	}

	public GrantInfo mcioConstraints(final List<Map<String, String>> mcioConstraints) {
		this.mcioConstraints = mcioConstraints;
		return this;
	}

	public GrantInfo addMcioConstraintsItem(final Map<String, String> mcioConstraintsItem) {
		if (this.mcioConstraints == null) {
			this.mcioConstraints = new ArrayList<>();
		}
		this.mcioConstraints.add(mcioConstraintsItem);
		return this;
	}

	/**
	 * The constraint values to be assigned to MCIOs of a VNF with containerized
	 * components. The key in the key-value pair indicates the parameter name of the
	 * MCIO constraint in the MCIO declarative descriptor and shall be one of the
	 * possible enumeration values of the \"mcioConstraintsParams\" attribute as
	 * specified in clause 7.1.6.2.2 of ETSI GS NFV-IFA 011 [10]. The value in the
	 * keyvalue pair indicates the value to be assigned to the MCIO constraint. This
	 * attribute shall be present if the granted resources are managed by a CISM.
	 * The attribute shall be absent if the granted resources are not managed by a
	 * CISM.
	 *
	 * @return mcioConstraints
	 **/
	@Schema(description = "The constraint values to be assigned to MCIOs of a VNF with containerized components. The key in the key-value pair indicates the parameter name of the MCIO constraint in the MCIO declarative descriptor and shall be one of the possible enumeration values of the \"mcioConstraintsParams\" attribute as specified in clause 7.1.6.2.2 of ETSI GS NFV-IFA 011 [10]. The value in the keyvalue pair indicates the value to be assigned to the MCIO constraint. This attribute shall be present if the granted resources are managed by a CISM. The attribute shall be absent if the granted resources are not managed by a CISM. ")
	@Valid
	public List<Map<String, String>> getMcioConstraints() {
		return mcioConstraints;
	}

	public void setMcioConstraints(final List<Map<String, String>> mcioConstraints) {
		this.mcioConstraints = mcioConstraints;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final GrantInfo grantInfo = (GrantInfo) o;
		return Objects.equals(this.resourceDefinitionId, grantInfo.resourceDefinitionId) &&
				Objects.equals(this.reservationId, grantInfo.reservationId) &&
				Objects.equals(this.vimConnectionId, grantInfo.vimConnectionId) &&
				Objects.equals(this.resourceProviderId, grantInfo.resourceProviderId) &&
				Objects.equals(this.zoneId, grantInfo.zoneId) &&
				Objects.equals(this.resourceGroupId, grantInfo.resourceGroupId) &&
				Objects.equals(this.containerNamespace, grantInfo.containerNamespace) &&
				Objects.equals(this.mcioConstraints, grantInfo.mcioConstraints);
	}

	@Override
	public int hashCode() {
		return Objects.hash(resourceDefinitionId, reservationId, vimConnectionId, resourceProviderId, zoneId, resourceGroupId, containerNamespace, mcioConstraints);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class GrantInfo {\n");

		sb.append("    resourceDefinitionId: ").append(toIndentedString(resourceDefinitionId)).append("\n");
		sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
		sb.append("    vimConnectionId: ").append(toIndentedString(vimConnectionId)).append("\n");
		sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
		sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
		sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
		sb.append("    containerNamespace: ").append(toIndentedString(containerNamespace)).append("\n");
		sb.append("    mcioConstraints: ").append(toIndentedString(mcioConstraints)).append("\n");
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
