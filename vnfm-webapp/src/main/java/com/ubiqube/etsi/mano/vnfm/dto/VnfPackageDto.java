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
package com.ubiqube.etsi.mano.vnfm.dto;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class VnfPackageDto {
	private UUID id;

	private String vnfdId;

	private String vnfdExtInvariantId;

	private String vnfProvider;

	private String vnfProductName;

	private String vnfSoftwareVersion;

	private String vnfdVersion;

	private String flavorId;

	private String flavourDescription;

	private String descriptorVersion;

	private String productInfoDescription;

	private OnboardingStateType onboardingState;

	private PackageOperationalState operationalState;

	private UsageStateEnum usageState;

}
