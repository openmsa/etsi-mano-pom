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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.MetricGroupService;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.mon.MonitoringManager;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;
import com.ubiqube.etsi.mano.vnfm.service.PmJobsService;

@ExtendWith(MockitoExtension.class)
class VnfmPmControllerImplTest {
	@Mock
	private PmJobsService pmJobsJpa;
	@Mock
	private SearchableService searchableService;
	@Mock
	private MetricGroupService metricGroupService;
	@Mock
	private SubscriptionService subscriptionService;
	@Mock
	private VnfInstanceGatewayService vnfInstanceGateway;
	@Mock
	private MonitoringManager monitoringManager;

	@Test
	void testDelete() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		when(pmJobsJpa.findById(null)).thenReturn(Optional.of(pm));
		srv.delete(null);
		assertTrue(true);
	}

	@Test
	void testDeleteFail() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		assertThrows(NotFoundException.class, () -> srv.delete(null));
	}

	@Test
	void testFindById() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		when(pmJobsJpa.findById(null)).thenReturn(Optional.of(pm));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindReport() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		assertThrows(UnsupportedOperationException.class, () -> srv.findReport(null, null));
	}

	@Test
	void testSaveBadVim() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		pm.getObjectInstanceIds().add(UUID.randomUUID().toString());
		final VnfInstance inst = TestFactory.createVnfInstance();
		when(vnfInstanceGateway.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.save(pm));
	}

	@Test
	void testSave() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		pm.setSubObjectInstanceIds(List.of("sub"));
		pm.getObjectInstanceIds().add(UUID.randomUUID().toString());
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		pm.setAuthentication(auth);
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = inst.getVnfPkg();
		pkg.setOsContainerDeployableUnits(Set.of());
		pkg.setOsContainer(Set.of());
		final VnfCompute comp = new VnfCompute();
		comp.setToscaName("sub");
		pkg.setVnfCompute(Set.of(comp));
		pkg.setVnfExtCp(Set.of());
		pkg.setVnfVl(Set.of());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		inst.setVimConnectionInfo(Set.of(vimConn));
		when(vnfInstanceGateway.findById(any())).thenReturn(inst);
		when(pmJobsJpa.save(any())).thenReturn(pm);
		final Subscription subs = new Subscription();
		when(subscriptionService.save(any())).thenReturn(subs);
		srv.save(pm);
		assertTrue(true);
	}

	@Test
	void testSaveNoMetricFound() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		pm.setSubObjectInstanceIds(List.of("sub"));
		pm.getObjectInstanceIds().add(UUID.randomUUID().toString());
		final VnfInstance inst = TestFactory.createVnfInstance();
		final VnfPackage pkg = inst.getVnfPkg();
		pkg.setOsContainerDeployableUnits(Set.of());
		pkg.setOsContainer(Set.of());
		pkg.setVnfCompute(Set.of());
		pkg.setVnfExtCp(Set.of());
		pkg.setVnfVl(Set.of());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		inst.setVimConnectionInfo(Set.of(vimConn));
		when(vnfInstanceGateway.findById(any())).thenReturn(inst);
		assertThrows(GenericException.class, () -> srv.save(pm));
	}

	@Test
	void testSaveNotFound() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		final PmJob pm = TestFactory.createPmJob();
		assertThrows(NotFoundException.class, () -> srv.save(pm));
	}

	@Test
	void testSearch() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmControllerImpl srv = new VnfmPmControllerImpl(pmJobsJpa, searchableService, metricGroupService, subscriptionService, props, vnfInstanceGateway, monitoringManager);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}
}
