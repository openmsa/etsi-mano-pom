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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.nsd.CpPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsHeal;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingLevelMapping;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.VnfScalingStepMapping;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.ExternalPortRecord;
import com.ubiqube.etsi.mano.dao.mano.vim.vnffg.Classifier;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.NsdPackageService;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsInstanceControllerServiceImplTest {
	@Mock
	private NsdPackageService nsdPackageService;
	@Mock
	private NsInstanceService nsInstanceService;
	@Mock
	private EventManager eventManager;
	@Mock
	private MapperFacade mapper;
	@Mock
	private NsBlueprintService nsBlueprintService;
	@Mock
	private SearchableService searchService;

	@Test
	void testCreateNsd() {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final NsdPackage nsdPkg = new NsdPackage();
		nsdPkg.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		nsdPkg.setNsdOperationalState(PackageOperationalState.ENABLED);
		nsdPkg.setNestedNsdInfoIds(Set.of());
		nsdPkg.setVnfPkgIds(Set.of());
		nsdPkg.setVnffgs(Set.of());
		final NsdInstance nsdInstance = new NsdInstance();
		when(nsInstanceService.save(any())).thenReturn(nsdInstance);
		when(nsdPackageService.findByNsdId(strId)).thenReturn(nsdPkg);
		srv.createNsd(strId, "name", "descr");
		assertTrue(true);
	}

	@Test
	void testCreateNsd_Advance() {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		final NsdPackage nsdPkg = new NsdPackage();
		nsdPkg.setNsdOnboardingState(OnboardingStateType.ONBOARDED);
		nsdPkg.setNsdOperationalState(PackageOperationalState.ENABLED);
		nsdPkg.setNestedNsdInfoIds(Set.of());
		final NsdPackageVnfPackage vnfPkg = new NsdPackageVnfPackage();
		final VnfScalingStepMapping step = new VnfScalingStepMapping();
		step.setLevels(Set.of());
		final VnfPackage pkg = new VnfPackage();
		vnfPkg.setVnfPackage(pkg);
		vnfPkg.setStepMapping(Set.of(step));
		final ForwarderMapping fw = new ForwarderMapping(strId, 0, strId, strId, strId);
		vnfPkg.setForwardMapping(Set.of(fw));
		final VnfScalingLevelMapping level = new VnfScalingLevelMapping();
		vnfPkg.setLevelMapping(Set.of(level));
		final ExternalPortRecord ext = new ExternalPortRecord();
		vnfPkg.setNets(Set.of(ext));
		final ListKeyPair pair = new ListKeyPair();
		vnfPkg.setVirtualLinks(Set.of(pair));
		nsdPkg.setVnfPkgIds(Set.of(vnfPkg));
		final VnffgDescriptor vnffg = new VnffgDescriptor();
		final NfpDescriptor nfp = new NfpDescriptor();
		final VnffgInstance vnffgInst = new VnffgInstance();
		final CpPair cpPair = new CpPair();
		vnffgInst.setPairs(List.of(cpPair));
		nfp.setInstances(List.of(vnffgInst));
		vnffg.setNfpd(List.of(nfp));
		nsdPkg.setVnffgs(Set.of(vnffg));
		final NsdInstance nsdInstance = new NsdInstance();
		vnfPkg.setNsdPackage(nsdPkg);
		final Classifier clas = new Classifier();
		when(nsInstanceService.save(any())).thenReturn(nsdInstance);
		when(nsdPackageService.findByNsdId(strId)).thenReturn(nsdPkg);
		when(mapper.map(any(), eq(ForwarderMapping.class))).thenReturn(fw);
		when(mapper.map(any(), eq(VnfScalingLevelMapping.class))).thenReturn(level);
		when(mapper.map(any(), eq(VnfScalingStepMapping.class))).thenReturn(step);
		when(mapper.map(any(), eq(ListKeyPair.class))).thenReturn(pair);
		when(mapper.map(any(), eq(Classifier.class))).thenReturn(clas);
		when(mapper.map(any(), eq(NfpDescriptor.class))).thenReturn(nfp);
		when(mapper.map(any(), eq(VnffgInstance.class))).thenReturn(vnffgInst);
		when(mapper.map(any(), eq(CpPair.class))).thenReturn(cpPair);
		srv.createNsd(strId, "name", "descr");
		assertTrue(true);
	}

	@Test
	void testInstantiate() throws Exception {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final NsInstantiate req = new NsInstantiate();
		final NsdInstance inst = new NsdInstance();
		final NsdPackage info = new NsdPackage();
		info.setVnffgs(Set.of());
		info.setVnfPkgIds(Set.of());
		info.setNestedNsdInfoIds(Set.of());
		inst.setNsdInfo(info);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		srv.instantiate(id, req);
		assertTrue(true);
	}

	@Test
	void testTerminate() throws Exception {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		srv.terminate(id, OffsetDateTime.now());
		verify(eventManager).sendActionNfvo(eq(ActionType.NS_TERMINATE), any(), any());
		assertTrue(true);
	}

	@Test
	void testHeal() throws Exception {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		final NsHeal req = new NsHeal();
		srv.heal(id, req);
		verify(eventManager).sendActionNfvo(eq(ActionType.NS_HEAL), any(), any());
		assertTrue(true);
	}

	@Test
	void testScale() throws Exception {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		final UUID id = UUID.randomUUID();
		final NsdInstance inst = new NsdInstance();
		inst.setInstantiationState(InstantiationState.INSTANTIATED);
		when(nsInstanceService.findById(id)).thenReturn(inst);
		final NsScale req = new NsScale();
		srv.scale(id, req);
		verify(eventManager).sendActionNfvo(eq(ActionType.NS_SCALE), any(), any());
		assertTrue(true);
	}

	@Test
	void testSearch() throws Exception {
		final NsInstanceControllerServiceImpl srv = new NsInstanceControllerServiceImpl(nsdPackageService, nsInstanceService, eventManager, mapper, nsBlueprintService, searchService);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}
}
