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
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;

@SuppressWarnings("static-method")
class FixExternalPointVisitorTest {

	@Test
	void testName() throws Exception {
		final FixExternalPointVisitor fep = new FixExternalPointVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		fep.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void test001() throws Exception {
		final FixExternalPointVisitor fep = new FixExternalPointVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfExtCp vec01 = new VnfExtCp();
		vnfPackage.setVnfExtCp(Set.of(vec01));
		fep.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void test002() throws Exception {
		final FixExternalPointVisitor fep = new FixExternalPointVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfExtCp vec01 = new VnfExtCp();
		vec01.setInternalVirtualLink("comp01");
		vnfPackage.setVnfExtCp(Set.of(vec01));
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("comp01");
		vnfPackage.setVnfCompute(Set.of(comp01));
		fep.visit(vnfPackage);
		assertTrue(true);
	}
}
