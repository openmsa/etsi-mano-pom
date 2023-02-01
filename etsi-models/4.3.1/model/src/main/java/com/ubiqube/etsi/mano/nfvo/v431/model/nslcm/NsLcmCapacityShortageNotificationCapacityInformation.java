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
 * NsLcmCapacityShortageNotificationCapacityInformation
 */
@Validated

public class NsLcmCapacityShortageNotificationCapacityInformation {
	@JsonProperty("vimId")
	private String vimId = null;

	@JsonProperty("_link")
	private Link _link = null;

	public NsLcmCapacityShortageNotificationCapacityInformation vimId(final String vimId) {
		this.vimId = vimId;
		return this;
	}

	/**
	 * Get vimId
	 *
	 * @return vimId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVimId() {
		return vimId;
	}

	public void setVimId(final String vimId) {
		this.vimId = vimId;
	}

	public NsLcmCapacityShortageNotificationCapacityInformation _link(final Link _link) {
		this._link = _link;
		return this;
	}

	/**
	 * Get _link
	 *
	 * @return _link
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getLink() {
		return _link;
	}

	public void setLink(final Link _link) {
		this._link = _link;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NsLcmCapacityShortageNotificationCapacityInformation nsLcmCapacityShortageNotificationCapacityInformation = (NsLcmCapacityShortageNotificationCapacityInformation) o;
		return Objects.equals(this.vimId, nsLcmCapacityShortageNotificationCapacityInformation.vimId) &&
				Objects.equals(this._link, nsLcmCapacityShortageNotificationCapacityInformation._link);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vimId, _link);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsLcmCapacityShortageNotificationCapacityInformation {\n");

		sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
		sb.append("    _link: ").append(toIndentedString(_link)).append("\n");
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
