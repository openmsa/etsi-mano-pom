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
package com.ubiqube.etsi.mano.nfvo.v431.model.lcmcoord;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.Link;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links to resources related to this resource.
 */
@Schema(description = "Links to resources related to this resource. ")
@Validated

public class LcmCoordLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("nsLcmOpOcc")
	private Link nsLcmOpOcc = null;

	@JsonProperty("nsInstance")
	private Link nsInstance = null;

	public LcmCoordLinks self(final Link self) {
		this.self = self;
		return this;
	}

	/**
	 * Get self
	 *
	 * @return self
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getSelf() {
		return self;
	}

	public void setSelf(final Link self) {
		this.self = self;
	}

	public LcmCoordLinks nsLcmOpOcc(final Link nsLcmOpOcc) {
		this.nsLcmOpOcc = nsLcmOpOcc;
		return this;
	}

	/**
	 * Get nsLcmOpOcc
	 *
	 * @return nsLcmOpOcc
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getNsLcmOpOcc() {
		return nsLcmOpOcc;
	}

	public void setNsLcmOpOcc(final Link nsLcmOpOcc) {
		this.nsLcmOpOcc = nsLcmOpOcc;
	}

	public LcmCoordLinks nsInstance(final Link nsInstance) {
		this.nsInstance = nsInstance;
		return this;
	}

	/**
	 * Get nsInstance
	 *
	 * @return nsInstance
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getNsInstance() {
		return nsInstance;
	}

	public void setNsInstance(final Link nsInstance) {
		this.nsInstance = nsInstance;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final LcmCoordLinks lcmCoordLinks = (LcmCoordLinks) o;
		return Objects.equals(this.self, lcmCoordLinks.self) &&
				Objects.equals(this.nsLcmOpOcc, lcmCoordLinks.nsLcmOpOcc) &&
				Objects.equals(this.nsInstance, lcmCoordLinks.nsInstance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(self, nsLcmOpOcc, nsInstance);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class LcmCoordLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    nsLcmOpOcc: ").append(toIndentedString(nsLcmOpOcc)).append("\n");
		sb.append("    nsInstance: ").append(toIndentedString(nsInstance)).append("\n");
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
