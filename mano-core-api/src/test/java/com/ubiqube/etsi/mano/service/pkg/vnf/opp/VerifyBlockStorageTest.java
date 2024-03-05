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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.GenericException;

@SuppressWarnings("static-method")
class VerifyBlockStorageTest {

	@Test
	void testBasic() throws Exception {
		final VerifyBlockStorage vbs = new VerifyBlockStorage();
		final VnfPackage vnfPackage = new VnfPackage();
		vbs.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testFailStorage() throws Exception {
		final VerifyBlockStorage vbs = new VerifyBlockStorage();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setStorages(Set.of("bad"));
		vnfPackage.setVnfCompute(Set.of(comp01));
		assertThrows(GenericException.class, () -> vbs.visit(vnfPackage));
	}

	@Test
	void testOkStorage() throws Exception {
		final VerifyBlockStorage vbs = new VerifyBlockStorage();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setStorages(Set.of("sto01"));
		vnfPackage.setVnfCompute(Set.of(comp01));
		final VnfStorage sto01 = new VnfStorage();
		sto01.setToscaName("sto01");
		vnfPackage.setVnfStorage(Set.of(sto01));
		vbs.visit(vnfPackage);
		assertTrue(true);
	}
}
