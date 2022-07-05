package com.ubiqube.etsi.mano.vnfm.v361.model.vnffm;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnfind.NotificationLink;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links to resources related to this notification.
 */
@Schema(description = "Links to resources related to this notification. ")
@Validated

public class AlarmListRebuiltNotificationLinks {
	@JsonProperty("subscription")
	private NotificationLink subscription = null;

	@JsonProperty("alarms")
	private NotificationLink alarms = null;

	public AlarmListRebuiltNotificationLinks subscription(final NotificationLink subscription) {
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

	public AlarmListRebuiltNotificationLinks alarms(final NotificationLink alarms) {
		this.alarms = alarms;
		return this;
	}

	/**
	 * Get alarms
	 *
	 * @return alarms
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NotificationLink getAlarms() {
		return alarms;
	}

	public void setAlarms(final NotificationLink alarms) {
		this.alarms = alarms;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AlarmListRebuiltNotificationLinks alarmListRebuiltNotificationLinks = (AlarmListRebuiltNotificationLinks) o;
		return Objects.equals(this.subscription, alarmListRebuiltNotificationLinks.subscription) &&
				Objects.equals(this.alarms, alarmListRebuiltNotificationLinks.alarms);
	}

	@Override
	public int hashCode() {
		return Objects.hash(subscription, alarms);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AlarmListRebuiltNotificationLinks {\n");

		sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
		sb.append("    alarms: ").append(toIndentedString(alarms)).append("\n");
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
