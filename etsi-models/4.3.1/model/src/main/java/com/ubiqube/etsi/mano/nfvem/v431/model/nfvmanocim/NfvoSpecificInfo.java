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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents information attributes specific to an NFVO entity, and
 * that can be relevant to more than one NFV-MANO service offered by an NFVO
 * entity. * NOTE 1: The information to be provided in this attribute shall
 * relate to the specification and version of the specification. For instance,
 * \&quot;ETSI GS NFV-SOL 001 (V2.5.1)\&quot;. NOTE 2: If this attribute is not
 * present, the value of this parameter is undefined.
 */
@Schema(description = "This type represents information attributes specific to an NFVO entity, and that can be relevant to more than one NFV-MANO service offered by an NFVO entity. * NOTE 1: The information to be provided in this attribute shall relate to the specification and version             of the specification. For instance, \"ETSI GS NFV-SOL 001 (V2.5.1)\".   NOTE 2: If this attribute is not present, the value of this parameter is undefined. ")
@Validated

public class NfvoSpecificInfo {
	@JsonProperty("maxOnboardedNsdNum")
	private Integer maxOnboardedNsdNum = null;

	@JsonProperty("maxOnboardedVnfPkgNum")
	private Integer maxOnboardedVnfPkgNum = null;

	@JsonProperty("supportedVnfdFormats")
	private List<NfvoSpecificInfoSupportedVnfdFormats> supportedVnfdFormats = null;

	@JsonProperty("supportedNsdFormats")
	private List<NfvoSpecificInfoSupportedNsdFormats> supportedNsdFormats = null;

	public NfvoSpecificInfo maxOnboardedNsdNum(final Integer maxOnboardedNsdNum) {
		this.maxOnboardedNsdNum = maxOnboardedNsdNum;
		return this;
	}

	/**
	 * Maximum number of NSDs that can be on-boarded on the NFVO. See note 2.
	 *
	 * @return maxOnboardedNsdNum
	 **/
	@Schema(description = "Maximum number of NSDs that can be on-boarded on the NFVO. See note 2. ")

	public Integer getMaxOnboardedNsdNum() {
		return maxOnboardedNsdNum;
	}

	public void setMaxOnboardedNsdNum(final Integer maxOnboardedNsdNum) {
		this.maxOnboardedNsdNum = maxOnboardedNsdNum;
	}

	public NfvoSpecificInfo maxOnboardedVnfPkgNum(final Integer maxOnboardedVnfPkgNum) {
		this.maxOnboardedVnfPkgNum = maxOnboardedVnfPkgNum;
		return this;
	}

	/**
	 * Maximum number of VNF Packages that can be on-boarded on the NFVO. See note
	 * 2.
	 *
	 * @return maxOnboardedVnfPkgNum
	 **/
	@Schema(description = "Maximum number of VNF Packages that can be on-boarded on the NFVO. See note 2. ")

	public Integer getMaxOnboardedVnfPkgNum() {
		return maxOnboardedVnfPkgNum;
	}

	public void setMaxOnboardedVnfPkgNum(final Integer maxOnboardedVnfPkgNum) {
		this.maxOnboardedVnfPkgNum = maxOnboardedVnfPkgNum;
	}

	public NfvoSpecificInfo supportedVnfdFormats(final List<NfvoSpecificInfoSupportedVnfdFormats> supportedVnfdFormats) {
		this.supportedVnfdFormats = supportedVnfdFormats;
		return this;
	}

	/**
	 * Get supportedVnfdFormats
	 *
	 * @return supportedVnfdFormats
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public List<NfvoSpecificInfoSupportedVnfdFormats> getSupportedVnfdFormats() {
		return supportedVnfdFormats;
	}

	public void setSupportedVnfdFormats(final List<NfvoSpecificInfoSupportedVnfdFormats> supportedVnfdFormats) {
		this.supportedVnfdFormats = supportedVnfdFormats;
	}

	public NfvoSpecificInfo supportedNsdFormats(final List<NfvoSpecificInfoSupportedNsdFormats> supportedNsdFormats) {
		this.supportedNsdFormats = supportedNsdFormats;
		return this;
	}

	/**
	 * Get supportedNsdFormats
	 *
	 * @return supportedNsdFormats
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public List<NfvoSpecificInfoSupportedNsdFormats> getSupportedNsdFormats() {
		return supportedNsdFormats;
	}

	public void setSupportedNsdFormats(final List<NfvoSpecificInfoSupportedNsdFormats> supportedNsdFormats) {
		this.supportedNsdFormats = supportedNsdFormats;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NfvoSpecificInfo nfvoSpecificInfo = (NfvoSpecificInfo) o;
		return Objects.equals(this.maxOnboardedNsdNum, nfvoSpecificInfo.maxOnboardedNsdNum) &&
				Objects.equals(this.maxOnboardedVnfPkgNum, nfvoSpecificInfo.maxOnboardedVnfPkgNum) &&
				Objects.equals(this.supportedVnfdFormats, nfvoSpecificInfo.supportedVnfdFormats) &&
				Objects.equals(this.supportedNsdFormats, nfvoSpecificInfo.supportedNsdFormats);
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxOnboardedNsdNum, maxOnboardedVnfPkgNum, supportedVnfdFormats, supportedNsdFormats);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NfvoSpecificInfo {\n");

		sb.append("    maxOnboardedNsdNum: ").append(toIndentedString(maxOnboardedNsdNum)).append("\n");
		sb.append("    maxOnboardedVnfPkgNum: ").append(toIndentedString(maxOnboardedVnfPkgNum)).append("\n");
		sb.append("    supportedVnfdFormats: ").append(toIndentedString(supportedVnfdFormats)).append("\n");
		sb.append("    supportedNsdFormats: ").append(toIndentedString(supportedNsdFormats)).append("\n");
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
