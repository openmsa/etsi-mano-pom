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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

@SuppressWarnings("static-method")
class SubscriptionEventTest {

	@Test
	void testBuilder() throws Exception {
		final Subscription subscr = new Subscription();
		final SubscriptionEvent ev = SubscriptionEvent.builder()
				.event(new EventMessage())
				.subscription(subscr)
				.build();
		ev.toString();
		ev.hashCode();
		assertTrue(true);
	}

	@Test
	void testBuilderToString() throws Exception {
		final Subscription subscr = new Subscription();
		final String str = SubscriptionEvent.builder()
				.event(new EventMessage())
				.subscription(subscr)
				.toString();
		assertNotNull(str);
	}

	@Test
	void testNoArgsCtor() {
		assertNotNull(new Subscription());
	}

	@Test
	void testStterGetter() {
		final SubscriptionEvent sub = new SubscriptionEvent();
		final EventMessage event = new EventMessage();
		sub.setEvent(event);
		final Subscription subs = new Subscription();
		sub.setSubscription(subs);
		assertNotNull(sub.getEvent());
		assertNotNull(sub.getSubscription());
	}
}
