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
package com.ubiqube.etsi.mano.nfvo.v271.services;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.ubiqube.etsi.mano.controller.FrontApiTypesEnum;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.Link;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.NotificationLink;
import com.ubiqube.etsi.mano.model.v271.sol003.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.model.v271.sol003.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.model.v271.sol003.vnf.VnfPkgInfoLinks;
import com.ubiqube.etsi.mano.nfvo.v271.controller.vnf.VnfPackages271Sol003Api;
import com.ubiqube.etsi.mano.nfvo.v271.controller.vnf.VnfPackages271Sol005Api;
import com.ubiqube.etsi.mano.nfvo.v271.controller.vnf.VnfSubscriptions271Sol005Api;
import com.ubiqube.etsi.mano.nfvo.v271.model.vnf.PkgmLinks;

public class Sol003Linkable implements Linkable {

	@Override
	public VnfPkgInfoLinks getVnfLinks(final String vnfPkgId) {
		final VnfPkgInfoLinks links = new VnfPkgInfoLinks();

		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfPackages271Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId, null)).withSelfRel().getHref());
		links.self(self);

		final Link vnfd = new Link();
		vnfd.setHref(linkTo(methodOn(VnfPackages271Sol003Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfPkgId, null, null)).withSelfRel().getHref());
		links.setVnfd(vnfd);

		final Link packageContent = new Link();
		packageContent.setHref(linkTo(methodOn(VnfPackages271Sol003Api.class).vnfPackagesVnfPkgIdPackageContentGet(vnfPkgId, null)).withSelfRel().getHref());
		links.setPackageContent(packageContent);
		return links;
	}

	@Override
	public PkgmSubscriptionLinks createSubscriptionsPkgmSubscriptionLinks(final String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FrontApiTypesEnum getApi() {
		return FrontApiTypesEnum.SOL003;
	}

	@Override
	public void makeLinks(final VnfPkgInfo vnfPkgInfo) {
		vnfPkgInfo.setLinks(getVnfLinks(vnfPkgInfo.getId()));
	}

	@Override
	public String getSelfLink(final VnfPkgInfo vnfPkgInfo) {
		return linkTo(methodOn(VnfPackages271Sol003Api.class).vnfPackagesVnfPkgIdGet(vnfPkgInfo.getId(), null)).withSelfRel().getHref();
	}

	@Override
	public PkgmLinks createVnfPackageOnboardingNotificationLinks(final UUID vnfPkgId, final String vnfdId, final UUID subscriptionId) {
		final PkgmLinks ret = new PkgmLinks();
		final NotificationLink subscription = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfSubscriptions271Sol005Api.class).subscriptionsSubscriptionIdGet(subscriptionId.toString())).withSelfRel().getHref());
		ret.setSubscription(subscription);

		final NotificationLink vnfPackage = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackages271Sol005Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId.toString())).withSelfRel().getHref());
		ret.setVnfPackage(vnfPackage);

		final NotificationLink vnfVnfdId = createVnfPackagesVnfPkgInfoLinksSelf(
				linkTo(methodOn(VnfPackages271Sol005Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfdId, null, null)).withSelfRel().getHref());
		ret.setVnfPackageByVnfdId(vnfVnfdId);
		return ret;
	}

	private static NotificationLink createVnfPackagesVnfPkgInfoLinksSelf(final String _href) {
		final NotificationLink link = new NotificationLink();
		link.setHref(_href);
		return link;
	}
}
