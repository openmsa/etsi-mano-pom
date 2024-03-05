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
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import jakarta.annotation.Priority;

/**
 *
 * @author olivier.vignaud
 *
 */
@Priority(50)
@Service
public class NesteedNsdPackageVisitor implements NsOnboardingVisitor {
	private final NsdPackageJpa nsdPackageJpa;

	public NesteedNsdPackageVisitor(final NsdPackageJpa nsdPackageJpa) {
		this.nsdPackageJpa = nsdPackageJpa;
	}

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final Set<NsdPackageNsdPackage> nsds = packageProvider.getNestedNsd(userData).stream()
				.map(x -> {
					final NsdPackage nestedNsd = nsdPackageJpa.findByNsdInvariantId(x.getNsdId()).orElseThrow(() -> new NotFoundException("Nsd invariant_id not found: " + x.getNsdId()));
					final NsdPackageNsdPackage nsdnsd = new NsdPackageNsdPackage();
					nsdnsd.setParent(nsPackage);
					nsdnsd.setChild(nestedNsd);
					nsdnsd.setToscaName(x.getName());
					nsdnsd.addVirtualLink(x.getVirtulaLink());
					return nsdnsd;
				})
				.collect(Collectors.toSet());
		nsPackage.setNestedNsdInfoIds(nsds);
	}

}
