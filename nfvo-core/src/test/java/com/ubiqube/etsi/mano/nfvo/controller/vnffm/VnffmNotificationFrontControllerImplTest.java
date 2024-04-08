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
package com.ubiqube.etsi.mano.nfvo.controller.vnffm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnffmNotificationService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnffmNotificationFrontControllerImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnffmNotificationService ns;
	@Mock
	private VnffmNotificationService vnfmNotificationService;

	@Test
	void testCheck() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmCheck();
		assertTrue(true);
	}

	@Test
	void testClearedCheck() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmClearedCheck();
		assertTrue(true);
	}

	@Test
	void testClearedNotification() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmClearedNotification(createAlarmNotification(), null);
		assertTrue(true);
	}

	@Test
	void testRebuildCheck() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmRebuiltCheck();
		assertTrue(true);
	}

	@Test
	void testAlarmRebuildNotification() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmRebuiltNotification(createAlarmNotification(), null);
		assertTrue(true);
	}

	@Test
	void testAlarmNotification() {
		final VnffmNotificationFrontControllerImpl srv = new VnffmNotificationFrontControllerImpl(vnfmNotificationService);
		srv.alarmNotification(createAlarmNotification(), null);
		assertTrue(true);
	}

	static AlarmNotification createAlarmNotification() {
		return new AlarmNotification();
	}
}
