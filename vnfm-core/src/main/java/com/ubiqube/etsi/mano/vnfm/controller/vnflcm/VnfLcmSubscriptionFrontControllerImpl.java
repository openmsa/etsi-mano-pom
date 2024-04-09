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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmSubscriptionFrontController;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfLcmSubscriptionFrontControllerImpl implements VnfLcmSubscriptionFrontController {
	private final SubscriptionFrontController subscriptionService;

	public VnfLcmSubscriptionFrontControllerImpl(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public <U> ResponseEntity<U> findById(final String id, final Function<Subscription, U> clazz, final Consumer<U> setLink) {
		return subscriptionService.findById(id, clazz, setLink, ApiVersionType.SOL003_VNFLCM);
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final @Nullable String nextpageOpaqueMarker, final Function<Subscription, U> clazz, final Consumer<U> setLink) {
		return subscriptionService.search(requestParams, clazz, setLink, ApiVersionType.SOL003_VNFLCM);
	}

	@Override
	public <U> ResponseEntity<U> create(final Subscription body, final Function<Subscription, U> func, final Class<?> versionController, final Consumer<U> makeLinks, final Function<U, String> setLink) {
		return subscriptionService.create(body, func, versionController, makeLinks, setLink, ApiVersionType.SOL003_VNFLCM);
	}

	@Override
	public ResponseEntity<Void> deleteById(final String id) {
		return subscriptionService.deleteById(id, ApiVersionType.SOL003_VNFLCM);
	}

}
