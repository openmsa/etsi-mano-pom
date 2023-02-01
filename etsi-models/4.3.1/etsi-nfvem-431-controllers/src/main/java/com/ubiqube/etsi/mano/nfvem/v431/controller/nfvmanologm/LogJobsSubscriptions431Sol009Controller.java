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
package com.ubiqube.etsi.mano.nfvem.v431.controller.nfvmanologm;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.nfvmanologm.LogJobsSubscriptionFrontController;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LogmSubscription;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LogmSubscriptionRequest;

@RestController
public class LogJobsSubscriptions431Sol009Controller implements LogJobsSubscriptions431Sol009Api {
	private final LogJobsSubscriptionFrontController logJobsFrontController;

	public LogJobsSubscriptions431Sol009Controller(final LogJobsSubscriptionFrontController logJobsFrontController) {
		this.logJobsFrontController = logJobsFrontController;
	}

	@Override
	public ResponseEntity<List<LogmSubscription>> subscriptionsGet(@Valid final String filter, @Valid final String nextpageOpaqueMarker) {
		return logJobsFrontController.search(filter, LogmSubscription.class);
	}

	@Override
	public ResponseEntity<LogmSubscription> subscriptionsPost(@Valid final LogmSubscriptionRequest body) {
		return logJobsFrontController.create(body, LogmSubscription.class);
	}

	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return logJobsFrontController.delete(subscriptionId);
	}

	@Override
	public ResponseEntity<LogmSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return logJobsFrontController.findById(subscriptionId, LogmSubscription.class);
	}

}
