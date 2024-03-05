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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VduInstantiationLevel;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

@ExtendWith(MockitoExtension.class)
class ScalingStrategyV2InstantiateTest {
	@Mock
	private VnfBlueprintService planService;

	@Test
	void testInstantiate() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute nb = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, nb.getCurrent());
		assertEquals(1, nb.getWanted());
	}

	@Test
	void testInstantiateWithLevelId_defaultPlan() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.getParameters().setInstantiationLevelId("level");
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VduInstantiationLevel instLevel = new VduInstantiationLevel("level", 12);
		compute.addVduInstantiationLevel(instLevel);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute nb = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, nb.getCurrent());
		assertEquals(12, nb.getWanted());
	}

}
