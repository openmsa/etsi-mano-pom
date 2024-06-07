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
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsVlLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsVlStepMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.StepMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VlBitRate;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.ToscaException;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.NsScaling;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.RootLeaf;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import jakarta.annotation.Priority;

/**
 *
 * @author olivier
 *
 */
@Priority(100)
@Service
public class NsScalingVisitor implements NsOnboardingVisitor {

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final NsScaling nsScaling = packageProvider.getNsScaling(userData);
		remapScaling(nsPackage, nsScaling);
	}

	private static void remapScaling(final NsdPackage nsPackage, final NsScaling nsScaling) {
		nsScaling.getNsStepMapping().forEach(x -> x.target().forEach(y -> {
			final NsdPackageNsdPackage nsd = findNesteedNs(nsPackage, y);
			final NsScalingStepMapping scaling = new NsScalingStepMapping(mapStepMapping(x.mapping()), x.aspectId());
			x.mapping().forEach((key, value) -> nsd.addStepMapping(scaling));
		}));
		nsScaling.getVnfStepMapping().forEach(x -> x.target().forEach(y -> {
			final NsdPackageVnfPackage nsd = findVnf(nsPackage, y);
			final VnfScalingStepMapping scaling = new VnfScalingStepMapping(mapStepMapping(x.mapping()), x.aspectId());
			x.mapping().forEach((key, value) -> nsd.addStepMapping(scaling));
		}));
		nsScaling.getVlStepMapping().forEach(x -> x.targets().forEach(y -> {
			final NsVirtualLink vl = findVl(nsPackage, y);
			final NsVlStepMapping mapping = new NsVlStepMapping(mapVlStep(x.mapping()), x.aspectId());
			vl.addStepMapping(mapping);
		}));

		nsScaling.getNsLevelMapping().forEach(x -> x.target().forEach(y -> {
			final NsdPackageNsdPackage nsd = findNesteedNs(nsPackage, y);
			x.mapping().forEach((key, value) -> {
                final NsScalingLevelMapping mapping =
                        new NsScalingLevelMapping(key, x.aspectId(), value);
                nsd.addLevelMapping(mapping);
            });
		}));
		nsScaling.getVnfLevelMapping().forEach(x -> x.target().forEach(y -> {
			final NsdPackageVnfPackage nsd = findVnf(nsPackage, y);
			x.mapping().forEach((key, value) -> {
                final VnfScalingLevelMapping mapping =
                        new VnfScalingLevelMapping(key, x.aspectId(), value);
                nsd.addLevelMapping(mapping);
            });
		}));
		nsScaling.getVlLevelMapping().forEach(x -> x.targets().forEach(y -> {
			final NsVirtualLink vl = findVl(nsPackage, y);
			x.mapping().forEach((key, value) -> {
                final NsVlLevelMapping mapping =
                        new NsVlLevelMapping(key, value.root(), value.leaf());
                vl.addLevelMapping(mapping);
            });
		}));
	}

	private static Set<StepMapping> mapStepMapping(final Map<Integer, Integer> mapping) {
		return mapping.entrySet().stream().map(x -> new StepMapping(x.getKey(), x.getValue())).collect(Collectors.toSet());
	}

	private static Set<VlBitRate> mapVlStep(final Map<Integer, RootLeaf> mapping) {
		return mapping.entrySet().stream()
				.map(x -> new VlBitRate(null, x.getKey(), x.getValue().root(), x.getValue().leaf()))
				.collect(Collectors.toSet());
	}

	private static NsdPackageVnfPackage findVnf(final NsdPackage nsPackage, final String y) {
		return nsPackage.getVnfPkgIds().stream().filter(x -> x.getToscaName().equals(y)).findAny().orElseThrow(() -> new GenericException("Could not find a VNFD named: " + y));
	}

	private static NsdPackageNsdPackage findNesteedNs(final NsdPackage nsPackage, final String y) {
		return nsPackage.getNestedNsdInfoIds().stream().filter(x -> x.getToscaName().equals(y)).findAny().orElseThrow(() -> new GenericException("Could not find a NSD named: " + y));
	}

	private static NsVirtualLink findVl(final NsdPackage nsPackage, final String virtualLinkId) {
		return nsPackage.getNsVirtualLinks().stream().filter(x -> x.getToscaName().equals(virtualLinkId)).findFirst().orElseThrow(() -> new ToscaException("Could not find VL named: " + virtualLinkId));
	}
}
