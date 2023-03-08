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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

	@Mock
	private JmsTemplate JmsTemplate;

	@Test
	void testName() {
		final EventController ec = new EventController(JmsTemplate);
		final EventMessageDto ev = new EventMessageDto();
		ev.setAdditionalParameters(Map.of());
		ev.setId(UUID.randomUUID());
		ev.setNotificationEvent(NotificationEvent.APP_INSTANCE_CREATE);
		ev.setObjectId(UUID.randomUUID());
		ec.notification(ev);
		assertNotNull(ev.getId());
		assertTrue(true);
	}
}
