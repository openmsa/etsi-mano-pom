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

package com.ubiqube.etsi.mano.nfvo.v261.services;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.ubiqube.etsi.mano.common.v261.model.Link;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfIndicatorLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfIndicatorValueChangeNotificationLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfPkgInfoLinks;
import com.ubiqube.etsi.mano.common.v261.services.Linkable;
import com.ubiqube.etsi.mano.nfvo.v261.controller.vnf.VnfPackage261Sol003Api;
import com.ubiqube.etsi.mano.nfvo.v261.controller.vnf.VnfSubscription261Sol003Api;

public class Sol003Linkable implements Linkable {

	public static VnfPkgInfoLinks getVnfLinks(final String vnfPkgId) {
		final VnfPkgInfoLinks links = new VnfPkgInfoLinks();

		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId)).withSelfRel().getHref());
		links.self(self);

		final Link vnfd = new Link();
		vnfd.setHref(linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfPkgId, null)).withSelfRel().getHref());
		links.setVnfd(vnfd);

		final Link packageContent = new Link();
		packageContent.setHref(linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdPackageContentGet(vnfPkgId)).withSelfRel().getHref());
		links.setPackageContent(packageContent);
		return links;
	}

	@Override
	public PkgmLinks createVnfPackageOnboardingNotificationLinks(final UUID vnfPkgId, final String vnfdId, final UUID subscriptionId) {
		final PkgmLinks ret = new PkgmLinks();
		final Link subscription = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfSubscription261Sol003Api.class).subscriptionsSubscriptionIdGet(subscriptionId.toString())).withSelfRel().getHref());
		ret.setSubscription(subscription);

		final Link vnfPackage = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId.toString())).withSelfRel().getHref());
		ret.setVnfPackage(vnfPackage);

		final Link vnfVnfdId = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfdId, null)).withSelfRel().getHref());
		ret.setVnfPackageByVnfdId(vnfVnfdId);
		return ret;
	}
	
	@Override
	public VnfIndicatorValueChangeNotificationLinks createVnfIndicatorValueChangeNotificationLinks(final String vnfIndicatorId, final String vnfInstanceId, final UUID subscriptionId) {
		final VnfIndicatorValueChangeNotificationLinks ret = new VnfIndicatorValueChangeNotificationLinks();
		return ret;
	}
	
	private static Link createVnfIndicatorInfoLinksSelf(final String href) {
		final Link link = new Link();
		link.setHref(href);
		return link;
	}

	private static Link createVnfPackagesVnfPkgInfoLinksSelf(final String href) {
		final Link link = new Link();
		link.setHref(href);
		return link;
	}

	@Override
	public PkgmSubscriptionLinks createSubscriptionsPkgmSubscriptionLinks(final String _subscriptionId) {
		final PkgmSubscriptionLinks subscriptionsPkgmSubscriptionLinks = new PkgmSubscriptionLinks();
		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfSubscription261Sol003Api.class).subscriptionsSubscriptionIdGet(_subscriptionId)).withSelfRel().getHref());
		subscriptionsPkgmSubscriptionLinks.setSelf(self);
		return subscriptionsPkgmSubscriptionLinks;
	}

	@Override
	public void makeSubscriptionLink(final PkgmSubscription pkgmSubscription) {
		pkgmSubscription.setLinks(createSubscriptionsPkgmSubscriptionLinks(pkgmSubscription.getId()));
	}

	@Override
	public void makeLinks(final VnfPkgInfo vnfPkgInfo) {
		vnfPkgInfo.setLinks(getVnfLinks(vnfPkgInfo.getId()));
	}

	@Override
	public String getSelfLink(final VnfPkgInfo vnfPkgInfo) {
		return linkTo(methodOn(VnfPackage261Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgInfo.getId())).withSelfRel().getHref();
	}
}
