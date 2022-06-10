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
package com.ubiqube.etsi.mano.service.pkg.vnf.opp;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class FixExternalPointVisitor implements OnboardingPostProcessorVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Set<VnfExtCp> vnfExtCp = vnfPackage.getVnfExtCp();
		vnfExtCp.forEach(x -> {
			if (isComputeNode(vnfPackage, x.getInternalVirtualLink())) {
				x.setComputeNode(true);
			}
		});
	}

	private static boolean isComputeNode(final VnfPackage vnfPackage, final String internalVirtualLink) {
		final Optional<VnfCompute> res = vnfPackage.getVnfCompute().stream().filter(x -> x.getToscaName().equals(internalVirtualLink)).findFirst();
		return res.isPresent();
	}

}
