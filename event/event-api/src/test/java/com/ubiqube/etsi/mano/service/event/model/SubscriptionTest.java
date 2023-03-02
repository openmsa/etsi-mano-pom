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

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.rest.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;

@SuppressWarnings("static-method")
class SubscriptionTest {

	@Test
	void testBuilder() throws Exception {
		final AuthentificationInformations auth = new AuthentificationInformations();
		final Subscription res = Subscription.builder()
				.api(ApiTypesEnum.SOL003)
				.authentication(auth)
				.callbackUri("")
				.filters(List.of())
				.id(UUID.randomUUID())
				.nodeFilter("")
				.subscriptionType(SubscriptionType.ALARM)
				.version("")
				.build();
		assertNotNull(res);
	}

	@Test
	void testBuilderToString() throws Exception {
		final AuthentificationInformations auth = new AuthentificationInformations();
		final String res = Subscription.builder()
				.api(ApiTypesEnum.fromValue("SOL003"))
				.authentication(auth)
				.callbackUri("")
				.filters(List.of())
				.id(UUID.randomUUID())
				.nodeFilter("")
				.subscriptionType(SubscriptionType.ALARM)
				.version("")
				.toString();
		assertNotNull(res);
	}

	@Test
	void testSetterGetter() throws Exception {
		final AuthentificationInformations auth = new AuthentificationInformations();
		final Subscription res = new Subscription();
		res.setApi(ApiTypesEnum.SOL003);
		res.setAuthentication(auth);
		res.setCallbackUri("");
		res.setFilters(List.of());
		res.setId(UUID.randomUUID());
		res.setNodeFilter("");
		res.setSubscriptionType(SubscriptionType.ALARM);
		res.setVersion("");
		assertNotNull(res);
		assertNotNull(res.getApi());
		assertNotNull(res.getAuthentication());
		assertNotNull(res.getCallbackUri());
		assertNotNull(res.getFilters());
		assertNotNull(res.getId());
		assertNotNull(res.getNodeFilter());
		assertNotNull(res.getSubscriptionType());
		assertNotNull(res.getVersion());
	}
}
