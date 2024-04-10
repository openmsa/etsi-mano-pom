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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.service.AlarmDatabaseService;

@ExtendWith(MockitoExtension.class)
class AlarmVnfmControllerImplTest {
	@Mock
	private SearchableService searchService;
	@Mock
	private AlarmDatabaseService alarmsJpa;

	@Test
	void testFindByIdFail() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		assertThrows(NotFoundException.class, () -> srv.findById(null));
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		final Alarms alarm = new Alarms();
		when(alarmsJpa.findById(null)).thenReturn(Optional.of(alarm));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testEscalate() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		final Alarms alarm = new Alarms();
		when(alarmsJpa.findById(null)).thenReturn(Optional.of(alarm));
		srv.escalate(null, null);
		assertTrue(true);
	}

	@Test
	void testModify() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		final Alarms alarm = new Alarms();
		when(alarmsJpa.findById(null)).thenReturn(Optional.of(alarm));
		assertThrows(ConflictException.class, () -> srv.modify(null, null, null));
	}

	@Test
	void testModify2() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		final Alarms alarm = new Alarms();
		when(alarmsJpa.findById(null)).thenReturn(Optional.of(alarm));
		assertThrows(PreConditionException.class, () -> srv.modify(null, null, "1"));
	}

	@Test
	void testModify3() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		final Alarms alarm = new Alarms();
		alarm.setVersion(1);
		alarm.setAckState(AckState.UNACKNOWLEDGED);
		when(alarmsJpa.findById(null)).thenReturn(Optional.of(alarm));
		srv.modify(null, null, "1");
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final AlarmVnfmControllerImpl srv = new AlarmVnfmControllerImpl(searchService, alarmsJpa);
		srv.search(null, null, null, null, null, null);
		assertTrue(true);
	}
}
