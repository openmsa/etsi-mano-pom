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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.alarm.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.repository.AlarmRepository;
import com.ubiqube.etsi.mano.alarm.service.aggregate.AggregateService;
import com.ubiqube.etsi.mano.alarm.service.transform.TransformService;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {
	@Mock
	private AlarmRepository alarmRepository;
	@Mock
	private TransformService transformService;
	@Mock
	private AggregateService aggregateService;

	@Test
	void testCreate() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		final Alarm alarm = new Alarm();
		srv.create(alarm);
		assertTrue(true);
	}

	@Test
	void testCreateFailedTransform() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		final Alarm alarm = new Alarm();
		when(transformService.checkErrors(anyList())).thenReturn(List.of("bad"));
		assertThrows(AlarmException.class, () -> srv.create(alarm));
		assertTrue(true);
	}

	@Test
	void testCreateFailedAggreate() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		final Alarm alarm = new Alarm();
		when(aggregateService.checkErrors(anyList())).thenReturn(List.of("bad"));
		assertThrows(AlarmException.class, () -> srv.create(alarm));
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testFind() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		srv.find();
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final AlarmService srv = new AlarmService(alarmRepository, transformService, aggregateService);
		srv.findById(null);
		assertTrue(true);
	}

}
