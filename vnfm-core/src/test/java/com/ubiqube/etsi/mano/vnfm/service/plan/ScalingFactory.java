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
package com.ubiqube.etsi.mano.vnfm.service.plan;

import java.util.LinkedHashSet;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

@SuppressWarnings("static-method")
public class ScalingFactory {

	public ScalingFactory() {
		// Nothing.
	}

	public static VnfCompute createBaseVnfCompute() {
		final VnfCompute vnfc = new VnfCompute();
		vnfc.setId(UUID.randomUUID());
		vnfc.setInstantiationLevel(new LinkedHashSet<>());
		vnfc.setScalingAspectDeltas(new LinkedHashSet<>());
		return vnfc;
	}

	public static VnfInstance createBaseInstance() {
		final VnfInstance inst = new VnfInstance();
		inst.setId(UUID.randomUUID());
		final BlueprintParameters instVnfInfo = new BlueprintParameters();
		instVnfInfo.setScaleStatus(new LinkedHashSet<>());
		inst.setInstantiatedVnfInfo(instVnfInfo);
		return inst;
	}

	public static VnfPackage createBaseVnfPackage() {
		final VnfPackage pkg = new VnfPackage();
		pkg.setId(UUID.randomUUID());
		pkg.setScaleStatus(new LinkedHashSet<>());
		return pkg;
	}

	public static VnfBlueprint createBaseBlueprint() {
		final VnfBlueprint blu = new VnfBlueprint();
		blu.setId(UUID.randomUUID());
		final BlueprintParameters params = new BlueprintParameters();
		blu.setParameters(params);
		return blu;
	}

}
