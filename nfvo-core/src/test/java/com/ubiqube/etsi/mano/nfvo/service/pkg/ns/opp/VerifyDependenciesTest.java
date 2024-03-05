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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.opp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

@ExtendWith(MockitoExtension.class)
class VerifyDependenciesTest {
	@Mock
	private NsdPackageJpa nsPkgJpa;

	@Test
	void test_Minimal() {
		final NsOnboardingPostProcessor proc = new VerifyDependencies(nsPkgJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		nsPkg.setId(id);
		nsPkg.setNestedNsdInfoIds(Set.of());
		proc.visit(nsPkg);
		assertTrue(true);
	}

	@Test
	void test() {
		final NsOnboardingPostProcessor proc = new VerifyDependencies(nsPkgJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		nsPkg.setId(id);
		final NsdPackageNsdPackage nsd = new NsdPackageNsdPackage();
		final NsdPackage nsd01 = new NsdPackage();
		nsd01.setId(UUID.randomUUID());
		nsd01.setNestedNsdInfoIds(Set.of());
		// Child is nullable ?
		nsd.setChild(nsd01);
		nsPkg.setNestedNsdInfoIds(Set.of(nsd));
		when(nsPkgJpa.findById(any())).thenReturn(Optional.of(nsd01));
		proc.visit(nsPkg);
		assertTrue(true);
	}

	@Test
	void test_Circular() {
		final NsOnboardingPostProcessor proc = new VerifyDependencies(nsPkgJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		nsPkg.setId(id);
		final NsdPackageNsdPackage nsd = new NsdPackageNsdPackage();
		final NsdPackage nsd01 = new NsdPackage();
		nsd01.setId(id);
		nsd01.setNestedNsdInfoIds(Set.of());
		// Child is nullable ?
		nsd.setChild(nsd01);
		nsPkg.setNestedNsdInfoIds(Set.of(nsd));
		when(nsPkgJpa.findById(any())).thenReturn(Optional.of(nsd01));
		assertThrows(GenericException.class, () -> proc.visit(nsPkg));
	}

}
