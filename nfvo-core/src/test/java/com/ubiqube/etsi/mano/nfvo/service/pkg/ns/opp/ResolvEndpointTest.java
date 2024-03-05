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

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

class ResolvEndpointTest {

	@Test
	void test_Minimal() {
		final NsOnboardingPostProcessor proc = new ResolvEndpoint();
		final NsdPackage nsPkg = new NsdPackage();
		nsPkg.setVnffgs(Set.of());
		proc.visit(nsPkg);
		assertTrue(true);
	}

	@Test
	void test_VlNotFound() {
		final NsOnboardingPostProcessor proc = new ResolvEndpoint();
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage vnf = new NsdPackageVnfPackage();
		final ForwarderMapping fw = new ForwarderMapping();
		fw.setForwardingName("");
		vnf.addForwardMapping(fw);
		nsPkg.setVnfPkgIds(Set.of(vnf));
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final NfpDescriptor nfp = new NfpDescriptor();
		final VnffgInstance inst = new VnffgInstance();
		final CpPair pair = new CpPair();
		inst.setPairs(List.of(pair));
		nfp.setInstances(List.of(inst));
		vnffg.setNfpd(List.of(nfp));
		nsPkg.setVnffgs(Set.of(vnffg));
		assertThrows(NotFoundException.class, () -> proc.visit(nsPkg));
	}

	@Test
	void test() {
		final NsOnboardingPostProcessor proc = new ResolvEndpoint();
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageVnfPackage vnf = new NsdPackageVnfPackage();
		final ForwarderMapping fw = new ForwarderMapping();
		fw.setForwardingName("");
		vnf.addForwardMapping(fw);
		nsPkg.setVnfPkgIds(Set.of(vnf));
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final NfpDescriptor nfp = new NfpDescriptor();
		final VnffgInstance inst = new VnffgInstance();
		final CpPair pair = new CpPair();
		pair.setIngress("");
		inst.setPairs(List.of(pair));
		nfp.setInstances(List.of(inst));
		vnffg.setNfpd(List.of(nfp));
		nsPkg.setVnffgs(Set.of(vnffg));
		proc.visit(nsPkg);
		assertTrue(true);
	}
}
