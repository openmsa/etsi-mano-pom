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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ConfigurableApplicationContext;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.utils.Version;

import io.micrometer.observation.ObservationRegistry;

@ExtendWith(MockitoExtension.class)
class ServerServiceTest {
	@Mock
	private ServersJpa serversJpa;
	@Mock
	private EventManager eventManager;
	@Mock
	private ConfigurableApplicationContext springContext;
	@Mock
	HttpGateway hg;
	private ServerService ss;
	@Mock
	ObservationRegistry observationRegistry;

	@BeforeEach
	void setup() {
		ss = new ServerService(serversJpa, eventManager, List.of(hg), springContext);
	}

	@Test
	void testBasic() throws Exception {
		final Subscription servers = Subscription.builder()
				.callbackUri(URI.create("http://www.123.com/"))
				.build();
		when(springContext.getBean(ObservationRegistry.class)).thenReturn(observationRegistry);
		final ServerAdapter res = ss.buildServerAdapter(servers);
		assertNotNull(res);
	}

	@Test
	void testServerAdapter002() throws Exception {
		final Subscription servers = Subscription.builder()
				.callbackUri(URI.create("http://www.123.com/"))
				.version("3.4.5")
				.build();
		when(springContext.getBean(ObservationRegistry.class)).thenReturn(observationRegistry);
		when(hg.getVersion()).thenReturn(new Version(3, 4, 5));
		final ServerAdapter res = ss.buildServerAdapter(servers);
		assertNotNull(res);
	}

	@Test
	void testCreateDuplicateServer() throws Exception {
		final Optional<Servers> opt = Optional.ofNullable(new Servers());
		final Servers servers = Servers.builder()
				.url(URI.create("http://www.123.com/"))
				.build();
		when(serversJpa.findByUrl(servers.getUrl())).thenReturn(opt);
		assertThrows(GenericException.class, () -> ss.createServer(servers));
	}

	@Test
	void testCreateServer() throws Exception {
		final Servers servers = Servers.builder()
				.url(URI.create("http://www.123.com/"))
				.serverStatus(PlanStatusType.REMOVED)
				.build();
		ss.createServer(servers);
		assertTrue(true);
	}

	@Test
	void testApaderServers() throws Exception {
		final Servers servers = Servers.builder()
				.url(URI.create("http://www.123.com/"))
				.serverStatus(PlanStatusType.REMOVED)
				.version("3.3.1")
				.build();
		when(springContext.getBean(ObservationRegistry.class)).thenReturn(observationRegistry);
		when(hg.getVersion()).thenReturn(new Version(3, 3, 1));
		final ServerAdapter res = ss.buildServerAdapter(servers);
		assertNotNull(res);
	}

	@Test
	void testDeleteByIt() throws Exception {
		final UUID id = UUID.randomUUID();
		assertThrows(GenericException.class, () -> ss.deleteById(id));
	}

	@Test
	void testDeleteByOkIt() throws Exception {
		final UUID id = UUID.randomUUID();
		final Servers srv = Servers.builder()
				.url(URI.create("http://localhost/"))
				.remoteSubscriptions(Set.of())
				.build();
		final Optional<Servers> srvOpt = Optional.of(srv);
		when(serversJpa.findById(id)).thenReturn(srvOpt);
		ss.deleteById(id);
		assertTrue(true);
	}

	@Test
	void testDeleteByOkIt02() throws Exception {
		final UUID id = UUID.randomUUID();
		final RemoteSubscription rs01 = new RemoteSubscription();
		rs01.setRemoteServerId(id);
		rs01.setSubscriptionType(SubscriptionType.VNF);
		final Servers srv = Servers.builder()
				.id(id)
				.url(URI.create("http://localhost/"))
				.remoteSubscriptions(Set.of(rs01))
				.version("1.2.3")
				.build();
		final Optional<Servers> srvOpt = Optional.of(srv);
		when(serversJpa.findById(id)).thenReturn(srvOpt);
		when(hg.getVersion()).thenReturn(new Version(1, 2, 3));
		ss.deleteById(id);
		assertTrue(true);
	}

	@Test
	void testFindNearest() throws Exception {
		// NPE because server does not have an URL.
		assertThrows(GenericException.class, () -> ss.findNearestServer());
	}

	@Test
	void testFindNearest001() throws Exception {
		final UUID id = UUID.randomUUID();
		final Servers srv = Servers.builder()
				.id(id)
				.url(URI.create("http://localhost/"))
				.version("1.2.3")
				.build();
		when(serversJpa.findByServerStatusIn(List.of(PlanStatusType.SUCCESS))).thenReturn(List.of(srv, srv));
		when(hg.getVersion()).thenReturn(new Version(1, 2, 3));
		when(springContext.getBean(ObservationRegistry.class)).thenReturn(observationRegistry);
		ss.findNearestServer();
		assertTrue(true);
	}

	@Test
	void testRetryById() {
		final UUID id = UUID.randomUUID();
		ss.retryById(id);
		verify(eventManager, times(1)).sendAction(ActionType.REGISTER_SERVER, id);
	}

	@Test
	void testConvertFeVersionToMano() {
		final String res = ss.convertFeVersionToMano(ApiVersionType.SOL003_VNFFM, null);
		assertEquals("2.6.1", res);
	}

	@Test
	void testConvertFeVersionToMano002() {
		final String res = ss.convertFeVersionToMano(ApiVersionType.SOL003_VNFFM, "1.2.3");
		assertNull(res);
	}

	@Test
	void testConvertManoVersionToFe() {
		final Optional<Version> res = ss.convertManoVersionToFe(SubscriptionType.VNFLCM, null);
		assertTrue(res.isEmpty());
	}

	@Test
	void testSubscriptionTypeToApiVersion001() {
		final Optional<Version> res = ss.convertManoVersionToFe(SubscriptionType.VNFIND, null);
		assertTrue(res.isEmpty());
	}

	@Test
	void testSubscriptionTypeToApiVersion002() {
		final Optional<Version> res = ss.convertManoVersionToFe(SubscriptionType.VRQAN, null);
		assertTrue(res.isEmpty());
	}

	@Test
	void testSubscriptionTypeToApiVersion003() {
		final Optional<Version> res = ss.convertManoVersionToFe(SubscriptionType.VNFPM, null);
		assertTrue(res.isEmpty());
	}

	@Test
	void testSubscriptionTypeToApiVersionFail() {
		assertThrows(GenericException.class, () -> ss.convertManoVersionToFe(SubscriptionType.MEOPKG, null));
	}
}
