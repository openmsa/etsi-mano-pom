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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@ExtendWith(MockitoExtension.class)
class DnsContributorTest {
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;

	@Test
	void test() {
		final DnsContributor con = new DnsContributor(vnfLiveInstance);
		final VnfPackage pkg = new VnfPackage();
		final VnfBlueprint params = new VnfBlueprint();
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void test_Vl() {
		final DnsContributor con = new DnsContributor(vnfLiveInstance);
		final VnfPackage pkg = new VnfPackage();
		final VnfVl vl = new VnfVl();
		vl.setToscaName("vl");
		pkg.setVnfVl(Set.of(vl));
		final VnfBlueprint params = new VnfBlueprint();
		params.setOperation(PlanOperationType.TERMINATE);
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void test_COmpute() {
		final DnsContributor con = new DnsContributor(vnfLiveInstance);
		final VnfPackage pkg = new VnfPackage();
		final VnfCompute comp = new VnfCompute();
		comp.setToscaName("comp");
		pkg.setVnfCompute(Set.of(comp));
		final VnfBlueprint params = new VnfBlueprint();
		con.contribute(pkg, params);
		assertTrue(true);
	}

}
