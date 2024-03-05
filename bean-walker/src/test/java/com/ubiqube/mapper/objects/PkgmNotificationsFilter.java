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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.mapper.objects;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PkgmNotificationsFilter {
	public enum NotificationTypesEnum {
		VnfPackageOnboardingNotification("VnfPackageOnboardingNotification"),

		VnfPackageChangeNotification("VnfPackageChangeNotification");

		private final String value;

		NotificationTypesEnum(final String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public static NotificationTypesEnum fromValue(final String text) {
			for (final NotificationTypesEnum b : NotificationTypesEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	private List<NotificationTypesEnum> notificationTypes;

	private List<PkgmNotificationsFilterVnfProductsFromProviders> vnfProductsFromProviders;

	private List<String> vnfdId;

	private List<String> vnfPkgId;

	private List<PackageOperationalStateType> operationalState;

	private List<PackageUsageStateType> usageState;

}
