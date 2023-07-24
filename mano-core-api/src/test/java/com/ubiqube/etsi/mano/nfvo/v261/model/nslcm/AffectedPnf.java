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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.nfvo.v261.model.nslcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.annotation.Nonnull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type provides information about added, deleted and modified PNFs. It
 * shall comply with the provisions in Table 6.5.3.3-1.
 */
@Schema(description = "This type provides information about added, deleted and modified PNFs.  It shall comply with the provisions in Table 6.5.3.3-1. ")
@Validated


public class AffectedPnf {
	@JsonProperty("pnfId")
	private String pnfId = null;

	@JsonProperty("pnfdId")
	private String pnfdId = null;

	@JsonProperty("pnfProfileId")
	private String pnfProfileId = null;

	@JsonProperty("pnfName")
	private String pnfName = null;

	@JsonProperty("cpInstanceId")
	@Valid
	private List<String> cpInstanceId = new ArrayList<>();

	/**
	 * Signals the type of change. Permitted values: - ADD - REMOVE - MODIFY
	 */
	public enum ChangeTypeEnum {
		ADD("ADD"),

		REMOVE("REMOVE"),

		MODIFY("MODIFY");

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

	/**
	 * Signals the result of change identified by the \"changeType\" attribute.
	 * Permitted values: - COMPLETED - ROLLED_BACK - FAILED
	 */
	public enum ChangeResultEnum {
		COMPLETED("COMPLETED"),

		ROLLED_BACK("ROLLED_BACK"),

		FAILED("FAILED");

		private final String value;

		ChangeResultEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ChangeResultEnum fromValue(final String text) {
			for (final ChangeResultEnum b : ChangeResultEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("changeResult")
	private ChangeResultEnum changeResult = null;

	public AffectedPnf pnfId(final String pnfId) {
		this.pnfId = pnfId;
		return this;
	}

	/**
	 * Identifier of the affected PNF. This identifier is allocated by the OSS/BSS.
	 * 
	 * @return pnfId
	 **/
	@Schema(required = true, description = "Identifier of the affected PNF. This identifier is allocated by the OSS/BSS. ")
	@Nonnull

	public String getPnfId() {
		return pnfId;
	}

	public void setPnfId(final String pnfId) {
		this.pnfId = pnfId;
	}

	public AffectedPnf pnfdId(final String pnfdId) {
		this.pnfdId = pnfdId;
		return this;
	}

	/**
	 * Identifier of the PNFD on which the PNF is based.
	 * 
	 * @return pnfdId
	 **/
	@Schema(required = true, description = "Identifier of the PNFD on which the PNF is based. ")
	@Nonnull

	public String getPnfdId() {
		return pnfdId;
	}

	public void setPnfdId(final String pnfdId) {
		this.pnfdId = pnfdId;
	}

	public AffectedPnf pnfProfileId(final String pnfProfileId) {
		this.pnfProfileId = pnfProfileId;
		return this;
	}

	/**
	 * Identifier of the VNF profile of the NSD.
	 * 
	 * @return pnfProfileId
	 **/
	@Schema(required = true, description = "Identifier of the VNF profile of the NSD. ")
	@Nonnull

	public String getPnfProfileId() {
		return pnfProfileId;
	}

	public void setPnfProfileId(final String pnfProfileId) {
		this.pnfProfileId = pnfProfileId;
	}

	public AffectedPnf pnfName(final String pnfName) {
		this.pnfName = pnfName;
		return this;
	}

	/**
	 * Name of the PNF.
	 * 
	 * @return pnfName
	 **/
	@Schema(description = "Name of the PNF. ")

	public String getPnfName() {
		return pnfName;
	}

	public void setPnfName(final String pnfName) {
		this.pnfName = pnfName;
	}

	public AffectedPnf cpInstanceId(final List<String> cpInstanceId) {
		this.cpInstanceId = cpInstanceId;
		return this;
	}

	public AffectedPnf addCpInstanceIdItem(final String cpInstanceIdItem) {
		this.cpInstanceId.add(cpInstanceIdItem);
		return this;
	}

	/**
	 * Identifier of the CP in the scope of the PNF.
	 * 
	 * @return cpInstanceId
	 **/
	@Schema(required = true, description = "Identifier of the CP in the scope of the PNF. ")
	@Nonnull

	public List<String> getCpInstanceId() {
		return cpInstanceId;
	}

	public void setCpInstanceId(final List<String> cpInstanceId) {
		this.cpInstanceId = cpInstanceId;
	}

	public AffectedPnf changeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
		return this;
	}

	/**
	 * Signals the type of change. Permitted values: - ADD - REMOVE - MODIFY
	 * 
	 * @return changeType
	 **/
	@Schema(description = "Signals the type of change. Permitted values: - ADD - REMOVE - MODIFY ")

	public ChangeTypeEnum getChangeType() {
		return changeType;
	}

	public void setChangeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
	}

	public AffectedPnf changeResult(final ChangeResultEnum changeResult) {
		this.changeResult = changeResult;
		return this;
	}

	/**
	 * Signals the result of change identified by the \"changeType\" attribute.
	 * Permitted values: - COMPLETED - ROLLED_BACK - FAILED
	 * 
	 * @return changeResult
	 **/
	@Schema(description = "Signals the result of change identified by the \"changeType\" attribute. Permitted values: - COMPLETED - ROLLED_BACK - FAILED ")

	public ChangeResultEnum getChangeResult() {
		return changeResult;
	}

	public void setChangeResult(final ChangeResultEnum changeResult) {
		this.changeResult = changeResult;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AffectedPnf affectedPnf = (AffectedPnf) o;
		return Objects.equals(this.pnfId, affectedPnf.pnfId) &&
				Objects.equals(this.pnfdId, affectedPnf.pnfdId) &&
				Objects.equals(this.pnfProfileId, affectedPnf.pnfProfileId) &&
				Objects.equals(this.pnfName, affectedPnf.pnfName) &&
				Objects.equals(this.cpInstanceId, affectedPnf.cpInstanceId) &&
				Objects.equals(this.changeType, affectedPnf.changeType) &&
				Objects.equals(this.changeResult, affectedPnf.changeResult);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pnfId, pnfdId, pnfProfileId, pnfName, cpInstanceId, changeType, changeResult);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AffectedPnf {\n");

		sb.append("    pnfId: ").append(toIndentedString(pnfId)).append("\n");
		sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
		sb.append("    pnfProfileId: ").append(toIndentedString(pnfProfileId)).append("\n");
		sb.append("    pnfName: ").append(toIndentedString(pnfName)).append("\n");
		sb.append("    cpInstanceId: ").append(toIndentedString(cpInstanceId)).append("\n");
		sb.append("    changeType: ").append(toIndentedString(changeType)).append("\n");
		sb.append("    changeResult: ").append(toIndentedString(changeResult)).append("\n");
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
