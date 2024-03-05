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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.dto.NsNsd;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class NesteedNsdPackageVisitorTest {
	@Mock
	private NsdPackageJpa nsPackageJpa;
	@Mock
	private NsPackageProvider packageProvider;

	@Test
	void testVisit_NoFail() {
		final NesteedNsdPackageVisitor nesteed = new NesteedNsdPackageVisitor(nsPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		nesteed.visit(nsPkg, packageProvider, Map.of());
		assertTrue(true);
	}

	@Test
	void testVisit() {
		final NesteedNsdPackageVisitor nesteed = new NesteedNsdPackageVisitor(nsPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final NsNsd nsd001 = new NsNsd();
		when(packageProvider.getNestedNsd(any())).thenReturn(Set.of(nsd001));
		when(nsPackageJpa.findByNsdInvariantId(any())).thenReturn(Optional.of(nsPkg));
		nesteed.visit(nsPkg, packageProvider, Map.of());
		final Set<NsdPackageNsdPackage> res = nsPkg.getNestedNsdInfoIds();
		assertNotNull(res);
		assertEquals(1, res.size());
	}

	@Test
	void testVisit_NotFound() {
		final NesteedNsdPackageVisitor nesteed = new NesteedNsdPackageVisitor(nsPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final NsNsd nsd001 = new NsNsd();
		when(packageProvider.getNestedNsd(any())).thenReturn(Set.of(nsd001));
		final Map<String, String> map = Map.of();
		assertThrows(NotFoundException.class, () -> nesteed.visit(nsPkg, packageProvider, map));
	}

}
