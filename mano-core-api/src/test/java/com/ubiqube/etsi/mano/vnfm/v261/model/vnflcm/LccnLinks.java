/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.v261.model.vnflcm;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 * This type represents the links to resources that a notification can contain.
 */
@Schema(description = "This type represents the links to resources that a notification can contain. ")
@Validated

public class LccnLinks {
	@JsonProperty("vnfInstance")
	private NotificationLink vnfInstance = null;

	@JsonProperty("subscription")
	private NotificationLink subscription = null;

	@JsonProperty("vnfLcmOpOcc")
	private NotificationLink vnfLcmOpOcc = null;

	public LccnLinks vnfInstance(final NotificationLink vnfInstance) {
		this.vnfInstance = vnfInstance;
		return this;
	}

	/**
	 * Get vnfInstance
	 *
	 * @return vnfInstance
	 **/
	@Schema(required = true, description = "")
	@Nonnull

	@Valid
	public NotificationLink getVnfInstance() {
		return vnfInstance;
	}

	public void setVnfInstance(final NotificationLink vnfInstance) {
		this.vnfInstance = vnfInstance;
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
	@Schema(required = true, description = "")
	@Nonnull

	@Valid
	public NotificationLink getSubscription() {
		return subscription;
	}

	public void setSubscription(final NotificationLink subscription) {
		this.subscription = subscription;
	}

	public LccnLinks vnfLcmOpOcc(final NotificationLink vnfLcmOpOcc) {
		this.vnfLcmOpOcc = vnfLcmOpOcc;
		return this;
	}

	/**
	 * Get vnfLcmOpOcc
	 *
	 * @return vnfLcmOpOcc
	 **/
	@Schema(description = "")

	@Valid
	public NotificationLink getVnfLcmOpOcc() {
		return vnfLcmOpOcc;
	}

	public void setVnfLcmOpOcc(final NotificationLink vnfLcmOpOcc) {
		this.vnfLcmOpOcc = vnfLcmOpOcc;
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
		return Objects.equals(this.vnfInstance, lccnLinks.vnfInstance) &&
				Objects.equals(this.subscription, lccnLinks.subscription) &&
				Objects.equals(this.vnfLcmOpOcc, lccnLinks.vnfLcmOpOcc);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfInstance, subscription, vnfLcmOpOcc);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class LccnLinks {\n");

		sb.append("    vnfInstance: ").append(toIndentedString(vnfInstance)).append("\n");
		sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
		sb.append("    vnfLcmOpOcc: ").append(toIndentedString(vnfLcmOpOcc)).append("\n");
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
