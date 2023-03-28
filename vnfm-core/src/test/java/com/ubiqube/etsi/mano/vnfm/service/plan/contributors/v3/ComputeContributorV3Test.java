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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3;

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

import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;

@ExtendWith(MockitoExtension.class)
class ComputeContributorV3Test {
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private ScalingStrategy scalingStrategy;
	@Mock
	private VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	@Test
	void testContribute_Minimal() {
		final ComputeContributorV3 con = new ComputeContributorV3(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
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
	void testContribute() {
		final ComputeContributorV3 con = new ComputeContributorV3(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		final VnfPackage pkg = new VnfPackage();
		final VnfCompute comp = new VnfCompute();
		comp.setStorages(Set.of("sto01"));
		comp.setPorts(Set.of());
		comp.setMonitoringParameters(Set.of(createMonitoring()));
		pkg.setVnfCompute(Set.of(comp));
		pkg.setVnfStorage(createStorage());
		final VnfBlueprint bp = new VnfBlueprint();
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters infos = new BlueprintParameters();
		infos.setScaleStatus(new LinkedHashSet<>());
		vnfInstance.setInstantiatedVnfInfo(infos);
		bp.setVnfInstance(vnfInstance);
		bp.setParameters(infos);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(vnfInstance);
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		con.contribute(pkg, bp);
		assertTrue(true);
	}

	@Test
	void testContributePotrs() {
		final ComputeContributorV3 con = new ComputeContributorV3(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		final VnfPackage pkg = new VnfPackage();
		final VnfCompute comp = new VnfCompute();
		comp.setStorages(Set.of());
		comp.setPorts(createPort());
		comp.setMonitoringParameters(Set.of());
		pkg.setVnfCompute(Set.of(comp));
		final VnfIndicator ind = new VnfIndicator();
		pkg.setVnfIndicator(Set.of(ind));
		final ListKeyPair kp = new ListKeyPair();
		kp.setValue("vl01");
		pkg.setVirtualLinks(Set.of(kp));
		final VnfBlueprint bp = new VnfBlueprint();
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters infos = new BlueprintParameters();
		infos.setScaleStatus(new LinkedHashSet<>());
		vnfInstance.setInstantiatedVnfInfo(infos);
		bp.setVnfInstance(vnfInstance);
		bp.setParameters(infos);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(vnfInstance);
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		con.contribute(pkg, bp);
		assertTrue(true);
	}

	private static Set<VnfLinkPort> createPort() {
		final VnfLinkPort p = new VnfLinkPort();
		p.setToscaName("p01");
		final VnfLinkPort vl01 = new VnfLinkPort();
		vl01.setToscaName("vl01");
		return Set.of(p, vl01);
	}

	@Test
	void testContribute_CrashOnUnknownStorage() {
		final ComputeContributorV3 con = new ComputeContributorV3(vnfLiveInstance, scalingStrategy, vnfInstanceServiceVnfm);
		final VnfPackage pkg = new VnfPackage();
		final VnfCompute comp = new VnfCompute();
		comp.setStorages(Set.of("sto022"));
		comp.setPorts(Set.of());
		comp.setMonitoringParameters(Set.of());
		pkg.setVnfCompute(Set.of(comp));
		pkg.setVnfStorage(createStorage());
		final VnfBlueprint bp = new VnfBlueprint();
		final VnfInstance vnfInstance = new VnfInstance();
		final BlueprintParameters infos = new BlueprintParameters();
		infos.setScaleStatus(new LinkedHashSet<>());
		vnfInstance.setInstantiatedVnfInfo(infos);
		bp.setVnfInstance(vnfInstance);
		bp.setParameters(infos);
		when(vnfInstanceServiceVnfm.findById(any())).thenReturn(vnfInstance);
		final NumberOfCompute numComp = new NumberOfCompute(0, 1, null);
		when(scalingStrategy.getNumberOfCompute(any(), any(), any(), any(), any())).thenReturn(numComp);
		assertThrows(NotFoundException.class, () -> con.contribute(pkg, bp));
	}

	private Set<VnfStorage> createStorage() {
		final VnfStorage sto = new VnfStorage();
		sto.setToscaName("sto01");
		return Set.of(sto);
	}

	private static MonitoringParams createMonitoring() {
		final MonitoringParams m = new MonitoringParams();
		m.setName("mon");
		return m;
	}
}
