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

package com.ubiqube.etsi.mano.vnfm.v261.controller.vnffm.sol003;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.ubiqube.etsi.mano.common.v261.model.vnf.NotificationLink;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfIndicatorValueChangeNotificationLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.common.v261.services.Linkable;
import com.ubiqube.etsi.mano.vnfm.v261.controller.vnfind.sol003.VnfIndSubscriptions261Sol003Api;

public class Sol003Linkable implements Linkable {
	
	@Override
	public VnfIndicatorValueChangeNotificationLinks createVnfIndicatorValueChangeNotificationLinks(final String vnfIndicatorId, final String vnfInstanceId, final UUID subscriptionId) {
		final VnfIndicatorValueChangeNotificationLinks ret = new VnfIndicatorValueChangeNotificationLinks();
		final NotificationLink subscription = createVnfIndicatorInfoLinksSelf(
				linkTo(methodOn(VnfIndSubscriptions261Sol003Api.class).subscriptionsSubscriptionIdGet(subscriptionId.toString())).withSelfRel().getHref());
		ret.setSubscription(subscription);
		return ret;
	}
	
	private static NotificationLink createVnfIndicatorInfoLinksSelf(final String href) {
		final NotificationLink link = new NotificationLink();
		link.setHref(href);
		return link;
	}

	@Override
	public void makeLinks(VnfPkgInfo vnfPkgInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelfLink(VnfPkgInfo vnfPkgInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmLinks createVnfPackageOnboardingNotificationLinks(UUID vnfPkgId, String vnfdId, UUID subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmSubscriptionLinks createSubscriptionsPkgmSubscriptionLinks(String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeSubscriptionLink(PkgmSubscription pkgmSubscription) {
		// TODO Auto-generated method stub
		
	}

}
