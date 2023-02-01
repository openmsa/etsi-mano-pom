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
package com.ubiqube.etsi.mano.nfvo.v431.model.vnflcm;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.ResourceHandle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type provides information about added and deleted external link ports
 * (link ports attached to external virtual links). NOTE: The
 * \&quot;resourceDefinitionId\&quot; attribute provides information to the API
 * consumer (i.e. the NFVO) to assist in correlating the resource changes
 * performed during the LCM operation with the granted resources in a specific
 * Grant exchange, which is identified by the \&quot;grantId\&quot; available in
 * the \&quot;Individual VNF lifecycle management operation occurrence\&quot;
 * and the \&quot;id\&quot; in the \&quot;Individual Grant\&quot;.
 */
@Schema(description = "This type provides information about added and deleted external link ports (link ports attached to external virtual links). NOTE: The \"resourceDefinitionId\" attribute provides information to the API consumer (i.e. the NFVO) to assist in correlating        the resource changes performed during the LCM operation with the granted resources in a specific Grant exchange, which        is identified by the \"grantId\" available in the \"Individual VNF lifecycle management operation occurrence\" and the \"id\"        in the \"Individual Grant\". ")
@Validated

public class AffectedExtLinkPort {
	@JsonProperty("id")
	private String id = null;

	/**
	 * Signals the type of change. Permitted values: - ADDED - MODIFIED - REMOVED
	 */
	public enum ChangeTypeEnum {
		ADDED("ADDED"),

		MODIFIED("MODIFIED"),

		REMOVED("REMOVED");

		private final String value;

		ChangeTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ChangeTypeEnum fromValue(final String text) {
			for (final ChangeTypeEnum b : ChangeTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("changeType")
	private ChangeTypeEnum changeType = null;

	@JsonProperty("extCpInstanceId")
	private String extCpInstanceId = null;

	@JsonProperty("resourceHandle")
	private ResourceHandle resourceHandle = null;

	@JsonProperty("resourceDefinitionId")
	private String resourceDefinitionId = null;

	public AffectedExtLinkPort id(final String id) {
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

	public AffectedExtLinkPort changeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
		return this;
	}

	/**
	 * Signals the type of change. Permitted values: - ADDED - MODIFIED - REMOVED
	 *
	 * @return changeType
	 **/
	@Schema(required = true, description = "Signals the type of change. Permitted values: - ADDED - MODIFIED - REMOVED ")
	@NotNull

	public ChangeTypeEnum getChangeType() {
		return changeType;
	}

	public void setChangeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
	}

	public AffectedExtLinkPort extCpInstanceId(final String extCpInstanceId) {
		this.extCpInstanceId = extCpInstanceId;
		return this;
	}

	/**
	 * Get extCpInstanceId
	 *
	 * @return extCpInstanceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getExtCpInstanceId() {
		return extCpInstanceId;
	}

	public void setExtCpInstanceId(final String extCpInstanceId) {
		this.extCpInstanceId = extCpInstanceId;
	}

	public AffectedExtLinkPort resourceHandle(final ResourceHandle resourceHandle) {
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

	public AffectedExtLinkPort resourceDefinitionId(final String resourceDefinitionId) {
		this.resourceDefinitionId = resourceDefinitionId;
		return this;
	}

	/**
	 * Get resourceDefinitionId
	 *
	 * @return resourceDefinitionId
	 **/
	@Schema(description = "")

	public String getResourceDefinitionId() {
		return resourceDefinitionId;
	}

	public void setResourceDefinitionId(final String resourceDefinitionId) {
		this.resourceDefinitionId = resourceDefinitionId;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AffectedExtLinkPort affectedExtLinkPort = (AffectedExtLinkPort) o;
		return Objects.equals(this.id, affectedExtLinkPort.id) &&
				Objects.equals(this.changeType, affectedExtLinkPort.changeType) &&
				Objects.equals(this.extCpInstanceId, affectedExtLinkPort.extCpInstanceId) &&
				Objects.equals(this.resourceHandle, affectedExtLinkPort.resourceHandle) &&
				Objects.equals(this.resourceDefinitionId, affectedExtLinkPort.resourceDefinitionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, changeType, extCpInstanceId, resourceHandle, resourceDefinitionId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AffectedExtLinkPort {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    changeType: ").append(toIndentedString(changeType)).append("\n");
		sb.append("    extCpInstanceId: ").append(toIndentedString(extCpInstanceId)).append("\n");
		sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
		sb.append("    resourceDefinitionId: ").append(toIndentedString(resourceDefinitionId)).append("\n");
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
