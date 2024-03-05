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
package com.ubiqube.etsi.mano.service.event;

import java.util.LinkedHashSet;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class TestFactory {

	private TestFactory() {
		//
	}

	public static VnfPackage createVnfPkg(final UUID id) {
		final VnfPackage vnfPkgInfo = new VnfPackage();
		vnfPkgInfo.setId(id);
		vnfPkgInfo.setOnboardingState(OnboardingStateType.ONBOARDED);
		vnfPkgInfo.setOperationalState(PackageOperationalState.ENABLED);
		vnfPkgInfo.setOsContainer(new LinkedHashSet<>());
		vnfPkgInfo.setOsContainerDeployableUnits(new LinkedHashSet<>());
		vnfPkgInfo.setOsContainerDesc(new LinkedHashSet<>());
		vnfPkgInfo.setMciops(new LinkedHashSet<>());
		return vnfPkgInfo;
	}
}
