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
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.em.v361.model.vnfconfig.ProblemDetails;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LccnLinks;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * NsChangeNotification
 */
@Validated

public class NsChangeNotification {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("nsInstanceId")
	private String nsInstanceId = null;

	@JsonProperty("nsComponentType")
	private NsComponentType nsComponentType = null;

	@JsonProperty("nsComponentId")
	private String nsComponentId = null;

	@JsonProperty("lcmOpOccIdImpactingNsComponent")
	private String lcmOpOccIdImpactingNsComponent = null;

	@JsonProperty("lcmOpNameImpactingNsComponent")
	private LcmOpNameForChangeNotificationType lcmOpNameImpactingNsComponent = null;

	@JsonProperty("lcmOpOccStatusImpactingNsComponent")
	private LcmOpOccStatusForChangeNotificationType lcmOpOccStatusImpactingNsComponent = null;

	@JsonProperty("notificationType")
	private String notificationType = null;

	@JsonProperty("subscriptionId")
	private String subscriptionId = null;

	@JsonProperty("timestamp")
	private OffsetDateTime timestamp = null;

	@JsonProperty("error")
	private ProblemDetails error = null;

	@JsonProperty("_links")
	private LccnLinks _links = null;

	public NsChangeNotification id(final String id) {
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

	public NsChangeNotification nsInstanceId(final String nsInstanceId) {
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

	public NsChangeNotification nsComponentType(final NsComponentType nsComponentType) {
		this.nsComponentType = nsComponentType;
		return this;
	}

	/**
	 * Get nsComponentType
	 *
	 * @return nsComponentType
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public NsComponentType getNsComponentType() {
		return nsComponentType;
	}

	public void setNsComponentType(final NsComponentType nsComponentType) {
		this.nsComponentType = nsComponentType;
	}

	public NsChangeNotification nsComponentId(final String nsComponentId) {
		this.nsComponentId = nsComponentId;
		return this;
	}

	/**
	 * Get nsComponentId
	 *
	 * @return nsComponentId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getNsComponentId() {
		return nsComponentId;
	}

	public void setNsComponentId(final String nsComponentId) {
		this.nsComponentId = nsComponentId;
	}

	public NsChangeNotification lcmOpOccIdImpactingNsComponent(final String lcmOpOccIdImpactingNsComponent) {
		this.lcmOpOccIdImpactingNsComponent = lcmOpOccIdImpactingNsComponent;
		return this;
	}

	/**
	 * Get lcmOpOccIdImpactingNsComponent
	 *
	 * @return lcmOpOccIdImpactingNsComponent
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getLcmOpOccIdImpactingNsComponent() {
		return lcmOpOccIdImpactingNsComponent;
	}

	public void setLcmOpOccIdImpactingNsComponent(final String lcmOpOccIdImpactingNsComponent) {
		this.lcmOpOccIdImpactingNsComponent = lcmOpOccIdImpactingNsComponent;
	}

	public NsChangeNotification lcmOpNameImpactingNsComponent(final LcmOpNameForChangeNotificationType lcmOpNameImpactingNsComponent) {
		this.lcmOpNameImpactingNsComponent = lcmOpNameImpactingNsComponent;
		return this;
	}

	/**
	 * Get lcmOpNameImpactingNsComponent
	 *
	 * @return lcmOpNameImpactingNsComponent
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public LcmOpNameForChangeNotificationType getLcmOpNameImpactingNsComponent() {
		return lcmOpNameImpactingNsComponent;
	}

	public void setLcmOpNameImpactingNsComponent(final LcmOpNameForChangeNotificationType lcmOpNameImpactingNsComponent) {
		this.lcmOpNameImpactingNsComponent = lcmOpNameImpactingNsComponent;
	}

	public NsChangeNotification lcmOpOccStatusImpactingNsComponent(final LcmOpOccStatusForChangeNotificationType lcmOpOccStatusImpactingNsComponent) {
		this.lcmOpOccStatusImpactingNsComponent = lcmOpOccStatusImpactingNsComponent;
		return this;
	}

	/**
	 * Get lcmOpOccStatusImpactingNsComponent
	 *
	 * @return lcmOpOccStatusImpactingNsComponent
	 **/
	@Schema(description = "")

	@Valid
	public LcmOpOccStatusForChangeNotificationType getLcmOpOccStatusImpactingNsComponent() {
		return lcmOpOccStatusImpactingNsComponent;
	}

	public void setLcmOpOccStatusImpactingNsComponent(final LcmOpOccStatusForChangeNotificationType lcmOpOccStatusImpactingNsComponent) {
		this.lcmOpOccStatusImpactingNsComponent = lcmOpOccStatusImpactingNsComponent;
	}

	public NsChangeNotification notificationType(final String notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	/**
	 * Discriminator for the different notification types. Shall be set to
	 * \"NsChangeNotification\" for this notification type.
	 *
	 * @return notificationType
	 **/
	@Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"NsChangeNotification\" for this notification type. ")
	@NotNull

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(final String notificationType) {
		this.notificationType = notificationType;
	}

	public NsChangeNotification subscriptionId(final String subscriptionId) {
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

	public NsChangeNotification timestamp(final OffsetDateTime timestamp) {
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

	public NsChangeNotification error(final ProblemDetails error) {
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

	public NsChangeNotification _links(final LccnLinks _links) {
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
		final NsChangeNotification nsChangeNotification = (NsChangeNotification) o;
		return Objects.equals(this.id, nsChangeNotification.id) &&
				Objects.equals(this.nsInstanceId, nsChangeNotification.nsInstanceId) &&
				Objects.equals(this.nsComponentType, nsChangeNotification.nsComponentType) &&
				Objects.equals(this.nsComponentId, nsChangeNotification.nsComponentId) &&
				Objects.equals(this.lcmOpOccIdImpactingNsComponent, nsChangeNotification.lcmOpOccIdImpactingNsComponent) &&
				Objects.equals(this.lcmOpNameImpactingNsComponent, nsChangeNotification.lcmOpNameImpactingNsComponent) &&
				Objects.equals(this.lcmOpOccStatusImpactingNsComponent, nsChangeNotification.lcmOpOccStatusImpactingNsComponent) &&
				Objects.equals(this.notificationType, nsChangeNotification.notificationType) &&
				Objects.equals(this.subscriptionId, nsChangeNotification.subscriptionId) &&
				Objects.equals(this.timestamp, nsChangeNotification.timestamp) &&
				Objects.equals(this.error, nsChangeNotification.error) &&
				Objects.equals(this._links, nsChangeNotification._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nsInstanceId, nsComponentType, nsComponentId, lcmOpOccIdImpactingNsComponent, lcmOpNameImpactingNsComponent, lcmOpOccStatusImpactingNsComponent, notificationType, subscriptionId, timestamp, error, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsChangeNotification {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    nsInstanceId: ").append(toIndentedString(nsInstanceId)).append("\n");
		sb.append("    nsComponentType: ").append(toIndentedString(nsComponentType)).append("\n");
		sb.append("    nsComponentId: ").append(toIndentedString(nsComponentId)).append("\n");
		sb.append("    lcmOpOccIdImpactingNsComponent: ").append(toIndentedString(lcmOpOccIdImpactingNsComponent)).append("\n");
		sb.append("    lcmOpNameImpactingNsComponent: ").append(toIndentedString(lcmOpNameImpactingNsComponent)).append("\n");
		sb.append("    lcmOpOccStatusImpactingNsComponent: ").append(toIndentedString(lcmOpOccStatusImpactingNsComponent)).append("\n");
		sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
		sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
		sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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
