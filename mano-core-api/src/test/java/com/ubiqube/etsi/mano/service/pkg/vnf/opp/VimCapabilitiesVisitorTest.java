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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VlProfileEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.vim.L2Data;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.VimCapability;

@SuppressWarnings("static-method")
class VimCapabilitiesVisitorTest {

	@Test
	void testEmpty() throws Exception {
		final VimCapabilitiesVisitor srv = new VimCapabilitiesVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		srv.visit(vnfPackage);
		assertTrue(true);
	}

	@Test
	void testMtu() throws Exception {
		final VimCapabilitiesVisitor srv = new VimCapabilitiesVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data01 = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setMtu(1234);
		data01.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data01));
		vl01.setVlProfileEntity(profile);
		vnfPackage.setVnfVl(Set.of(vl01));
		srv.visit(vnfPackage);
		final Set<VimCapability> vimCaps = vnfPackage.getVimCapabilities();
		assertNotNull(vimCaps);
		assertEquals(1, vimCaps.size());
		final VimCapability caps = vimCaps.iterator().next();
		assertEquals(VimCapability.HAVE_NET_MTU, caps);
	}

	@Test
	void testVlanTransparent() throws Exception {
		final VimCapabilitiesVisitor srv = new VimCapabilitiesVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data01 = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setVlanTransparent(true);
		data01.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data01));
		vl01.setVlProfileEntity(profile);
		vnfPackage.setVnfVl(Set.of(vl01));
		srv.visit(vnfPackage);
		final Set<VimCapability> vimCaps = vnfPackage.getVimCapabilities();
		assertNotNull(vimCaps);
		assertEquals(1, vimCaps.size());
		final VimCapability caps = vimCaps.iterator().next();
		assertEquals(VimCapability.HAVE_VLAN_TRANSPARENT, caps);
	}

	@Test
	void testVxNet() throws Exception {
		final VimCapabilitiesVisitor srv = new VimCapabilitiesVisitor();
		final VnfPackage vnfPackage = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data01 = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setNetworkType("vxLan");
		data01.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data01));
		vl01.setVlProfileEntity(profile);
		vnfPackage.setVnfVl(Set.of(vl01));
		srv.visit(vnfPackage);
		final Set<VimCapability> vimCaps = vnfPackage.getVimCapabilities();
		assertNotNull(vimCaps);
		assertEquals(1, vimCaps.size());
		final VimCapability caps = vimCaps.iterator().next();
		assertEquals(VimCapability.HAVE_VXNET, caps);
	}

}
