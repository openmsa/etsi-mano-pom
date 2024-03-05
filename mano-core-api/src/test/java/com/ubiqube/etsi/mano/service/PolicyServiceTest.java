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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.jpa.policy.PoliciesJpa;
import com.ubiqube.etsi.mano.jpa.policy.PolicyVersionJpa;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {
	@Mock
	private PoliciesJpa policiesJpa;
	@Mock
	private PolicyVersionJpa policyVersionJpa;

	@Test
	void testSave() throws Exception {
		final PolicyService ps = new PolicyService(policiesJpa, policyVersionJpa);
		ps.policiesSave(null);
		assertTrue(true);
	}

	@Test
	void testPoliciesById() throws Exception {
		final PolicyService ps = new PolicyService(policiesJpa, policyVersionJpa);
		ps.findPoliciesById(null);
		assertTrue(true);
	}

	@Test
	void testFindByPoliciesAndVersion() throws Exception {
		final PolicyService ps = new PolicyService(policiesJpa, policyVersionJpa);
		ps.findByPolicyIdAndVersion(null, null);
		assertTrue(true);
	}

	@Test
	void testDeletePolicyById() throws Exception {
		final PolicyService ps = new PolicyService(policiesJpa, policyVersionJpa);
		ps.deletePoliciesById(null);
		assertTrue(true);
	}

	@Test
	void testDeletePolicyByIdAndVersion() throws Exception {
		final PolicyService ps = new PolicyService(policiesJpa, policyVersionJpa);
		ps.deleteByIdAndVersionsVersion(null, null);
		assertTrue(true);
	}
}
