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
package com.ubiqube.etsi.mano.nfvo.v331;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.ubiqube.etsi.mano.c331.services.Linkable;
import com.ubiqube.etsi.mano.controller.FrontApiTypesEnum;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.NotificationLink;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.PkgmLinks;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPkgInfoLinks;
import com.ubiqube.etsi.mano.vnfm.v331.controller.vnf.VnfPackages331Sol003Api;
import com.ubiqube.etsi.mano.vnfm.v331.controller.vnf.VnfSubscriptions331Sol003Api;

public class Sol003Linkable implements Linkable {

	@Override
	public VnfPkgInfoLinks getVnfLinks(final String vnfPkgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmSubscriptionLinks createSubscriptionsPkgmSubscriptionLinks(final String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FrontApiTypesEnum getApi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeLinks(final VnfPkgInfo vnfPkgInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSelfLink(final VnfPkgInfo vnfPkgInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmLinks createVnfPackageOnboardingNotificationLinks(final UUID vnfPkgId, final String vnfdId, final UUID subscriptionId) {
		final PkgmLinks ret = new PkgmLinks();
		final NotificationLink subscription = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfSubscriptions331Sol003Api.class).subscriptionsSubscriptionIdGet(subscriptionId.toString())).withSelfRel().getHref());
		ret.setSubscription(subscription);

		final NotificationLink vnfPackage = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackages331Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId.toString(), null)).withSelfRel().getHref());
		ret.setVnfPackage(vnfPackage);

		final NotificationLink vnfVnfdId = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackages331Sol003Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfdId, null, null)).withSelfRel().getHref());
		ret.setVnfPackageByVnfdId(vnfVnfdId);
		return ret;
	}

	private static NotificationLink createVnfPackagesVnfPkgInfoLinksSelf(final String _href) {
		final NotificationLink link = new NotificationLink();
		link.setHref(_href);
		return link;
	}

}
