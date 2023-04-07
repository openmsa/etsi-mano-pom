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
package com.ubiqube.etsi.mano.vnfm.service.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VduInstantiationLevel;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

@ExtendWith(MockitoExtension.class)
class ScalingStrategyV2Test {
	@Mock
	private VnfBlueprintService planService;

	@Test
	void testUnknownScalingMode() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(planService);
		final VnfBlueprint blueprint = createBaseBlueprint();
		final VnfPackage bundle = createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = createBaseVnfCompute();
		final VnfInstance instance = createBaseInstance();
		assertThrows(GenericException.class, () -> srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance));
	}

	@Test
	void testByLEvelId() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(planService);
		final VnfBlueprint blueprint = createBaseBlueprint();
		blueprint.getParameters().setInstantiationLevelId("level");
		final VnfPackage bundle = createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = createBaseVnfCompute();
		final VnfInstance instance = createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(res.getCurrent(), 0);
		assertEquals(res.getWanted(), 0);
	}

	@Test
	void testByLevelIdOneCompute() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(planService);
		final VnfBlueprint blueprint = createBaseBlueprint();
		blueprint.getParameters().setInstantiationLevelId("level");
		final VnfPackage bundle = createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = createBaseVnfCompute();
		final VduInstantiationLevel instLevel = new VduInstantiationLevel();
		instLevel.setLevelName("level");
		instLevel.setNumberOfInstances(55);
		compute.addVduInstantiationLevel(instLevel);
		final VnfInstance instance = createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(res.getCurrent(), 0);
		assertEquals(res.getWanted(), 55);
	}

	private VnfCompute createBaseVnfCompute() {
		final VnfCompute vnfc = new VnfCompute();
		vnfc.setId(UUID.randomUUID());
		vnfc.setInstantiationLevel(new LinkedHashSet<>());
		return vnfc;
	}

	private VnfInstance createBaseInstance() {
		final VnfInstance inst = new VnfInstance();
		inst.setId(UUID.randomUUID());
		return inst;
	}

	private VnfPackage createBaseVnfPackage() {
		final VnfPackage pkg = new VnfPackage();
		pkg.setId(UUID.randomUUID());
		return pkg;
	}

	private VnfBlueprint createBaseBlueprint() {
		final VnfBlueprint blu = new VnfBlueprint();
		blu.setId(UUID.randomUUID());
		final BlueprintParameters params = new BlueprintParameters();
		blu.setParameters(params);
		return blu;
	}

}
