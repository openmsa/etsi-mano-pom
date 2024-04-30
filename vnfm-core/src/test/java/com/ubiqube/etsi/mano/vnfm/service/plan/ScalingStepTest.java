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
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.vnfm.service.VnfBlueprintService;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

@ExtendWith(MockitoExtension.class)
class ScalingStepTest {
	@Mock
	private VnfBlueprintService planService;

	@Test
	void testByLevelId() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}

	@Test
	void testByLevelId2() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aa");
		bundle.getScaleStatus().add(si);
		final ScaleInfo si2 = new ScaleInfo();
		si2.setAspectId("bb");
		bundle.getScaleStatus().add(si2);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}

	@Test
	void testByLevelId3() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		blueprint.getParameters().setNumberOfSteps(2);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aspect_2");
		bundle.getScaleStatus().add(si);
		final ScaleInfo si2 = new ScaleInfo();
		si2.setAspectId("bb");
		bundle.getScaleStatus().add(si2);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfComputeAspectDelta ad = new VnfComputeAspectDelta("aspect_2", "dn", 34, 1, 5, "target", 4);
		compute.addScalingAspectDeltas(ad);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}

	@Test
	void testByLevelId4() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		blueprint.getParameters().setNumberOfSteps(2);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aspect_2");
		si.setScaleLevel(0);
		bundle.getScaleStatus().add(si);
		final ScaleInfo si2 = new ScaleInfo();
		si2.setAspectId("bb");
		bundle.getScaleStatus().add(si2);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfComputeAspectDelta ad = new VnfComputeAspectDelta("aspect_2", "dn", 34, 1, 5, "target", 4);
		compute.addScalingAspectDeltas(ad);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		instance.getInstantiatedVnfInfo().getScaleStatus().add(si);
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}

	@Test
	void testByLevelId_scaleIn() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		blueprint.getParameters().setNumberOfSteps(7);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aspect");
		bundle.getScaleStatus().add(si);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfComputeAspectDelta ad = new VnfComputeAspectDelta("aspect", "dn", 34, 1, 5, "target", 4);
		compute.getScalingAspectDeltas().add(ad);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(4, res.getWanted());
	}

	@Test
	void testByLevelId_scaleOut() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		blueprint.getParameters().setNumberOfSteps(7);
		blueprint.getParameters().setScaleType(ScaleTypeEnum.IN);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aspect");
		bundle.getScaleStatus().add(si);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfComputeAspectDelta ad = new VnfComputeAspectDelta("aspect", "dn", 34, 1, 5, "target", 4);
		compute.getScalingAspectDeltas().add(ad);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}

	@Test
	void testByLevelId_scaleOut_0() {
		final ScalingStrategyV2 srv = new ScalingStrategyV2(new ScaleByStep(planService));
		final VnfBlueprint blueprint = ScalingFactory.createBaseBlueprint();
		blueprint.getParameters().setAspectId("aspect");
		blueprint.getParameters().setNumberOfSteps(0);
		blueprint.getParameters().setScaleType(ScaleTypeEnum.IN);
		final VnfPackage bundle = ScalingFactory.createBaseVnfPackage();
		final ScaleInfo si = new ScaleInfo();
		si.setAspectId("aspect");
		bundle.getScaleStatus().add(si);
		final Set<ScaleInfo> scaling = Set.of();
		final VnfCompute compute = ScalingFactory.createBaseVnfCompute();
		final VnfComputeAspectDelta ad = new VnfComputeAspectDelta("aspect", "dn", 34, 1, 5, "target", 0);
		compute.getScalingAspectDeltas().add(ad);
		final VnfInstance instance = ScalingFactory.createBaseInstance();
		final NumberOfCompute res = srv.getNumberOfCompute(blueprint, bundle, scaling, compute, instance);
		assertEquals(0, res.getCurrent());
		assertEquals(1, res.getWanted());
	}
}
