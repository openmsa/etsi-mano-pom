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

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
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
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.DynamicDiscovery;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.OauthServerInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.ProvidedConfiguration;
import com.ubiqube.etsi.mano.service.EndpointService.Endpoint;
import com.ubiqube.etsi.mano.service.EndpointService.HttpMethod;

@Service
public class InterfaceInfoService {

	private final EndpointService endpointService;
	private final URI baseUrl;
	private final OAuth2ResourceServerProperties oAuth2Props;

	public InterfaceInfoService(final EndpointService endpointService, final ManoProperties props, final OAuth2ResourceServerProperties oAuth2Props) {
		this.endpointService = endpointService;
		this.baseUrl = props.getFrontendUrl();
		this.oAuth2Props = oAuth2Props;
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
				.apiMajorVersion(x.version().getMajor() + "")
				.apiName(x.part() + "-" + x.version())
				.apiRoot(x.part())
				.apiUri(baseUrl + "/" + x.part() + "/v" + x.version().getMajor() + "/")
				.build());
		cmii.setApiVersion(x.version().toString());
		cmii.setInterfaceState(defaultStates());
		cmii.setMaxConcurrentIntOpNumber(null);
		cmii.setMaxConcurrentIntOpNumber(null);
		cmii.setMetadata(Map.of());
		cmii.setName(x.part() + "-" + x.version());
		cmii.setProviderSpecificApiVersion(null);
		cmii.setSecurityInfo(createSecInfo());
		cmii.setStandardVersion(x.version().toString());
		cmii.setProviderSpecificApiVersion(x.version().toString());
		final List<SupportedOperations> ope = createSupportedOperation(x.lst());
		cmii.setSupportedOperations(ope);
		cmii.setType(x.part());
		return cmii;
	}

	private static List<SupportedOperations> createSupportedOperation(final List<HttpMethod> lst) {
		return lst.stream().map(x -> {
			final SupportedOperations so = new SupportedOperations();
			so.setOperationName(x.action() + x.payh());
			return so;
		}).toList();
	}

	/**
	 * Specified in clause 5.1.3 of ETSI GS NFV-SEC 022.
	 *
	 * @return
	 */
	private ClientInterfaceSecurityInfo createSecInfo() {
		final ClientInterfaceSecurityInfo sec = new ClientInterfaceSecurityInfo();
		sec.setAuthType(List.of(AuthTypeEnum.OAUTH2));
		final OauthServerInfo aouthInfo = new OauthServerInfo();
		final ProvidedConfiguration conf = new ProvidedConfiguration();
		conf.setAuthServerId(oAuth2Props.getJwt().getIssuerUri());
		aouthInfo.setProvidedConfiguration(conf);
		final DynamicDiscovery dynConf = new DynamicDiscovery();
		dynConf.setWebFingerHost(null);
		aouthInfo.setDynamicDiscovery(dynConf);
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
