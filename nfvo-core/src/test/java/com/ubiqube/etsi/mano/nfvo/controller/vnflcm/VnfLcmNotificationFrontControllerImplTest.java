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
package com.ubiqube.etsi.mano.nfvo.controller.vnflcm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vnflcm.VnfLcmNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfLcmNotificationService;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class VnfLcmNotificationFrontControllerImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfLcmNotificationService notificationService;

	@Test
	void testCreationCheck() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.creationCheck();
		assertTrue(true);
	}

	@Test
	void testCreationNotification() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.creationNotification(createVnfLcmNotification(), null);
		assertTrue(true);
	}

	@Test
	void testDeleteCheck() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.deletionCheck();
		assertTrue(true);
	}

	@Test
	void testNotificationCheck() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.deletionNotification(createVnfLcmNotification(), null);
		assertTrue(true);
	}

	@Test
	void testVnfLcmOpOccsCheck() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.vnflcmopoccCheck();
		assertTrue(true);
	}

	@Test
	void testVnflcmopoccNotification() {
		final VnfLcmNotificationFrontControllerImpl srv = new VnfLcmNotificationFrontControllerImpl(notificationService);
		srv.vnflcmopoccNotification(createVnfLcmNotification(), null);
		assertTrue(true);
	}

	static VnfLcmNotification createVnfLcmNotification() {
		return new VnfLcmNotification();
	}

}
