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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.VimCapability;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VimCapabilitiesVisitor implements OnboardingPostProcessorVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Set<VimCapability> vimCapabilities = new HashSet<>();
		vnfPackage.setVimCapabilities(vimCapabilities);
		addIfNeeded(VimCapability.HAVE_NET_MTU, vimCapabilities, vnfPackage, x -> x.getL2ProtocolData().getMtu(), Objects::nonNull);
		addIfNeeded(VimCapability.HAVE_VLAN_TRANSPARENT, vimCapabilities, vnfPackage, x -> x.getL2ProtocolData().getVlanTransparent(), Boolean::booleanValue);
		addIfNeeded(VimCapability.HAVE_VXNET, vimCapabilities, vnfPackage, x -> x.getL2ProtocolData().getNetworkType(), x -> x.equalsIgnoreCase("vxlan"));
	}

	private static <U> void addIfNeeded(final VimCapability vc, final Set<VimCapability> svc, final VnfPackage vnfPackage, final Function<VlProtocolData, U> tr, final Predicate<U> p) {
		vnfPackage.getVnfVl().stream()
				.filter(x -> x.getVlProfileEntity().getVirtualLinkProtocolData() != null)
				.flatMap(x -> x.getVlProfileEntity().getVirtualLinkProtocolData().stream())
				.map(tr)
				.filter(Objects::nonNull)
				.filter(p)
				.findFirst()
				.ifPresent(x -> svc.add(vc));
	}

}
