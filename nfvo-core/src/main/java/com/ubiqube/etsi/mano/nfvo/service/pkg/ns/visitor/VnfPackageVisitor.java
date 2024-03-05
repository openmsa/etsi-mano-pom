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

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.dto.NsVnf;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.ExternalPortRecord;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.PackageVersion;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import jakarta.annotation.Nullable;
import jakarta.annotation.Priority;

/**
 *
 * @author olivier.vignaud
 *
 */
@Priority(50)
@Service
public class VnfPackageVisitor implements NsOnboardingVisitor {
	private final VnfPackageService vnfPackageService;

	public VnfPackageVisitor(final VnfPackageService vnfPackageService) {
		this.vnfPackageService = vnfPackageService;
	}

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final Set<NsdPackageVnfPackage> vnfds = packageProvider.getVnfd(userData).stream()
				.map(x -> {
					final Optional<VnfPackage> optPackage = getVnfPackage(x.getVnfdId());
					final VnfPackage vnfPackage = optPackage.orElseThrow(() -> new NotFoundException("Vnfd descriptor_id not found: " + x.getVnfdId()));
					final NsdPackageVnfPackage nsdPackageVnfPackage = new NsdPackageVnfPackage();
					mapVirtualLinks(nsdPackageVnfPackage, x);
					final Set<ExternalPortRecord> nets = getNetworks(vnfPackage);
					nets.addAll(getVl(nsdPackageVnfPackage));
					nsdPackageVnfPackage.setNets(nets);
					nsdPackageVnfPackage.setVnfdId(vnfPackage.getVnfdId());
					nsdPackageVnfPackage.setNsdPackage(nsPackage);
					nsdPackageVnfPackage.setToscaName(x.getName());
					nsdPackageVnfPackage.setVnfPackage(vnfPackage);
					vnfPackage.addNsdPackage(nsdPackageVnfPackage);
					vnfPackageService.save(vnfPackage);
					return nsdPackageVnfPackage;
				})
				.collect(Collectors.toSet());
		nsPackage.setVnfPkgIds(vnfds);
	}

	private static Set<ExternalPortRecord> getVl(final NsdPackageVnfPackage nsPackageVnfPackage) {
		return nsPackageVnfPackage.getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.map(x -> new ExternalPortRecord(x.getValue(), getVlName(x), null))
				.collect(Collectors.toSet());
	}

	private static String getVlName(final ListKeyPair x) {
		if (x.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + x.getIdx();
	}

	private static Set<ExternalPortRecord> getNetworks(final VnfPackage vnfPackage) {
		final Set<ExternalPortRecord> ret = new HashSet<>();
		final Set<ExternalPortRecord> n = vnfPackage.getVnfCompute().stream()
				.flatMap(y -> y.getNetworks().stream())
				.map(y -> new ExternalPortRecord(y, null, null))
				.collect(Collectors.toSet());
		ret.addAll(n);
		return ret;
	}

	private static void mapVirtualLinks(final NsdPackageVnfPackage vnfPackage, final NsVnf nsVnf) {
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink1());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink2());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink3());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink4());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink5());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink6());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink7());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink8());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink9());
		vnfPackage.addVirtualLink(nsVnf.getVirtualLink10());
		final Set<ListKeyPair> newList = vnfPackage.getVirtualLinks().stream().filter(y -> y.getValue() != null).collect(Collectors.toSet());
		vnfPackage.setVirtualLinks(newList);
	}

	private Optional<VnfPackage> getVnfPackage(final @Nullable String flavor, final @Nullable String descriptorId, final @Nullable String version) {
		int part = 0;
		if (flavor != null) {
			part++;
		}
		if (descriptorId != null) {
			part++;
		}
		if (version != null) {
			part++;
		}
		if (part == 0) {
			return Optional.empty();
		}
		if (part == 1) {
			return vnfPackageService.findByVnfdId(descriptorId);
		}
		if (part == 2) {
			return vnfPackageService.findByVnfdIdAndSoftwareVersion(descriptorId, version);
		}
		if (part == 3) {
			return vnfPackageService.findByVnfdIdFlavorIdVnfdVersion(descriptorId, flavor, version);
		}
		throw new GenericException("Unknown version " + part);
	}

	private Optional<VnfPackage> getVnfPackage(final String x) {
		final PackageVersion pv = new PackageVersion(x);
		return getVnfPackage(pv.getFlavorId(), pv.getName(), pv.getVersion());
	}
}
