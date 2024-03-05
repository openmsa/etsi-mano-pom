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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.vnf.TestVnfPackageReader;

@ExtendWith(MockitoExtension.class)
class VnfdVisitorTest {
	@Mock
	private VnfPackageRepository vnfPackageRepository;

	@Test
	void testBasic() throws Exception {
		final VnfdVisitor vv = new VnfdVisitor(vnfPackageRepository);
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		vv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	@Test
	void testName() throws Exception {
		final VnfdVisitor vv = new VnfdVisitor(vnfPackageRepository);
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		vnfReader.setVnfdFiles(List.of("test"));
		//
		//
		vv.visit(vnfPackage, vnfReader, Map.of());
		//
		assertTrue(true);
	}
}
