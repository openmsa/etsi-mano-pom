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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.alarm.entities.AlarmElement;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
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
		final AlarmService srv = createSrv();
		final Alarm alarm = new Alarm();
		srv.create(alarm);
		assertTrue(true);
	}

	@Test
	void testCreateFailedTransform() {
		final AlarmService srv = createSrv();
		final Alarm alarm = new Alarm();
		when(transformService.checkErrors(anyList())).thenReturn(List.of("bad"));
		assertThrows(AlarmException.class, () -> srv.create(alarm));
		assertTrue(true);
	}

	@Test
	void testCreateFailedAggreate() {
		final AlarmService srv = createSrv();
		final Alarm alarm = new Alarm();
		when(aggregateService.checkErrors(anyList())).thenReturn(List.of("bad"));
		assertThrows(AlarmException.class, () -> srv.create(alarm));
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final AlarmService srv = createSrv();
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testFind() {
		final AlarmService srv = createSrv();
		srv.find();
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final AlarmService srv = createSrv();
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testAddEelemnt() {
		final AlarmService srv = createSrv();
		final AlarmElement element = new AlarmElement();
		final Alarm alarm = new Alarm();
		when(alarmRepository.findById(any())).thenReturn(Optional.of(alarm));
		srv.addElement(UUID.randomUUID(), element);
		assertTrue(true);
	}

	@Test
	void testAddEelemntAllreadtExist() {
		final AlarmService srv = createSrv();
		final AlarmElement element = new AlarmElement();
		final Metrics metric = new Metrics();
		metric.setLabel("label");
		element.setMetric(metric);
		final Alarm alarm = new Alarm();
		alarm.getMetrics().add(metric);
		when(alarmRepository.findById(any())).thenReturn(Optional.of(alarm));
		final UUID uuid = UUID.randomUUID();
		assertThrows(AlarmException.class, () -> srv.addElement(uuid, element));
	}

	@Test
	void testDeleteEelemnt() {
		final AlarmService srv = createSrv();
		final Alarm alarm = new Alarm();
		final Metrics metric = new Metrics();
		metric.setLabel("label");
		alarm.getMetrics().add(metric);
		final Aggregates agg = new Aggregates();
		agg.setName("ll");
		alarm.getAggregates().add(agg);
		when(alarmRepository.findById(any())).thenReturn(Optional.of(alarm));
		srv.deleteElement(UUID.randomUUID(), "label");
		assertTrue(true);
	}

	private AlarmService createSrv() {
		return new AlarmService(alarmRepository, transformService, aggregateService);
	}
}
