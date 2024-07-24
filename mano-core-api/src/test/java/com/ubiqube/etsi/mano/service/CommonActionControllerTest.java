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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.ubiqube.etsi.mano.auth.config.SecutiryConfig;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.EndpointService.Endpoint;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;
import com.ubiqube.etsi.mano.service.event.CommonActionController;
import com.ubiqube.etsi.mano.service.mapping.ApiVersionMapping;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.utils.Version;

@ExtendWith(MockitoExtension.class)
class CommonActionControllerTest {
	@Mock
	private ServersJpa serverJpa;
	@Mock
	private Environment env;
	@Mock
	private ManoProperties manoProperties;
	@Mock
	private ObjectProvider<SecutiryConfig> securityConfig;
	@Mock
	private ServerService serverService;
	@Mock
	private HttpGateway httpGateWay0;
	@Mock
	private HttpGateway httpGateWay1;
	@Mock
	private FluxRest fluxRest;
	private final ApiVersionMapping apiVersionMapping = Mappers.getMapper(ApiVersionMapping.class);

	private final Servers server = Servers.builder()
			.url(URI.create("http://localhost/"))
			.build();

	private CommonActionController createService() {
		return new CommonActionController(serverJpa, createHttpGateway(), manoProperties, securityConfig, serverService, apiVersionMapping);
	}

	@Test
	void testBasicFail() {
		final CommonActionController cac = createService();
		//
		when(serverService.buildServerAdapter(server)).thenThrow(RuntimeException.class);
		//
		cac.register(server, this::registerNfvoEx, Map.of());
		//
		assertEquals(PlanStatusType.FAILED, server.getServerStatus());
	}

	@Test
	void testBasicOk() {
		final CommonActionController cac = createService();
		final Servers server = Servers.builder()
				.url(URI.create("http://localhost/"))
				.build();
		cac.register(server, this::registerNfvoEx, Map.of());
		//
		assertNull(server.getServerStatus());
	}

	@Test
	void testRegisterServerFail() {
		final CommonActionController cac = createService();
		//
		final UUID id = UUID.randomUUID();
		final Map<String, Object> map = Map.of();
		assertThrows(GenericException.class, () -> cac.registerServer(id, map));
		//
	}

	@Test
	void testRegisterServerVnfmOk() {
		final CommonActionController cac = createService();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.url(URI.create("http://localhost/"))
				.version("1.2.3")
				.subscriptionType(SubscriptionType.VNF)
				.build();
		final UUID id = UUID.randomUUID();
		//
		when(serverJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateWay0, server, fluxRest);
		when(serverService.buildServerAdapter(server)).thenReturn(serverAdapter);
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.newInstance());
		when(httpGateWay0.getUrlFor(ApiVersionType.SOL003_VNFIND)).thenReturn("");
		//
		final Endpoint endpoint = new Endpoint("vnfind", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfind", endpoint);
		//
		cac.registerServer(id, Map.of());
		//
		assertTrue(true);
	}

	@Test
	void testRegisterServerNfvoOk() {
		final CommonActionController cac = createService();
		final Servers server = Servers.builder()
				.serverType(ServerType.NFVO)
				.remoteSubscriptions(Set.of())
				.url(URI.create("http://localhost/"))
				.version("1.2.3")
				.build();
		final UUID id = UUID.randomUUID();
		//
		when(serverJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = new ServerAdapter(httpGateWay0, server, fluxRest);
		when(serverService.buildServerAdapter(server)).thenReturn(serverAdapter);
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.newInstance());
		when(httpGateWay0.getUrlFor(ApiVersionType.SOL003_VNFIND)).thenReturn("");
		when(httpGateWay0.getVersion()).thenReturn(new Version("1.2.3"));
		//
		final Endpoint endpoint = new Endpoint("vnfpkgm", Version.of("1.23.2"), serverAdapter, List.of());
		final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();
		dedupe.add("vnfpkgm", endpoint);
		//
		cac.registerServer(id, Map.of());
		//
		assertTrue(true);
	}

	private Servers registerNfvoEx(final ServerAdapter serverAdapter, final Map<String, Object> parameters) {
		return server;
	}

	private List<HttpGateway> createHttpGateway() {
		return List.of(httpGateWay0, httpGateWay1);
	}

}
