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
package com.ubiqube.etsi.mano.controller.policy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.policy.ActivationStatusType;
import com.ubiqube.etsi.mano.dao.mano.policy.Policies;
import com.ubiqube.etsi.mano.dao.mano.policy.PolicyVersion;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.PolicyService;
import com.ubiqube.etsi.mano.service.SearchableService;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class PolicyControllerImplTest {
	@Mock
	private PolicyService policyService;
	@Mock
	private SearchableService searchService;

	@Test
	void testDeleteById() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testDeleteByIdAndVersion() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		srv.deleteByIdAndVersion(null, null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindByIdFail() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		assertThrows(NoSuchElementException.class, () -> srv.findById(null));
	}

	@Test
	void testGetContentByPolicyIdAndVersion() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final PolicyVersion policy = new PolicyVersion(null, "");
		when(policyService.findByPolicyIdAndVersion(any(), any())).thenReturn(Optional.of(policy));
		srv.getContentByPolicyIdAndVersion(null, null);
		assertTrue(true);
	}

	@Test
	void testGetContentByPolicyIdAndVersionFail() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		assertThrows(GenericException.class, () -> srv.getContentByPolicyIdAndVersion(null, null));
		assertTrue(true);
	}

	@Test
	void testGetContentBySelectedVersion() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policy = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policy));
		final PolicyVersion policyVersion = new PolicyVersion(null, "");
		when(policyService.findByPolicyIdAndVersion(any(), any())).thenReturn(Optional.of(policyVersion));
		srv.getContentBySelectedVersion(null);
		assertTrue(true);
	}

	@Test
	void testGetContentBySelectedVersionFail() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		assertThrows(NoSuchElementException.class, () -> srv.getContentBySelectedVersion(null));
	}

	@Test
	void testPutContentFail2() throws IOException {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		final InputStream is = Mockito.mock(InputStream.class);
		when(is.readAllBytes()).thenThrow(IOException.class);
		assertThrows(GenericException.class, () -> srv.putContent(null, null, is));
	}

	@Test
	void testPutContentFail() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		assertThrows(NoSuchElementException.class, () -> srv.putContent(null, null, null));
		assertTrue(true);
	}

	@Test
	void testPutContent() throws IOException {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		final InputStream is = Mockito.mock(InputStream.class);
		when(is.readAllBytes()).thenReturn("hello".getBytes());
		srv.putContent(null, null, is);
		assertTrue(true);
	}

	@Test
	void testCreate() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		srv.create(null);
		assertTrue(true);
	}

	@Test
	void testModify() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		final PolicyPatchDto patch = new PolicyPatchDto();
		srv.modify(null, patch);
		assertTrue(true);
	}

	@Test
	void testModify2() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		final PolicyPatchDto patch = new PolicyPatchDto();
		patch.setActivationStatus(ActivationStatusType.ACTIVATED);
		patch.setAddAssociations(List.of());
		patch.setRemoveAllAssociations(true);
		patch.setSelectedVersion("");
		srv.modify(null, patch);
		assertTrue(true);
	}

	@Test
	void testModify3() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		final Policies policies = new Policies();
		when(policyService.findPoliciesById(any())).thenReturn(Optional.of(policies));
		final PolicyPatchDto patch = new PolicyPatchDto();
		patch.setActivationStatus(ActivationStatusType.ACTIVATED);
		patch.setRemoveAllAssociations(true);
		patch.setRemoveAssociations(List.of(""));
		patch.setSelectedVersion("");
		srv.modify(null, patch);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final PolicyControllerImpl srv = new PolicyControllerImpl(policyService, searchService);
		srv.search(null, null, null, null);
		assertTrue(true);
	}

}
