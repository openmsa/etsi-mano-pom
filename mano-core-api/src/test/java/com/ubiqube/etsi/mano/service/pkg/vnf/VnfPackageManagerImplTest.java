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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;

@SuppressWarnings("static-method")
class VnfPackageManagerImplTest {

	@Test
	void testEmptyList() throws Exception {
		final VnfPackageManagerImpl srv = new VnfPackageManagerImpl(List.of());
		assertThrows(GenericException.class, () -> srv.getProviderFor(""));
		final ByteArrayResource bos = new ByteArrayResource("".getBytes(), null);
		assertThrows(GenericException.class, () -> srv.getProviderFor(bos));
	}

	@Test
	void testList() throws Exception {
		final PackageDescriptor<VnfPackageReader> def = new TestPackageDescriptor();
		final VnfPackageManagerImpl srv = new VnfPackageManagerImpl(List.of(def));
		final PackageDescriptor<VnfPackageReader> res = srv.getProviderFor("test");
		assertNotNull(res);
		final ByteArrayResource bos = new ByteArrayResource("".getBytes(), null);
		srv.getProviderFor(bos);
	}
}
