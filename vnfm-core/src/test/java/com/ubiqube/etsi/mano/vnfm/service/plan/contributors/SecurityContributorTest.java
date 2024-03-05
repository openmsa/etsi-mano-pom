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

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.SecurityGroup;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

@ExtendWith(MockitoExtension.class)
class SecurityContributorTest {
	@Mock
	private VnfLiveInstanceJpa vnfLiveInstance;

	@Test
	void test() {
		final SecurityContributor con = new SecurityContributor(vnfLiveInstance);
		final VnfPackage pkg = new VnfPackage();
		pkg.setSecurityGroups(Set.of());
		final VnfBlueprint params = new VnfBlueprint();
		con.contribute(pkg, params);
		assertTrue(true);
	}

	@Test
	void testOk() {
		final SecurityContributor con = new SecurityContributor(vnfLiveInstance);
		final VnfPackage pkg = new VnfPackage();
		final SecurityGroup sg = new SecurityGroup();
		sg.setToscaName("sg");
		pkg.setSecurityGroups(Set.of(sg));
		final VnfBlueprint params = new VnfBlueprint();
		con.contribute(pkg, params);
		assertTrue(true);
	}
}
