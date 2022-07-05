package com.ubiqube.etsi.mano.vnfm.v261.model.vnfind;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnflcm.NotificationLink;

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
