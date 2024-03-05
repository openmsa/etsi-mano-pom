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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.dto.NsVnf;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class VnfPackageVisitorTest {
	@Mock
	private NsPackageProvider packageProvider;
	@Mock
	private VnfPackageService vnfPackageService;

	@Test
	void test_PackageNotFound() {
		final NsOnboardingVisitor vis = new VnfPackageVisitor(vnfPackageService);
		final NsdPackage nsPackage = new NsdPackage();
		final NsVnf nsVnf = new NsVnf();
		nsVnf.setVnfdId("a707412c-c68d-11ed-98ec-c8f750509d3b");
		when(packageProvider.getVnfd(any())).thenReturn(Set.of(nsVnf));
		final Map<String, String> map = Map.of();
		assertThrows(NotFoundException.class, () -> vis.visit(nsPackage, packageProvider, map));
	}

	@Test
	void test() {
		final NsOnboardingVisitor vis = new VnfPackageVisitor(vnfPackageService);
		final NsdPackage nsPackage = new NsdPackage();
		final NsVnf nsVnf = new NsVnf();
		nsVnf.setVnfdId("a707412c-c68d-11ed-98ec-c8f750509d3b");
		nsVnf.setVirtualLink("lnk");
		when(packageProvider.getVnfd(any())).thenReturn(Set.of(nsVnf));
		final VnfPackage pkg = new VnfPackage();
		final ListKeyPair pair = new ListKeyPair();
		pair.setValue("p");
		pkg.setVirtualLinks(Set.of(pair));
		when(vnfPackageService.findByVnfdId(anyString())).thenReturn(Optional.of(pkg));
		vis.visit(nsPackage, packageProvider, Map.of());
		assertTrue(true);
	}

	@Test
	void test_002() {
		final NsOnboardingVisitor vis = new VnfPackageVisitor(vnfPackageService);
		final NsdPackage nsPackage = new NsdPackage();
		final NsVnf nsVnf = new NsVnf();
		nsVnf.setVnfdId("a707412c-c68d-11ed-98ec-c8f750509d3b/df");
		nsVnf.setVirtualLink1("lnk");
		when(packageProvider.getVnfd(any())).thenReturn(Set.of(nsVnf));
		final VnfPackage pkg = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setNetworks(Set.of("vl"));
		pkg.setVnfCompute(Set.of(comp01));
		final ListKeyPair pair = new ListKeyPair();
		pkg.setVirtualLinks(Set.of(pair));
		when(vnfPackageService.findByVnfdIdAndSoftwareVersion(anyString(), anyString())).thenReturn(Optional.of(pkg));
		vis.visit(nsPackage, packageProvider, Map.of());
		assertTrue(true);
	}

}
