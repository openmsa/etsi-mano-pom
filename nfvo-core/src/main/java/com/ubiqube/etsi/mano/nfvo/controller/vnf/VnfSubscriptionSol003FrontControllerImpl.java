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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionManagement;
import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionSol003FrontController;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfSubscriptionSol003FrontControllerImpl implements VnfSubscriptionSol003FrontController {

	private final VnfSubscriptionManagement vnfSubscriptionManagement;

	private final SubscriptionService subscriptionService;

	public VnfSubscriptionSol003FrontControllerImpl(final VnfSubscriptionManagement vnfSubscriptionManagement, final SubscriptionService subscriptionService) {
		this.vnfSubscriptionManagement = vnfSubscriptionManagement;
		this.subscriptionService = subscriptionService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final String filters, final Function<Subscription, U> func, final Consumer<U> makeLinks) {
		final List<Subscription> list = subscriptionService.query(filters, ApiVersionType.SOL005_VNFPKGM);
		final List<U> pkgms = list.stream().map(func::apply).toList();
		pkgms.stream().forEach(makeLinks::accept);
		return ResponseEntity.ok(pkgms);
	}

	@Override
	public <U> ResponseEntity<U> create(final Subscription subscriptionsPostQuery, final Function<Subscription, U> mapper, final Class<?> version, final Consumer<U> makeLinks) {
		final Subscription subscription = subscriptionService.save(subscriptionsPostQuery, version, ApiVersionType.SOL005_VNFPKGM);
		final U pkgmSubscription = mapper.apply(subscription);
		makeLinks.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		subscriptionService.delete(getSafeUUID(subscriptionId), ApiVersionType.SOL005_VNFPKGM);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Function<Subscription, U> func, final Consumer<U> makeLinks) {
		final Subscription subscription = subscriptionService.findById(getSafeUUID(subscriptionId), ApiVersionType.SOL005_VNFPKGM);
		final U pkgmSubscription = func.apply(subscription);
		makeLinks.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.OK);
	}

	@Override
	public void vnfPackageChangeNotificationPost(final VnfPackageChangeNotification notificationsMessage) {
        vnfSubscriptionManagement.vnfPackageChangeNotificationPost(notificationsMessage);
	}

	@Override
	public void vnfPackageOnboardingNotificationPost(final VnfPackageOnboardingNotification notificationsMessage) {
        vnfSubscriptionManagement.vnfPackageOnboardingNotificationPost(notificationsMessage);
	}

}
