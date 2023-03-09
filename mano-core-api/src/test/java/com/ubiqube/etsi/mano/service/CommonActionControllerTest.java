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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.config.SecutiryConfig;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.common.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.CommonActionController;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.rest.model.ServerType;
import com.ubiqube.etsi.mano.utils.Version;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class CommonActionControllerTest {
	@Mock
	private ServersJpa serverJpa;
	@Mock
	private Environment env;
	@Mock
	private MapperFacade mapper;
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

	@Test
	void testBasicFail() throws Exception {
		final CommonActionController cac = new CommonActionController(serverJpa, env, createHttpGateway(), mapper, manoProperties, securityConfig, serverService);
		final Servers server = Servers.builder()
				.build();
		//
		when(serverService.buildServerAdapter(server)).thenThrow(RuntimeException.class);
		//
		cac.register(server, this::registerNfvoEx, Map.of());
		//
		assertEquals(PlanStatusType.FAILED, server.getServerStatus());
	}

	@Test
	void testBasicOk() throws Exception {
		final CommonActionController cac = new CommonActionController(serverJpa, env, createHttpGateway(), mapper, manoProperties, securityConfig, serverService);
		final Servers server = Servers.builder()
				.build();
		cac.register(server, this::registerNfvoEx, Map.of());
		//
		assertNull(server.getServerStatus());
	}

	@Test
	void testRegisterServerFail() throws Exception {
		final CommonActionController cac = new CommonActionController(serverJpa, env, createHttpGateway(), mapper, manoProperties, securityConfig, serverService);
		//
		final UUID id = UUID.randomUUID();
		final Map<String, Object> map = Map.of();
		assertThrows(GenericException.class, () -> cac.registerServer(id, map));
		//
	}

	@Test
	void testRegisterServerVnfmOk() throws Exception {
		final CommonActionController cac = new CommonActionController(serverJpa, env, createHttpGateway(), mapper, manoProperties, securityConfig, serverService);
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
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
		cac.registerServer(id, Map.of());
		//
		assertTrue(true);
	}

	@Test
	void testRegisterServerNfvoOk() throws Exception {
		final CommonActionController cac = new CommonActionController(serverJpa, env, createHttpGateway(), mapper, manoProperties, securityConfig, serverService);
		final Servers server = Servers.builder()
				.serverType(ServerType.NFVO)
				.remoteSubscriptions(Set.of())
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
		cac.registerServer(id, Map.of());
		//
		assertTrue(true);
	}

	private Servers registerNfvoEx(final ServerAdapter serverAdapter, final Map<String, Object> parameters) {
		return Servers.builder().build();
	}

	private List<HttpGateway> createHttpGateway() {
		return List.of(httpGateWay0, httpGateWay1);
	}

}
