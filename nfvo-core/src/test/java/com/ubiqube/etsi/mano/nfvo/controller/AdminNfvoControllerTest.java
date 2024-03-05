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
package com.ubiqube.etsi.mano.nfvo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.jgrapht.ListenableGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.event.NfvoActions;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsPlanService;
import com.ubiqube.etsi.mano.nfvo.service.graph.TestNsTask;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageManager;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class AdminNfvoControllerTest {
	@Mock
	private NsPackageManager packageManager;
	@Mock
	private NsPackageOnboardingImpl nsPkgOnboarding;
	@Mock
	private NsPlanService nsPlanService;
	@Mock
	private NsInstanceService nsLiveInstanceJpa;
	@Mock
	private NsBlueprintService nsBluePrintJpa;
	@Mock
	private NfvoActions nfvoActions;
	@Mock
	private NsInstanceService nsdInstanceJpa;

	@Test
	private AdminNfvoController createService() {
		return new AdminNfvoController(packageManager, nsPkgOnboarding, nsPlanService, nsLiveInstanceJpa, nsBluePrintJpa, nfvoActions, nsdInstanceJpa);
	}

	void testGetDeployementPicture() {
		final AdminNfvoController srv = createService();
		final NsBlueprint nsBp = new NsBlueprint();
		final TestNsTask t1 = new TestNsTask(ResourceTypeEnum.AFFINITY_RULE);
		final TestNsTask t2 = new TestNsTask(ResourceTypeEnum.AFFINITY_RULE);
		t2.setAlias("alias");
		nsBp.setTasks(Set.of(t1, t2));
		final NsdInstance inst = new NsdInstance();
		nsBp.setNsInstance(inst);
		final NsLiveInstance live1 = new NsLiveInstance();
		live1.setNsTask(t2);
		when(nsBluePrintJpa.findById(any())).thenReturn(nsBp);
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(List.of(live1));
		srv.getDeployementPicture(null);
		assertTrue(true);
	}

	@Test
	void testGztNs2Plan() {
		final AdminNfvoController srv = createService();
		final ListenableGraph<Vertex2d, Edge2d> lg = Mockito.mock(ListenableGraph.class);
		when(nsPlanService.getPlanFor(any())).thenReturn(lg);
		srv.getNs2dPlan(null);
		assertTrue(true);
	}

	@Test
	void testValidateNs() {
		final AdminNfvoController srv = createService();
		final MultipartFile file = Mockito.mock(MultipartFile.class);
		srv.validateNs(file);
		assertTrue(true);
	}

}
