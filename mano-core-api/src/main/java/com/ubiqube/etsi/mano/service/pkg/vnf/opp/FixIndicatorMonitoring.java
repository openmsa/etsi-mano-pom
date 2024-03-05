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

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.AttributeAssignements;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardingPostProcessorVisitor;

@Service
public class FixIndicatorMonitoring implements OnboardingPostProcessorVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage) {
		final List<String> indicators = vnfPackage.getVnfIndicator().stream().flatMap(x -> x.getIndicators().stream()).distinct().toList();
		checkIndicators(indicators, vnfPackage.getOverloadedAttribute());
		// XXX: We should test attributes, but it's should be at done at tosca level.
	}

	private static void checkIndicators(final List<String> indicators, final List<AttributeAssignements> list) {
		final List<String> res = indicators.stream()
				.filter(x -> notIn(list, x))
				.toList();
		if (res.isEmpty()) {
			return;
		}
		throw new GenericException("Unable to find attribute for the following indicator element: " + res);
	}

	private static boolean notIn(final List<AttributeAssignements> list, final String toMatch) {
		return list.stream()
				.map(AttributeAssignements::getName)
				.noneMatch(x -> x.equals(toMatch));
	}

}
