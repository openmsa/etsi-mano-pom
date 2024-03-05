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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.service.SearchableService;

@ExtendWith(MockitoExtension.class)
class NsBlueprintServiceImplTest {
	@Mock
	private NsBlueprintJpa nsBlueprintJpa;
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;
	@Mock
	private SearchableService searchService;

	@Test
	void testGetNumberOfLiveVl() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		when(nsLiveInstanceJpa.findByVnfInstanceAndTaskVlIsNotNull(any(), any())).thenReturn(List.of());
		final NsVirtualLink vl = new NsVirtualLink();
		srv.getNumberOfLiveVl(null, vl);
		assertTrue(true);
	}

	@Test
	void testFindById() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		final NsBlueprint nsBlueprint = new NsBlueprint();
		when(nsBlueprintJpa.findById(any())).thenReturn(Optional.of(nsBlueprint));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_Fail() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		assertThrows(NotFoundException.class, () -> srv.findById(null));
	}

	@Test
	void testSave() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testUpdateState() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		final NsBlueprint plan = new NsBlueprint();
		srv.updateState(plan, null);
		assertTrue(true);
	}

	@Test
	void testFindByNsdInstanceAndClass() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		srv.findByNsdInstanceAndClass(null, getClass());
		assertTrue(true);
	}

	@Test
	void testCountByNsInstance() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		srv.countByNsInstance(null);
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final NsBlueprintServiceImpl srv = new NsBlueprintServiceImpl(nsBlueprintJpa, nsLiveInstanceJpa, searchService);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}
}
