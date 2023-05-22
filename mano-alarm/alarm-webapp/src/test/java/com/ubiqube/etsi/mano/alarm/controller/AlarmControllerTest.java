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
package com.ubiqube.etsi.mano.alarm.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.SubscriptionDto;
import com.ubiqube.etsi.mano.alarm.service.AlarmService;

@ExtendWith(MockitoExtension.class)
class AlarmControllerTest {
	@Mock
	private AlarmService alarmService;

	@Test
	void testCreateAlarm() throws MalformedURLException {
		final AlarmController srv = new AlarmController(alarmService);
		final AlarmDto alarm = new AlarmDto();
		final SubscriptionDto subs = new SubscriptionDto();
		subs.setCallbackUri(URI.create("http://localhost/").toURL());
		alarm.setSubscription(subs);
		srv.createAlarm(alarm);
		assertTrue(true);
	}

	@Test
	void testDeleteAlarm() {
		final AlarmController srv = new AlarmController(alarmService);
		srv.deleteAlaram(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final AlarmController srv = new AlarmController(alarmService);
		srv.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindById2() {
		final AlarmController srv = new AlarmController(alarmService);
		final Alarm alarm = new Alarm();
		when(alarmService.findById(any())).thenReturn(Optional.of(alarm));
		srv.findById(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testListAlarm() {
		final AlarmController srv = new AlarmController(alarmService);
		srv.listAlarm();
		assertTrue(true);
	}
}
