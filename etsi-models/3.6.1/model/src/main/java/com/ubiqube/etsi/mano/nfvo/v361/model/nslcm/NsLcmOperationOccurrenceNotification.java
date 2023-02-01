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
package com.ubiqube.etsi.mano.nfvo.v361.model.nslcm;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.em.v361.model.vnfconfig.ProblemDetails;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.AffectedVirtualLink;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LccnLinks;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LcmOpOccNotificationVerbosityType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * NsLcmOperationOccurrenceNotification
 */
@Validated

public class NsLcmOperationOccurrenceNotification {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("nsInstanceId")
	private String nsInstanceId = null;

	@JsonProperty("nsLcmOpOccId")
	private String nsLcmOpOccId = null;

	@JsonProperty("operation")
	private NsLcmOpType operation = null;

	@JsonProperty("notificationType")
	private String notificationType = null;

	@JsonProperty("subscriptionId")
	private String subscriptionId = null;

	@JsonProperty("timestamp")
	private OffsetDateTime timestamp = null;

	/**
	 * Indicates whether this notification reports about the start of a NS lifecycle
	 * operation or the result of a NS lifecycle operation. Permitted values: -
	 * START: Informs about the start of the NS LCM operation occurrence. - RESULT:
	 * Informs about the final or intermediate result of the NS LCM operation
	 * occurrence.
	 */
	public enum NotificationStatusEnum {
		START("START"),

		RESULT("RESULT");

		private final String value;

		NotificationStatusEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static NotificationStatusEnum fromValue(final String text) {
			for (final NotificationStatusEnum b : NotificationStatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("notificationStatus")
	private NotificationStatusEnum notificationStatus = null;

	@JsonProperty("operationState")
	private NsLcmOperationStateType operationState = null;

	@JsonProperty("isAutomaticInvocation")
	private Boolean isAutomaticInvocation = null;

	@JsonProperty("verbosity")
	private LcmOpOccNotificationVerbosityType verbosity = null;

	@JsonProperty("affectedVnf")
	@Valid
	private List<AffectedVnf> affectedVnf = null;

	@JsonProperty("affectedPnf")
	@Valid
	private List<AffectedPnf> affectedPnf = null;

	@JsonProperty("affectedVl")
	@Valid
	private List<AffectedVirtualLink> affectedVl = null;

	@JsonProperty("affectedVnffg")
	@Valid
	private List<AffectedVnffg> affectedVnffg = null;

	@JsonProperty("affectedNs")
	@Valid
	private List<AffectedNs> affectedNs = null;

	@JsonProperty("affectedSap")
	@Valid
	private List<AffectedSap> affectedSap = null;

	@JsonProperty("error")
	private ProblemDetails error = null;

	@JsonProperty("_links")
	private LccnLinks _links = null;

	public NsLcmOperationOccurrenceNotification id(final String id) {
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

	public NsLcmOperationOccurrenceNotification nsInstanceId(final String nsInstanceId) {
		this.nsInstanceId = nsInstanceId;
		return this;
	}

	/**
	 * Get nsInstanceId
	 *
	 * @return nsInstanceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getNsInstanceId() {
		return nsInstanceId;
	}

	public void setNsInstanceId(final String nsInstanceId) {
		this.nsInstanceId = nsInstanceId;
	}

	public NsLcmOperationOccurrenceNotification nsLcmOpOccId(final String nsLcmOpOccId) {
		this.nsLcmOpOccId = nsLcmOpOccId;
		return this;
	}

	/**
	 * Get nsLcmOpOccId
	 *
	 * @return nsLcmOpOccId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getNsLcmOpOccId() {
		return nsLcmOpOccId;
	}

	public void setNsLcmOpOccId(final String nsLcmOpOccId) {
		this.nsLcmOpOccId = nsLcmOpOccId;
	}

	public NsLcmOperationOccurrenceNotification operation(final NsLcmOpType operation) {
		this.operation = operation;
		return this;
	}

	/**
	 * Get operation
	 *
	 * @return operation
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NsLcmOpType getOperation() {
		return operation;
	}

	public void setOperation(final NsLcmOpType operation) {
		this.operation = operation;
	}

	public NsLcmOperationOccurrenceNotification notificationType(final String notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	/**
	 * Discriminator for the different notification types. Shall be set to
	 * \"NsLcmOperationOccurrenceNotification\" for this notification type.
	 *
	 * @return notificationType
	 **/
	@Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"NsLcmOperationOccurrenceNotification\" for this notification type. ")
	@NotNull

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(final String notificationType) {
		this.notificationType = notificationType;
	}

	public NsLcmOperationOccurrenceNotification subscriptionId(final String subscriptionId) {
		this.subscriptionId = subscriptionId;
		return this;
	}

	/**
	 * Get subscriptionId
	 *
	 * @return subscriptionId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(final String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public NsLcmOperationOccurrenceNotification timestamp(final OffsetDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	/**
	 * Get timestamp
	 *
	 * @return timestamp
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public NsLcmOperationOccurrenceNotification notificationStatus(final NotificationStatusEnum notificationStatus) {
		this.notificationStatus = notificationStatus;
		return this;
	}

	/**
	 * Indicates whether this notification reports about the start of a NS lifecycle
	 * operation or the result of a NS lifecycle operation. Permitted values: -
	 * START: Informs about the start of the NS LCM operation occurrence. - RESULT:
	 * Informs about the final or intermediate result of the NS LCM operation
	 * occurrence.
	 *
	 * @return notificationStatus
	 **/
	@Schema(required = true, description = "Indicates whether this notification reports about the start of a NS lifecycle operation or the result of a NS lifecycle operation. Permitted values: - START: Informs about the start of the NS LCM operation occurrence. - RESULT: Informs about the final or intermediate result of the NS LCM operation occurrence. ")
	@NotNull

	public NotificationStatusEnum getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(final NotificationStatusEnum notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public NsLcmOperationOccurrenceNotification operationState(final NsLcmOperationStateType operationState) {
		this.operationState = operationState;
		return this;
	}

	/**
	 * Get operationState
	 *
	 * @return operationState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NsLcmOperationStateType getOperationState() {
		return operationState;
	}

	public void setOperationState(final NsLcmOperationStateType operationState) {
		this.operationState = operationState;
	}

	public NsLcmOperationOccurrenceNotification isAutomaticInvocation(final Boolean isAutomaticInvocation) {
		this.isAutomaticInvocation = isAutomaticInvocation;
		return this;
	}

	/**
	 * Get isAutomaticInvocation
	 *
	 * @return isAutomaticInvocation
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public Boolean getIsAutomaticInvocation() {
		return isAutomaticInvocation;
	}

	public void setIsAutomaticInvocation(final Boolean isAutomaticInvocation) {
		this.isAutomaticInvocation = isAutomaticInvocation;
	}

	public NsLcmOperationOccurrenceNotification verbosity(final LcmOpOccNotificationVerbosityType verbosity) {
		this.verbosity = verbosity;
		return this;
	}

	/**
	 * Get verbosity
	 *
	 * @return verbosity
	 **/
	@Schema(description = "")

	@Valid
	public LcmOpOccNotificationVerbosityType getVerbosity() {
		return verbosity;
	}

	public void setVerbosity(final LcmOpOccNotificationVerbosityType verbosity) {
		this.verbosity = verbosity;
	}

	public NsLcmOperationOccurrenceNotification affectedVnf(final List<AffectedVnf> affectedVnf) {
		this.affectedVnf = affectedVnf;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedVnfItem(final AffectedVnf affectedVnfItem) {
		if (this.affectedVnf == null) {
			this.affectedVnf = new ArrayList<>();
		}
		this.affectedVnf.add(affectedVnfItem);
		return this;
	}

	/**
	 * Information about the VNF instances that were affected during the lifecycle
	 * operation. See note.
	 *
	 * @return affectedVnf
	 **/
	@Schema(description = "Information about the VNF instances that were affected during the lifecycle operation. See note. ")
	@Valid
	public List<AffectedVnf> getAffectedVnf() {
		return affectedVnf;
	}

	public void setAffectedVnf(final List<AffectedVnf> affectedVnf) {
		this.affectedVnf = affectedVnf;
	}

	public NsLcmOperationOccurrenceNotification affectedPnf(final List<AffectedPnf> affectedPnf) {
		this.affectedPnf = affectedPnf;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedPnfItem(final AffectedPnf affectedPnfItem) {
		if (this.affectedPnf == null) {
			this.affectedPnf = new ArrayList<>();
		}
		this.affectedPnf.add(affectedPnfItem);
		return this;
	}

	/**
	 * Information about the PNF instances that were affected during the lifecycle
	 * operation. See note.
	 *
	 * @return affectedPnf
	 **/
	@Schema(description = "Information about the PNF instances that were affected during the lifecycle operation. See note. ")
	@Valid
	public List<AffectedPnf> getAffectedPnf() {
		return affectedPnf;
	}

	public void setAffectedPnf(final List<AffectedPnf> affectedPnf) {
		this.affectedPnf = affectedPnf;
	}

	public NsLcmOperationOccurrenceNotification affectedVl(final List<AffectedVirtualLink> affectedVl) {
		this.affectedVl = affectedVl;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedVlItem(final AffectedVirtualLink affectedVlItem) {
		if (this.affectedVl == null) {
			this.affectedVl = new ArrayList<>();
		}
		this.affectedVl.add(affectedVlItem);
		return this;
	}

	/**
	 * Information about the VL instances that were affected during the lifecycle
	 * operation. See note.
	 *
	 * @return affectedVl
	 **/
	@Schema(description = "Information about the VL instances that were affected during the lifecycle operation. See note. ")
	@Valid
	public List<AffectedVirtualLink> getAffectedVl() {
		return affectedVl;
	}

	public void setAffectedVl(final List<AffectedVirtualLink> affectedVl) {
		this.affectedVl = affectedVl;
	}

	public NsLcmOperationOccurrenceNotification affectedVnffg(final List<AffectedVnffg> affectedVnffg) {
		this.affectedVnffg = affectedVnffg;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedVnffgItem(final AffectedVnffg affectedVnffgItem) {
		if (this.affectedVnffg == null) {
			this.affectedVnffg = new ArrayList<>();
		}
		this.affectedVnffg.add(affectedVnffgItem);
		return this;
	}

	/**
	 * Information about the VNFFG instances that were affected during the lifecycle
	 * operation. See note.
	 *
	 * @return affectedVnffg
	 **/
	@Schema(description = "Information about the VNFFG instances that were affected during the lifecycle operation. See note. ")
	@Valid
	public List<AffectedVnffg> getAffectedVnffg() {
		return affectedVnffg;
	}

	public void setAffectedVnffg(final List<AffectedVnffg> affectedVnffg) {
		this.affectedVnffg = affectedVnffg;
	}

	public NsLcmOperationOccurrenceNotification affectedNs(final List<AffectedNs> affectedNs) {
		this.affectedNs = affectedNs;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedNsItem(final AffectedNs affectedNsItem) {
		if (this.affectedNs == null) {
			this.affectedNs = new ArrayList<>();
		}
		this.affectedNs.add(affectedNsItem);
		return this;
	}

	/**
	 * Information about the SAP instances that were affected during the lifecycle
	 * operation.See note.
	 *
	 * @return affectedNs
	 **/
	@Schema(description = "Information about the SAP instances that were affected during the lifecycle operation.See note. ")
	@Valid
	public List<AffectedNs> getAffectedNs() {
		return affectedNs;
	}

	public void setAffectedNs(final List<AffectedNs> affectedNs) {
		this.affectedNs = affectedNs;
	}

	public NsLcmOperationOccurrenceNotification affectedSap(final List<AffectedSap> affectedSap) {
		this.affectedSap = affectedSap;
		return this;
	}

	public NsLcmOperationOccurrenceNotification addAffectedSapItem(final AffectedSap affectedSapItem) {
		if (this.affectedSap == null) {
			this.affectedSap = new ArrayList<>();
		}
		this.affectedSap.add(affectedSapItem);
		return this;
	}

	/**
	 * Information about the SAP instances that were affected during the lifecycle
	 * operation. See note.
	 *
	 * @return affectedSap
	 **/
	@Schema(description = "Information about the SAP instances that were affected during the lifecycle operation. See note. ")
	@Valid
	public List<AffectedSap> getAffectedSap() {
		return affectedSap;
	}

	public void setAffectedSap(final List<AffectedSap> affectedSap) {
		this.affectedSap = affectedSap;
	}

	public NsLcmOperationOccurrenceNotification error(final ProblemDetails error) {
		this.error = error;
		return this;
	}

	/**
	 * Get error
	 *
	 * @return error
	 **/
	@Schema(description = "")

	@Valid
	public ProblemDetails getError() {
		return error;
	}

	public void setError(final ProblemDetails error) {
		this.error = error;
	}

	public NsLcmOperationOccurrenceNotification _links(final LccnLinks _links) {
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
	public LccnLinks getLinks() {
		return _links;
	}

	public void setLinks(final LccnLinks _links) {
		this._links = _links;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final NsLcmOperationOccurrenceNotification nsLcmOperationOccurrenceNotification = (NsLcmOperationOccurrenceNotification) o;
		return Objects.equals(this.id, nsLcmOperationOccurrenceNotification.id) &&
				Objects.equals(this.nsInstanceId, nsLcmOperationOccurrenceNotification.nsInstanceId) &&
				Objects.equals(this.nsLcmOpOccId, nsLcmOperationOccurrenceNotification.nsLcmOpOccId) &&
				Objects.equals(this.operation, nsLcmOperationOccurrenceNotification.operation) &&
				Objects.equals(this.notificationType, nsLcmOperationOccurrenceNotification.notificationType) &&
				Objects.equals(this.subscriptionId, nsLcmOperationOccurrenceNotification.subscriptionId) &&
				Objects.equals(this.timestamp, nsLcmOperationOccurrenceNotification.timestamp) &&
				Objects.equals(this.notificationStatus, nsLcmOperationOccurrenceNotification.notificationStatus) &&
				Objects.equals(this.operationState, nsLcmOperationOccurrenceNotification.operationState) &&
				Objects.equals(this.isAutomaticInvocation, nsLcmOperationOccurrenceNotification.isAutomaticInvocation) &&
				Objects.equals(this.verbosity, nsLcmOperationOccurrenceNotification.verbosity) &&
				Objects.equals(this.affectedVnf, nsLcmOperationOccurrenceNotification.affectedVnf) &&
				Objects.equals(this.affectedPnf, nsLcmOperationOccurrenceNotification.affectedPnf) &&
				Objects.equals(this.affectedVl, nsLcmOperationOccurrenceNotification.affectedVl) &&
				Objects.equals(this.affectedVnffg, nsLcmOperationOccurrenceNotification.affectedVnffg) &&
				Objects.equals(this.affectedNs, nsLcmOperationOccurrenceNotification.affectedNs) &&
				Objects.equals(this.affectedSap, nsLcmOperationOccurrenceNotification.affectedSap) &&
				Objects.equals(this.error, nsLcmOperationOccurrenceNotification.error) &&
				Objects.equals(this._links, nsLcmOperationOccurrenceNotification._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nsInstanceId, nsLcmOpOccId, operation, notificationType, subscriptionId, timestamp, notificationStatus, operationState, isAutomaticInvocation, verbosity, affectedVnf, affectedPnf, affectedVl, affectedVnffg, affectedNs, affectedSap, error, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsLcmOperationOccurrenceNotification {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    nsInstanceId: ").append(toIndentedString(nsInstanceId)).append("\n");
		sb.append("    nsLcmOpOccId: ").append(toIndentedString(nsLcmOpOccId)).append("\n");
		sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
		sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
		sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
		sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
		sb.append("    notificationStatus: ").append(toIndentedString(notificationStatus)).append("\n");
		sb.append("    operationState: ").append(toIndentedString(operationState)).append("\n");
		sb.append("    isAutomaticInvocation: ").append(toIndentedString(isAutomaticInvocation)).append("\n");
		sb.append("    verbosity: ").append(toIndentedString(verbosity)).append("\n");
		sb.append("    affectedVnf: ").append(toIndentedString(affectedVnf)).append("\n");
		sb.append("    affectedPnf: ").append(toIndentedString(affectedPnf)).append("\n");
		sb.append("    affectedVl: ").append(toIndentedString(affectedVl)).append("\n");
		sb.append("    affectedVnffg: ").append(toIndentedString(affectedVnffg)).append("\n");
		sb.append("    affectedNs: ").append(toIndentedString(affectedNs)).append("\n");
		sb.append("    affectedSap: ").append(toIndentedString(affectedSap)).append("\n");
		sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
