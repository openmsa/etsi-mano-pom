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

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links to resources related to this resource.
 */
@Schema(description = "Links to resources related to this resource. ")
@Validated

public class ManoEntityLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("manoServiceInterfaces")
	private Link manoServiceInterfaces = null;

	@JsonProperty("peerEntities")
	private Link peerEntities = null;

	@JsonProperty("changeState")
	private Link changeState = null;

	@JsonProperty("changeStateOpOccs")
	private Link changeStateOpOccs = null;

	public ManoEntityLinks self(final Link self) {
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

	public ManoEntityLinks manoServiceInterfaces(final Link manoServiceInterfaces) {
		this.manoServiceInterfaces = manoServiceInterfaces;
		return this;
	}

	/**
	 * Get manoServiceInterfaces
	 *
	 * @return manoServiceInterfaces
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getManoServiceInterfaces() {
		return manoServiceInterfaces;
	}

	public void setManoServiceInterfaces(final Link manoServiceInterfaces) {
		this.manoServiceInterfaces = manoServiceInterfaces;
	}

	public ManoEntityLinks peerEntities(final Link peerEntities) {
		this.peerEntities = peerEntities;
		return this;
	}

	/**
	 * Get peerEntities
	 *
	 * @return peerEntities
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getPeerEntities() {
		return peerEntities;
	}

	public void setPeerEntities(final Link peerEntities) {
		this.peerEntities = peerEntities;
	}

	public ManoEntityLinks changeState(final Link changeState) {
		this.changeState = changeState;
		return this;
	}

	/**
	 * Get changeState
	 *
	 * @return changeState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getChangeState() {
		return changeState;
	}

	public void setChangeState(final Link changeState) {
		this.changeState = changeState;
	}

	public ManoEntityLinks changeStateOpOccs(final Link changeStateOpOccs) {
		this.changeStateOpOccs = changeStateOpOccs;
		return this;
	}

	/**
	 * Get changeStateOpOccs
	 *
	 * @return changeStateOpOccs
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getChangeStateOpOccs() {
		return changeStateOpOccs;
	}

	public void setChangeStateOpOccs(final Link changeStateOpOccs) {
		this.changeStateOpOccs = changeStateOpOccs;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ManoEntityLinks manoEntityLinks = (ManoEntityLinks) o;
		return Objects.equals(this.self, manoEntityLinks.self) &&
				Objects.equals(this.manoServiceInterfaces, manoEntityLinks.manoServiceInterfaces) &&
				Objects.equals(this.peerEntities, manoEntityLinks.peerEntities) &&
				Objects.equals(this.changeState, manoEntityLinks.changeState) &&
				Objects.equals(this.changeStateOpOccs, manoEntityLinks.changeStateOpOccs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(self, manoServiceInterfaces, peerEntities, changeState, changeStateOpOccs);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoEntityLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    manoServiceInterfaces: ").append(toIndentedString(manoServiceInterfaces)).append("\n");
		sb.append("    peerEntities: ").append(toIndentedString(peerEntities)).append("\n");
		sb.append("    changeState: ").append(toIndentedString(changeState)).append("\n");
		sb.append("    changeStateOpOccs: ").append(toIndentedString(changeStateOpOccs)).append("\n");
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
