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

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.em.v431.model.vnfconfig.ProblemDetails;
import com.ubiqube.etsi.mano.nfvo.v431.model.lcmcoord.LcmCoordLinks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * This type represents an LCM coordination result.
 */
@Schema(description = "This type represents an LCM coordination result. ")
@Validated

public class LcmCoord {
	@JsonProperty("id")
	private String id = null;

	/**
	 * The enumeration LcmCoordResultType defines the permitted values to represent
	 * the result of executing an LCM coordination action. The coordination result
	 * also implies the action to be performed by the VNFM as the follow-up to this
	 * coordination. Value | Description ------|------------ CONTINUE | The related
	 * LCM operation shall be continued, staying in the state \"PROCESSING\". ABORT
	 * | The related LCM operation shall be aborted by transitioning into the state
	 * \"FAILED_TEMP\". CANCELLED | The coordination action has been cancelled upon
	 * request of the API consumer, i.e. the VNFM. The related LCM operation shall
	 * be aborted by transitioning into the state \"FAILED_TEMP\".
	 */
	public enum CoordinationResultEnum {
		CONTINUE("CONTINUE"),

		ABORT("ABORT"),

		CANCELLED("CANCELLED");

		private final String value;

		CoordinationResultEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static CoordinationResultEnum fromValue(final String text) {
			for (final CoordinationResultEnum b : CoordinationResultEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("coordinationResult")
	private CoordinationResultEnum coordinationResult = null;

	@JsonProperty("vnfInstanceId")
	private String vnfInstanceId = null;

	@JsonProperty("vnfLcmOpOccId")
	private String vnfLcmOpOccId = null;

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
	private LcmOperationTypeEnum lcmOperationType = null;

	@JsonProperty("coordinationActionName")
	private String coordinationActionName = null;

	@JsonProperty("outputParams")
	private Object outputParams = null;

	@JsonProperty("warnings")
	private String warnings = null;

	@JsonProperty("error")
	private ProblemDetails error = null;

	@JsonProperty("_links")
	private LcmCoordLinks _links = null;

	public LcmCoord id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * An identifier with the intention of being globally unique.
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "An identifier with the intention of being globally unique. ")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public LcmCoord coordinationResult(final CoordinationResultEnum coordinationResult) {
		this.coordinationResult = coordinationResult;
		return this;
	}

	/**
	 * The enumeration LcmCoordResultType defines the permitted values to represent
	 * the result of executing an LCM coordination action. The coordination result
	 * also implies the action to be performed by the VNFM as the follow-up to this
	 * coordination. Value | Description ------|------------ CONTINUE | The related
	 * LCM operation shall be continued, staying in the state \"PROCESSING\". ABORT
	 * | The related LCM operation shall be aborted by transitioning into the state
	 * \"FAILED_TEMP\". CANCELLED | The coordination action has been cancelled upon
	 * request of the API consumer, i.e. the VNFM. The related LCM operation shall
	 * be aborted by transitioning into the state \"FAILED_TEMP\".
	 *
	 * @return coordinationResult
	 **/
	@Schema(required = true, description = "The enumeration LcmCoordResultType defines the permitted values to represent the result of executing an LCM coordination action. The coordination result also implies the action to be performed by the VNFM as the follow-up to this coordination. Value | Description ------|------------ CONTINUE | The related LCM operation shall be continued, staying in the state \"PROCESSING\". ABORT | The related LCM operation shall be aborted by transitioning into the state \"FAILED_TEMP\". CANCELLED | The coordination action has been cancelled upon request of the API consumer, i.e. the VNFM. The related LCM operation shall be aborted by transitioning into the state \"FAILED_TEMP\". ")
	@NotNull

	public CoordinationResultEnum getCoordinationResult() {
		return coordinationResult;
	}

	public void setCoordinationResult(final CoordinationResultEnum coordinationResult) {
		this.coordinationResult = coordinationResult;
	}

	public LcmCoord vnfInstanceId(final String vnfInstanceId) {
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

	public LcmCoord vnfLcmOpOccId(final String vnfLcmOpOccId) {
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

	public LcmCoord lcmOperationType(final LcmOperationTypeEnum lcmOperationType) {
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

	public LcmCoord coordinationActionName(final String coordinationActionName) {
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

	public LcmCoord outputParams(final Object outputParams) {
		this.outputParams = outputParams;
		return this;
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

	public Object getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(final Object outputParams) {
		this.outputParams = outputParams;
	}

	public LcmCoord warnings(final String warnings) {
		this.warnings = warnings;
		return this;
	}

	/**
	 * Warning messages that were generated while the operation was executing.
	 *
	 * @return warnings
	 **/
	@Schema(description = "Warning messages that were generated while the operation was executing. ")

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(final String warnings) {
		this.warnings = warnings;
	}

	public LcmCoord error(final ProblemDetails error) {
		this.error = error;
		return this;
	}

	/**
	 * Get error
	 *
	 * @return error
	 **/
	@Schema(description = "")

	@Valid
	public ProblemDetails getError() {
		return error;
	}

	public void setError(final ProblemDetails error) {
		this.error = error;
	}

	public LcmCoord _links(final LcmCoordLinks _links) {
		this._links = _links;
		return this;
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
		final LcmCoord inlineResponse201 = (LcmCoord) o;
		return Objects.equals(this.id, inlineResponse201.id) &&
				Objects.equals(this.coordinationResult, inlineResponse201.coordinationResult) &&
				Objects.equals(this.vnfInstanceId, inlineResponse201.vnfInstanceId) &&
				Objects.equals(this.vnfLcmOpOccId, inlineResponse201.vnfLcmOpOccId) &&
				Objects.equals(this.lcmOperationType, inlineResponse201.lcmOperationType) &&
				Objects.equals(this.coordinationActionName, inlineResponse201.coordinationActionName) &&
				Objects.equals(this.outputParams, inlineResponse201.outputParams) &&
				Objects.equals(this.warnings, inlineResponse201.warnings) &&
				Objects.equals(this.error, inlineResponse201.error) &&
				Objects.equals(this._links, inlineResponse201._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, coordinationResult, vnfInstanceId, vnfLcmOpOccId, lcmOperationType, coordinationActionName, outputParams, warnings, error, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class InlineResponse201 {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    coordinationResult: ").append(toIndentedString(coordinationResult)).append("\n");
		sb.append("    vnfInstanceId: ").append(toIndentedString(vnfInstanceId)).append("\n");
		sb.append("    vnfLcmOpOccId: ").append(toIndentedString(vnfLcmOpOccId)).append("\n");
		sb.append("    lcmOperationType: ").append(toIndentedString(lcmOperationType)).append("\n");
		sb.append("    coordinationActionName: ").append(toIndentedString(coordinationActionName)).append("\n");
		sb.append("    outputParams: ").append(toIndentedString(outputParams)).append("\n");
		sb.append("    warnings: ").append(toIndentedString(warnings)).append("\n");
		sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
