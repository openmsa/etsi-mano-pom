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
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;

@SuppressWarnings("static-method")
class RemapNetworksVisitorTest {

	@Test
	void testName() throws Exception {
		final RemapNetworksVisitor rn = new RemapNetworksVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		rn.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void test001() throws Exception {
		final RemapNetworksVisitor rn = new RemapNetworksVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		vnfPackage.setVnfCompute(Set.of(comp01));
		rn.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void test002() throws Exception {
		final RemapNetworksVisitor rn = new RemapNetworksVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("tosca");
		vnfPackage.setVnfCompute(Set.of(comp01));

		final VnfLinkPort vlp01 = new VnfLinkPort();
		vlp01.setVirtualBinding("tosca");
		vnfPackage.setVnfLinkPort(Set.of(vlp01));
		rn.visit(vnfPackage);
		assertTrue(true);
	}
}
