/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.parser.tosca;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NS;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NsVirtualLink;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.Sap;

class NsdTest extends AbstractToscaApiTest {

	@Override
	protected void prepareArchive() {
		StaticTestTools.createNsdPackage();
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testGeneric(final int num, final Class<?> clazz) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException, IntrospectionException {
		runTest(num, clazz);
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(2, NS.class),
				Arguments.of(1, NsVirtualLink.class),
				Arguments.of(1, Sap.class));
		// 2.7.+ Arguments.of(1, NFP.class),
		// 2.7.+ Arguments.of(1, NfpRule.class),
		// 2.7.+ Arguments.of(1, NfpPosition.class),
		// 2.7.° Arguments.of(2, NfpPositionElement.class));
	}

	@Override
	protected List<String> getIgnoreList() {
		final List<String> ignore = new ArrayList<>();
		ignore.add("getInternalDescription");
		ignore.add("getInternalName");
		ignore.add("getArtifacts");
		ignore.add("getTriggers");
		ignore.add("getTargets");
		ignore.add("getVirtualLinkable");// Should be removed, it's a bug.
		// Nslcm
		ignore.add("getInstantiateEnd");
		ignore.add("getScaleEnd");
		ignore.add("getUpdateStart");
		ignore.add("getUpdateEnd");
		ignore.add("getUpdate");
		ignore.add("getInstantiateStart");
		ignore.add("getHealEnd");
		ignore.add("getScale");
		ignore.add("getHealStart");
		ignore.add("getTerminateEnd");
		ignore.add("getHeal");
		ignore.add("getTerminate");
		ignore.add("getTerminateStart");
		ignore.add("getScaleStart");
		// 2.6.1
		ignore.add("getInstantiate");
		ignore.add("getServiceAvailabilityLevel");
		// NS 4.2.1
		ignore.add("getScaleStatus");
		ignore.add("getPriority");
		// NS VL 4.2.1
		ignore.add("getVirtualLinkProtocolData");
		ignore.add("getFixedIpAddress");
		ignore.add("getIpAddressAssignmentSubtype");

		return ignore;
	}

}
