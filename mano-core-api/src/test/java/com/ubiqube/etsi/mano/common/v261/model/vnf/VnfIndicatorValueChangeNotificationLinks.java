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
package com.ubiqube.etsi.mano.common.v261.model.vnf;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links for this resource.
 */
@Schema(description = "Links for this resource. ")
@Validated

public class VnfIndicatorValueChangeNotificationLinks {
	@JsonProperty("vnfInstance")
	private NotificationLink vnfInstance = null;

	@JsonProperty("subscription")
	private NotificationLink subscription = null;

	public VnfIndicatorValueChangeNotificationLinks vnfInstance(final NotificationLink vnfInstance) {
		this.vnfInstance = vnfInstance;
		return this;
	}

	/**
	 * Get vnfInstance
	 *
	 * @return vnfInstance
	 **/
	@Schema(description = "")

	@Valid
	public NotificationLink getVnfInstance() {
		return vnfInstance;
	}

	public void setVnfInstance(final NotificationLink vnfInstance) {
		this.vnfInstance = vnfInstance;
	}

	public VnfIndicatorValueChangeNotificationLinks subscription(final NotificationLink subscription) {
		this.subscription = subscription;
		return this;
	}

	/**
	 * Get subscription
	 *
	 * @return subscription
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NotificationLink getSubscription() {
		return subscription;
	}

	public void setSubscription(final NotificationLink subscription) {
		this.subscription = subscription;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfIndicatorValueChangeNotificationLinks vnfIndicatorValueChangeNotificationLinks = (VnfIndicatorValueChangeNotificationLinks) o;
		return Objects.equals(this.vnfInstance, vnfIndicatorValueChangeNotificationLinks.vnfInstance) &&
				Objects.equals(this.subscription, vnfIndicatorValueChangeNotificationLinks.subscription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfInstance, subscription);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfIndicatorValueChangeNotificationLinks {\n");

		sb.append("    vnfInstance: ").append(toIndentedString(vnfInstance)).append("\n");
		sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
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
