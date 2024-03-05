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

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class FixExternalPointVisitor implements OnboardingPostProcessorVisitor {
	private static final String VIRTUAL_LINK = "virtual_link";
	private static final Pattern pVl = Pattern.compile("virtual_link_(?<idx>\\d+)");

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Set<VnfExtCp> vnfExtCp = vnfPackage.getVnfExtCp();
		vnfExtCp.forEach(x -> {
			if (isComputeNode(vnfPackage, x.getInternalVirtualLink())) {
				x.setComputeNode(true);
			}
			handleExtCp(vnfPackage, x);
		});
	}

	private static void handleExtCp(final VnfPackage vnfPackage, final VnfExtCp extCp) {
		if (null == extCp.getExternalVirtualLink()) {
			// Point on first used virtual_link
			extCp.setExternalVirtualLink(VIRTUAL_LINK);
		}
		if (extCp.getExternalVirtualLink().startsWith(VIRTUAL_LINK)) {
			final int vl = toscaNameToVlId(extCp.getExternalVirtualLink());
			final Optional<ListKeyPair> res = vnfPackage.getVirtualLinks().stream().filter(y -> y.getIdx() == vl).findFirst();
			if (res.isEmpty()) {
				throw new GenericException("Unable to find VL on extCp: " + extCp.getToscaName() + " => " + extCp.getExternalVirtualLink());
			}
			extCp.setExternalVirtualLink(res.get().getValue());
		}
	}

	private static boolean isComputeNode(final VnfPackage vnfPackage, final String internalVirtualLink) {
		final Optional<VnfCompute> res = vnfPackage.getVnfCompute().stream().filter(x -> x.getToscaName().equals(internalVirtualLink)).findFirst();
		return res.isPresent();
	}

	private static int toscaNameToVlId(final String cpdId) {
		if (VIRTUAL_LINK.equals(cpdId)) {
			return 0;
		}
		final Matcher m = pVl.matcher(cpdId);
		if (!m.matches()) {
			throw new GenericException("Unable to match 'virtual_link_' in " + cpdId);
		}
		return Integer.parseInt(m.group("idx"));
	}

}
