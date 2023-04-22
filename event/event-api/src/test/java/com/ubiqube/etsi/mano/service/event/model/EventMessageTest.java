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
package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class EventMessageTest {

	@Test
	void test() {
		final EventMessage ev = new EventMessage(NotificationEvent.APP_SCALE_FAILED, UUID.randomUUID(), Map.of());
		assertNotNull(ev.getAdditionalParameters());
		assertNull(ev.getId());
		assertNotNull(ev.getNotificationEvent());
		assertNotNull(ev.getObjectId());
		ev.setAdditionalParameters(Map.of());
		ev.setId(UUID.randomUUID());
		ev.setNotificationEvent(NotificationEvent.APPINSTANTIATE);
		ev.setObjectId(UUID.randomUUID());
	}

	@Test
	void testFilterAttributes() {
		FilterAttributes fa = new FilterAttributes("", "");
		fa = new FilterAttributes();
		fa = FilterAttributes.of("a", "b");
		assertNull(fa.getId());
		fa.setAttribute("");
		fa.setId(UUID.randomUUID());
		fa.setValue("");
		assertNotNull(fa.getId());
		assertNotNull(fa.getAttribute());
		assertNotNull(fa.getValue());
	}
}
