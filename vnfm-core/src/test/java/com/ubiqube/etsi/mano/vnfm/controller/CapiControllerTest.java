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
package com.ubiqube.etsi.mano.vnfm.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.service.CapiServerService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

@ExtendWith(MockitoExtension.class)
class CapiControllerTest {
	@Mock
	private CapiServerService capiServerJpa;
	@Mock
	private OsClusterService osClusterService;
	@Mock
	private CapiServerMapping mapper;

	CapiController createService() {
		return new CapiController(capiServerJpa, osClusterService, mapper);
	}

	@Test
	void testListCapiConn() {
		final CapiController srv = createService();
		srv.listCapiConnections();
	}

	@Test
	void testPost() {
		final CapiController srv = createService();
		srv.post(null);
	}

	@Test
	void testPostKubeConfig() throws IOException {
		final CapiController srv = createService();
		final MultipartFile file = Mockito.mock(MultipartFile.class);
		srv.postKubeConfig("ctx", file);

	}

	@Test
	void testDelete() {
		final CapiController srv = createService();
		srv.delete(UUID.randomUUID());
		assertTrue(true);
	}

}
