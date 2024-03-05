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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.test.ext.YamlParameterResolver;
import com.ubiqube.etsi.mano.test.ext.YamlTestData;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

@ExtendWith({ MockitoExtension.class, YamlParameterResolver.class })
class ComputeContributorTest {
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private ScalingStrategy scalingStrategy;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	@Test
	void testContribute_Minimal() {
		final ComputeContributor con = new ComputeContributor(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		final VnfPackage pkg = new VnfPackage();
		pkg.setVnfCompute(Set.of());
		final VnfBlueprint bp = new VnfBlueprint();
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters infos = new BlueprintParameters();
		infos.setScaleStatus(new LinkedHashSet<>());
		vnfInstance.setInstantiatedVnfInfo(infos);
		bp.setVnfInstance(vnfInstance);
		bp.setParameters(infos);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(vnfInstance);
		con.contribute(pkg, bp);
		assertTrue(true);
	}

	@Test
	void testContribute_GoodStorage(@YamlTestData("contributor-compute-good-storage-vnfPackage.yaml") final VnfPackage pkgIn,
			@YamlTestData("contributor-compute-good-storage-vnfBlueprint.yaml") final VnfBlueprint bpIn) {
		final ComputeContributor con = new ComputeContributor(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(bpIn.getInstance());
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		con.contribute(pkgIn, bpIn);
		assertTrue(true);
	}

	@Test
	void testContributePotrs(@YamlTestData("contributor-compute-ports-vnfPackage.yaml") final VnfPackage pkgIn,
			@YamlTestData("contributor-compute-ports-vnfBlueprint.yaml") final VnfBlueprint bpIn) {
		final ComputeContributor con = new ComputeContributor(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(bpIn.getVnfInstance());
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		con.contribute(pkgIn, bpIn);
		assertTrue(true);
	}

	@Test
	void testContribute_CrashOnUnknownStorage(@YamlTestData("contributor-compute-bad-storage-vnfPackage.yaml") final VnfPackage pkgIn,
			@YamlTestData("contributor-compute-bad-storage-vnfBlueprint.yaml") final VnfBlueprint bpIn) {
		final ComputeContributor con = new ComputeContributor(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(bpIn.getInstance());
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		assertThrows(NotFoundException.class, () -> con.contribute(pkgIn, bpIn));
	}
}
