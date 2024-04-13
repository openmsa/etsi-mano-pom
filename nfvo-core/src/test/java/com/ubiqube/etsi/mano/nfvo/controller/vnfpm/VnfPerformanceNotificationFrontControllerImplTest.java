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
package com.ubiqube.etsi.mano.nfvo.controller.vnfpm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.nfvo.service.VnfPerformanceNotificationService;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class VnfPerformanceNotificationFrontControllerImplTest {
	@Mock
	private VnfPerformanceNotificationService notificationService;

	@Test
	void testAvailableCheck() {
		final VnfPerformanceNotificationFrontControllerImpl srv = new VnfPerformanceNotificationFrontControllerImpl(notificationService);
		srv.availableCheck();
		assertTrue(true);
	}

	@Test
	void testAvailablePost() {
		final VnfPerformanceNotificationFrontControllerImpl srv = new VnfPerformanceNotificationFrontControllerImpl(notificationService);
		srv.availablePost(createPerformanceInformationAvailableNotification(), null);
		assertTrue(true);
	}

	@Test
	void testCrossedCheck() {
		final VnfPerformanceNotificationFrontControllerImpl srv = new VnfPerformanceNotificationFrontControllerImpl(notificationService);
		srv.crossedCheck();
		assertTrue(true);
	}

	@Test
	void testCrossedPost() {
		final VnfPerformanceNotificationFrontControllerImpl srv = new VnfPerformanceNotificationFrontControllerImpl(notificationService);
		srv.crossedPost(createThresholdCrossedNotification(), null);
		assertTrue(true);
	}

	ThresholdCrossedNotification createThresholdCrossedNotification() {
		return new ThresholdCrossedNotification();
	}

	PerformanceInformationAvailableNotification createPerformanceInformationAvailableNotification() {
		return new PerformanceInformationAvailableNotification();
	}
}
