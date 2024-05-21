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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@ExtendWith(MockitoExtension.class)
class OsContainerContributorTest {
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstanceJpa;

	@Test
	void test() {
		final OsContainerContributor con = new OsContainerContributor(vnfLiveInstanceJpa);
		final VnfBlueprint params = new VnfBlueprint();
		final VnfPackage pkg = new VnfPackage();
		pkg.setOsContainerDeployableUnits(Set.of());
		pkg.setOsContainer(Set.of());
		pkg.setMciops(Set.of());
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void test_Mciops() {
		final OsContainerContributor con = new OsContainerContributor(vnfLiveInstanceJpa);
		final VnfBlueprint params = new VnfBlueprint();
		final VnfPackage pkg = new VnfPackage();
		pkg.setOsContainerDeployableUnits(Set.of());
		pkg.setOsContainer(Set.of());
		final McIops mciop = new McIops();
		mciop.setToscaName("mciop");
		mciop.setAssociatedVdu(Set.of("vdu"));
		pkg.setMciops(Set.of(mciop));
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void test_Du() {
		final OsContainerContributor con = new OsContainerContributor(vnfLiveInstanceJpa);
		final VnfBlueprint params = new VnfBlueprint();
		params.setVnfInstance(new VnfInstance());
		params.getVnfInstance().setId(UUID.randomUUID());
		final VnfPackage pkg = new VnfPackage();
		final OsContainerDeployableUnit du = new OsContainerDeployableUnit();
		du.setVirtualStorageReq(Set.of("sto"));
		du.setName("du");
		du.setContainerReq(Set.of("osc"));
		pkg.setOsContainerDeployableUnits(Set.of(du));
		final OsContainer osc = new OsContainer();
		osc.setName("osc");
		pkg.setOsContainer(Set.of(osc));
		pkg.setMciops(Set.of());
		final VnfStorage sto = new VnfStorage();
		sto.setToscaName("sto");
		pkg.setVnfStorage(Set.of(sto));
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void test_Du_NotFound() {
		final OsContainerContributor con = new OsContainerContributor(vnfLiveInstanceJpa);
		final VnfBlueprint params = new VnfBlueprint();
		final VnfPackage pkg = new VnfPackage();
		final OsContainerDeployableUnit du = new OsContainerDeployableUnit();
		du.setVirtualStorageReq(Set.of("stoww"));
		du.setName("du");
		du.setContainerReq(Set.of("osc"));
		pkg.setOsContainerDeployableUnits(Set.of(du));
		final OsContainer osc = new OsContainer();
		osc.setName("osc");
		pkg.setOsContainer(Set.of(osc));
		pkg.setMciops(Set.of());
		final VnfStorage sto = new VnfStorage();
		sto.setToscaName("sto");
		pkg.setVnfStorage(Set.of(sto));
		assertThrows(NotFoundException.class, () -> con.contribute(pkg, params));
	}

	@Test
	void test_Du_StorageNotFound() {
		final OsContainerContributor con = new OsContainerContributor(vnfLiveInstanceJpa);
		final VnfBlueprint params = new VnfBlueprint();
		final VnfPackage pkg = new VnfPackage();
		final OsContainerDeployableUnit du = new OsContainerDeployableUnit();
		du.setVirtualStorageReq(Set.of("stoww"));
		pkg.setOsContainerDeployableUnits(Set.of(du));
		pkg.setOsContainer(Set.of());
		pkg.setMciops(Set.of());
		final VnfStorage sto = new VnfStorage();
		sto.setToscaName("sto");
		pkg.setVnfStorage(Set.of(sto));
		assertThrows(NotFoundException.class, () -> con.contribute(pkg, params));
	}
}
