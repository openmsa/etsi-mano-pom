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
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.NotificationLink;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * LccnLinks
 */
@Validated

public class LccnLinks {
	@JsonProperty("nsInstance")
	private NotificationLink nsInstance = null;

	@JsonProperty("subscription")
	private NotificationLink subscription = null;

	@JsonProperty("nslcmOpOcc")
	private NotificationLink nslcmOpOcc = null;

	public LccnLinks nsInstance(final NotificationLink nsInstance) {
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
	public NotificationLink getNsInstance() {
		return nsInstance;
	}

	public void setNsInstance(final NotificationLink nsInstance) {
		this.nsInstance = nsInstance;
	}

	public LccnLinks subscription(final NotificationLink subscription) {
		this.subscription = subscription;
		return this;
	}

	/**
	 * Get subscription
	 *
	 * @return subscription
	 **/
	@Schema(description = "")

	@Valid
	public NotificationLink getSubscription() {
		return subscription;
	}

	public void setSubscription(final NotificationLink subscription) {
		this.subscription = subscription;
	}

	public LccnLinks nslcmOpOcc(final NotificationLink nslcmOpOcc) {
		this.nslcmOpOcc = nslcmOpOcc;
		return this;
	}

	/**
	 * Get nslcmOpOcc
	 *
	 * @return nslcmOpOcc
	 **/
	@Schema(description = "")

	@Valid
	public NotificationLink getNslcmOpOcc() {
		return nslcmOpOcc;
	}

	public void setNslcmOpOcc(final NotificationLink nslcmOpOcc) {
		this.nslcmOpOcc = nslcmOpOcc;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final LccnLinks lccnLinks = (LccnLinks) o;
		return Objects.equals(this.nsInstance, lccnLinks.nsInstance) &&
				Objects.equals(this.subscription, lccnLinks.subscription) &&
				Objects.equals(this.nslcmOpOcc, lccnLinks.nslcmOpOcc);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nsInstance, subscription, nslcmOpOcc);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class LccnLinks {\n");

		sb.append("    nsInstance: ").append(toIndentedString(nsInstance)).append("\n");
		sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
		sb.append("    nslcmOpOcc: ").append(toIndentedString(nslcmOpOcc)).append("\n");
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
