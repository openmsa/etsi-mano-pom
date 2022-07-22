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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.opp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

@Service
public class VerifyDependencies implements NsOnboardingPostProcessor {
	private final NsdPackageJpa nsdPackageJpa;

	public VerifyDependencies(final NsdPackageJpa nsdPackageJpa) {
		super();
		this.nsdPackageJpa = nsdPackageJpa;
	}

	@Override
	public void visit(final NsdPackage nsPackage) {
		verifyCircularDependencies(nsPackage, new ArrayDeque<>());
	}

	private void verifyCircularDependencies(final NsdPackage nsPackage, final Deque<UUID> stack) {
		stack.push(nsPackage.getId());
		nsPackage.getNestedNsdInfoIds().forEach(x -> {
			if (stack.contains(x.getId())) {
				throw new GenericException("Circular dependency detected, trying to include " + x.getId() + ", chain: " + stack);
			}
			final NsdPackage p = nsdPackageJpa.findById(x.getChild().getId()).orElseThrow();
			verifyCircularDependencies(p, stack);
		});
		stack.pop();
	}

}
