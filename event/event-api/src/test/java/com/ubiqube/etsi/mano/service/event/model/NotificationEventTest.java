/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class NotificationEventTest {

	@Test
	void testName() throws Exception {
		assertEquals(NotificationEvent.APP_TERMINATE_SUCCESS, NotificationEvent.fromValue("APP_TERMINATE_SUCCESS"));
		assertEquals("APP_TERMINATE_SUCCESS", NotificationEvent.fromValue("APP_TERMINATE_SUCCESS").value());
	}

	@Test
	void testFromValueNull() throws Exception {
		assertEquals(null, NotificationEvent.fromValue(null));
	}

	@Test
	void testToString() {
		assertNotNull(NotificationEvent.APP_INSTANCE_CREATE.toString());
	}
}
