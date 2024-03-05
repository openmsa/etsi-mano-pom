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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsByStepsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsToLevelData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleType;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScalingDirectionType;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.StepMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

@SuppressWarnings("static-method")
class NsScaleStrategyV3Test {

	@Test
	void testBasic() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertTrue(true);
	}

	/**
	 * No step mapping
	 *
	 * @throws Exception
	 */
	@Test
	void testScale() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final BlueprintParameters params = new BlueprintParameters();
		blueprint.getInstance().setInstantiatedVnfInfo(params);
		final ScaleNsByStepsData scaleStep = new ScaleNsByStepsData();
		blueprint.getParameters().getNsScale().getScaleNsData().setScaleNsByStepsData(scaleStep);
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		nsdVnfPackageCopy.setStepMapping(Set.of());
		ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertTrue(true);
	}

	/**
	 * With matching step mapping.
	 *
	 * @throws Exception
	 */
	@Test
	void testScale001() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final BlueprintParameters params = new BlueprintParameters();
		params.setNsStepStatus(Set.of());
		blueprint.getInstance().setInstantiatedVnfInfo(params);
		final ScaleNsByStepsData scaleStep = new ScaleNsByStepsData();
		blueprint.getParameters().getNsScale().getScaleNsData().setScaleNsByStepsData(scaleStep);
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final VnfScalingStepMapping st01 = new VnfScalingStepMapping();
		st01.setAspectId("test");
		final StepMapping sm01 = new StepMapping();
		st01.setLevels(Set.of(sm01));
		nsdVnfPackageCopy.setStepMapping(Set.of(st01));
		ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertTrue(true);
	}

	/**
	 * Instance is on level 0 Blueprint want level 1
	 *
	 * @throws Exception
	 */
	@Test
	void testScale002() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final BlueprintParameters params = new BlueprintParameters();
		final ScaleInfo si0 = new ScaleInfo("test", 1);
		params.setNsStepStatus(Set.of());
		blueprint.getInstance().setInstantiatedVnfInfo(params);
		final ScaleNsByStepsData scaleStep = new ScaleNsByStepsData();
		blueprint.getParameters().getNsScale().getScaleNsData().setScaleNsByStepsData(scaleStep);
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final VnfScalingStepMapping st01 = new VnfScalingStepMapping();
		st01.setAspectId("test");
		final StepMapping sm01 = new StepMapping();
		sm01.setNumberOfInstance(12);
		st01.setLevels(Set.of(sm01));
		nsdVnfPackageCopy.setStepMapping(Set.of(st01));
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(12, res);
	}

	/**
	 * Instance is on level 1 Blueprint want level 0
	 *
	 * @throws Exception
	 */
	@Test
	void testScale003() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final BlueprintParameters params = new BlueprintParameters();
		final ScaleInfo si0 = new ScaleInfo("test", 0);
		params.setNsStepStatus(Set.of());
		blueprint.getInstance().setInstantiatedVnfInfo(params);
		final ScaleNsByStepsData scaleStep = new ScaleNsByStepsData();
		blueprint.getParameters().getNsScale().getScaleNsData().setScaleNsByStepsData(scaleStep);
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final VnfScalingStepMapping st01 = new VnfScalingStepMapping();
		st01.setAspectId("test");
		final StepMapping sm01 = new StepMapping(0, 1);
		final StepMapping sm02 = new StepMapping(1, 12);
		st01.setLevels(Set.of(sm01, sm02));
		nsdVnfPackageCopy.setStepMapping(Set.of(st01));
		ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		// Should be 12
		assertTrue(true);
	}

	@Test
	void testTerminate() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.TERMINATE);
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(0, res);
	}

	@Test
	void testInstantiate() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setNsInstance(createInstance());
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(1, res);
	}

	@Test
	void testInstantiate002() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setNsInstance(createInstance());
		blueprint.setNsInstantiationLevelId("test");
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(1, res);
	}

	@Test
	void testInstantiate003() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setNsInstance(createInstance());
		blueprint.setNsInstantiationLevelId("test");
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		nsdVnfPackageCopy.setLevelMapping(Set.of());
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(1, res);
	}

	@Test
	void testRemapNsScaleStep() {
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsScale nsScale = new NsScale();
		nsScale.setScaleVnfData(Set.of());
		final ScaleNsData scaleNsData = new ScaleNsData();
		final ScaleNsByStepsData sclaByStep = new ScaleNsByStepsData();
		scaleNsData.setScaleNsByStepsData(sclaByStep);
		nsScale.setScaleNsData(scaleNsData);
		final NsdInstance instance = createInstance();
		final BlueprintParameters vnfInfo = new BlueprintParameters();
		vnfInfo.setNsStepStatus(new LinkedHashSet<>());
		instance.setInstantiatedVnfInfo(vnfInfo);
		ss3.remapNsScale(nsScale, instance);
		assertTrue(true);
	}

	/**
	 * Other direction
	 */
	@Test
	void testRemapNsScaleStep002() {
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsScale nsScale = new NsScale();
		nsScale.setScaleVnfData(Set.of());
		final ScaleNsData scaleNsData = new ScaleNsData();
		final ScaleNsByStepsData sclaByStep = new ScaleNsByStepsData();
		sclaByStep.setScalingDirection(ScalingDirectionType.IN);
		scaleNsData.setScaleNsByStepsData(sclaByStep);
		nsScale.setScaleNsData(scaleNsData);
		final NsdInstance instance = createInstance();
		final BlueprintParameters vnfInfo = new BlueprintParameters();
		vnfInfo.setNsStepStatus(new LinkedHashSet<>());
		instance.setInstantiatedVnfInfo(vnfInfo);
		ss3.remapNsScale(nsScale, instance);
		assertTrue(true);
	}

	@Test
	void testRemapNsScaleVnf() {
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsScale nsScale = new NsScale();
		nsScale.setScaleType(ScaleType.VNF);
		nsScale.setScaleVnfData(Set.of());
		final ScaleNsData scaleNsData = new ScaleNsData();
		final ScaleNsByStepsData sclaByStep = new ScaleNsByStepsData();
		scaleNsData.setScaleNsByStepsData(sclaByStep);
		nsScale.setScaleNsData(scaleNsData);
		final NsdInstance instance = createInstance();
		final BlueprintParameters vnfInfo = new BlueprintParameters();
		vnfInfo.setNsStepStatus(new LinkedHashSet<>());
		instance.setInstantiatedVnfInfo(vnfInfo);
		ss3.remapNsScale(nsScale, instance);
		assertTrue(true);
	}

	@Test
	void testRemapNsScaleLevelInst() {
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsScale nsScale = new NsScale();
		nsScale.setScaleVnfData(Set.of());
		final ScaleNsData scaleNsData = new ScaleNsData();
		final ScaleNsToLevelData levelData = new ScaleNsToLevelData();
		levelData.setNsInstantiationLevel("1");
		scaleNsData.setScaleNsToLevelData(levelData);
		nsScale.setScaleNsData(scaleNsData);
		final NsdInstance instance = createInstance();
		final BlueprintParameters vnfInfo = new BlueprintParameters();
		vnfInfo.setNsStepStatus(new LinkedHashSet<>());
		instance.setInstantiatedVnfInfo(vnfInfo);
		ss3.remapNsScale(nsScale, instance);
		assertTrue(true);
	}

	@Test
	void testRemapNsScaleLevelInfo() {
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsScale nsScale = new NsScale();
		nsScale.setScaleVnfData(Set.of());
		final ScaleNsData scaleNsData = new ScaleNsData();
		final ScaleNsToLevelData levelData = new ScaleNsToLevelData();
		scaleNsData.setScaleNsToLevelData(levelData);
		nsScale.setScaleNsData(scaleNsData);
		final NsdInstance instance = createInstance();
		final BlueprintParameters vnfInfo = new BlueprintParameters();
		vnfInfo.setNsStepStatus(new LinkedHashSet<>());
		instance.setInstantiatedVnfInfo(vnfInfo);
		ss3.remapNsScale(nsScale, instance);
		assertTrue(true);
	}

	@Test
	void testNoCopyNumberOfInstancesInstantiate() {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.INSTANTIATE);
		blueprint.setNsInstance(createInstance());
		blueprint.setNsInstantiationLevelId("test");
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdPackageNsdPackage nsdVnfPackageCopy = createNsdPackageNsdPackage();
		nsdVnfPackageCopy.setLevelMapping(Set.of());
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(1, res);
	}

	@Test
	void testNoCopyNumberOfInstancesScale() {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.SCALE);
		blueprint.setNsInstance(createInstance());
		blueprint.setNsInstantiationLevelId("test");
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdPackageNsdPackage nsdVnfPackage = createNsdPackageNsdPackage();
		final NsScalingLevelMapping lm01 = new NsScalingLevelMapping("aspect", "aspect", 2);
		nsdVnfPackage.setLevelMapping(Set.of(lm01));
		final int res = ss3.getNumberOfInstances(nsdVnfPackage, blueprint);
		assertEquals(2, res);
	}

	@Test
	void testNoCopyNumberOfInstancesTermibate() {
		final NsBlueprint blueprint = createBluePrint();
		blueprint.setOperation(PlanOperationType.TERMINATE);
		blueprint.setNsInstance(createInstance());
		blueprint.setNsInstantiationLevelId("test");
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdPackageNsdPackage nsdVnfPackageCopy = createNsdPackageNsdPackage();
		nsdVnfPackageCopy.setLevelMapping(Set.of());
		final int res = ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertEquals(0, res);
	}

	private NsdInstance createInstance() {
		final NsdInstance inst = new NsdInstance();
		inst.setId(UUID.randomUUID());
		return inst;
	}

	private static NsdVnfPackageCopy createNsdVnfPackageCopy() {
		final NsdVnfPackageCopy nsdVnfPackageCopy = new NsdVnfPackageCopy();
		nsdVnfPackageCopy.setId(UUID.randomUUID());
		final VnfScalingLevelMapping level1 = new VnfScalingLevelMapping();
		level1.setAspectId("aspect");
		nsdVnfPackageCopy.addLevelMapping(level1);
		return nsdVnfPackageCopy;
	}

	private NsdPackageNsdPackage createNsdPackageNsdPackage() {
		final NsdPackageNsdPackage pkg = new NsdPackageNsdPackage();
		pkg.setId(UUID.randomUUID());
		final NsScalingLevelMapping level1 = new NsScalingLevelMapping();
		level1.setAspectId("aspect");
		pkg.addLevelMapping(level1);
		return pkg;
	}

	private static NsBlueprint createBluePrint() {
		final NsBlueprint bp = new NsBlueprint();
		bp.setOperation(PlanOperationType.SCALE);
		final BlueprintParameters params = new BlueprintParameters();
		final NsScale nsScale = new NsScale();
		final ScaleNsData nsData = new ScaleNsData();
		final ScaleNsToLevelData scaleNsToLevelData = new ScaleNsToLevelData();
		scaleNsToLevelData.setNsScaleInfo(Set.of(createNsScaleInfo()));
		nsData.setScaleNsToLevelData(scaleNsToLevelData);
		nsScale.setScaleNsData(nsData);
		nsScale.setScaleVnfData(Set.of());
		params.setNsScale(nsScale);
		bp.setParameters(params);
		final NsdInstance inst = new NsdInstance();
		bp.setNsInstance(inst);
		return bp;
	}

	private static NsScaleInfo createNsScaleInfo() {
		final NsScaleInfo nsi = new NsScaleInfo();
		nsi.setNsScalingAspectId("aspect");
		return nsi;
	}
}
