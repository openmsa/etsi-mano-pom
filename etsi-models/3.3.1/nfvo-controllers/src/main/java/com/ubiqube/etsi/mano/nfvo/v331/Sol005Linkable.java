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

import java.util.UUID;

import com.ubiqube.etsi.mano.c331.services.Linkable;
import com.ubiqube.etsi.mano.controller.FrontApiTypesEnum;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.PkgmLinks;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPkgInfoLinks;

public class Sol005Linkable implements Linkable {

	@Override
	public VnfPkgInfoLinks getVnfLinks(String vnfPkgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmSubscriptionLinks createSubscriptionsPkgmSubscriptionLinks(String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FrontApiTypesEnum getApi() {
		// TODO Auto-generated method stub
		return null;
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
	public PkgmLinks createNotificationLink(UUID vnfPkgId, UUID subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PkgmLinks createVnfPackageOnboardingNotificationLinks(UUID vnfPkgId, UUID subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
