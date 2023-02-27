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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.ScaleNsToLevelData;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

class NsScaleStrategyV3Test {

	@SuppressWarnings("static-method")
	@Test
	void testName() throws Exception {
		final NsBlueprint blueprint = createBluePrint();
		final NsScaleStrategyV3 ss3 = new NsScaleStrategyV3();
		final NsdVnfPackageCopy nsdVnfPackageCopy = createNsdVnfPackageCopy();
		ss3.getNumberOfInstances(nsdVnfPackageCopy, blueprint);
		assertTrue(true);
	}

	private static NsdVnfPackageCopy createNsdVnfPackageCopy() {
		final NsdVnfPackageCopy nsdVnfPackageCopy = new NsdVnfPackageCopy();
		nsdVnfPackageCopy.setId(UUID.randomUUID());
		final VnfScalingLevelMapping level1 = new VnfScalingLevelMapping();
		level1.setAspectId("aspect");
		nsdVnfPackageCopy.addLevelMapping(level1);
		return nsdVnfPackageCopy;
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
		return bp;
	}

	private static NsScaleInfo createNsScaleInfo() {
		final NsScaleInfo nsi = new NsScaleInfo();
		nsi.setNsScalingAspectId("aspect");
		return nsi;
	}
}
