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
package com.ubiqube.etsi.mano.nfvo.controller.vnfind;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfIndicatorNotificationService;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class VnfIndicatorNotificationFrontControllerImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private VnfIndicatorNotificationService vnfNotificationService;

	@Test
	void testSupportedChangeCheck() {
		final VnfIndicatorNotificationFrontControllerImpl srv = new VnfIndicatorNotificationFrontControllerImpl(vnfNotificationService);
		srv.supportedChangeCheck();
		assertTrue(true);
	}

	@Test
	void testSupportedChangeNotification() {
		final VnfIndicatorNotificationFrontControllerImpl srv = new VnfIndicatorNotificationFrontControllerImpl(vnfNotificationService);
		srv.supportedChangeNotification(createVnfIndiValueChangeNotification(), null);
		assertTrue(true);
	}

	@Test
	void testValueChangeCheck() {
		final VnfIndicatorNotificationFrontControllerImpl srv = new VnfIndicatorNotificationFrontControllerImpl(vnfNotificationService);
		srv.valueChangeCheck();
		assertTrue(true);
	}

	@Test
	void testValueChangeNotification() {
		final VnfIndicatorNotificationFrontControllerImpl srv = new VnfIndicatorNotificationFrontControllerImpl(vnfNotificationService);
		srv.valueChangeNotification(createVnfIndiValueChangeNotification(), null);
		assertTrue(true);
	}

	static VnfIndiValueChangeNotification createVnfIndiValueChangeNotification() {
		return new VnfIndiValueChangeNotification();
	}
}
