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
package com.ubiqube.etsi.mano.service.event;

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
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.rest.model.ServerType;
import com.ubiqube.etsi.mano.utils.Version;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class CommonActionControllerTest {
	@Mock
	private ServersJpa serversJpa;
	@Mock
	private Environment env;
	@Mock
	private HttpGateway hg;
	@Mock
	private MapperFacade mapper;
	@Mock
	private ManoProperties manoProperties;
	@Mock
	private ObjectProvider<SecutiryConfig> securityConfig;
	@Mock
	private ServerService serverService;
	@Mock
	private FluxRest fluxRest;

	@Test
	void testVnfm() throws Exception {
		final CommonActionController cac = new CommonActionController(serversJpa, env, List.of(hg), mapper, manoProperties, securityConfig, serverService);
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.serverType(ServerType.VNFM)
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = new ServerAdapter(hg, server, fluxRest);
		when(serverService.buildServerAdapter(server)).thenReturn(serverAdapter);
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://test/"));
		when(hg.getVersion()).thenReturn(new Version("4.3.2"));
		cac.registerServer(id, Map.of());
		assertTrue(true);
	}

	@Test
	void testNfvo() throws Exception {
		final CommonActionController cac = new CommonActionController(serversJpa, env, List.of(hg), mapper, manoProperties, securityConfig, serverService);
		final UUID id = UUID.randomUUID();
		final Servers server = Servers.builder()
				.remoteSubscriptions(Set.of())
				.version("4.3.2")
				.serverType(ServerType.NFVO)
				.build();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));
		final ServerAdapter serverAdapter = new ServerAdapter(hg, server, fluxRest);
		when(serverService.buildServerAdapter(server)).thenReturn(serverAdapter);
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://test/"));
		when(hg.getVersion()).thenReturn(new Version("4.3.2"));
		cac.registerServer(id, Map.of());
		assertTrue(true);
	}
}
