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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.auth.config.SecurityType;
import com.ubiqube.etsi.mano.auth.config.SecutiryConfig;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.config.LocalAuth;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.EndpointService.Endpoint;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.mapping.ApiVersionMapping;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.utils.Version;

@ExtendWith(MockitoExtension.class)
class CommonActionControllerTest {
	@Mock
	private ServersJpa serversJpa;
	@Mock
	private Environment env;
	@Mock
	private HttpGateway hg;
	@Mock
	private ManoProperties manoProperties;
	@Mock
	private ObjectProvider<SecutiryConfig> securityConfigProvider;
	@Mock
	private SecutiryConfig securityConfig;
	@Mock
	private ServerService serverService;
	@Mock
	private FluxRest fluxRest;
	private final ApiVersionMapping apiVersionMapping = Mappers.getMapper(ApiVersionMapping.class);

	@Test
	void testVnfm() throws Exception {
		final CommonActionController cac = createService();
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.serverType(ServerType.VNFM)
				.subscriptionType(SubscriptionType.VNF)
				.url(URI.create("http://localhost/"))
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = createServerAdapter(server);
		//
		final Endpoint endpoint = new Endpoint("vnfind", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfind", endpoint);
		final Subscription subsc = new Subscription();
		subsc.setId(UUID.randomUUID());
		subsc.setSubscriptionType(SubscriptionType.VNF);
		when(serversJpa.save(any())).thenReturn(server);
		when(fluxRest.post(any(), any(), any(), any())).thenReturn(subsc);
		when(hg.mapToVnfIndicatorSubscription(any())).thenReturn(subsc);
		final Servers res = cac.registerServer(id, Map.of());
		assertNotNull(res);
		assertEquals(PlanStatusType.SUCCESS, res.getServerStatus());
	}

	private CommonActionController createService() {
		return new CommonActionController(serversJpa, List.of(hg), manoProperties, securityConfigProvider, serverService, apiVersionMapping);
	}

	@Test
	void testVnfmFail() throws Exception {
		final CommonActionController cac = createService();
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.url(URI.create("http://localhost/"))
				.serverType(ServerType.VNFM)
				.subscriptionType(SubscriptionType.VNF)
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = createServerAdapter(server);
		//
		final Endpoint endpoint = new Endpoint("vnfind", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfind", endpoint);
//		when(hg.getVersion()).thenReturn(new Version("4.3.2"));
		when(serversJpa.save(any())).thenReturn(server);
		final Servers res = cac.registerServer(id, Map.of());
		assertNotNull(res);
		assertEquals(PlanStatusType.FAILED, res.getServerStatus());
	}

	@Test
	void testNfvo() throws Exception {
		final CommonActionController cac = createService();
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.serverType(ServerType.NFVO)
				.url(URI.create("http://localhost/"))
				.subscriptionType(SubscriptionType.NSD)
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = createServerAdapter(server);
		//
		final Endpoint endpoint = new Endpoint("vnfpkgm", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfpkgm", endpoint);
		//
		final Subscription subsc = new Subscription();
		subsc.setId(UUID.randomUUID());
		subsc.setSubscriptionType(SubscriptionType.NSD);
		when(serversJpa.save(any())).thenReturn(server);
		// Security
		when(securityConfigProvider.getIfAvailable()).thenReturn(securityConfig);
		when(fluxRest.post(any(), any(), any(), any())).thenReturn(subsc);
		when(hg.mapToPkgmSubscription(any())).thenReturn(subsc);
		final Servers res = cac.registerServer(id, Map.of());
		assertEquals(PlanStatusType.SUCCESS, res.getServerStatus());
	}

	@Test
	void testNfvoCoverAuth2() throws Exception {
		final CommonActionController cac = createService();
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.url(URI.create("http://localhost/"))
				.serverType(ServerType.NFVO)
				.subscriptionType(SubscriptionType.NSD)
				.localUser(new LocalAuth())
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = createServerAdapter(server);
		//
		final Endpoint endpoint = new Endpoint("vnfpkgm", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfpkgm", endpoint);
		//
		final Subscription subsc = new Subscription();
		subsc.setId(UUID.randomUUID());
		subsc.setSubscriptionType(SubscriptionType.NSD);
		when(serversJpa.save(any())).thenReturn(server);
		// Security
		when(securityConfigProvider.getIfAvailable()).thenReturn(securityConfig);
		when(securityConfig.getSecurityType()).thenReturn(SecurityType.OAUTH2);
		when(fluxRest.post(any(), any(), any(), any())).thenReturn(subsc);
		when(hg.mapToPkgmSubscription(any())).thenReturn(subsc);
		final Servers res = cac.registerServer(id, Map.of());
		assertEquals(PlanStatusType.SUCCESS, res.getServerStatus());
	}

	private ServerAdapter createServerAdapter(final Servers server) {
		final ServerAdapter serverAdapter = new ServerAdapter(hg, server, fluxRest);
		when(serverService.buildServerAdapter(server)).thenReturn(serverAdapter);
		when(hg.getUrlFor(any())).thenReturn("http://localhost/");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://test/"));
		return serverAdapter;
	}
}
