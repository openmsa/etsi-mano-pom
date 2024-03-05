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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;

@ExtendWith(MockitoExtension.class)
class VnffgPostContributorV3Test {
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;

	@Test
	void testName() throws Exception {
		final VnffgPostContributorV3 srv = new VnffgPostContributorV3(nsLiveInstanceJpa);
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdPackage bundle = new NsdPackage();
		bundle.setVnffgs(Set.of());
		final List<SclableResources<VnffgPostTask>> res = srv.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void test_Not_found() throws Exception {
		final VnffgPostContributorV3 srv = new VnffgPostContributorV3(nsLiveInstanceJpa);
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdPackage bundle = new NsdPackage();
		final VnffgDescriptor vnffg01 = new VnffgDescriptor();
		final Classifier cla01 = new Classifier();
		vnffg01.setClassifier(cla01);
		vnffg01.setNfpd(List.of());
		bundle.setVnffgs(Set.of(vnffg01));
		bundle.setVnfPkgIds(Set.of());
		assertThrows(GenericException.class, () -> srv.contribute(bundle, blueprint));
	}

	@Test
	void test_Found() throws Exception {
		final VnffgPostContributorV3 srv = new VnffgPostContributorV3(nsLiveInstanceJpa);
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdPackage bundle = new NsdPackage();
		final VnffgDescriptor vnffg01 = new VnffgDescriptor();
		final Classifier cla01 = new Classifier();
		cla01.setLogicalDestinationPort("name");
		cla01.setLogicalSourcePort("name");
		vnffg01.setClassifier(cla01);
		final NfpDescriptor nfpd01 = new NfpDescriptor();
		final VnffgInstance inst01 = new VnffgInstance();
		nfpd01.setInstances(List.of(inst01));
		vnffg01.setNfpd(List.of(nfpd01));
		vnffg01.setName("vnffg01");
		bundle.setVnffgs(Set.of(vnffg01));
		final NsdPackageVnfPackage pkg01 = new NsdPackageVnfPackage();
		final ForwarderMapping fw01 = new ForwarderMapping();
		fw01.setForwardingName("name");
		fw01.setVduName("vdu01");
		pkg01.setForwardMapping(Set.of(fw01));
		bundle.setVnfPkgIds(Set.of(pkg01));
		final List<SclableResources<VnffgPostTask>> res = srv.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(1, res.size());
		TestUtils.assertNameNotNull(res);
	}

}
