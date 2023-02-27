/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.em.v431.model.lcmcoord;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvo.v431.model.lcmcoord.LcmCoordLinks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LcmCoordRequest {

	@JsonProperty("vnfInstanceId")
	private String vnfInstanceId;

	@JsonProperty("vnfLcmOpOccId")
	private String vnfLcmOpOccId;

	/**
	 * The enumeration LcmOperationForCoordType defines the permitted values to
	 * represent VNF lifecycle operation types in VNF LCM operation coordination
	 * actions. * INSTANTIATE: Represents the \"Instantiate VNF\" LCM operation. *
	 * SCALE: Represents the \"Scale VNF\" LCM operation. * SCALE_TO_LEVEL:
	 * Represents the \"Scale VNF to Level\" LCM operation. * CHANGE_FLAVOUR:
	 * Represents the \"Change VNF Flavour\" LCM operation. * TERMINATE: Represents
	 * the \"Terminate VNF\" LCM operation. * HEAL: Represents the \"Heal VNF\" LCM
	 * operation. * OPERATE: Represents the \"Operate VNF\" LCM operation. *
	 * CHANGE_EXT_CONN: Represents the \"Change external VNF connectivity\" LCM
	 * operation. * MODIFY_INFO: Represents the \"Modify VNF Information\" LCM
	 * operation. * CREATE_SNAPSHOT: Represents the \"Create VNF Snapshot\" LCM
	 * operation. * REVERT_TO_SNAPSHOT: Represents the \"Revert To VNF Snapshot\"
	 * LCM operation. * CHANGE_VNFPKG: Represents the \"Change current VNF package\"
	 * LCM operation.
	 */
	public enum LcmOperationTypeEnum {
		INSTANTIATE("INSTANTIATE"),

		SCALE("SCALE"),

		SCALE_TO_LEVEL("SCALE_TO_LEVEL"),

		CHANGE_FLAVOUR("CHANGE_FLAVOUR"),

		TERMINATE("TERMINATE"),

		HEAL("HEAL"),

		OPERATE("OPERATE"),

		CHANGE_EXT_CONN("CHANGE_EXT_CONN"),

		MODIFY_INFO("MODIFY_INFO"),

		CREATE_SNAPSHOT("CREATE_SNAPSHOT"),

		REVERT_TO_SNAPSHOT("REVERT_TO_SNAPSHOT"),

		CHANGE_VNFPKG("CHANGE_VNFPKG");

		private final String value;

		LcmOperationTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static LcmOperationTypeEnum fromValue(final String text) {
			for (final LcmOperationTypeEnum b : LcmOperationTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("lcmOperationType")
	private LcmOperationTypeEnum lcmOperationType;

	@JsonProperty("coordinationActionName")
	private String coordinationActionName;

	@JsonProperty("inputParams")
	private Map<String, String> inputParams;

	@JsonProperty("_links")
	private LcmCoordLinks _links;

	public LcmCoordRequest vnfInstanceId(final String vnfInstanceId) {
		this.vnfInstanceId = vnfInstanceId;
		return this;
	}

	/**
	 * An identifier with the intention of being globally unique.
	 *
	 * @return vnfInstanceId
	 **/
	@Schema(required = true, description = "An identifier with the intention of being globally unique. ")
	@NotNull

	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	public void setVnfInstanceId(final String vnfInstanceId) {
		this.vnfInstanceId = vnfInstanceId;
	}

	public LcmCoordRequest vnfLcmOpOccId(final String vnfLcmOpOccId) {
		this.vnfLcmOpOccId = vnfLcmOpOccId;
		return this;
	}

	/**
	 * An identifier with the intention of being globally unique.
	 *
	 * @return vnfLcmOpOccId
	 **/
	@Schema(required = true, description = "An identifier with the intention of being globally unique. ")
	@NotNull

	public String getVnfLcmOpOccId() {
		return vnfLcmOpOccId;
	}

	public void setVnfLcmOpOccId(final String vnfLcmOpOccId) {
		this.vnfLcmOpOccId = vnfLcmOpOccId;
	}

	public LcmCoordRequest lcmOperationType(final LcmOperationTypeEnum lcmOperationType) {
		this.lcmOperationType = lcmOperationType;
		return this;
	}

	/**
	 * The enumeration LcmOperationForCoordType defines the permitted values to
	 * represent VNF lifecycle operation types in VNF LCM operation coordination
	 * actions. * INSTANTIATE: Represents the \"Instantiate VNF\" LCM operation. *
	 * SCALE: Represents the \"Scale VNF\" LCM operation. * SCALE_TO_LEVEL:
	 * Represents the \"Scale VNF to Level\" LCM operation. * CHANGE_FLAVOUR:
	 * Represents the \"Change VNF Flavour\" LCM operation. * TERMINATE: Represents
	 * the \"Terminate VNF\" LCM operation. * HEAL: Represents the \"Heal VNF\" LCM
	 * operation. * OPERATE: Represents the \"Operate VNF\" LCM operation. *
	 * CHANGE_EXT_CONN: Represents the \"Change external VNF connectivity\" LCM
	 * operation. * MODIFY_INFO: Represents the \"Modify VNF Information\" LCM
	 * operation. * CREATE_SNAPSHOT: Represents the \"Create VNF Snapshot\" LCM
	 * operation. * REVERT_TO_SNAPSHOT: Represents the \"Revert To VNF Snapshot\"
	 * LCM operation. * CHANGE_VNFPKG: Represents the \"Change current VNF package\"
	 * LCM operation.
	 *
	 * @return lcmOperationType
	 **/
	@Schema(required = true, description = "The enumeration LcmOperationForCoordType defines the permitted values to represent VNF lifecycle operation types in VNF LCM operation coordination actions. * INSTANTIATE: Represents the \"Instantiate VNF\" LCM operation. * SCALE: Represents the \"Scale VNF\" LCM operation. * SCALE_TO_LEVEL: Represents the \"Scale VNF to Level\" LCM operation. * CHANGE_FLAVOUR: Represents the \"Change VNF Flavour\" LCM operation. * TERMINATE: Represents the \"Terminate VNF\" LCM operation. * HEAL: Represents the \"Heal VNF\" LCM operation. * OPERATE: Represents the \"Operate VNF\" LCM operation. * CHANGE_EXT_CONN: Represents the \"Change external VNF connectivity\" LCM operation. * MODIFY_INFO: Represents the \"Modify VNF Information\" LCM operation. * CREATE_SNAPSHOT: Represents the \"Create VNF Snapshot\" LCM operation. * REVERT_TO_SNAPSHOT: Represents the \"Revert To VNF Snapshot\" LCM operation. * CHANGE_VNFPKG: Represents the \"Change current VNF package\" LCM operation. ")
	@NotNull

	public LcmOperationTypeEnum getLcmOperationType() {
		return lcmOperationType;
	}

	public void setLcmOperationType(final LcmOperationTypeEnum lcmOperationType) {
		this.lcmOperationType = lcmOperationType;
	}

	public LcmCoordRequest coordinationActionName(final String coordinationActionName) {
		this.coordinationActionName = coordinationActionName;
		return this;
	}

	/**
	 * Indicates the actual LCM coordination action. The coordination actions that a
	 * VNF supports are declared in the VNFD.
	 *
	 * @return coordinationActionName
	 **/
	@Schema(required = true, description = "Indicates the actual LCM coordination action. The coordination actions that a VNF supports are declared in the VNFD. ")
	@NotNull

	public String getCoordinationActionName() {
		return coordinationActionName;
	}

	public void setCoordinationActionName(final String coordinationActionName) {
		this.coordinationActionName = coordinationActionName;
	}

	/**
	 * This type represents a list of key-value pairs. The order of the pairs in the
	 * list is not significant. In JSON, a set of keyvalue pairs is represented as
	 * an object. It shall comply with the provisions defined in clause 4 of IETF
	 * RFC 8259. In the following example, a list of key-value pairs with four keys
	 * (\"aString\", \"aNumber\", \"anArray\" and \"anObject\") is provided to
	 * illustrate that the values associated with different keys can be of different
	 * type.
	 *
	 * @return outputParams
	 **/
	@Schema(description = "This type represents a list of key-value pairs. The order of the pairs in the list is not significant. In JSON, a set of keyvalue pairs is represented as an object. It shall comply with the provisions defined in clause 4 of IETF RFC 8259. In the following example, a list of key-value pairs with four keys (\"aString\", \"aNumber\", \"anArray\" and \"anObject\") is provided to illustrate that the values associated with different keys can be of different type. ")

	public LcmCoordRequest _links(final LcmCoordLinks _links) {
		this._links = _links;
		return this;
	}

	public Map<String, String> getInputParams() {
		return inputParams;
	}

	public void setInputParams(final Map<String, String> inputParams) {
		this.inputParams = inputParams;
	}

	/**
	 * Get _links
	 *
	 * @return _links
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public LcmCoordLinks getLinks() {
		return _links;
	}

	public void setLinks(final LcmCoordLinks _links) {
		this._links = _links;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final LcmCoordRequest inlineResponse201 = (LcmCoordRequest) o;
		return Objects.equals(this.vnfInstanceId, inlineResponse201.vnfInstanceId) &&
				Objects.equals(this.vnfLcmOpOccId, inlineResponse201.vnfLcmOpOccId) &&
				Objects.equals(this.lcmOperationType, inlineResponse201.lcmOperationType) &&
				Objects.equals(this.coordinationActionName, inlineResponse201.coordinationActionName) &&
				Objects.equals(this.inputParams, inlineResponse201.inputParams) &&
				Objects.equals(this._links, inlineResponse201._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfInstanceId, vnfLcmOpOccId, lcmOperationType, coordinationActionName, inputParams, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class InlineResponse201 {\n");

		sb.append("    vnfInstanceId: ").append(toIndentedString(vnfInstanceId)).append("\n");
		sb.append("    vnfLcmOpOccId: ").append(toIndentedString(vnfLcmOpOccId)).append("\n");
		sb.append("    lcmOperationType: ").append(toIndentedString(lcmOperationType)).append("\n");
		sb.append("    coordinationActionName: ").append(toIndentedString(coordinationActionName)).append("\n");
		sb.append("    outputParams: ").append(toIndentedString(inputParams)).append("\n");
		sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
