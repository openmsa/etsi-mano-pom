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
package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VlProfileEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.vim.L2Data;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.VimCapability;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.service.event.QuotaNeeded;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class VrQanVimSelectorTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private VrQanService vrQanService;

	@Test
	void testSelectVims() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final Iterable<VimConnectionInformation> it = List.of();
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_rejectCnf() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final OsContainerDeployableUnit os01 = new OsContainerDeployableUnit();
		vnfPkg.setOsContainerDeployableUnits(Set.of(os01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of());
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_acceptCnf() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final OsContainerDeployableUnit os01 = new OsContainerDeployableUnit();
		vnfPkg.setOsContainerDeployableUnits(Set.of(os01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of(VimCapability.HAVE_CNF));
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_rejectVxNet() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setNetworkType("vxlan");
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of());
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_AcceptVxNet() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setNetworkType("vxlan");
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of(VimCapability.HAVE_VXNET));
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_RejectTransparent() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setVlanTransparent(true);
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of());
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_AcceptTransparent() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setVlanTransparent(true);
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of(VimCapability.HAVE_VLAN_TRANSPARENT));
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_RejectMtu() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setMtu(1500);
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of());
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSelectVims001_AcceptMtu() {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		//
		final VnfVl vl01 = new VnfVl();
		final VlProfileEntity profile = new VlProfileEntity();
		final VlProtocolData data = new VlProtocolData();
		final L2Data l2 = new L2Data();
		l2.setMtu(1500);
		data.setL2ProtocolData(l2);
		profile.setVirtualLinkProtocolData(Set.of(data));
		vl01.setVlProfileEntity(profile);
		vnfPkg.setVnfVl(Set.of(vl01));
		//
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimCapabilities(Set.of(VimCapability.HAVE_NET_MTU));
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSuota() throws Exception {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		//
		final VrQan vrQan = new VrQan();
		when(vrQanService.findByVimId(null)).thenReturn(Optional.of(vrQan));
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSuota_Ram() throws Exception {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		//
		final VrQan vrQan = new VrQan();
		vrQan.setRamFree(-1);
		when(vrQanService.findByVimId(null)).thenReturn(Optional.of(vrQan));
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}

	@Test
	void testSuota_Cpu() throws Exception {
		final VrQanVimSelector vq = new VrQanVimSelector(vimManager, vrQanService);
		final VnfPackage vnfPkg = new VnfPackage();
		final GrantResponse grantResp = new GrantResponse();
		final QuotaNeeded needed = new QuotaNeeded();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final Iterable<VimConnectionInformation> it = List.of(vimConn);
		when(vimManager.findAllVimconnections()).thenReturn(it);
		//
		final VrQan vrQan = new VrQan();
		vrQan.setVcpuFree(-1);
		when(vrQanService.findByVimId(null)).thenReturn(Optional.of(vrQan));
		vq.selectVims(vnfPkg, grantResp, needed);
		assertTrue(true);
	}
}
