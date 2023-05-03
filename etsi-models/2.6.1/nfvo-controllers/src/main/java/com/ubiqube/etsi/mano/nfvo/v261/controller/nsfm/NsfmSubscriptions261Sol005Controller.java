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
package com.ubiqube.etsi.mano.nfvo.v261.controller.nsfm;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.common.v261.model.Link;
import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.AlarmLinks;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.FmSubscription;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.FmSubscriptionRequest;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;

import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
public class NsfmSubscriptions261Sol005Controller implements NsfmSubscriptions261Sol005Api {
	private final SubscriptionFrontController subscriptionService;

	public NsfmSubscriptions261Sol005Controller(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public ResponseEntity<List<FmSubscription>> subscriptionsGet(@Valid final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return subscriptionService.search(requestParams, FmSubscription.class, NsfmSubscriptions261Sol005Controller::makeLinks, SubscriptionType.NSFM);
	}

	@Override
	public ResponseEntity<FmSubscription> subscriptionsPost(@Valid final FmSubscriptionRequest body) {
		return subscriptionService.create(body, FmSubscription.class, getClass(), NsfmSubscriptions261Sol005Controller::makeLinks, NsfmSubscriptions261Sol005Controller::makeSelf, SubscriptionType.NSFM);
	}

	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return subscriptionService.deleteById(subscriptionId, SubscriptionType.NSFM);
	}

	@Override
	public ResponseEntity<FmSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return subscriptionService.findById(subscriptionId, FmSubscription.class, NsfmSubscriptions261Sol005Controller::makeLinks, SubscriptionType.NSFM);
	}

	private static void makeLinks(final FmSubscription subscription) {
		final AlarmLinks links = new AlarmLinks();
		final Link link = new Link();
		link.setHref(linkTo(methodOn(NsfmSubscriptions261Sol005Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		links.setSelf(link);
		subscription.setLinks(links);
	}

	private static String makeSelf(final FmSubscription subscription) {
		return linkTo(methodOn(NsfmSubscriptions261Sol005Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
	}
}
