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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.ManoServiceInterface;
import com.ubiqube.etsi.mano.service.EndpointService.Endpoint;
import com.ubiqube.etsi.mano.utils.Version;

@ExtendWith(MockitoExtension.class)
class InterfaceInfoServiceTest {
	@Mock
	private EndpointService endpointService;
	OAuth2ResourceServerProperties oAuth2Props = new OAuth2ResourceServerProperties();
	private final ManoProperties props = new ManoProperties();

	@Test
	void testBasic() throws Exception {
		final InterfaceInfoService iis = new InterfaceInfoService(endpointService, props, oAuth2Props);
		final MultiValueMap<String, Endpoint> endp = new LinkedMultiValueMap<>();
		//
		when(endpointService.getEndpoints()).thenReturn(endp);
		final List<ManoServiceInterface> res = iis.getInterfaceEndpoint();
		assertNotNull(res);
	}

	@Test
	void testName() throws Exception {
		final InterfaceInfoService iis = new InterfaceInfoService(endpointService, props, oAuth2Props);
		final MultiValueMap<String, Endpoint> endp = new LinkedMultiValueMap<>();
		final Endpoint v01 = new Endpoint("part", new Version("1.2.3"), new Object(), new ArrayList<>());
		endp.add("endpoint01", v01);
		//
		when(endpointService.getEndpoints()).thenReturn(endp);
		final List<ManoServiceInterface> res = iis.getInterfaceEndpoint();
		assertNotNull(res);
		assertEquals(1, res.size());
		final ManoServiceInterface r = res.get(0);
		assertEquals("part-1.2.3", r.getName());
		assertEquals("part", r.getType());
		assertEquals("1.2.3", r.getStandardVersion());
		assertEquals("1.2.3", r.getProviderSpecificApiVersion());
		assertEquals("1.2.3", r.getApiVersion());
		System.out.println(res);
	}
}
