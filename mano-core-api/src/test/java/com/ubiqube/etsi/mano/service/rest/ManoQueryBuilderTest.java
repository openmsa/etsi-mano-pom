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

import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;

@ExtendWith(MockitoExtension.class)
class ManoQueryBuilderTest {
	@Mock
	private QueryParameters manoClient;
	@Mock
	private HttpGateway httpGateway;
	@Mock
	private FluxRest fluxRest;

	private final Servers server = Servers.builder().url(URI.create("http://localhost/")).build();

	@Test
	void testDelete() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		when(fluxRest.delete(any(), any(), any())).thenReturn(null);
		mqb.delete();
		assertTrue(true);
	}

	private ManoQueryBuilder createService() {
		return new ManoQueryBuilder(manoClient);
	}

	@Test
	void testGetRaw() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		mqb.setWireOutClass(func);
		mqb.getRaw();
		assertTrue(true);
	}

	@Test
	void testGetSingleNull() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		mqb.setWireOutClass(func);
		mqb.getSingle();
		assertTrue(true);
	}

	@Test
	void testGetSingleNonNull() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		when(fluxRest.getWithReturn(any(), any(), any())).thenReturn(ResponseEntity.ok(""));
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		mqb.setWireOutClass(func);
		final BiFunction mapper = (x, y) -> "";
		mqb.setOutClass(mapper);
		mqb.getSingle();
		assertTrue(true);
	}

	@Test
	void testGetList() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		// when(fluxRest.get(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		final Function<HttpGateway, ParameterizedTypeReference<List<Class<?>>>> func2 = x -> new ParameterizedTypeReference<>() {
			// Nothing.
		};
		mqb.setWireOutClass(func);
		mqb.setInClassList(func2);
		final BiFunction mapper = (x, y) -> "";
		mqb.setOutClass(mapper);
		when(fluxRest.get(any(), (ParameterizedTypeReference) any(), any())).thenReturn(List.of());
		mqb.getList();
		assertTrue(true);
	}

	@Test
	void testPostObject() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		// when(fluxRest.get(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		final BiFunction<HttpGateway, Object, Object> func2 = (x, y) -> "";
		mqb.setWireInClass(func2);
		mqb.setWireOutClass(func);
		final BiFunction mapper = (x, y) -> "";
		mqb.setOutClass(mapper);
//		when(fluxRest.get(any(), (Class) any(), any())).thenReturn(List.of());
		mqb.post("");
		assertTrue(true);
	}

	@Test
	void testPostRawObject() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		// when(fluxRest.get(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		final BiFunction<HttpGateway, Object, Object> func2 = (x, y) -> "";
		mqb.setWireInClass(func2);
		mqb.setWireOutClass(func);
		mqb.postRaw("");
		assertTrue(true);
	}

	@Test
	void testPostRaw() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		final Function func2 = x -> "";
		when(manoClient.getRequestObject()).thenReturn(func2);
		mqb.setWireOutClass(func);
		mqb.postRaw();
		assertTrue(true);
	}

	@Test
	void testPost() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		// when(fluxRest.get(any(), any(), any())).thenReturn(null);
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		final Function func2 = x -> "";
		when(manoClient.getRequestObject()).thenReturn(func2);
		mqb.setWireOutClass(func);
		final BiFunction func4 = (x, y) -> "";
		mqb.setOutClass(func4);
		mqb.post();
		assertTrue(true);
	}

	@Test
	void testDownlad() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		mqb.download(Paths.get("/tmp/test"));
		assertTrue(true);
	}

	@Test
	void testDownlad2() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		mqb.download(Paths.get("/tmp/test"), null);
		assertTrue(true);
	}

	@Test
	void testUpload() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		mqb.upload(Paths.get("/tmp/test"), null);
		assertTrue(true);
	}

	@Test
	void testUpload2() throws Exception {
		final ManoQueryBuilder mqb = createService();
		final ServerAdapter serverAdp = new ServerAdapter(httpGateway, server, fluxRest);
		when(manoClient.getServer()).thenReturn(serverAdp);
		when(manoClient.getQueryType()).thenReturn(ApiVersionType.SOL002_VNFFM);
		when(manoClient.getFragment()).thenReturn("frg");
		when(fluxRest.uriBuilder()).thenReturn(UriComponentsBuilder.fromHttpUrl("http://localhost/"));
		//
		final Function<HttpGateway, Class<?>> func = x -> String.class;
		mqb.setWireOutClass(func);
		final BiFunction mapper = (x, y) -> "";
		mqb.setOutClass(mapper);
		mqb.patch(null, Map.of());
		assertTrue(true);
	}
}
