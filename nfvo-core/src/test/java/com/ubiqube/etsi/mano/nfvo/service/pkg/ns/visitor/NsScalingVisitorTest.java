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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.LevelMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.NsScaling;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.RootLeaf;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.StepMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.VlLevelMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.nsscaling.VlStepMapping;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class NsScalingVisitorTest {
	@Mock
	private NsPackageProvider packageProvider;

	@Test
	void testVisit_Base() {
		final NsScalingVisitor vis = new NsScalingVisitor();
		final NsdPackage nsPkg = new NsdPackage();
		final NsScaling nsScaling = new NsScaling();
		nsScaling.setNsStepMapping(List.of());
		nsScaling.setVnfStepMapping(List.of());
		nsScaling.setVlStepMapping(List.of());
		nsScaling.setVlLevelMapping(List.of());
		nsScaling.setNsLevelMapping(List.of());
		nsScaling.setVnfLevelMapping(List.of());
		when(packageProvider.getNsScaling(anyMap())).thenReturn(nsScaling);
		vis.visit(nsPkg, packageProvider, Map.of());
		assertTrue(true);
	}

	@Test
	void testVisit() {
		final NsScalingVisitor vis = new NsScalingVisitor();
		final NsdPackage nsPkg = new NsdPackage();
		final NsdPackageNsdPackage pkgs = new NsdPackageNsdPackage();
		pkgs.setToscaName("tgt");
		nsPkg.setNestedNsdInfoIds(Set.of(pkgs));
		final NsScaling nsScaling = new NsScaling();
		final StepMapping sm = new StepMapping("aspect", List.of("tgt"), Map.of(0, 1));
		nsScaling.setNsStepMapping(List.of(sm));
		final NsdPackageVnfPackage vnf00 = new NsdPackageVnfPackage();
		vnf00.setToscaName("tgt");
		nsPkg.setVnfPkgIds(Set.of(vnf00));
		nsScaling.setVnfStepMapping(List.of(sm));
		final VlStepMapping vm0 = new VlStepMapping("aspect", List.of("tgt"), Map.of());
		final NsVirtualLink vl01 = new NsVirtualLink();
		vl01.setToscaName("tgt");
		nsPkg.setNsVirtualLinks(Set.of(vl01));
		nsScaling.setVlStepMapping(List.of(vm0));
		final LevelMapping lm0 = new LevelMapping("aspect", List.of("tgt"), Map.of("0", 1));
		nsScaling.setNsLevelMapping(List.of(lm0));
		nsScaling.setVnfLevelMapping(List.of(lm0));
		final VlLevelMapping vlm0 = new VlLevelMapping(List.of("tgt"), Map.of("", new RootLeaf(0, 0)));
		nsScaling.setVlLevelMapping(List.of(vlm0));
		when(packageProvider.getNsScaling(anyMap())).thenReturn(nsScaling);
		vis.visit(nsPkg, packageProvider, Map.of());
		assertTrue(true);
	}
}
