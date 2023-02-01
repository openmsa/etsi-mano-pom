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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnfpm;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents a notification that is sent when a threshold has been
 * crossed. NOTE: The timing of sending this notification is determined by the
 * capability of the producing entity to evaluate the threshold crossing
 * condition. The notification shall be triggered by the VNFM when a threshold
 * has been crossed. NOTE: The sub-object allows to structure the measured
 * object, but is not to be confused with sub-counters which allow to structure
 * the measurement.
 */
@Schema(description = "This type represents a notification that is sent when a threshold has been crossed. NOTE: The timing of sending this notification is determined by the capability of the        producing entity to evaluate the threshold crossing condition.        The notification shall be triggered by the VNFM when a threshold has been crossed. NOTE: The sub-object allows to structure the measured object, but is not to be confused        with sub-counters which allow to structure the measurement. ")
@Validated

public class ThresholdCrossedNotification {
	@JsonProperty("id")
	private String id = null;

	/**
	 * Discriminator for the different notification types. Shall be set to
	 * \"ThresholdCrossedNotification\" for this notification type.
	 */
	public enum NotificationTypeEnum {
		THRESHOLDCROSSEDNOTIFICATION("ThresholdCrossedNotification");

		private final String value;

		NotificationTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static NotificationTypeEnum fromValue(final String text) {
			for (final NotificationTypeEnum b : NotificationTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("notificationType")
	private NotificationTypeEnum notificationType = null;

	@JsonProperty("timeStamp")
	private OffsetDateTime timeStamp = null;

	@JsonProperty("thresholdId")
	private String thresholdId = null;

	@JsonProperty("crossingDirection")
	private CrossingDirectionType crossingDirection = null;

	@JsonProperty("objectType")
	private String objectType = null;

	@JsonProperty("objectInstanceId")
	private String objectInstanceId = null;

	@JsonProperty("subObjectInstanceId")
	private String subObjectInstanceId = null;

	@JsonProperty("performanceMetric")
	private String performanceMetric = null;

	@JsonProperty("performanceValue")
	private Object performanceValue = null;

	@JsonProperty("context")
	private Map<String, String> context = null;

	@JsonProperty("_links")
	private ThresholdCrossedNotificationLinks _links = null;

	public ThresholdCrossedNotification id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public ThresholdCrossedNotification notificationType(final NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	/**
	 * Discriminator for the different notification types. Shall be set to
	 * \"ThresholdCrossedNotification\" for this notification type.
	 *
	 * @return notificationType
	 **/
	@Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"ThresholdCrossedNotification\" for this notification type. ")
	@NotNull

	public NotificationTypeEnum getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(final NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
	}

	public ThresholdCrossedNotification timeStamp(final OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	/**
	 * Get timeStamp
	 *
	 * @return timeStamp
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(final OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public ThresholdCrossedNotification thresholdId(final String thresholdId) {
		this.thresholdId = thresholdId;
		return this;
	}

	/**
	 * Get thresholdId
	 *
	 * @return thresholdId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(final String thresholdId) {
		this.thresholdId = thresholdId;
	}

	public ThresholdCrossedNotification crossingDirection(final CrossingDirectionType crossingDirection) {
		this.crossingDirection = crossingDirection;
		return this;
	}

	/**
	 * Get crossingDirection
	 *
	 * @return crossingDirection
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public CrossingDirectionType getCrossingDirection() {
		return crossingDirection;
	}

	public void setCrossingDirection(final CrossingDirectionType crossingDirection) {
		this.crossingDirection = crossingDirection;
	}

	public ThresholdCrossedNotification objectType(final String objectType) {
		this.objectType = objectType;
		return this;
	}

	/**
	 * Type of the measured object. The applicable measured object type for a
	 * measurement is defined in clause 7.2 of ETSI GS NFV-IFA 027.
	 *
	 * @return objectType
	 **/
	@Schema(required = true, description = "Type of the measured object. The applicable measured object type for a measurement is defined in clause 7.2 of ETSI GS NFV-IFA 027. ")
	@NotNull

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(final String objectType) {
		this.objectType = objectType;
	}

	public ThresholdCrossedNotification objectInstanceId(final String objectInstanceId) {
		this.objectInstanceId = objectInstanceId;
		return this;
	}

	/**
	 * Get objectInstanceId
	 *
	 * @return objectInstanceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getObjectInstanceId() {
		return objectInstanceId;
	}

	public void setObjectInstanceId(final String objectInstanceId) {
		this.objectInstanceId = objectInstanceId;
	}

	public ThresholdCrossedNotification subObjectInstanceId(final String subObjectInstanceId) {
		this.subObjectInstanceId = subObjectInstanceId;
		return this;
	}

	/**
	 * Get subObjectInstanceId
	 *
	 * @return subObjectInstanceId
	 **/
	@Schema(description = "")

	public String getSubObjectInstanceId() {
		return subObjectInstanceId;
	}

	public void setSubObjectInstanceId(final String subObjectInstanceId) {
		this.subObjectInstanceId = subObjectInstanceId;
	}

	public ThresholdCrossedNotification performanceMetric(final String performanceMetric) {
		this.performanceMetric = performanceMetric;
		return this;
	}

	/**
	 * Performance metric associated with the threshold. This attribute shall
	 * contain the related \"Measurement Name\" value as defined in clause 7.2 of
	 * ETSI GS NFV-IFA 027.
	 *
	 * @return performanceMetric
	 **/
	@Schema(required = true, description = "Performance metric associated with the threshold. This attribute shall contain the related \"Measurement Name\" value as defined in clause 7.2 of ETSI GS NFV-IFA 027. ")
	@NotNull

	public String getPerformanceMetric() {
		return performanceMetric;
	}

	public void setPerformanceMetric(final String performanceMetric) {
		this.performanceMetric = performanceMetric;
	}

	public ThresholdCrossedNotification performanceValue(final Object performanceValue) {
		this.performanceValue = performanceValue;
		return this;
	}

	/**
	 * Value of the metric that resulted in threshold crossing. The type of this
	 * attribute shall correspond to the related \"Measurement Unit\" as defined in
	 * clause 7.2 of ETSI GS NFV-IFA 027.
	 *
	 * @return performanceValue
	 **/
	@Schema(required = true, description = "Value of the metric that resulted in threshold crossing. The type of this attribute shall correspond to the related \"Measurement Unit\" as defined in clause 7.2 of ETSI GS NFV-IFA 027. ")
	@NotNull

	public Object getPerformanceValue() {
		return performanceValue;
	}

	public void setPerformanceValue(final Object performanceValue) {
		this.performanceValue = performanceValue;
	}

	public ThresholdCrossedNotification context(final Map<String, String> context) {
		this.context = context;
		return this;
	}

	/**
	 * Get context
	 *
	 * @return context
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(final Map<String, String> context) {
		this.context = context;
	}

	public ThresholdCrossedNotification _links(final ThresholdCrossedNotificationLinks _links) {
		this._links = _links;
		return this;
	}

	/**
	 * Get _links
	 *
	 * @return _links
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ThresholdCrossedNotificationLinks getLinks() {
		return _links;
	}

	public void setLinks(final ThresholdCrossedNotificationLinks _links) {
		this._links = _links;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ThresholdCrossedNotification thresholdCrossedNotification = (ThresholdCrossedNotification) o;
		return Objects.equals(this.id, thresholdCrossedNotification.id) &&
				Objects.equals(this.notificationType, thresholdCrossedNotification.notificationType) &&
				Objects.equals(this.timeStamp, thresholdCrossedNotification.timeStamp) &&
				Objects.equals(this.thresholdId, thresholdCrossedNotification.thresholdId) &&
				Objects.equals(this.crossingDirection, thresholdCrossedNotification.crossingDirection) &&
				Objects.equals(this.objectType, thresholdCrossedNotification.objectType) &&
				Objects.equals(this.objectInstanceId, thresholdCrossedNotification.objectInstanceId) &&
				Objects.equals(this.subObjectInstanceId, thresholdCrossedNotification.subObjectInstanceId) &&
				Objects.equals(this.performanceMetric, thresholdCrossedNotification.performanceMetric) &&
				Objects.equals(this.performanceValue, thresholdCrossedNotification.performanceValue) &&
				Objects.equals(this.context, thresholdCrossedNotification.context) &&
				Objects.equals(this._links, thresholdCrossedNotification._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, notificationType, timeStamp, thresholdId, crossingDirection, objectType, objectInstanceId, subObjectInstanceId, performanceMetric, performanceValue, context, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ThresholdCrossedNotification {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
		sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
		sb.append("    thresholdId: ").append(toIndentedString(thresholdId)).append("\n");
		sb.append("    crossingDirection: ").append(toIndentedString(crossingDirection)).append("\n");
		sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
		sb.append("    objectInstanceId: ").append(toIndentedString(objectInstanceId)).append("\n");
		sb.append("    subObjectInstanceId: ").append(toIndentedString(subObjectInstanceId)).append("\n");
		sb.append("    performanceMetric: ").append(toIndentedString(performanceMetric)).append("\n");
		sb.append("    performanceValue: ").append(toIndentedString(performanceValue)).append("\n");
		sb.append("    context: ").append(toIndentedString(context)).append("\n");
		sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
