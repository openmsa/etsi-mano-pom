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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.service.mapping.VnfPackageMapping;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;

@ExtendWith(MockitoExtension.class)
class VnfOnboardingMapperServiceTest {
	@Mock
	private CustomOnboarding cunstomOnboarding;
	private VnfPackageReader vnfPkgReader;
	private final VnfPackageMapping vnfPackageMapping = Mappers.getMapper(VnfPackageMapping.class);

	@Test
	void testName() throws Exception {
		final VnfOnboardingMapperService srv = createService();
		final VnfPackage VnfPackage = new VnfPackage();
		final ProviderData providerData = new ProviderData();
		srv.mapper(vnfPkgReader, VnfPackage, providerData);
		assertTrue(true);
	}

	private VnfOnboardingMapperService createService() {
		return new VnfOnboardingMapperService(List.of(), List.of(), cunstomOnboarding, vnfPackageMapping);
	}

	@Test
	void testWithVisitors() throws Exception {
		final OnboardVisitor ov01 = new TestOnboardVisitor();
		final OnboardingPostProcessorVisitor op01 = new TestOnboardingPostProcessorVisitor();
		final VnfOnboardingMapperService srv = new VnfOnboardingMapperService(List.of(ov01), List.of(op01), cunstomOnboarding, vnfPackageMapping);
		final VnfPackage VnfPackage = new VnfPackage();
		final ProviderData providerData = new ProviderData();
		srv.mapper(vnfPkgReader, VnfPackage, providerData);
		assertTrue(true);
	}

	@Test
	void testVirtualLink() throws Exception {
		final VnfOnboardingMapperService srv = createService();
		final VnfPackage vnfPackage = new VnfPackage();
		final ProviderData providerData = new ProviderData();
		providerData.setVirtualLink5Req("vl05");
		srv.mapper(vnfPkgReader, vnfPackage, providerData);
		final Set<ListKeyPair> vls = vnfPackage.getVirtualLinks();
		assertNotNull(vls);
		assertEquals(1, vls.size());
		final ListKeyPair vl0 = vls.iterator().next();
		assertEquals("vl05", vl0.getValue());
		assertEquals(5, vl0.getIdx());
	}

}
