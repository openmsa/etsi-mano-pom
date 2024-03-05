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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.visitor;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import jakarta.annotation.Priority;

/**
 *
 * @author olivier
 *
 */
@Priority(0)
@Service
public class NsVirtualLinkVisitor implements NsOnboardingVisitor {

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final Set<NsVirtualLink> nsVirtualLink = packageProvider.getNsVirtualLink(userData);
		nsVirtualLink.forEach(x -> x.getNsVlProfile().getVlProtocolData().forEach(y -> y.getL3ProtocolData().setDhcpEnabled(Boolean.TRUE)));
		nsPackage.setNsVirtualLinks(nsVirtualLink);

	}

}
