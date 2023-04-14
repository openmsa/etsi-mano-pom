/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.test.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.OperateChanges;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;

public class TestFactory {
	private TestFactory() {
		//
	}

	public static VnfBlueprint createBlueprint() {
		final VnfBlueprint bp = new VnfBlueprint();
		bp.setCancelMode(CancelModeTypeEnum.FORCEFUL);
		bp.setVimConnections(new LinkedHashSet<>());
		bp.setOperateChanges(new OperateChanges());
		return bp;
	}

	public static VnfInstance createVnfInstance() {
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setId(UUID.randomUUID());
		vnfInstance.setVnfPkg(createVnfPkg(UUID.randomUUID()));
		vnfInstance.setInstantiatedVnfInfo(new BlueprintParameters());
		vnfInstance.setVimConnectionInfo(new LinkedHashSet<>());
		return vnfInstance;
	}

	public static VnfPackage createVnfPkg(final UUID id) {
		final VnfPackage vnfPkgInfo = new VnfPackage();
		vnfPkgInfo.setId(id);
		vnfPkgInfo.setOnboardingState(OnboardingStateType.ONBOARDED);
		vnfPkgInfo.setOperationalState(PackageOperationalState.ENABLED);
		return vnfPkgInfo;
	}

	public static VnfLinkPort createVnfLinkPort() {
		final VnfLinkPort vnfLink = new VnfLinkPort();
		vnfLink.setToscaName("port01");
		return vnfLink;
	}

	public static VnfPortTask createVnfPortTask() {
		final VnfPortTask port = new VnfPortTask();
		port.setToscaName("port01");
		port.setVnfLinkPort(createVnfLinkPort());
		return port;
	}

	public static VnfLiveInstance createLiveCompute() {
		final VnfLiveInstance liveCompute = new VnfLiveInstance();
		liveCompute.setId(UUID.randomUUID());
		final ComputeTask task = new ComputeTask();
		final VnfCompute vnfCompute = new VnfCompute();
		final VnfLinkPort port = createVnfLinkPort();
		vnfCompute.setPorts(Set.of(port));
		task.setVnfCompute(vnfCompute);
		liveCompute.setTask(task);
		return liveCompute;
	}
}
