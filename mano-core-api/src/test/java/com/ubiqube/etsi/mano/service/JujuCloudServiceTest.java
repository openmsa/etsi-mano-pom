/**
 *     Copyright (C) 2019-2023 Ubiqube.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@ExtendWith(MockitoExtension.class)
public class JujuCloudServiceTest {

	@Mock
	private JujuCloudJpa jujuCloudJpa;

	@Mock
	private JujuCredentialJpa jujuCredentialJpa;

	@Mock
	private JujuMetadataJpa jujuMetadataJpa;

	@Mock
	private JujuRemoteService jujuRemoteService;
	
	private JujuCloudService jujuCloudService;

	@BeforeEach
	void init() {
		jujuCloudService = new JujuCloudService(jujuCloudJpa, jujuCredentialJpa, jujuMetadataJpa, jujuRemoteService);
	}

	@Test
	public void testSaveCloud() throws Exception {
		JujuCloud jCloud = new JujuCloud();
		jujuCloudService.saveCloud(jCloud);
		assertTrue(true);
	}

	@Test
	public void testFindByMetadataName() throws Exception {
		String controllerName = "testcontroller1";
		JujuCloud jujuCloud1 = new JujuCloud();
		List<JujuCloud> expectedResult = Arrays.asList(jujuCloud1);
		when(jujuCloudJpa.findByMetadataName(controllerName,"PASS")).thenReturn(expectedResult);
		assertEquals(1, jujuCloudService.findByMetadataName(controllerName,"PASS").size());
	}
}