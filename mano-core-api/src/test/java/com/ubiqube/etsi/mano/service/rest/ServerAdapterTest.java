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

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServerAdapterTest {

    @Mock
    private HttpGateway httpGateway;

    @Mock
    private FluxRest fluxRest;

    @Mock
    private Servers servers;

    private ServerAdapter serverAdapter;

    @BeforeEach
    void init() {
        this.serverAdapter = new ServerAdapter(httpGateway, servers, fluxRest);
    }

    @Test
    public void getUriFor_nominalCase() {
        ApiVersionType apiVersionType = ApiVersionType.SOL003_VNFIND;
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString("http://localhost/mano") ;
        when(this.httpGateway.getUrlFor(apiVersionType)).thenReturn("/sol003/v1/");
        when(this.fluxRest.uriBuilder()).thenReturn(uriComponentsBuilder);
        final URI uriFor = serverAdapter.getUriFor(apiVersionType, "/vnfind");
        assertEquals("http://localhost/mano/sol003/v1/vnfind", uriFor.toString());
    }

    @Test
    public void getUriFor_nominalCase_v2() {
        ApiVersionType apiVersionType = ApiVersionType.SOL003_VNFIND;
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString("http://localhost/mano") ;
        when(this.httpGateway.getUrlFor(apiVersionType)).thenReturn("/sol003/v1/");
        when(this.fluxRest.uriBuilder()).thenReturn(uriComponentsBuilder);
        final URI uriFor = serverAdapter.getUriFor(apiVersionType, "vnfind");
        assertEquals("http://localhost/mano/sol003/v1/vnfind", uriFor.toString());
    }

    @Test
    public void getUriFor_nullCase() {
        ApiVersionType apiVersionType = ApiVersionType.SOL003_VNFIND;
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString("http://localhost/mano") ;
        when(this.httpGateway.getUrlFor(apiVersionType)).thenReturn("sol003/v1/");
        when(this.fluxRest.uriBuilder()).thenReturn(uriComponentsBuilder);
        final URI uriFor = serverAdapter.getUriFor(apiVersionType, null);
        assertEquals("http://localhost/mano/sol003/v1/", uriFor.toString());
    }
}
