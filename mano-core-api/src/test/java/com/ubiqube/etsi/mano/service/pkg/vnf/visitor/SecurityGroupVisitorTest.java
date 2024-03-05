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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.SecurityGroup;
import com.ubiqube.etsi.mano.service.pkg.bean.SecurityGroupAdapter;
import com.ubiqube.etsi.mano.service.pkg.vnf.TestVnfPackageReader;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class SecurityGroupVisitorTest {
	@Mock
	private MapperFacade mapper;

	@Test
	void testName() throws Exception {
		final SecurityGroupVisitor sgv = new SecurityGroupVisitor(mapper);
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		sgv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	@Test
	void testSg01() throws Exception {
		final SecurityGroupVisitor sgv = new SecurityGroupVisitor(mapper);
		final VnfPackage vnfPackage = new VnfPackage();
		final VnfCompute comp01 = new VnfCompute();
		comp01.setToscaName("compute");
		vnfPackage.setVnfCompute(Set.of(comp01));
		final VnfExtCp ext01 = new VnfExtCp();
		ext01.setToscaName("compute");
		vnfPackage.setVnfExtCp(Set.of(ext01));
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		final SecurityGroup sg01 = new SecurityGroup();
		final SecurityGroupAdapter securityGroup01 = new SecurityGroupAdapter(sg01, List.of("compute"));
		vnfReader.setSecurityGroup(Set.of(securityGroup01));
		sgv.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}
}
