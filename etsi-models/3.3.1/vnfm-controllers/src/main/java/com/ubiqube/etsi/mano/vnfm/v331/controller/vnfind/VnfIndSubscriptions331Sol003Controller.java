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
package com.ubiqube.etsi.mano.vnfm.v331.controller.vnfind;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.em.v331.model.vnfind.VnfIndicatorSubscription;
import com.ubiqube.etsi.mano.em.v331.model.vnfind.VnfIndicatorSubscriptionLinks;
import com.ubiqube.etsi.mano.em.v331.model.vnfind.VnfIndicatorSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v331.model.vnflcm.Link;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;

import jakarta.validation.Valid;

@RestController
public class VnfIndSubscriptions331Sol003Controller implements VnfIndSubscriptions331Sol003Api {
	private final SubscriptionFrontController subscriptionService;

	public VnfIndSubscriptions331Sol003Controller(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public ResponseEntity<List<VnfIndicatorSubscription>> subscriptionsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return subscriptionService.search(requestParams, VnfIndicatorSubscription.class, VnfIndSubscriptions331Sol003Controller::makeLinks, SubscriptionType.VNFIND);
	}

	@Override
	public ResponseEntity<VnfIndicatorSubscription> subscriptionsPost(@Valid final VnfIndicatorSubscriptionRequest vnfIndicatorSubscriptionRequest) {
		return subscriptionService.create(vnfIndicatorSubscriptionRequest, VnfIndicatorSubscription.class, VnfIndSubscriptions331Sol003Api.class, VnfIndSubscriptions331Sol003Controller::makeLinks, VnfIndSubscriptions331Sol003Controller::makeSelf, SubscriptionType.VNFPM);
	}

	@Override
	public ResponseEntity<Void> indicatorsSubscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return subscriptionService.deleteById(subscriptionId, SubscriptionType.VNFIND);
	}

	@Override
	public ResponseEntity<VnfIndicatorSubscription> indicatorsSubscriptionsSubscriptionIdGet(final String subscriptionId) {
		return subscriptionService.findById(subscriptionId, VnfIndicatorSubscription.class, VnfIndSubscriptions331Sol003Controller::makeLinks, SubscriptionType.VNFIND);
	}

	private static String makeSelf(final VnfIndicatorSubscription subscription) {
		return linkTo(methodOn(VnfIndSubscriptions331Sol003Api.class).indicatorsSubscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
	}

	private static void makeLinks(final VnfIndicatorSubscription subscription) {
		final VnfIndicatorSubscriptionLinks links = new VnfIndicatorSubscriptionLinks();
		final Link link = new Link();
		link.setHref(linkTo(methodOn(VnfIndSubscriptions331Sol003Api.class).indicatorsSubscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		subscription.setLinks(links);
	}

}
