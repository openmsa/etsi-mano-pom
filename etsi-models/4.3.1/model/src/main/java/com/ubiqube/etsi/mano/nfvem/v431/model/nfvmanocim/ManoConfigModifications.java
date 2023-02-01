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
 * This type represents attribute modifications that were performed on the
 * \&quot;NFV-MANO entity\&quot; resource of the producer NFV-MANO functional
 * entity. The attributes that can be included consist of those requested to be
 * modified explicitly in the \&quot;ManoConfigModificationRequest\&quot; data
 * structure.
 */
@Schema(description = "This type represents attribute modifications that were performed on the \"NFV-MANO entity\" resource of the producer NFV-MANO functional entity. The attributes that can be included consist of those requested to be modified explicitly in the \"ManoConfigModificationRequest\" data structure. ")
@Validated

public class ManoConfigModifications {
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
	private List<ManoConfigModificationsManoServiceModifications> manoServiceModifications = null;

	public ManoConfigModifications name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * If present, this attribute signals modifications of the \"name\" attribute in
	 * \"ManoEntity\", as defined in clause 5.6.2.3
	 *
	 * @return name
	 **/
	@Schema(description = "If present, this attribute signals modifications of the \"name\" attribute in \"ManoEntity\", as defined in clause 5.6.2.3 ")

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ManoConfigModifications description(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * If present, this attribute signals modifications of the \"description\"
	 * attribute in \"ManoEntity\", as defined in clause 5.6.2.3.
	 *
	 * @return description
	 **/
	@Schema(description = "If present, this attribute signals modifications of the \"description\" attribute in \"ManoEntity\", as defined in clause 5.6.2.3. ")

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ManoConfigModifications clockSyncs(final Map<String, ClockSyncInfo> clockSyncs) {
		this.clockSyncs = clockSyncs;
		return this;
	}

	public ManoConfigModifications putClockSyncsItem(final String key, final ClockSyncInfo clockSyncsItem) {
		if (this.clockSyncs == null) {
			this.clockSyncs = new HashMap<>();
		}
		this.clockSyncs.put(key, clockSyncsItem);
		return this;
	}

	/**
	 * If present, this attribute signals modifications of the \"clockSyncs\"
	 * attribute in \"ManoEntityConfigurableParams\", as defined in clause 5.6.2.3.
	 *
	 * @return clockSyncs
	 **/
	@Schema(description = "If present, this attribute signals modifications of the \"clockSyncs\" attribute in \"ManoEntityConfigurableParams\", as defined in clause 5.6.2.3. ")
	@Valid
	public Map<String, ClockSyncInfo> getClockSyncs() {
		return clockSyncs;
	}

	public void setClockSyncs(final Map<String, ClockSyncInfo> clockSyncs) {
		this.clockSyncs = clockSyncs;
	}

	public ManoConfigModifications defaultLogCompileBySizeValue(final BigDecimal defaultLogCompileBySizeValue) {
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

	public ManoConfigModifications defaultLogCompileByTimerValue(final BigDecimal defaultLogCompileByTimerValue) {
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

	public ManoConfigModifications manoServiceModifications(final List<ManoConfigModificationsManoServiceModifications> manoServiceModifications) {
		this.manoServiceModifications = manoServiceModifications;
		return this;
	}

	public ManoConfigModifications addManoServiceModificationsItem(final ManoConfigModificationsManoServiceModifications manoServiceModificationsItem) {
		if (this.manoServiceModifications == null) {
			this.manoServiceModifications = new ArrayList<>();
		}
		this.manoServiceModifications.add(manoServiceModificationsItem);
		return this;
	}

	/**
	 * If present, this attribute signals modifications of the \"manoServices\"
	 * attribute array in the \"ManoEntity\", as defined in clause 5.6.2.3.
	 *
	 * @return manoServiceModifications
	 **/
	@Schema(description = "If present, this attribute signals modifications of the \"manoServices\" attribute array in the \"ManoEntity\", as defined in clause 5.6.2.3. ")
	@Valid
	public List<ManoConfigModificationsManoServiceModifications> getManoServiceModifications() {
		return manoServiceModifications;
	}

	public void setManoServiceModifications(final List<ManoConfigModificationsManoServiceModifications> manoServiceModifications) {
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
		final ManoConfigModifications manoConfigModifications = (ManoConfigModifications) o;
		return Objects.equals(this.name, manoConfigModifications.name) &&
				Objects.equals(this.description, manoConfigModifications.description) &&
				Objects.equals(this.clockSyncs, manoConfigModifications.clockSyncs) &&
				Objects.equals(this.defaultLogCompileBySizeValue, manoConfigModifications.defaultLogCompileBySizeValue) &&
				Objects.equals(this.defaultLogCompileByTimerValue, manoConfigModifications.defaultLogCompileByTimerValue) &&
				Objects.equals(this.manoServiceModifications, manoConfigModifications.manoServiceModifications);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, clockSyncs, defaultLogCompileBySizeValue, defaultLogCompileByTimerValue, manoServiceModifications);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoConfigModifications {\n");

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
