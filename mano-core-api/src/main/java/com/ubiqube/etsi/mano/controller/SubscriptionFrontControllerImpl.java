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
package com.ubiqube.etsi.mano.controller;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static com.ubiqube.etsi.mano.Constants.getSingleField;

import java.net.URI;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class SubscriptionFrontControllerImpl implements SubscriptionFrontController {

	private final SubscriptionService subscriptionService;

	public SubscriptionFrontControllerImpl(final @Lazy SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final Function<Subscription, U> mapper, final Consumer<U> makeLinks, final ApiVersionType type) {
		final String filter = getSingleField(requestParams, "filter");
		final List<Subscription> list = subscriptionService.query(filter, type);
		final List<U> pkgms = list.stream().map(mapper::apply).toList();
		pkgms.stream().forEach(makeLinks);
		return ResponseEntity.ok(pkgms);
	}

	@Override
	public <U> ResponseEntity<U> create(final Subscription subscriptionRequest, final Function<Subscription, U> mapper, final Class<?> versionController, final Consumer<U> makeLinks, final Function<U, String> getSelfLink, final ApiVersionType type) {
		final Subscription subscription = subscriptionService.save(subscriptionRequest, versionController, type);
		final U res = mapper.apply(subscription);
		makeLinks.accept(res);
		final String link = getSelfLink.apply(res);
		final URI location = URI.create(link);
		return ResponseEntity.created(location).body(res);
	}

	@Override
	public ResponseEntity<Void> deleteById(final String subscriptionId, final ApiVersionType type) {
		subscriptionService.delete(getSafeUUID(subscriptionId), type);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Function<Subscription, U> mapper, final Consumer<U> makeLinks, final ApiVersionType type) {
		final Subscription subscription = subscriptionService.findById(getSafeUUID(subscriptionId), type);
		final U res = mapper.apply(subscription);
		makeLinks.accept(res);
		return ResponseEntity.ok(res);
	}

}
