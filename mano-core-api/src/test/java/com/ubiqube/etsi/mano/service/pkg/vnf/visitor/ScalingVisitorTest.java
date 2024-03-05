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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.TestVnfPackageReader;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.InstantiationLevel;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ScaleInfo;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VduLevel;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.InstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInitialDelta;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduScalingAspectDeltas;

@SuppressWarnings("static-method")
class ScalingVisitorTest {

	@SuppressWarnings("static-method")
	@Test
	void testInstantiationLevel() throws Exception {
		final ScalingVisitor sv = new ScalingVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		final InstantiationLevels ils1 = new InstantiationLevels();
		final InstantiationLevel il1 = new InstantiationLevel();
		final ScaleInfo si1 = new ScaleInfo();
		si1.setScaleLevel(123);
		il1.setScaleInfo(Map.of("", si1));
		ils1.setLevels(Map.of("", il1));
		vnfReader.setInstatiationLevels(List.of(ils1));
		sv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	@Test
	void testVduInstantiationLevel() throws Exception {
		final ScalingVisitor sv = new ScalingVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		final VduInstantiationLevels ils1 = new VduInstantiationLevels();
		final VduLevel il1 = new VduLevel();
		il1.setNumberOfInstances(123);
		ils1.setLevels(Map.of("", il1));
		ils1.setTargets(List.of("compute"));
		vnfReader.setVduInstantiationLevels(List.of(ils1));
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("compute");
		vnfPackage.setVnfCompute(Set.of(comp01));
		sv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	@Test
	void testVduScalingAspectDeltas() throws Exception {
		final ScalingVisitor sv = new ScalingVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		final VduScalingAspectDeltas ils1 = new VduScalingAspectDeltas();
		final VduLevel il1 = new VduLevel();
		il1.setNumberOfInstances(123);
		ils1.setDeltas(Map.of("", il1));
		vnfReader.setVduScalingAspectDeltas(List.of(ils1));
		ils1.setTargets(List.of("compute"));
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("compute");
		vnfPackage.setVnfCompute(Set.of(comp01));
		//
		final VduInitialDelta vid01 = new VduInitialDelta();
		vid01.setTargets(List.of("compute"));
		vid01.setInitialDelta(il1);
		vnfReader.setVduInitialDelta(List.of(vid01));
		sv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

}
