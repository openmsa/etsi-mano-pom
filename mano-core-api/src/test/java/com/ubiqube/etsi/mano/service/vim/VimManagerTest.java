/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.vim;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.CnfServerJpa;
import com.ubiqube.etsi.mano.jpa.VimConnectionInformationJpa;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;
import com.ubiqube.etsi.mano.service.SystemService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.vim.dto.SwImage;
import com.ubiqube.etsi.mano.vim.dummy.DummyVim;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class VimManagerTest {
	@Mock
	private VimConnectionInformationJpa vimConnJpa;
	@Mock
	private EntityManager entityManager;
	@Mock
	private SystemService systemService;
	@Mock
	private CnfServerJpa cnfServerJpa;
	@Mock
	private VrQanJpa vrQanJpa;
	@Mock
	private EventManager eventManager;
	@Mock
	private ManoSearch manoSearch;

	private static final String uuidStr = "4d02c1b8-c185-11ed-ba42-c8f750509d3b";
	private static final UUID uuid = UUID.fromString(uuidStr);

	@Test
	void getVimById() throws Exception {
		final VimManager vm = createVimManager(new DummyVim());
		final Vim res = vm.getVimById(uuid);
		assertNotNull(res);
	}

	@Test
	void testCallVrQan() throws Exception {
		final DummyVim vim001 = new DummyVim();
		final VimManager vm = createVimManager(vim001);
		final VimConnectionInformation vci = createVimConnection();
		final ResourceQuota quotas = new DefaultQuotas();
		vim001.setQuota(quotas);
		final VrQan vrquan = new VrQan(uuid);
		when(vrQanJpa.save(any())).thenReturn(vrquan);
		vm.callVrqan(vci);
		assertTrue(true);
	}

	@Test
	void testCallVrQanChange() throws Exception {
		final DummyVim vim001 = new DummyVim();
		final VimManager vm = createVimManager(vim001);
		final VimConnectionInformation vci = createVimConnection();
		final ResourceQuota quotas = new DefaultQuotas();
		vim001.setQuota(quotas);
		final VrQan vrquan = new VrQan(uuid);
		vrquan.setFloatingIpMax(123);
		when(vrQanJpa.save(any())).thenReturn(vrquan);
		vm.callVrqan(vci);
		verify(eventManager).sendNotification(eq(NotificationEvent.VRQAN), eq(uuid), any());
	}

	@Test
	void testGetDetailedImageList() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		final List<SoftwareImage> res = vm.getDetailedImageList(vci);
		assertNotNull(res);
	}

	@Test
	void testGetDetailedImageList_002() {
		final DummyVim dVim = Mockito.mock(DummyVim.class);
		when(dVim.getType()).thenReturn("dummy-vim");
		final Storage storage = Mockito.mock(Storage.class);
		final VimManager vm = createVimManager(dVim);
		final VimConnectionInformation vci = createVimConnection();
		when(dVim.storage(vci)).thenReturn(storage);
		final SwImage sw1 = new SwImage("");
		when(storage.getImageList()).thenReturn(List.of(sw1));
		final List<SoftwareImage> res = vm.getDetailedImageList(vci);
		assertNotNull(res);
	}

	@Test
	void testRefresh() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findById(vci.getId())).thenReturn(Optional.of(vci));
		final VimConnectionInformation res = vm.refresh(vci.getId());
		assertNull(res);
	}

	@Test
	void testRegister() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		final VimConnectionInformation res = vm.register(vci);
		assertNotNull(res);
	}

	@Test
	void testRegisterAlreadyRegistered() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findByVimId(vci.getVimId())).thenReturn(Optional.of(vci));
		assertThrows(GenericException.class, () -> vm.register(vci));
	}

	@Test
	void testRegisterMergeCnf() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		final CnfServer cnfServer = new CnfServer();
		final CnfInformations info = new CnfInformations();
		info.setClusterTemplate("tmpl");
		cnfServer.setInfo(info);
		when(cnfServerJpa.findById(getSafeUUID(vci.getVimId()))).thenReturn(Optional.of(cnfServer));
		final VimConnectionInformation res = vm.register(vci);
		assertNotNull(res);
	}

	@Test
	void testDeleteVim() {
		final VimManager vm = createVimManager(new DummyVim());
		vm.deleteVim(uuid);
		assertTrue(true);
	}

	@Test
	void testRegisterIfNeeded() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		vm.registerIfNeeded(vci);
		assertTrue(true);
	}

	@Test
	void testRegisterIfNeededAlreadyExists() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findByVimId(vci.getVimId())).thenReturn(Optional.of(vci));
		vm.registerIfNeeded(vci);
		assertTrue(true);
	}

	@Test
	void testFindAllConn() {
		final VimManager vm = createVimManager(new DummyVim());
		vm.findAllVimconnections();
		assertTrue(true);
	}

	@Test
	void testOptionalVimById() {
		final VimManager vm = createVimManager(new DummyVim());
		vm.findOptionalVimByVimId(null);
		assertTrue(true);
	}

	@Test
	void testSave() {
		final VimManager vm = createVimManager(new DummyVim());
		vm.save(null);
		assertTrue(true);
	}

	@Test
	void testFindVimById() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findById(null)).thenReturn(Optional.of(vci));
		vm.findVimById(null);
		assertTrue(true);
	}

	@Test
	void testFindVimByVimId() {
		final VimManager vm = createVimManager(new DummyVim());
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findByVimId(null)).thenReturn(Optional.of(vci));
		vm.findVimByVimId(null);
		assertTrue(true);
	}

	@Test
	void testgetVimByType() {
		final VimManager vm = createVimManager(new DummyVim());
		vm.getVimByType(null);
		assertTrue(true);
	}

	@Test
	void testGetVimByDistance() {
		final VimManager vm = createVimManager(new DummyVim());
		final GeoPoint point = new GeoPoint(0, 0);
		vm.getVimByDistance(point);
		assertTrue(true);
	}

	private VimManager createVimManager(final DummyVim vim01) {
		final List<Vim> vims = List.of(vim01);
		final VimConnectionInformation vci = createVimConnection();
		when(vimConnJpa.findByVimType("dummy-vim")).thenReturn(Set.of(vci));
		return new VimManager(vims, vimConnJpa, systemService, cnfServerJpa, vrQanJpa, eventManager, manoSearch);
	}

	private static VimConnectionInformation createVimConnection() {
		final VimConnectionInformation vci = new VimConnectionInformation();
		vci.setId(uuid);
		vci.setVimId(uuidStr);
		vci.setVimType("dummy-vim");
		return vci;
	}
}
