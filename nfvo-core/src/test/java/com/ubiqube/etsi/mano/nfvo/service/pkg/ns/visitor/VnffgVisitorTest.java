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
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class VnffgVisitorTest {
	@Mock
	private NsPackageProvider packageProvider;

	@Test
	void test() {
		final NsOnboardingVisitor srv = new VnffgVisitor();
		final NsdPackage nsPackage = new NsdPackage();
		nsPackage.setVnfPkgIds(Set.of());
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final NfpDescriptor nfp01 = new NfpDescriptor();
		final VnffgInstance inst = new VnffgInstance();
		inst.setPairs(List.of());

		nfp01.setInstances(List.of(inst));
		vnffg.setNfpd(List.of(nfp01));
		final Classifier cls = new Classifier();
		vnffg.setClassifier(cls);
		when(packageProvider.getVnffg(anyMap())).thenReturn(Set.of(vnffg));
		srv.visit(nsPackage, packageProvider, Map.of());
		assertTrue(true);
	}

	@Test
	void test_WithPair() {
		final NsOnboardingVisitor srv = new VnffgVisitor();
		final NsdPackage nsPackage = new NsdPackage();
		final NsdPackageVnfPackage pkg = new NsdPackageVnfPackage();
		final ListKeyPair vl = new ListKeyPair();
		vl.setValue("vl");
		pkg.setVirtualLinks(Set.of(vl));
		nsPackage.setVnfPkgIds(Set.of(pkg));
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final NfpDescriptor nfp01 = new NfpDescriptor();
		final VnffgInstance inst = new VnffgInstance();
		final CpPair pair = new CpPair();
		pair.setIngressVl("ingress");
		pair.setEgressVl("vl");
		pair.setEgress("vl");
		inst.setPairs(List.of(pair));

		nfp01.setInstances(List.of(inst));
		vnffg.setNfpd(List.of(nfp01));
		final Classifier cls = new Classifier();
		vnffg.setClassifier(cls);
		when(packageProvider.getVnffg(anyMap())).thenReturn(Set.of(vnffg));
		srv.visit(nsPackage, packageProvider, Map.of());
		assertTrue(true);
	}

}
