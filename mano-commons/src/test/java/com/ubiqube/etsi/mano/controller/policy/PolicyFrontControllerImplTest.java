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
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamSource;

import com.ubiqube.etsi.mano.dao.mano.policy.Policies;
import com.ubiqube.etsi.mano.exception.GenericException;

@ExtendWith(MockitoExtension.class)
class PolicyFrontControllerImplTest {
	@Mock
	private PolicyController policyController;

	@Test
	void testCreate() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final Policies val = new Policies();
		final Consumer<Object> mkLink = x -> {
		};
		srv.create(val, x -> "", mkLink);
		assertTrue(true);
	}

	@Test
	void testDeleteById() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		srv.deleteById(id);
		assertTrue(true);
	}

	@Test
	void testDeleteByVersion() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		srv.deleteByVersion(id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		final Consumer<Object> mkLink = x -> {
		};
		final Policies pol = new Policies();
		when(policyController.findById(id)).thenReturn(pol);
		srv.findById(id.toString(), x -> "", mkLink);
		assertTrue(true);
	}

	@Test
	void testGetContentByPolicyIdAndVersion() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		srv.getContentByPolicyIdAndVersion(id.toString(), null);
		assertTrue(true);
	}

	@Test
	void testsearch() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testGetContentBySelectedVersion() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		srv.getContentBySelectedVersion(id.toString());
		assertTrue(true);
	}

	@Test
	void testPutContent() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		final InputStreamSource is = Mockito.mock(InputStreamSource.class);
		srv.putContent(id.toString(), null, is);
		assertTrue(true);
	}

	@Test
	void testPutContent2() throws IOException {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		final InputStreamSource is = Mockito.mock(InputStreamSource.class);
		when(is.getInputStream()).thenThrow(IOException.class);
		final String sid = id.toString();
		assertThrows(GenericException.class, () -> srv.putContent(sid, null, is));
	}

	@Test
	void testModify() {
		final PolicyFrontControllerImpl srv = new PolicyFrontControllerImpl(policyController);
		final UUID id = UUID.randomUUID();
		final Consumer<Object> mkLink = x -> {
		};
		srv.modify(id.toString(), null, x -> "", mkLink);
		assertTrue(true);
	}

}
