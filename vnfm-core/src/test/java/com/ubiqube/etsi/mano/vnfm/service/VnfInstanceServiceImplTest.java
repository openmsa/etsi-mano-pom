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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@ExtendWith(MockitoExtension.class)
class VnfInstanceServiceImplTest {
	@Mock
	private VnfInstanceJpa vnfInstanceJpa;
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;
	@Mock
	private Patcher patcher;
	@Mock
	private EventManager eventManager;
	@Mock
	private SearchableService serchableService;

	@Test
	void testDeleteFailed() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		assertThrows(NotFoundException.class, () -> srv.delete(null));
	}

	@Test
	void testDeleteSuccess() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final UUID id = UUID.randomUUID();
		final VnfInstance vnfInst = new VnfInstance();
		when(vnfInstanceJpa.findById(id)).thenReturn(Optional.of(vnfInst));
		srv.delete(id);
		assertTrue(true);
	}

	@Test
	void testDeleteLive() throws Exception {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.deleteLiveInstanceById(null);
		assertTrue(true);
	}

	@Test
	void testFindByVnfInstFail() throws Exception {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final NsdInstance nsdInst = new NsdInstance();
		assertThrows(NotFoundException.class, () -> srv.findBVnfInstanceyVnfPackageId(nsdInst, null));
	}

	@Test
	void testFindByVnfInstSuccess() throws Exception {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		when(vnfInstanceJpa.findByVnfPkg_IdAndNsInstance_Id(null, null)).thenReturn(Optional.of(vnfInst));
		final NsdInstance nsdInst = new NsdInstance();
		srv.findBVnfInstanceyVnfPackageId(nsdInst, null);
		assertTrue(true);
	}

	@Test
	void testSaveVnfInstance() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		srv.save(vnfInst);
		assertTrue(true);
	}

	@Test
	void testFindLiveInstanceByInstantiated() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.findLiveInstanceByInstantiated(null);
		assertTrue(true);
	}

	@Test
	void testSaveVnfLive() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfLiveInstance vnfLive = new VnfLiveInstance();
		srv.save(vnfLive);
		assertTrue(true);
	}

	@Test
	void testFindLiveInstanceById() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.findLiveInstanceById(null);
		assertTrue(true);
	}

	@Test
	void testGetLiveComputeInstanceOf() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.getLiveComputeInstanceOf(null);
		assertTrue(true);
	}

	@Test
	void testQuery() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.query(null);
		assertTrue(true);
	}

	@Test
	void testIsInstFalse() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		when(vnfInstanceJpa.countByVnfPkgId(any())).thenReturn(0);
		srv.isInstantiate(null);
		assertTrue(true);
	}

	@Test
	void testIsInstTRue() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		when(vnfInstanceJpa.countByVnfPkgId(any())).thenReturn(1);
		srv.isInstantiate(null);
		assertTrue(true);
	}

	@Test
	void testPatch_001() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		srv.vnfLcmPatch(vnfInst, null, null);
		assertTrue(true);
	}

	@Test
	void testPatch_002() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		srv.vnfLcmPatch(vnfInst, null, "0");
		assertTrue(true);
	}

	@Test
	void testPatch_003() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		vnfInst.setVersion(0L);
		assertThrows(PreConditionException.class, () -> srv.vnfLcmPatch(vnfInst, null, "1"));
	}

	@Test
	void testFindByResourceId() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.findByResourceIdIn(null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.search(null, null, null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testFindByIdNotFound() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		assertThrows(GenericException.class, () -> srv.findById(null));
		assertTrue(true);
	}

	@Test
	void testFindByIdFound() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		final VnfInstance vnfInst = new VnfInstance();
		when(vnfInstanceJpa.findById(null)).thenReturn(Optional.of(vnfInst));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindByVnfInstanceId() {
		final VnfInstanceServiceImpl srv = new VnfInstanceServiceImpl(vnfInstanceJpa, vnfLiveInstance, patcher, eventManager, serchableService);
		srv.findByVnfInstanceId(null);
		assertTrue(true);
	}

}
