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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvo.v431.model.nfvici.Link;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links related to NS resources affected by the shortage of this operation
 * occurrence.
 */
@Schema(description = "Links related to NS resources affected by the shortage of this operation occurrence. ")
@Validated

public class NsLcmCapacityShortageNotificationLinks {
	@JsonProperty("affectedLcmOpOcc")
	private Link affectedLcmOpOcc = null;

	@JsonProperty("affectedNs")
	private Link affectedNs = null;

	public NsLcmCapacityShortageNotificationLinks affectedLcmOpOcc(final Link affectedLcmOpOcc) {
		this.affectedLcmOpOcc = affectedLcmOpOcc;
		return this;
	}

	/**
	 * Get affectedLcmOpOcc
	 *
	 * @return affectedLcmOpOcc
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getAffectedLcmOpOcc() {
		return affectedLcmOpOcc;
	}

	public void setAffectedLcmOpOcc(final Link affectedLcmOpOcc) {
		this.affectedLcmOpOcc = affectedLcmOpOcc;
	}

	public NsLcmCapacityShortageNotificationLinks affectedNs(final Link affectedNs) {
		this.affectedNs = affectedNs;
		return this;
	}

	/**
	 * Get affectedNs
	 *
	 * @return affectedNs
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getAffectedNs() {
		return affectedNs;
	}

	public void setAffectedNs(final Link affectedNs) {
		this.affectedNs = affectedNs;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NsLcmCapacityShortageNotificationLinks nsLcmCapacityShortageNotificationLinks = (NsLcmCapacityShortageNotificationLinks) o;
		return Objects.equals(this.affectedLcmOpOcc, nsLcmCapacityShortageNotificationLinks.affectedLcmOpOcc) &&
				Objects.equals(this.affectedNs, nsLcmCapacityShortageNotificationLinks.affectedNs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(affectedLcmOpOcc, affectedNs);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsLcmCapacityShortageNotificationLinks {\n");

		sb.append("    affectedLcmOpOcc: ").append(toIndentedString(affectedLcmOpOcc)).append("\n");
		sb.append("    affectedNs: ").append(toIndentedString(affectedNs)).append("\n");
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
