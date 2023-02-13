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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NFP;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NS;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NfpPosition;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NfpPositionElement;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.NsVirtualLink;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.Sap;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NfpRule;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NsAutoScale;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NsInstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NsScalingAspects;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NsToInstantiationLevelMapping;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.NsToLevelMapping;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VirtualLinkToInstantiationLevelMapping;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VirtualLinkToLevelMapping;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VnfToInstantiationLevelMapping;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VnfToLevelMapping;

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

	@Test
	void TestNfpPosition() throws IllegalArgumentException, InvocationTargetException, IllegalAccessException, IntrospectionException {
		final List<NfpPosition> nfpos = runTest(1, NfpPosition.class);
		assertEquals(2, nfpos.get(0).getElementReq().size());
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(2, NS.class),
				Arguments.of(1, NsVirtualLink.class),
				Arguments.of(1, Sap.class),
				Arguments.of(1, NFP.class),
				Arguments.of(1, NfpRule.class),
				Arguments.of(1, NfpPosition.class),
				Arguments.of(2, NfpPositionElement.class),
				Arguments.of(2, NsAutoScale.class),
				Arguments.of(2, VirtualLinkToInstantiationLevelMapping.class),
				Arguments.of(2, NsToInstantiationLevelMapping.class),
				Arguments.of(2, VnfToInstantiationLevelMapping.class),
				Arguments.of(2, NsInstantiationLevels.class),
				Arguments.of(2, VirtualLinkToLevelMapping.class),
				Arguments.of(2, NsToLevelMapping.class),
				Arguments.of(2, VnfToLevelMapping.class),
				Arguments.of(2, NsScalingAspects.class));
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
		//
		ignore.add("getFixedIpAddress");
		ignore.add("getIpAddressAssignmentSubtype");
		//
		ignore.add("getType");
		return ignore;
	}

}
