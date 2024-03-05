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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class RemapNetworksVisitor implements OnboardingPostProcessorVisitor {

	private static final Logger LOG = LoggerFactory.getLogger(RemapNetworksVisitor.class);

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Set<VnfCompute> cNodes = vnfPackage.getVnfCompute();
		final Set<VnfLinkPort> vcNodes = vnfPackage.getVnfLinkPort();
		cNodes.forEach(x -> {
			final Set<VnfLinkPort> nodes = filter(vcNodes, x.getToscaName());
			if (nodes.isEmpty()) {
				LOG.warn("Node {} have no network.", x.getToscaName());
			}
			x.setNetworks(nodes.stream().map(VnfLinkPort::getVirtualLink).collect(Collectors.toSet()));
			x.setPorts(nodes);
		});
	}

	private static Set<VnfLinkPort> filter(final Set<VnfLinkPort> vcNodes, final String toscaName) {
		return vcNodes.stream()
				.filter(x -> x.getVirtualBinding().equals(toscaName))
				.collect(Collectors.toSet());
	}

}
