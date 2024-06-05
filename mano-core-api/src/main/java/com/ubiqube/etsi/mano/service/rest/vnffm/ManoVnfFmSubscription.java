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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.rest.vnffm;

import java.util.UUID;
import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

public class ManoVnfFmSubscription {
	private final QueryParameters client;

	public ManoVnfFmSubscription(final QueryParameters manoClient) {
		this.client = manoClient;
	}

	public Subscription subscribe(final Subscription subscription) {
		client.setFragment("/subscriptions");
		final BiFunction<HttpGateway, Object, Object> request = (final HttpGateway httpGateway, final Object r) -> httpGateway.createVnfFmSubscriptionRequest(subscription);
		return (Subscription) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfFmSubscriptionClass)
				.setOutClass(HttpGateway::mapVnfFmSubscription)
				.post(subscription);
	}

	public Subscription find(final UUID id) {
		client.setFragment("/subscriptions/{id}");
		client.setObjectId(id);
		return (Subscription) client.createQuery()
				.setWireOutClass(HttpGateway::getVnfFmSubscriptionClass)
				.setOutClass(HttpGateway::mapVnfFmSubscription)
				.getSingle();
	}

	public void delete(final UUID id) {
		client.setFragment("/subscriptions/{id}");
		client.setObjectId(id);
		client.createQuery().delete();
	}

}
