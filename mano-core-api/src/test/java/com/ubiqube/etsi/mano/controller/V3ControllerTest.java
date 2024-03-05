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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageManager;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

@ExtendWith(MockitoExtension.class)
class V3ControllerTest {

	@Mock
	private VnfPackageManager packageManager;
	@Mock
	private VnfPackageOnboardingImpl vnfPackageOnboard;
	@Mock
	private VnfPlanService vnfPlanService;

	@Test
	void testValidateVnf() throws IOException {
		final V3Controller srv = new V3Controller(packageManager, vnfPackageOnboard, vnfPlanService);
		final MultipartFile file = Mockito.mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[0]);
		final PackageDescriptor<VnfPackageReader> pkgDesc = Mockito.mock(PackageDescriptor.class);
		when(packageManager.getProviderFor((ManoResource) any())).thenReturn(pkgDesc);
		srv.validateVnf(file);
		assertTrue(true);
	}

	@Test
	void testValidateVnfFail() throws IOException {
		final V3Controller srv = new V3Controller(packageManager, vnfPackageOnboard, vnfPlanService);
		final MultipartFile file = Mockito.mock(MultipartFile.class);
		when(file.getBytes()).thenThrow(IOException.class);
		assertThrows(GenericException.class, () -> srv.validateVnf(file));
	}

	@Test
	void testGetVnf2dPlan() {
		final V3Controller srv = new V3Controller(packageManager, vnfPackageOnboard, vnfPlanService);
		final ListenableGraph<Vertex2d, Edge2d> g = Mockito.mock(DefaultListenableGraph.class);
		when(vnfPlanService.getPlanFor(any())).thenReturn(g);
		srv.getVnf2dPlan(null);
		assertTrue(true);
	}
}
