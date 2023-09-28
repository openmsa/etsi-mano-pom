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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.ApiEndpoint;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.InterfaceState;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.ManoServiceInterface;
import com.ubiqube.etsi.mano.dao.mano.sol009.iface.SupportedOperations;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.AdministrativeState;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.AuthTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.ClientInterfaceSecurityInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.OauthServerInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.ProvidedConfiguration;
import com.ubiqube.etsi.mano.service.EndpointService.Endpoint;

@Service
public class InterfaceInfoService {

	private final EndpointService endpointService;
	private final URI baseUrl;

	public InterfaceInfoService(final EndpointService endpointService, final ManoProperties props) {
		this.endpointService = endpointService;
		this.baseUrl = props.getFrontendUrl();
	}

	public List<ManoServiceInterface> getInterfaceEndpoint() {
		final MultiValueMap<String, Endpoint> ep = endpointService.getEndpoints();
		return ep.values().stream()
				.map(this::convertToManoInterface)
				.flatMap(List::stream)
				.toList();
	}

	private List<ManoServiceInterface> convertToManoInterface(final List<Endpoint> x) {
		return x.stream().map(this::convert).toList();
	}

	private ManoServiceInterface convert(final Endpoint x) {
		final ManoServiceInterface cmii = new ManoServiceInterface();
		cmii.setApiEndpoint(ApiEndpoint.builder()
				.apiMajorVersion(x.versoin().getMajor() + "")
				.apiName(x.part() + "-" + x.versoin())
				.apiRoot(x.part())
				.apiUri(baseUrl + "/" + x.part() + "/v" + x.versoin().getMajor() + "/")
				.build());
		cmii.setApiVersion(x.versoin().toString());
		cmii.setInterfaceState(defaultStates());
		cmii.setMaxConcurrentIntOpNumber(null);
		cmii.setMaxConcurrentIntOpNumber(null);
		cmii.setMetadata(null);
		cmii.setName(x.part() + "-" + x.versoin());
		cmii.setProviderSpecificApiVersion(null);
		cmii.setSecurityInfo(createSecInfo());
		cmii.setStandardVersion(x.versoin().toString());
		cmii.setProviderSpecificApiVersion(x.versoin().toString());
		final List<SupportedOperations> ope = new ArrayList<>();
		final SupportedOperations so = new SupportedOperations();
		so.setOperationName("opName");
		ope.add(so);
		cmii.setSupportedOperations(ope);
		cmii.setType(x.part());
		return cmii;
	}

	private static ClientInterfaceSecurityInfo createSecInfo() {
		final ClientInterfaceSecurityInfo sec = new ClientInterfaceSecurityInfo();
		sec.setAuthType(List.of(AuthTypeEnum.OAUTH2));
		final OauthServerInfo aouthInfo = new OauthServerInfo();
		final ProvidedConfiguration conf = new ProvidedConfiguration();
		conf.setAuthServerId(null);
		aouthInfo.setProvidedConfiguration(conf);
		aouthInfo.setTlsCipherSuites(Set.of("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256"));
		sec.setOauthServerInfo(aouthInfo);
		return sec;
	}

	private static InterfaceState defaultStates() {
		final InterfaceState ret = new InterfaceState();
		ret.setAdministrativeState(AdministrativeState.UNLOCKED);
		ret.setOperationalState(OperationalStateType.STARTED);
		ret.setUsageState(UsageStateEnum.IN_USE);
		return ret;
	}
}
