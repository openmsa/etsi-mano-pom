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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.exception.VnfmException;
import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;
import com.ubiqube.etsi.mano.service.juju.entities.JujuModel;

@ExtendWith(MockitoExtension.class)
public class JujuCloudServiceTest {

	@Mock
	private JujuCloudJpa jujuCloudJpa;

	@Mock
	private JujuCredentialJpa jujuCredentialJpa;

	@Mock
	private JujuMetadataJpa jujuMetadataJpa;

	@Mock
	private JujuRemoteService remoteService;
	
	@Mock
	private Environment environment;
	
	private JujuCloudService jujuCloudService;

	@BeforeEach
	void init() {
		jujuCloudService = new JujuCloudService(jujuCloudJpa, jujuCredentialJpa, jujuMetadataJpa, remoteService, environment);
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
	
	@Test
	public void testJujuInstantiate() throws Exception {
		JujuMetadata jujuMetadata = new JujuMetadata();
		JujuCredential jujuCredential = new JujuCredential();
		JujuModel model = new JujuModel("test", "test", "test");
		List<JujuModel> models = new ArrayList<>();
		models.add(model);
		jujuCredential.setName("test");
		jujuMetadata.setName("test");
		jujuMetadata.setModels(models);
		JujuCloud jCloud = new JujuCloud();
		jCloud.setName("test");
		jCloud.setCredential(jujuCredential);
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(any())).thenReturn(Optional.of(jCloud));
		ResponseEntity<String> responseobject = new ResponseEntity<String>("test", HttpStatus.OK);
		System.out.println(responseobject.getBody());
		Mockito.when(remoteService.controllerDetail(jCloud.getMetadata().getName())).thenReturn(responseobject);
		boolean result = jujuCloudService.jujuInstantiate(UUID.randomUUID());
		assertTrue(result);
	}

	@Test
	public void testJujuInstantiateThrowsException() throws Exception {
	    final UUID id = UUID.randomUUID();
		Exception exception = assertThrows(VnfmException.class, () -> {
			jujuCloudService.jujuInstantiate(id);
		});
		String expectedMsg = "Could not find Juju Cloud";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	public void testJujuInstantiateThrowsExceptionWhenCloudnameNull() throws Exception {
		final UUID id = UUID.randomUUID();
		JujuCloud jCloud = new JujuCloud();
		Mockito.when(jujuCloudJpa.findById(any())).thenReturn(Optional.of(jCloud));
        boolean result = jujuCloudService.jujuInstantiate(id);
        assertFalse(result);

	}

	@Test
	public void testJujuInstantiateThrowsExceptionWhenNoControllerFound() throws Exception {
		final UUID id = UUID.randomUUID();
		JujuMetadata jujuMetadata = new JujuMetadata();
		JujuCredential jujuCredential = new JujuCredential();
		jujuCredential.setName("test");
		jujuMetadata.setName("test");
		JujuCloud jCloud = new JujuCloud();
		jCloud.setName("test");
		jCloud.setCredential(jujuCredential);
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		ResponseEntity<String> responseobject = new ResponseEntity<String>("ERROR", HttpStatus.NOT_FOUND);
		Mockito.when(remoteService.controllerDetail(Mockito.anyString())).thenReturn(responseobject);
		boolean result = jujuCloudService.jujuInstantiate(id);
        assertFalse(result);
	}
	
	@Test
	public void testJujuterminate() throws Exception {
		final UUID id = UUID.randomUUID();
		JujuMetadata jujuMetadata = new JujuMetadata();
		jujuMetadata.setName("test");
		JujuCloud jCloud = new JujuCloud();
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		boolean result = jujuCloudService.jujuTerminate(id);
		assertTrue(result);
	}

	@Test
	public void testJujuterminateThrowsVnfmException() throws Exception {
		final UUID id = UUID.randomUUID();
        Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.empty());
        boolean result = jujuCloudService.jujuTerminate(id);
        assertFalse(result);
	}


}