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
package com.ubiqube.etsi.mano.dao.mano;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@ToString
public class VnfPackageChangeNotification {
	private String id;

	private String notificationType;

	private String subscriptionId;

	private OffsetDateTime timeStamp;

	private String vnfPkgId;

	private String vnfdId;

	private PackageChangeType changeType;

	private PackageOperationalStateType operationalState;

}
