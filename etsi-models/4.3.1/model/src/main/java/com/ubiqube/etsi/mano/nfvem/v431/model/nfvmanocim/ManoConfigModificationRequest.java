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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents attribute modifications for configuration parameters of
 * an NFV-MANO functional entity. * NOTE: At least one of these attributes shall
 * be provided if requesting a modification of the NFV-MANO service identified
 * by \&quot;id\&quot;.
 */
@Schema(description = "This type represents attribute modifications for configuration parameters of an NFV-MANO functional entity. * NOTE: At least one of these attributes shall be provided if requesting a modification         of the NFV-MANO service identified by \"id\". ")
@Validated

public class ManoConfigModificationRequest {
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("clockSyncs")
	@Valid
	private Map<String, ClockSyncInfo> clockSyncs = null;

	@JsonProperty("defaultLogCompileBySizeValue")
	private BigDecimal defaultLogCompileBySizeValue = null;

	@JsonProperty("defaultLogCompileByTimerValue")
	private BigDecimal defaultLogCompileByTimerValue = null;

	@JsonProperty("manoServiceModifications")
	@Valid
	private List<ManoConfigModificationRequestManoServiceModifications> manoServiceModifications = null;

	public ManoConfigModificationRequest name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * New value of the \"name\" attribute in \"ManoEntity\".
	 *
	 * @return name
	 **/
	@Schema(description = "New value of the \"name\" attribute in \"ManoEntity\". ")

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ManoConfigModificationRequest description(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * New value of the \"description\" attribute in \"ManoEntity\".
	 *
	 * @return description
	 **/
	@Schema(description = "New value of the \"description\" attribute in \"ManoEntity\". ")

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ManoConfigModificationRequest clockSyncs(final Map<String, ClockSyncInfo> clockSyncs) {
		this.clockSyncs = clockSyncs;
		return this;
	}

	public ManoConfigModificationRequest putClockSyncsItem(final String key, final ClockSyncInfo clockSyncsItem) {
		if (this.clockSyncs == null) {
			this.clockSyncs = new HashMap<>();
		}
		this.clockSyncs.put(key, clockSyncsItem);
		return this;
	}

	/**
	 * Modifications of the \"clockSyncs\" attribute in the
	 * \"ManoEntityConfigurableParams\". If present, these modifications shall be
	 * applied according to the rules of JSON Merge PATCH (see IETF RFC 7396).
	 *
	 * @return clockSyncs
	 **/
	@Schema(description = "Modifications of the \"clockSyncs\" attribute in the \"ManoEntityConfigurableParams\". If present, these modifications shall be applied according to the rules of JSON Merge PATCH (see IETF RFC 7396). ")
	@Valid
	public Map<String, ClockSyncInfo> getClockSyncs() {
		return clockSyncs;
	}

	public void setClockSyncs(final Map<String, ClockSyncInfo> clockSyncs) {
		this.clockSyncs = clockSyncs;
	}

	public ManoConfigModificationRequest defaultLogCompileBySizeValue(final BigDecimal defaultLogCompileBySizeValue) {
		this.defaultLogCompileBySizeValue = defaultLogCompileBySizeValue;
		return this;
	}

	/**
	 * Unsigned integer
	 *
	 * @return defaultLogCompileBySizeValue
	 **/
	@Schema(description = "Unsigned integer ")

	@Valid
	public BigDecimal getDefaultLogCompileBySizeValue() {
		return defaultLogCompileBySizeValue;
	}

	public void setDefaultLogCompileBySizeValue(final BigDecimal defaultLogCompileBySizeValue) {
		this.defaultLogCompileBySizeValue = defaultLogCompileBySizeValue;
	}

	public ManoConfigModificationRequest defaultLogCompileByTimerValue(final BigDecimal defaultLogCompileByTimerValue) {
		this.defaultLogCompileByTimerValue = defaultLogCompileByTimerValue;
		return this;
	}

	/**
	 * Unsigned integer
	 *
	 * @return defaultLogCompileByTimerValue
	 **/
	@Schema(description = "Unsigned integer ")

	@Valid
	public BigDecimal getDefaultLogCompileByTimerValue() {
		return defaultLogCompileByTimerValue;
	}

	public void setDefaultLogCompileByTimerValue(final BigDecimal defaultLogCompileByTimerValue) {
		this.defaultLogCompileByTimerValue = defaultLogCompileByTimerValue;
	}

	public ManoConfigModificationRequest manoServiceModifications(final List<ManoConfigModificationRequestManoServiceModifications> manoServiceModifications) {
		this.manoServiceModifications = manoServiceModifications;
		return this;
	}

	public ManoConfigModificationRequest addManoServiceModificationsItem(final ManoConfigModificationRequestManoServiceModifications manoServiceModificationsItem) {
		if (this.manoServiceModifications == null) {
			this.manoServiceModifications = new ArrayList<>();
		}
		this.manoServiceModifications.add(manoServiceModificationsItem);
		return this;
	}

	/**
	 * New content of certain entries in the \"manoServices\" attribute array in the
	 * \"ManoEntity\", as defined below this table. The following provisions shall
	 * apply when modifying an attribute that is an array of structured
	 * \"manoServiceModifications\". Assumptions: A) \"oldList\" is the array to be
	 * modified, \"newList\" is the array that contains the changes. B) \"oldEntry\"
	 * is an entry in \"oldList\" and \"newEntry\" is an entry in \"newList\". C) A
	 * \"newEntry\" has a \"corresponding entry\" if there exists an \"oldEntry\"
	 * that has the same content of \"id\" attribute as the \"newEntry\". D) In any
	 * array of objects, the content of \"id\" is unique (i.e. there are no two
	 * entries with the same content of \"id\"). Provisions: 1) For each
	 * \"newEntry\" in \"newList\" that has a corresponding \"oldEntry\" in
	 * \"oldList\", the values of the sub-attributes of \"oldEntry\" shall be
	 * replaced by the value of the sub-attributes in the \"newEntry\". 2) If the
	 * \"newEntry\" in \"newList\" has no corresponding \"oldEntry\" in \"oldList\"
	 * (i.e. the \"id\" is not found), the modification operation will fail as a
	 * whole and an error message shall be raised.
	 *
	 * @return manoServiceModifications
	 **/
	@Schema(description = "New content of certain entries in the \"manoServices\" attribute array in the \"ManoEntity\", as defined below this table. The following provisions shall apply when modifying an attribute that is an array of structured \"manoServiceModifications\". Assumptions:   A) \"oldList\" is the array to be modified, \"newList\" is the array that contains the changes.   B) \"oldEntry\" is an entry in \"oldList\" and \"newEntry\" is an entry in \"newList\".   C) A \"newEntry\" has a \"corresponding entry\" if there exists an \"oldEntry\" that has the same         content of \"id\" attribute as the \"newEntry\".   D) In any array of objects, the content of \"id\" is unique (i.e. there are no two entries         with the same content of \"id\"). Provisions:   1) For each \"newEntry\" in \"newList\" that has a corresponding \"oldEntry\" in \"oldList\", the values of         the sub-attributes of \"oldEntry\" shall be replaced by the value of the sub-attributes in the \"newEntry\".   2) If the \"newEntry\" in \"newList\" has no corresponding \"oldEntry\" in \"oldList\" (i.e. the \"id\" is         not found), the modification operation will fail as a whole and an error message shall be raised. ")
	@Valid
	public List<ManoConfigModificationRequestManoServiceModifications> getManoServiceModifications() {
		return manoServiceModifications;
	}

	public void setManoServiceModifications(final List<ManoConfigModificationRequestManoServiceModifications> manoServiceModifications) {
		this.manoServiceModifications = manoServiceModifications;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ManoConfigModificationRequest manoConfigModificationRequest = (ManoConfigModificationRequest) o;
		return Objects.equals(this.name, manoConfigModificationRequest.name) &&
				Objects.equals(this.description, manoConfigModificationRequest.description) &&
				Objects.equals(this.clockSyncs, manoConfigModificationRequest.clockSyncs) &&
				Objects.equals(this.defaultLogCompileBySizeValue, manoConfigModificationRequest.defaultLogCompileBySizeValue) &&
				Objects.equals(this.defaultLogCompileByTimerValue, manoConfigModificationRequest.defaultLogCompileByTimerValue) &&
				Objects.equals(this.manoServiceModifications, manoConfigModificationRequest.manoServiceModifications);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, clockSyncs, defaultLogCompileBySizeValue, defaultLogCompileByTimerValue, manoServiceModifications);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoConfigModificationRequest {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    clockSyncs: ").append(toIndentedString(clockSyncs)).append("\n");
		sb.append("    defaultLogCompileBySizeValue: ").append(toIndentedString(defaultLogCompileBySizeValue)).append("\n");
		sb.append("    defaultLogCompileByTimerValue: ").append(toIndentedString(defaultLogCompileByTimerValue)).append("\n");
		sb.append("    manoServiceModifications: ").append(toIndentedString(manoServiceModifications)).append("\n");
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
