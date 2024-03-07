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
package com.ubiqube.etsi.mano.vnfm.controller.vnfind;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.vnfm.fc.vnfind.VnfIndSubscriptionsFrontController;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class Sol003VnfIndSubscriptionsFrontControllerImpl implements VnfIndSubscriptionsFrontController {
	private final SubscriptionFrontController subscriptionService;

	public Sol003VnfIndSubscriptionsFrontControllerImpl(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLink) {
		return subscriptionService.search(requestParams, clazz, makeLink, ApiVersionType.SOL003_VNFIND);
	}

	@Override
	public <U> ResponseEntity<U> create(final Object vnfIndicatorSubscriptionRequest, final Class<U> clazz, final Class<?> versionController, final Consumer<U> makeLink, final Function<U, String> getSelfLink) {
		return subscriptionService.create(vnfIndicatorSubscriptionRequest, clazz, versionController, makeLink, getSelfLink, ApiVersionType.SOL003_VNFIND);
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		return subscriptionService.deleteById(subscriptionId, ApiVersionType.SOL003_VNFIND);
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Class<U> clazz, final Consumer<U> makeLink) {
		return subscriptionService.findById(subscriptionId, clazz, makeLink, ApiVersionType.SOL003_VNFIND);
	}

}
