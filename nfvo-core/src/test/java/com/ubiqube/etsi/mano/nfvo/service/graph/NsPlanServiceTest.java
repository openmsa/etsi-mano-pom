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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;

@ExtendWith(MockitoExtension.class)
class NsPlanServiceTest {
	@Mock
	private NsdPackageService nsdPackageService;

	@Test
	void testName() throws Exception {
		final NsPlanService plan = new NsPlanService(nsdPackageService);
		final UUID id = UUID.randomUUID();
		final NsdPackage nsdPkg = new NsdPackage();
		final NsVirtualLink vl = new NsVirtualLink();
		vl.setToscaName("vl01");
		nsdPkg.setNsVirtualLinks(Set.of(vl));
		final NsdPackageNsdPackage ns01 = new NsdPackageNsdPackage(nsdPkg, null, "toscaNemr", Set.of());
		nsdPkg.setNestedNsdInfoIds(Set.of(ns01));
		final NsdPackageVnfPackage pkg = new NsdPackageVnfPackage();
		pkg.setToscaName("pkg01");
		final ListKeyPair kp = new ListKeyPair();
		kp.setValue("vl01");
		pkg.setVirtualLinks(Set.of(kp));
		pkg.setForwardMapping(Set.of());// Here
		nsdPkg.setVnfPkgIds(Set.of(pkg));
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		vnffg.setName("vnffg");
		final NfpDescriptor nfpd = new NfpDescriptor();
		nfpd.setToscaName("nfpd");
		final VnffgInstance ins = new VnffgInstance();
		ins.setToscaName("inst");
		final CpPair pair = new CpPair();
		pair.setToscaName("pair");
		pair.setEgressVl("egress");
		pair.setIngressVl("ingress");
		pair.setVnf("pkg01");
		ins.setPairs(List.of(pair));
		nfpd.setInstances(List.of(ins));
		nfpd.setToscaName("nfpd");
		vnffg.setNfpd(List.of(nfpd));
		nsdPkg.setVnffgs(Set.of(vnffg));
		when(nsdPackageService.findById(id)).thenReturn(nsdPkg);
		plan.getPlanFor(id);
		assertTrue(true);
	}
}
