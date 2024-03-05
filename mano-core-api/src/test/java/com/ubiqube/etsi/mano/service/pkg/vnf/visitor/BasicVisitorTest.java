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

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.TestVnfPackageReader;

@SuppressWarnings("static-method")
class BasicVisitorTest {

	@ParameterizedTest
	@MethodSource("providerTest")
	void testName(final OnboardVisitor v) throws Exception {
		final VnfPackage vnfPackage = new VnfPackage();
		final TestVnfPackageReader vnfReader = new TestVnfPackageReader();
		v.visit(vnfPackage, vnfReader, Map.of());
		assertTrue(true);
	}

	private static Stream<Arguments> providerTest() {
		return Stream.of(
				Arguments.of(new ComputeVisitor()),
				Arguments.of(new LinkPortVisitor()),
				Arguments.of(new StorageVisitor()),
				Arguments.of(new VirtualLinkVisitor()),
				Arguments.of(new VnfExtCpVisitor()),
				Arguments.of(new VnfIndicatorVisitor()),
				Arguments.of(new AdditionalArtefactsVisitor()));
	}
}
