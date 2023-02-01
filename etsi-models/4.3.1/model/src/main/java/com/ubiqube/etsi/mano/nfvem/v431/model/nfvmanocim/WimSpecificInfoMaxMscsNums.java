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

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Maximum number of MSCS that the WIM can manage.
 */
@Schema(description = "Maximum number of MSCS that the WIM can manage. ")
@Validated

public class WimSpecificInfoMaxMscsNums {
	@JsonProperty("numMscs")
	private Integer numMscs = null;

	@JsonProperty("criteriaNumMscs")
	private Map<String, String> criteriaNumMscs = null;

	public WimSpecificInfoMaxMscsNums numMscs(final Integer numMscs) {
		this.numMscs = numMscs;
		return this;
	}

	/**
	 * Maximum number of MSCS.
	 *
	 * @return numMscs
	 **/
	@Schema(required = true, description = "Maximum number of MSCS. ")
	@NotNull

	public Integer getNumMscs() {
		return numMscs;
	}

	public void setNumMscs(final Integer numMscs) {
		this.numMscs = numMscs;
	}

	public WimSpecificInfoMaxMscsNums criteriaNumMscs(final Map<String, String> criteriaNumMscs) {
		this.criteriaNumMscs = criteriaNumMscs;
		return this;
	}

	/**
	 * Get criteriaNumMscs
	 *
	 * @return criteriaNumMscs
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getCriteriaNumMscs() {
		return criteriaNumMscs;
	}

	public void setCriteriaNumMscs(final Map<String, String> criteriaNumMscs) {
		this.criteriaNumMscs = criteriaNumMscs;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final WimSpecificInfoMaxMscsNums wimSpecificInfoMaxMscsNums = (WimSpecificInfoMaxMscsNums) o;
		return Objects.equals(this.numMscs, wimSpecificInfoMaxMscsNums.numMscs) &&
				Objects.equals(this.criteriaNumMscs, wimSpecificInfoMaxMscsNums.criteriaNumMscs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numMscs, criteriaNumMscs);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class WimSpecificInfoMaxMscsNums {\n");

		sb.append("    numMscs: ").append(toIndentedString(numMscs)).append("\n");
		sb.append("    criteriaNumMscs: ").append(toIndentedString(criteriaNumMscs)).append("\n");
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
