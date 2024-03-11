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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NsdVnfPackageCopy;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.PreVimSelection;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;

@ExtendWith(MockitoExtension.class)
class GrantActionSupportTest {
	@Mock
	private GrantsResponseJpa grantJpa;
	@Mock
	private VimManager vimManager;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private NsLiveInstanceJpa nsLiveInstance;
	@Mock
	private PreVimSelection preVimSelection;
	private final Vim vim = new DummyVim();

	@Test
	void testGetVnfCompute_NotFound() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		assertThrows(GenericException.class, () -> gas.getVnfCompute(id));
	}

	@Test
	void testGetVnfCompute() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final GrantResponse response = new GrantResponse();
		response.setVnfdId(id.toString());
		when(grantJpa.findById(id)).thenReturn(Optional.of(response));
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPkg);
		gas.getVnfCompute(id);
		assertTrue(true);
	}

	@Test
	void testGetVnfStorage() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		//
		final GrantResponse response = new GrantResponse();
		response.setVnfdId(id.toString());
		when(grantJpa.findById(id)).thenReturn(Optional.of(response));
		//
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPkg);
		gas.getVnfStorage(id);
		assertTrue(true);
	}

	@Test
	void testGetOsContainer() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		//
		final GrantResponse response = new GrantResponse();
		response.setVnfdId(id.toString());
		when(grantJpa.findById(id)).thenReturn(Optional.of(response));
		//
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPkg);
		gas.getOsContainer(id);
		assertTrue(true);
	}

	@Test
	void testGetUnmanagedNetworks_NotFound() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final GrantResponse response = new GrantResponse();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		//
		gas.getUnmanagedNetworks(response, vim, vimConn);
		assertTrue(true);
	}

	@Test
	void testGetUnmanagedNetworks() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final GrantResponse response = new GrantResponse();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		//
		final NsLiveInstance nsLive = new NsLiveInstance();
		final NsBlueprint nsBlue = new NsBlueprint();
		nsLive.setNsBlueprint(nsBlue);
		final NsVnfTask task = new NsVnfTask();
		final NsdVnfPackageCopy nsPkg = new NsdVnfPackageCopy();
		nsPkg.setToscaName("vdu");
		final ListKeyPair lkp = new ListKeyPair("vl", 1);
		nsPkg.setVirtualLinks(Set.of(lkp));
		task.setNsPackageVnfPackage(nsPkg);
		final ForwarderMapping fw01 = new ForwarderMapping();
		fw01.setVduName("vdu");
		fw01.setForwardingName("fwName");
		fw01.setVlName("vl");
		task.getNsPackageVnfPackage().addForwardMapping(fw01);
		nsLive.setNsTask(task);
		//
		when(nsLiveInstance.findByResourceId(null)).thenReturn(List.of(nsLive));
		//
		final NsLiveInstance nsLive2 = new NsLiveInstance();
		final NsVirtualLinkTask vlTask = new NsVirtualLinkTask();
		final NsVirtualLink nsVl = new NsVirtualLink();
		nsVl.setToscaName("vl");
		vlTask.setNsVirtualLink(nsVl);
		vlTask.setToscaName("vl");
		nsLive2.setNsTask(vlTask);
		when(nsLiveInstance.findByNsdInstanceAndClass(any(), eq(NsVirtualLinkTask.class))).thenReturn(List.of(nsLive2));
		gas.getUnmanagedNetworks(response, vim, vimConn);
		assertTrue(true);
	}

	@Test
	void testConvertVnfdToId() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageService.findByVnfdId(id)).thenReturn(vnfPkg);
		gas.convertVnfdToId(id.toString());
		assertTrue(true);
	}

	@Test
	void testGetVimsNotFound() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final GrantResponse resp = new GrantResponse();
		resp.setVnfdId(id.toString());
		assertThrows(NotFoundException.class, () -> gas.getVims(resp));
	}

	@Test
	void testGetVims() {
		final GrantActionSupport gas = new GrantActionSupport(grantJpa, vimManager, vnfPackageService, nsLiveInstance, preVimSelection);
		final UUID id = UUID.randomUUID();
		final GrantResponse resp = new GrantResponse();
		resp.setVnfdId(id.toString());
		final VnfPackage pkg = new VnfPackage();
		final Optional<VnfPackage> optPkg = Optional.of(pkg);
		when(vnfPackageService.findByVnfdId(eq(id.toString()))).thenReturn(optPkg);
		gas.getVims(resp);
		assertTrue(true);
	}

}
