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
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;
import com.ubiqube.etsi.mano.vnfm.service.alarm.AlarmSystem;

@ExtendWith(MockitoExtension.class)
class VnfmThresholdControllerDirectVimTest {
	@Mock
	private ThresholdJpa thresholdJpa;
	@Mock
	private VnfBlueprintJpa vnfBluePrintJpa;
	@Mock
	private SearchableService search;
	@Mock
	private AlarmSystem alarmSystem;

	@Test
	void testFindById() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		final Threshold ret = new Threshold();
		when(thresholdJpa.findById(any())).thenReturn(Optional.of(ret));
		srv.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindByIdFail() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		final UUID id = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> srv.findById(id));
	}

	@Test
	void testSearch() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		final Threshold ret = new Threshold();
		when(thresholdJpa.findById(any())).thenReturn(Optional.of(ret));
		srv.delete(null);
		assertTrue(true);
	}

	@Test
	void testSaveFail() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		final Threshold ret = new Threshold();
		assertThrows(NotFoundException.class, () -> srv.save(ret));
	}

	@Test
	void testSave() {
		final VnfmThresholdControllerDirectVim srv = new VnfmThresholdControllerDirectVim(thresholdJpa, vnfBluePrintJpa, search, alarmSystem);
		final Threshold ret2 = new Threshold();
		final VnfBlueprint ret = new VnfBlueprint();
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		ret.setVimConnections(Set.of(vimConn));
		when(vnfBluePrintJpa.findById(any())).thenReturn(Optional.of(ret));
		srv.save(ret2);
		assertTrue(true);
	}
}
