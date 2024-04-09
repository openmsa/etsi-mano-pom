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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.vim.AffinityRule;
import com.ubiqube.etsi.mano.service.mapping.AffinityRuleMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.AffinityRuleAdapater;
import com.ubiqube.etsi.mano.service.pkg.vnf.TestVnfPackageReader;

@ExtendWith(MockitoExtension.class)
class AffinityRulesVisitorTest {
	AffinityRuleMapping affinityRuleMapping = Mappers.getMapper(AffinityRuleMapping.class);

	@Test
	void testSimple() throws Exception {
		final AffinityRulesVisitor srv = createService();
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		srv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	private AffinityRulesVisitor createService() {
		return new AffinityRulesVisitor(affinityRuleMapping);
	}

	@Test
	void test01() throws Exception {
		final AffinityRulesVisitor srv = createService();
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("compute");
		// comp01.setAffinityRule(new HashSet<>());
		vnfPackage.setVnfCompute(Set.of(comp01));
		final VnfVl ext01 = new VnfVl();
		ext01.setToscaName("compute");
		vnfPackage.setVnfVl(Set.of(ext01));
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		final AffinityRuleAdapater ara01 = new AffinityRuleAdapater();
		final AffinityRule ar01 = new AffinityRule();
		ara01.setAffinityRule(ar01);
		ara01.setTargets(List.of("compute"));
		vnfReader.setAffinityRules(Set.of(ara01));
		srv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}
}
