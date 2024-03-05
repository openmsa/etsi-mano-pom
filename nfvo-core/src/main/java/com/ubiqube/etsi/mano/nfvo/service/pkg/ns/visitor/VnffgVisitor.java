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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import jakarta.annotation.Nullable;
import jakarta.annotation.Priority;

/**
 *
 * @author olivier.vignaud
 *
 */
@Priority(200)
@Service
public class VnffgVisitor implements NsOnboardingVisitor {

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final Set<VnffgDescriptor> vnffg = packageProvider.getVnffg(userData);
		setLogicalPorts(vnffg);
		nsPackage.setVnffgs(vnffg);
		rebuildConnectivity(vnffg, nsPackage);
	}

	private static void setLogicalPorts(final Set<VnffgDescriptor> vnffg) {
		vnffg.forEach(x -> {
			final Classifier cla = x.getClassifier();
			cla.setLogicalSourcePort(getLogicalSourcePort(x));
			cla.setLogicalDestinationPort(getLogicalDestinationPort(x));
		});
	}

	private static @Nullable String getLogicalDestinationPort(final VnffgDescriptor vnffg) {
		if (vnffg.getNfpd().isEmpty()) {
			return null;
		}
		final List<NfpDescriptor> nfpds = vnffg.getNfpd();
		final List<VnffgInstance> insts = nfpds.get(nfpds.size() - 1).getInstances();
		if (insts.isEmpty()) {
			return null;
		}
		final List<CpPair> pairs = insts.get(insts.size() - 1).getPairs();
		if (pairs.isEmpty()) {
			return null;
		}
		final CpPair p = pairs.get(pairs.size() - 1);
		if (p.getEgress() != null) {
			return p.getEgress();
		}
		return p.getIngress();
	}

	private static @Nullable String getLogicalSourcePort(final VnffgDescriptor vnffg) {
		if (vnffg.getNfpd().isEmpty()) {
			return null;
		}
		final List<VnffgInstance> insts = vnffg.getNfpd().get(0).getInstances();
		if (insts.isEmpty()) {
			return null;
		}
		final List<CpPair> pairs = insts.get(0).getPairs();
		if (pairs.isEmpty()) {
			return null;
		}
		return pairs.get(0).getIngress();
	}

	private static void rebuildConnectivity(final Set<VnffgDescriptor> vnffg, final NsdPackage nsPackage) {
		vnffg.stream().flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream())
				.forEach(x -> {
					if (null != x.getEgressVl()) {
						findVnfMatchingVl(nsPackage, x.getEgress(), x.getEgressVl());
					}
					if (null != x.getIngressVl()) {
						findVnfMatchingVl(nsPackage, x.getIngress(), x.getIngressVl());
					}
				});
		nsPackage.getVnfPkgIds().forEach(x -> x.getVirtualLinks().stream().filter(y -> Objects.nonNull(y.getValue())).forEach(ListKeyPair::getValue));
	}

	private static void findVnfMatchingVl(final NsdPackage pack, final String forwardName, final String vlName) {
		pack.getVnfPkgIds().stream().forEach(x -> x.getVirtualLinks().stream()
				.filter(y -> Objects.nonNull(y.getValue()))
				.filter(y -> y.getValue().equals(forwardName))
				.findFirst()
				.ifPresent(y -> x.addForwardMapping(new ForwarderMapping(x.getToscaName(), y.getIdx(), forwardName, vlName, getVlName(y)))));
	}

	private static String getVlName(final ListKeyPair x) {
		if (x.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + x.getIdx();
	}

}
