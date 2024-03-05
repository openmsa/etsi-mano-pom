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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VlProfileEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.vim.AffinityRule;
import com.ubiqube.etsi.mano.dao.mano.vim.L2Data;
import com.ubiqube.etsi.mano.dao.mano.vim.SecurityGroup;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;

@ExtendWith(MockitoExtension.class)
class VnfPlanServiceTest {
	@Mock
	private VnfPackageService vnfService;

	@Test
	void testName() throws Exception {
		final VnfPlanService vnfPlanService = new VnfPlanService(vnfService);
		final UUID id = UUID.randomUUID();
		final VnfPackage pkg = createVnfPackage(id);
		when(vnfService.findById(id)).thenReturn(pkg);
		vnfPlanService.getPlanFor(id);
		assertTrue(true);
	}

	private static VnfPackage createVnfPackage(final UUID id) {
		final VnfPackage pkg = new VnfPackage();
		pkg.setId(id);
		pkg.setAffinityRules(Set.of(createAffinityRule()));
		pkg.setAdditionalArtifacts(Set.of());
		pkg.setAttributes(List.of());
		pkg.setMciopId(Set.of());
		pkg.setMciops(Set.of());
		pkg.setSecurityGroups(Set.of(createSecurityGroup()));
		pkg.setOsContainer(Set.of());
		pkg.setOsContainerDeployableUnits(Set.of(createDeployableUnit()));
		pkg.setVirtualLinks(Set.of(createVirtualLink()));
		pkg.setVnfCompute(Set.of(createCompute()));
		pkg.setVnfVl(Set.of(createVl()));
		pkg.setVnfExtCp(Set.of(createVnfExtCp()));
		return pkg;
	}

	private static OsContainerDeployableUnit createDeployableUnit() {
		final OsContainerDeployableUnit ocdu = new OsContainerDeployableUnit();
		ocdu.setName("ocdu-001");
		ocdu.setVirtualStorageReq(Set.of());
		return ocdu;
	}

	private static VnfCompute createCompute() {
		final VnfCompute vc = new VnfCompute();
		vc.setToscaName("vc-001");
		vc.setStorages(Set.of());
		vc.setAffinityRule(Set.of());
		vc.setMonitoringParameters(Set.of());
		vc.setSecurityGroup(Set.of());
		vc.setPorts(createComputePorts());
		vc.setPlacementGroup(Set.of());
		return vc;
	}

	private static Set<VnfLinkPort> createComputePorts() {
		final VnfLinkPort vlp001 = new VnfLinkPort();
		vlp001.setToscaName("vlp-001");
		vlp001.setVirtualLink("vv-001");
		return Set.of(vlp001);
	}

	private static VnfExtCp createVnfExtCp() {
		final VnfExtCp vec = new VnfExtCp();
		vec.setToscaName("ext-000");
		vec.setInternalVirtualLink("vv-001");
		return vec;
	}

	private static VnfVl createVl() {
		final VnfVl vv = new VnfVl();
		vv.setToscaName("vv-001");
		final VlProfileEntity profile = new VlProfileEntity();
		profile.setVirtualLinkProtocolData(Set.of(createVlProtocolData()));
		vv.setVlProfileEntity(profile);
		return vv;
	}

	private static VlProtocolData createVlProtocolData() {
		final VlProtocolData vpd = new VlProtocolData();
		final L2Data l2Data = new L2Data();
		l2Data.setName("l1-001");
		vpd.setL2ProtocolData(l2Data);
		return vpd;
	}

	private static ListKeyPair createVirtualLink() {
		return new ListKeyPair("eth0", 0);
	}

	private static SecurityGroup createSecurityGroup() {
		final SecurityGroup sg = new SecurityGroup();
		sg.setToscaName("sg001");
		return sg;
	}

	private static AffinityRule createAffinityRule() {
		final AffinityRule ar = new AffinityRule();
		ar.setToscaName("ar001");
		return ar;
	}
}
