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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;
import com.ubiqube.etsi.mano.service.pkg.vnf.CustomOnboarding;

@ExtendWith(MockitoExtension.class)
class NsOnboardingMapperServiceTest {
	@Mock
	private CustomOnboarding customOnboarding;
	@Mock
	private NsPackageProvider packageReader;
	@Mock
	private NsOnboardingVisitor visitor;
	@Mock
	private NsOnboardingPostProcessor post;

	@Test
	void testName() throws Exception {
		final List<NsOnboardingVisitor> visitors = List.of();
		final List<NsOnboardingPostProcessor> postProcess = List.of();
		final NsOnboardingMapperService srv = new NsOnboardingMapperService(visitors, postProcess, customOnboarding);
		final NsdPackage nsdPackage = new NsdPackage();
		srv.mapper(packageReader, nsdPackage);
		assertTrue(true);
	}

	@Test
	void testMapper() throws Exception {
		final List<NsOnboardingVisitor> visitors = List.of(visitor);
		final List<NsOnboardingPostProcessor> postProcess = List.of(post);
		final NsOnboardingMapperService srv = new NsOnboardingMapperService(visitors, postProcess, customOnboarding);
		final NsdPackage nsdPackage = new NsdPackage();
		srv.mapper(packageReader, nsdPackage);
		assertTrue(true);
	}
}
