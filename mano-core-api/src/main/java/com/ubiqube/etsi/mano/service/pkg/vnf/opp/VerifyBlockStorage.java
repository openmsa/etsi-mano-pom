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

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

/**
 *
 * @author olivier
 *
 */
@Service
public class VerifyBlockStorage implements OnboardingPostProcessorVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final Set<VnfCompute> comp = vnfPackage.getVnfCompute();
		final Set<String> l = comp.stream()
				.map(VnfCompute::getStorages)
				.filter(Objects::nonNull)
				.flatMap(Set::stream)
				.filter(x -> !isFindable(x, vnfPackage.getVnfStorage()))
				.map(x -> "Compute ref: " + x)
				.collect(Collectors.toSet());
		if (!l.isEmpty()) {
			throw new GenericException("The following block storage are not found: " + l);
		}
	}

	private static boolean isFindable(final String toscaName, final Set<VnfStorage> vnfStorage) {
		return vnfStorage.stream().anyMatch(x -> x.getToscaName().equals(toscaName));
	}

}
