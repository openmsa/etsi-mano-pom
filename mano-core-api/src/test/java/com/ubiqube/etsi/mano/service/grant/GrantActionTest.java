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
package com.ubiqube.etsi.mano.service.grant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.elect.VimElection;
import com.ubiqube.etsi.mano.service.event.flavor.FlavorManager;
import com.ubiqube.etsi.mano.service.event.images.SoftwareImageService;
import com.ubiqube.etsi.mano.service.grant.ccm.CcmManager;
import com.ubiqube.etsi.mano.service.grant.executor.ExecutorFacade;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;

@ExtendWith(MockitoExtension.class)
class GrantActionTest {
	@Mock
	private GrantsResponseJpa grantJpa;
	@Mock
	private VimManager vimManager;
	@Mock
	private VimElection vimElection;
	@Mock
	private SoftwareImageService imageService;
	@Mock
	private FlavorManager flavorManager;
	@Mock
	private GrantSupport grantSupport;
	@Mock
	private GrantContainerAction grantContainerAction;
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private CcmManager ccmManager;
	@Mock
	private CirConnectionManager cirManager;
	@Mock
	private ExecutorFacade facade;

	@Test
	void testName() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> ga.grantRequest(id));
	}

	private GrantAction createService() {
		return new GrantAction(grantJpa, vimManager, vimElection, grantSupport, grantContainerAction, facade);
	}

	@Test
	void test002() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		final GrantResponse grantResponse = new GrantResponse();
		final Optional<GrantResponse> optGrant = Optional.of(grantResponse);
		when(grantJpa.findById(id)).thenReturn(optGrant);
		// No suitable vim
		ga.grantRequest(id);
		assertTrue(true);
	}

	@Test
	void test003() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		final GrantResponse grantResponse = new GrantResponse();
		grantResponse.setOperation("INSTANTIATE");
		grantResponse.setVnfdId(id.toString());
		final Optional<GrantResponse> optGrant = Optional.of(grantResponse);
		//
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setVirtualLinks(Set.of());
		when(grantJpa.findById(id)).thenReturn(optGrant);
		when(vimElection.doElection(any(LinkedList.class), eq(null), any(HashSet.class), any(HashSet.class))).thenReturn(vimConn);
		when(vimManager.getVimById(any())).thenReturn(new DummyVim());
		ga.grantRequest(id);
		assertTrue(true);
	}

	@Test
	void test004() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		final GrantResponse grantResponse = new GrantResponse();
		grantResponse.setOperation("TERMINATE");
		grantResponse.setVnfdId(id.toString());
		final Optional<GrantResponse> optGrant = Optional.of(grantResponse);
		//
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setVirtualLinks(Set.of());
		when(grantJpa.findById(id)).thenReturn(optGrant);
		when(vimElection.doElection(any(LinkedList.class), eq(null), any(HashSet.class), any(HashSet.class))).thenReturn(vimConn);
		when(vimManager.getVimById(any())).thenReturn(new DummyVim());
		ga.grantRequest(id);
		assertTrue(true);
	}

	@Test
	void testCni001() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		final GrantResponse grantResponse = new GrantResponse();
		grantResponse.setOperation("INSTANTIATE");
		grantResponse.setVnfdId(id.toString());
		final GrantInformationExt ge01 = new GrantInformationExt();
		final GrantInformationExt ge02 = new GrantInformationExt();
		ge02.setType(ResourceTypeEnum.OS_CONTAINER);
		grantResponse.setAddResources(Set.of(ge01, ge02));
		final Optional<GrantResponse> optGrant = Optional.of(grantResponse);
		//
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfPackage vnfPackage = new VnfPackage();
		vnfPackage.setVirtualLinks(Set.of());
		final OsContainer osc01 = new OsContainer();
		vnfPackage.setOsContainer(Set.of(osc01));
		when(grantJpa.findById(id)).thenReturn(optGrant);
		when(vimElection.doElection(any(LinkedList.class), eq(null), any(HashSet.class), any(HashSet.class))).thenReturn(vimConn);
		when(vimManager.getVimById(any())).thenReturn(new DummyVim());
		when(facade.getZone(vimConn, grantResponse)).thenReturn(() -> "");
		final ZoneGroupInformation zgi = new ZoneGroupInformation();
		zgi.setZoneId(Set.of("abc"));
		when(facade.getServerGroup(grantResponse)).thenReturn(() -> zgi);
		ga.grantRequest(id);
		assertTrue(true);
	}

	@Test
	void test005() {
		final GrantAction ga = createService();
		final UUID id = UUID.randomUUID();
		final GrantResponse grantResponse = new GrantResponse();
		grantResponse.setOperation("TERMINATE");
		final GrantInformationExt ge01 = new GrantInformationExt();
		grantResponse.addRemoveResources(ge01);
		final Optional<GrantResponse> optGrant = Optional.of(grantResponse);
		//
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		when(grantJpa.findById(id)).thenReturn(optGrant);
		when(vimElection.doElection(any(LinkedList.class), eq(null), any(HashSet.class), any(HashSet.class))).thenReturn(vimConn);
		//
		when(facade.getZone(vimConn, grantResponse)).thenReturn(() -> "");
		final ZoneGroupInformation zgi = new ZoneGroupInformation();
		zgi.setZoneId(Set.of("abc"));
		when(facade.getServerGroup(grantResponse)).thenReturn(() -> zgi);
		ga.grantRequest(id);
		assertTrue(true);
	}
}
