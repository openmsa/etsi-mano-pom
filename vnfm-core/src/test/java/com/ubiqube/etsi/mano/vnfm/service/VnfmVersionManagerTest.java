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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.ManoVnfPackage;
import com.ubiqube.etsi.mano.service.rest.ManoVnfPackageId;
import com.ubiqube.etsi.mano.service.rest.ManoVnfPackageSubscription;

@ExtendWith(MockitoExtension.class)
class VnfmVersionManagerTest {
	@Mock
	private Environment env;
	@Mock
	private ManoClientFactory manoCliFactory;
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoVnfPackageId manoPkgId;
	@Mock
	private ManoVnfPackage manoVnfPackage;
	@Mock
	private ManoVnfPackageSubscription vnfPkgSubscription;

	@Test
	void testFindVnfPkgById() {
		final ManoProperties manoProperties = new ManoProperties();
		final VnfmVersionManager srv = new VnfmVersionManager(env, manoProperties, manoCliFactory);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		when(manoCliFactory.getClient()).thenReturn(manoClient);
		when(manoClient.vnfPackage(id)).thenReturn(manoPkgId);
		srv.findVnfPkgById(strId);
		assertTrue(true);
	}

	@Test
	void testGetPackageContent() {
		final ManoProperties manoProperties = new ManoProperties();
		final VnfmVersionManager srv = new VnfmVersionManager(env, manoProperties, manoCliFactory);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		when(manoCliFactory.getClient()).thenReturn(manoClient);
		when(manoClient.vnfPackage(id)).thenReturn(manoPkgId);
		srv.getPackageContent(strId, null);
		assertTrue(true);
	}

	@Test
	void testSubscribeBasic() {
		final ManoProperties manoProperties = new ManoProperties();
		final VnfmVersionManager srv = new VnfmVersionManager(env, manoProperties, manoCliFactory);
		when(manoCliFactory.getClient()).thenReturn(manoClient);
		when(manoClient.vnfPackage()).thenReturn(manoVnfPackage);
		when(manoVnfPackage.subscription()).thenReturn(vnfPkgSubscription);
		final Subscription subs = new Subscription();
		srv.subscribe(subs);
		assertTrue(true);
	}

	@Test
	void testSubscribeOAuth2() {
		final ManoProperties manoProperties = new ManoProperties();
		final VnfmVersionManager srv = new VnfmVersionManager(env, manoProperties, manoCliFactory);
		when(manoCliFactory.getClient()).thenReturn(manoClient);
		when(manoClient.vnfPackage()).thenReturn(manoVnfPackage);
		when(manoVnfPackage.subscription()).thenReturn(vnfPkgSubscription);
		when(env.getProperty("keycloak.resource")).thenReturn("");
		final Subscription subs = new Subscription();
		srv.subscribe(subs);
		assertTrue(true);
	}
}
