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
package com.ubiqube.etsi.mano.vnfm.v261.model.vnfind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents a subscription filter related to notifications about VNF
 * indicator value changes. At a particular nesting level in the filter
 * structure, the following applies: All attributes shall match in order for the
 * filter to match (logical \&quot;and\&quot; between different filter
 * attributes). If an attribute is an array, the attribute shall match if at
 * least one of the values in the array matches (logical \&quot;or\&quot;
 * between the values of one filter attribute).
 */
@Schema(description = "This type represents a subscription filter related to notifications about VNF indicator value changes. At a particular nesting level in the filter structure, the following applies: All attributes shall match in order for the filter to match (logical \"and\" between different filter attributes). If an attribute is an array, the attribute shall match if at least one of the values in the array matches (logical \"or\" between the values of one filter attribute). ")
@Validated

public class VnfIndicatorNotificationsFilter {
	@JsonProperty("vnfInstanceSubscriptionFilter")
	private VnfInstanceSubscriptionFilter vnfInstanceSubscriptionFilter = null;

	@JsonProperty("indicatorIds")
	@Valid
	private List<String> indicatorIds = null;

	public VnfIndicatorNotificationsFilter vnfInstanceSubscriptionFilter(final VnfInstanceSubscriptionFilter vnfInstanceSubscriptionFilter) {
		this.vnfInstanceSubscriptionFilter = vnfInstanceSubscriptionFilter;
		return this;
	}

	public enum NotificationTypesEnum {
		VNFINDICATORVALUECHANGENOTIFICATION("VnfIndicatorValueChangeNotification"),

		SUPPORTEDINDICATORSCHANGENOTIFICATION("SupportedIndicatorsChangeNotification");

		private final String value;

		NotificationTypesEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static NotificationTypesEnum fromValue(final String text) {
			for (final NotificationTypesEnum b : NotificationTypesEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("notificationTypes")
	private List<NotificationTypesEnum> notificationTypes = null;

	/**
	 * Filter criteria to select VNF instances about which to notify.
	 *
	 * @return vnfInstanceSubscriptionFilter
	 **/
	@Schema(description = "Filter criteria to select VNF instances about which to notify. ")

	@Valid

	public VnfInstanceSubscriptionFilter getVnfInstanceSubscriptionFilter() {
		return vnfInstanceSubscriptionFilter;
	}

	public void setVnfInstanceSubscriptionFilter(final VnfInstanceSubscriptionFilter vnfInstanceSubscriptionFilter) {
		this.vnfInstanceSubscriptionFilter = vnfInstanceSubscriptionFilter;
	}

	public VnfIndicatorNotificationsFilter notificationTypes(final List<NotificationTypesEnum> notificationTypes) {
		this.notificationTypes = notificationTypes;
		return this;
	}

	/**
	 * Match particular notification types. Permitted values: *
	 * VnfIndicatorValueChangeNotification * SupportedIndicatorsChangeNotification
	 * The permitted values of the \"notificationTypes\" attribute are spelled
	 * exactly as the names of the notification types to facilitate automated code
	 * generation systems.
	 *
	 * @return notificationTypes
	 **/
	@Schema(description = "Match particular notification types. Permitted values: * VnfIndicatorValueChangeNotification * SupportedIndicatorsChangeNotification The permitted values of the \"notificationTypes\" attribute are spelled exactly as the names of the notification types to facilitate automated code generation systems. ")

	public List<NotificationTypesEnum> getNotificationTypes() {
		return notificationTypes;
	}

	public void setNotificationTypes(final List<NotificationTypesEnum> notificationTypes) {
		this.notificationTypes = notificationTypes;
	}

	public VnfIndicatorNotificationsFilter indicatorIds(final List<String> indicatorIds) {
		this.indicatorIds = indicatorIds;
		return this;
	}

	public VnfIndicatorNotificationsFilter addIndicatorIdsItem(final String indicatorIdsItem) {
		if (this.indicatorIds == null) {
			this.indicatorIds = new ArrayList<>();
		}
		this.indicatorIds.add(indicatorIdsItem);
		return this;
	}

	/**
	 * Match particular VNF indicator identifiers.
	 *
	 * @return indicatorIds
	 **/
	@Schema(description = "Match particular VNF indicator identifiers. ")

	public List<String> getIndicatorIds() {
		return indicatorIds;
	}

	public void setIndicatorIds(final List<String> indicatorIds) {
		this.indicatorIds = indicatorIds;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfIndicatorNotificationsFilter vnfIndicatorNotificationsFilter = (VnfIndicatorNotificationsFilter) o;
		return Objects.equals(this.vnfInstanceSubscriptionFilter, vnfIndicatorNotificationsFilter.vnfInstanceSubscriptionFilter) &&
				Objects.equals(this.indicatorIds, vnfIndicatorNotificationsFilter.indicatorIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnfInstanceSubscriptionFilter, indicatorIds);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfIndicatorNotificationsFilter {\n");

		sb.append("    vnfInstanceSubscriptionFilter: ").append(toIndentedString(vnfInstanceSubscriptionFilter)).append("\n");
		sb.append("    indicatorIds: ").append(toIndentedString(indicatorIds)).append("\n");
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
