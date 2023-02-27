/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
		ignore.add("getVirtualLinkable");
		// Vnflcm
		ignore.add("getScaleToLevelStart");
		ignore.add("getChangeExternalConnectivityEnd");
		ignore.add("getChangeFlavourEnd");
		ignore.add("getInstantiateEnd");
		ignore.add("getChangeExternalConnectivity");
		ignore.add("getScaleEnd");
		ignore.add("getChangeCurrentPackageStart");
		ignore.add("getScaleToLevel");
		ignore.add("getScaleToLevelEnd");
		ignore.add("getInstantiateStart");
		ignore.add("getHealEnd");
		ignore.add("getCreateSnapshotStart");
		ignore.add("getChangeExternalConnectivityStart");
		ignore.add("getModifyInformation");
		ignore.add("getHealStart");
		ignore.add("getModifyInformationStart");
		ignore.add("getInstantiate");
		ignore.add("getOperate");
		ignore.add("getRevertToSnapshotEnd");
		ignore.add("getOperateEnd");
		ignore.add("getChangeCurrentPackage");
		ignore.add("getTerminateEnd");
		ignore.add("getCreateSnapshot");
		ignore.add("getModifyInformationEnd");
		ignore.add("getHeal");
		ignore.add("getCreateSnapshotEnd");
		ignore.add("getTerminate");
		ignore.add("getChangeCurrentPackageEnd");
		ignore.add("getTerminateStart");
		ignore.add("getScaleStart");
		ignore.add("getChangeFlavourStart");
		ignore.add("getChangeFlavour");
		ignore.add("getOperateStart");
		ignore.add("getRevertToSnapshotStart");
		ignore.add("getRevertToSnapshot");
		// Upper
		ignore.add("getNfviMaintenanceInfo");
		ignore.add("getTrunkBinding");
		ignore.add("getFixedIpAddress");
		ignore.add("getIpAddressAssignmentSubtype");
		ignore.add("getNfviMaintenanceGroupInfo");
		// 2.6.1
		ignore.add("getSegmentationId");
		ignore.add("getServiceAvailabilityLevel");
		ignore.add("getScale");
		ignore.add("getScaleStatus");
		ignore.add("getBootOrder");
		ignore.add("getNfviConstraints");
		ignore.add("getBootOrder");
		ignore.add("getVimSpecificProperties");
		ignore.add("getKvpData");
		ignore.add("getSourcePath");
		ignore.add("getDestinationPath");
		ignore.add("getData");
		// SW data for 2.6.1
		ignore.add("getAlgorithm");
		ignore.add("getChecksumAlgorithm");
		ignore.add("getFile");
		ignore.add("getProvider");
		ignore.add("getProperties");
		ignore.add("getType");
		ignore.add("getRepository");
		ignore.add("getDescription");
		ignore.add("getArtifactVersion");
		ignore.add("getDeployPath");
		// 2.5.1
		ignore.add("getVnfProfile");
		//
		ignore.add("getChecksum");
		// NS
		ignore.add("getFlavourId");
		ignore.add("getNsProfile");
		ignore.add("getUpdateStart");
		ignore.add("getUpdateEnd");
		ignore.add("getUpdate");
		ignore.add("getPriority");
		// NS VL
		ignore.add("getVirtualLinkProtocolData");
		// NS notifications
		ignore.add("getNotifications");
		ignore.add("getInputs");
		return ignore;
	}

}
