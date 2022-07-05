package com.ubiqube.etsi.mano.vnfm.v361.model.vnfpm;

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

public class PerformanceInformationAvailableNotificationLinks {
	@JsonProperty("objectInstance")
	private NotificationLink objectInstance = null;

	@JsonProperty("pmJob")
	private NotificationLink pmJob = null;

	@JsonProperty("performanceReport")
	private NotificationLink performanceReport = null;

	public PerformanceInformationAvailableNotificationLinks objectInstance(final NotificationLink objectInstance) {
		this.objectInstance = objectInstance;
		return this;
	}

	/**
	 * Get objectInstance
	 *
	 * @return objectInstance
	 **/
	@Schema(description = "")

	@Valid
	public NotificationLink getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(final NotificationLink objectInstance) {
		this.objectInstance = objectInstance;
	}

	public PerformanceInformationAvailableNotificationLinks pmJob(final NotificationLink pmJob) {
		this.pmJob = pmJob;
		return this;
	}

	/**
	 * Get pmJob
	 *
	 * @return pmJob
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NotificationLink getPmJob() {
		return pmJob;
	}

	public void setPmJob(final NotificationLink pmJob) {
		this.pmJob = pmJob;
	}

	public PerformanceInformationAvailableNotificationLinks performanceReport(final NotificationLink performanceReport) {
		this.performanceReport = performanceReport;
		return this;
	}

	/**
	 * Get performanceReport
	 *
	 * @return performanceReport
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NotificationLink getPerformanceReport() {
		return performanceReport;
	}

	public void setPerformanceReport(final NotificationLink performanceReport) {
		this.performanceReport = performanceReport;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final PerformanceInformationAvailableNotificationLinks performanceInformationAvailableNotificationLinks = (PerformanceInformationAvailableNotificationLinks) o;
		return Objects.equals(this.objectInstance, performanceInformationAvailableNotificationLinks.objectInstance) &&
				Objects.equals(this.pmJob, performanceInformationAvailableNotificationLinks.pmJob) &&
				Objects.equals(this.performanceReport, performanceInformationAvailableNotificationLinks.performanceReport);
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectInstance, pmJob, performanceReport);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class PerformanceInformationAvailableNotificationLinks {\n");

		sb.append("    objectInstance: ").append(toIndentedString(objectInstance)).append("\n");
		sb.append("    pmJob: ").append(toIndentedString(pmJob)).append("\n");
		sb.append("    performanceReport: ").append(toIndentedString(performanceReport)).append("\n");
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
