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
package com.ubiqube.etsi.mano.nfvo.v281;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.ubiqube.etsi.mano.em.v281.model.vnflcm.NotificationLink;
import com.ubiqube.etsi.mano.nfvo.v281.controller.vnf.VnfPackages281Sol005Api;
import com.ubiqube.etsi.mano.nfvo.v281.controller.vnf.VnfSubscriptions281Sol005Api;
import com.ubiqube.etsi.mano.nfvo.v281.model.vnfconfig.PkgmLinks;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class Sol005Linkable {
	public PkgmLinks createNotificationLink(final UUID _vnfPkgId, final UUID _subscriptionId) {
		final PkgmLinks ret = new PkgmLinks();
		final NotificationLink subscription = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfSubscriptions281Sol005Api.class).subscriptionsSubscriptionIdGet(_subscriptionId.toString())).withSelfRel().getHref());
		ret.setSubscription(subscription);

		final NotificationLink vnfPackage = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackages281Sol005Api.class).vnfPackagesVnfPkgIdGet(_vnfPkgId.toString())).withSelfRel().getHref());
		ret.setVnfPackage(vnfPackage);
		return ret;
	}

	private static NotificationLink createVnfPackagesVnfPkgInfoLinksSelf(final String _href) {
		final NotificationLink link = new NotificationLink();
		link.setHref(_href);
		return link;
	}

	public PkgmLinks createVnfPackageOnboardingNotificationLinks(final UUID objectId, final UUID subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
