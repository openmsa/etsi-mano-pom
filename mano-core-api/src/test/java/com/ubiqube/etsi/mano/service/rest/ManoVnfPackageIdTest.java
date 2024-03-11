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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackageId;

@ExtendWith(MockitoExtension.class)
class ManoVnfPackageIdTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoQueryBuilder manoQueryBuilder;

	@Test
	void testFind() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		srv.find();
		assertTrue(true);
	}

	@Test
	void testDownloadContent() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		srv.downloadContent(null);
		assertTrue(true);
	}

	@Test
	void testDelete() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		srv.delete();
		assertTrue(true);
	}

	@Test
	void testOnboard() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		srv.onboard(null, null);
		assertTrue(true);
	}

	@Test
	void testDownloadArtifact() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		srv.downloadArtefact(null, null);
		assertTrue(true);
	}

	@Test
	void testPatch() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		srv.patch("", Map.of());
		assertTrue(true);
	}

	@Test
	void testWaitOnboarding() throws Exception {
		final ManoVnfPackageId srv = new ManoVnfPackageId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		final VnfPackage pkg = new VnfPackage();
		pkg.setOnboardingState(OnboardingStateType.ONBOARDED);
		when(manoQueryBuilder.getSingle()).thenReturn(pkg);
		srv.waitOnboading();
		assertTrue(true);
	}
}
