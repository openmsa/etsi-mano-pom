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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.parser.tosca.objects.tosca.groups.nfv.PlacementGroup;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VNF;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VduCp;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VnfExtCp;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.VnfVirtualLink;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.Compute;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.VirtualBlockStorage;
import com.ubiqube.parser.tosca.objects.tosca.nodes.nfv.vdu.VirtualObjectStorage;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.AffinityRule;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.ScalingAspects;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.SecurityGroupRule;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.SupportedVnfInterface;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInitialDelta;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduInstantiationLevels;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VduScalingAspectDeltas;
import com.ubiqube.parser.tosca.objects.tosca.policies.nfv.VirtualLinkBitrateInitialDelta;

public class VnfdTest extends AbstractToscaApiTest {

	@Override
	protected void prepareArchive() {
		StaticTestTools.createVnfPackage();
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testGeneric(final int num, final Class<?> clazz) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException, IntrospectionException {
		runTest(num, clazz);
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(1, VirtualBlockStorage.class),
				Arguments.of(1, VirtualObjectStorage.class),
				Arguments.of(3, VnfVirtualLink.class),
				Arguments.of(4, VduCp.class),
				Arguments.of(1, PlacementGroup.class),
				Arguments.of(1, VduInstantiationLevels.class),
				Arguments.of(1, VnfExtCp.class),
				Arguments.of(1, VNF.class),
				Arguments.of(2, ScalingAspects.class),
				Arguments.of(2, VduInitialDelta.class),
				Arguments.of(2, VduScalingAspectDeltas.class),
				Arguments.of(1, SecurityGroupRule.class),
				Arguments.of(1, SupportedVnfInterface.class),
				Arguments.of(1, AffinityRule.class),
				Arguments.of(1, VirtualLinkBitrateInitialDelta.class),
				Arguments.of(2, Compute.class));
	}

	@Test
	void testVduCp() throws IllegalArgumentException, InvocationTargetException, IllegalAccessException, IntrospectionException {
		final List<VduCp> cps = runTest(4, VduCp.class);
		assertNotNull(cps.get(0).getVirtualBindingReq());
		assertNotNull(cps.get(0).getVirtualLinkReq());
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
		ignore.add("getChecksumAlgorithm");
		ignore.add("getFile");
		ignore.add("getProvider");
		ignore.add("getProperties");
		ignore.add("getType");
		ignore.add("getRepository");
		ignore.add("getDescription");
		ignore.add("getArtifactVersion");
		ignore.add("getDeployPath");
		return ignore;
	}

}
