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
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.audit.Audit;
import com.ubiqube.etsi.mano.dao.mano.BlueZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class VnfGrantMapperTest {

	private final PodamFactoryImpl podam;
	final VnfGrantMapping vnfGrantMapping = Mappers.getMapper(VnfGrantMapping.class);

	public VnfGrantMapperTest() {
		podam = new PodamFactoryImpl();
		final DefaultClassInfoStrategy clz = (DefaultClassInfoStrategy) podam.getClassStrategy();
		clz.addExcludedField(NetworkTask.class, "blueprint");
		clz.addExcludedField(ComputeTask.class, "blueprint");
		clz.addExcludedField(StorageTask.class, "blueprint");
		clz.addExcludedField(VnfTask.class, "blueprint");
		clz.addExcludedField(DnsHostTask.class, "blueprint");
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	VnfGrantMapper createService() {

		return new VnfGrantMapper(vnfGrantMapping);
	}

	@Test
	void testNull() {
		final VnfGrantMapper mapper = createService();
		final GrantResponse r = mapper.mapToGrantResponse(null);
		assertNull(r);
	}

	@Test
	void testEmpty() {
		final VnfGrantMapper mapper = createService();
		final VnfBlueprint blueprint = new VnfBlueprint();
		mapper.mapToGrantResponse(blueprint);
		assertTrue(true);
	}

	@Test
	void test() {
		final VnfGrantMapper mapper = createService();
		final VnfBlueprint blueprint = new VnfBlueprint();
		final BlueZoneGroupInformation zgi = podam.manufacturePojo(BlueZoneGroupInformation.class);
		final VnfTask vt = podam.manufacturePojo(DnsHostTask.class);
		blueprint.setTasks(Set.of(vt));
		blueprint.setZoneGroups(Set.of(zgi));
		blueprint.setAdditionalParams(Map.of());
		blueprint.setAffectedVipCps(List.of());
		blueprint.setAudit(new Audit());
		blueprint.setAutomaticInvocation(true);
		blueprint.setCancelMode(CancelModeTypeEnum.FORCEFUL);
		blueprint.setCancelPending(true);
		blueprint.setVnfInstance(new VnfInstance());
		blueprint.setId(UUID.randomUUID());
		blueprint.setCirConnectionInfo(Map.of());
		blueprint.setVimConnections(Set.of());
		blueprint.setZones(Set.of());
		blueprint.setOperation(PlanOperationType.CHANGE_EXT_CONN);
		mapper.mapToGrantResponse(blueprint);

		assertTrue(true);
	}

	@Test
	void testMapToVnfInstantiatedVirtualLinkNull() {
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedVirtualLink(null);
		assertNull(res);
	}

	@Test
	void testMapToVnfInstantiatedVirtualLink() {
		final NetworkTask nt = podam.manufacturePojo(NetworkTask.class);
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedVirtualLink(nt);
		assertNotNull(res);
	}

	@Test
	void testMapToVnfInstantiatedComputeNull() {
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedCompute(null);
		assertNull(res);
	}

	@Test
	void testMapToVnfInstantiatedComputeLink() {
		final ComputeTask ct = podam.manufacturePojo(ComputeTask.class);
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedCompute(ct);
		assertNotNull(res);
	}

	@Test
	void testMapToVnfInstantiatedStorageNull() {
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedStorage(null);
		assertNull(res);
	}

	@Test
	void testMapToVnfInstantiatedStorageLink() {
		final StorageTask st = podam.manufacturePojo(StorageTask.class);
		final GrantInformationExt res = vnfGrantMapping.mapToVnfInstantiatedStorage(st);
		assertNotNull(res);
	}

	@Test
	void testMapToVnfTaskNull() {
		final Set<GrantInformationExt> res = vnfGrantMapping.mapVnfTasks(null);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testMapToVnfTaskEmpty() {
		final Set<GrantInformationExt> res = vnfGrantMapping.mapVnfTasks(Set.of());
		assertNotNull(res);
	}

	@Test
	void testMapToVnfTask() {
		final VnfTask vt = podam.manufacturePojo(DnsHostTask.class);
		vt.setType(ResourceTypeEnum.DNSHOST);
		final StorageTask st = podam.manufacturePojo(StorageTask.class);
		st.setType(ResourceTypeEnum.STORAGE);
		final ComputeTask ct = podam.manufacturePojo(ComputeTask.class);
		ct.setType(ResourceTypeEnum.COMPUTE);
		final NetworkTask nt = podam.manufacturePojo(NetworkTask.class);
		nt.setType(ResourceTypeEnum.VL);
		final NetworkTask lp = podam.manufacturePojo(NetworkTask.class);
		lp.setType(ResourceTypeEnum.LINKPORT);
		final Set<GrantInformationExt> res = vnfGrantMapping.mapVnfTasks(Set.of(vt, st, ct, nt, lp));
		assertNotNull(res);
		assertEquals(4, res.size());
	}

}
