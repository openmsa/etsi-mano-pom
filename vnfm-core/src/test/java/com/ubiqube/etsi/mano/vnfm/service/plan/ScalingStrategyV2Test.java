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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

import jakarta.annotation.Nonnull;

@ExtendWith(MockitoExtension.class)
class ScalingStrategyV2Test {
	@Mock
	@Nonnull
	private VnfBlueprintService planService;

	@Test
	void testUnknownScalingMode() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		assertThrows(GenericException.class, () -> srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance));
	}

	@Test
	void testByLEvelId() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setInstantiationLevelId("level");
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(0, res.getWanted());
	}

	@Test
	void testByLevelIdOneCompute() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setInstantiationLevelId("level");
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VduInstantiationLevel instLevel = new VduInstantiationLevel();
		instLevel.setLevelName("level");
		instLevel.setNumberOfInstances(55);
		compute.addVduInstantiationLevel(instLevel);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(55, res.getWanted());
	}

}
