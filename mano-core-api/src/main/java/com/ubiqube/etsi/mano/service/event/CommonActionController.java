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
package com.ubiqube.etsi.mano.service.event;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponents;

import com.ubiqube.etsi.mano.auth.config.SecurityType;
import com.ubiqube.etsi.mano.auth.config.SecutiryConfig;
import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.config.LocalAuth;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersion;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.model.ApiVersionInformation;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class CommonActionController {
	private static final Logger LOG = LoggerFactory.getLogger(CommonActionController.class);

	private static final String SUBSCRIPTIONS = "subscriptions";
	private static final String NOTIFICATION_TYPES_0 = "notificationTypes[0]";
	private static final List<String> VNFM_FRAGMENT = Arrays.asList("vnflcm", "vnfpm", "vnffm", "vnfind", "vrqan", "vnfsnapshotpkgm");
	private static final List<String> NFVO_FRAGMENT = Arrays.asList("grant", "vnfpkgm", "nsd", "nslcm", "nspm", "nsfm", "nfvici", "vnfsnapshotpkgm", "lcmcoord");

	private final ServersJpa serversJpa;
	private final List<HttpGateway> httpGateway;
	private final MapperFacade mapper;
	private final ManoProperties manoProperties;
	private final ObjectProvider<SecutiryConfig> secutiryConfig;

	private final ServerService serverService;

	public CommonActionController(final ServersJpa serversJpa, final List<com.ubiqube.etsi.mano.service.HttpGateway> httpGateway,
			final MapperFacade mapper, final ManoProperties manoProperties, final ObjectProvider<SecutiryConfig> secutiryConfig, final ServerService serverService) {
		this.serversJpa = serversJpa;
		this.httpGateway = httpGateway;
		this.mapper = mapper;
		this.manoProperties = manoProperties;
		this.secutiryConfig = secutiryConfig;
		this.serverService = serverService;
	}

	public Servers registerServer(final UUID objectId, final Map<String, Object> parameters) {
		final Servers server = serversJpa.findById(objectId).orElseThrow(() -> new GenericException("Could not find server: " + objectId));
		if (server.getServerType() == ServerType.NFVO) {
			LOG.debug("Registrating an NFVO.");
			return register(server, this::registerNfvoEx, parameters);
		}
		LOG.debug("Registrating an VNFM.");
		return register(server, this::registerVnfmEx, parameters);
	}

	public Servers register(final Servers server, final BiFunction<ServerAdapter, Map<String, Object>, Servers> func, final Map<String, Object> parameters) {
		try {
			final ServerAdapter serverAdapter = serverService.buildServerAdapter(server);
			final Servers res = func.apply(serverAdapter, parameters);
			res.setFailureDetails(null);
			res.setServerStatus(PlanStatusType.SUCCESS);
			return serversJpa.save(res);
		} catch (final RuntimeException e) {
			LOG.error("", e);
			server.setFailureDetails(new FailureDetails(500, e.getMessage()));
			server.setServerStatus(PlanStatusType.FAILED);
			return serversJpa.save(server);
		}
	}

	private Servers registerVnfmEx(final ServerAdapter serverAdapter, final Map<String, Object> parameters) {
		final Servers server = serverAdapter.getServer();
		extractVersions(server);
		server.setRemoteSubscriptions(removeStalledSubscription(server.getRemoteSubscriptions()));
		server.setServerStatus(PlanStatusType.SUCCESS);
		final Set<RemoteSubscription> remoteSubscription = server.getRemoteSubscriptions();
		extractEndpoint(server);
		if (!isSubscribe(SubscriptionType.VNFIND, remoteSubscription)) {
			addSubscription(serverAdapter, this::vnfIndicatorValueChangeSubscribe, remoteSubscription);
		}
		return serversJpa.save(server);
	}

	private void extractVersions(final Servers server) {
		if (server.getServerType() == ServerType.VNFM) {
			extratVersion(VNFM_FRAGMENT, server);
		} else {
			extratVersion(NFVO_FRAGMENT, server);
		}
	}

	private void extratVersion(final List<String> fragments, final Servers server) {
		final Set<ApiVersion> versions = new LinkedHashSet<>();
		server.setVersions(versions);
		fragments.forEach(x -> Optional.ofNullable(getVersion(x, server)).ifPresent(versions::add));
	}

	private @Nullable ApiVersion getVersion(final String fragment, final Servers server) {
		try {
			final Map<String, Object> uriVariables = Map.of("fragment", fragment);
			final FluxRest rest = new FluxRest(server);
			final UriComponents uri = rest.uriBuilder().pathSegment("{fragment}/api_versions")
					.buildAndExpand(uriVariables);
			final ApiVersionInformation res = rest.get(uri.toUri(), ApiVersionInformation.class, null);
			return mapper.map(res, ApiVersion.class);
		} catch (final RuntimeException e) {
			LOG.info("Error fetching " + fragment, e);
		}
		return null;
	}

	private Servers registerNfvoEx(final ServerAdapter serverAdapter, final Map<String, Object> parameters) {
		final Servers server = serverAdapter.getServer();
		extractVersions(server);
		server.setRemoteSubscriptions(removeStalledSubscription(server.getRemoteSubscriptions()));
		final Set<RemoteSubscription> remoteSubscription = server.getRemoteSubscriptions();
		// We probably need to split the subscription in 2.
		if (!isSubscribe(SubscriptionType.VNF, remoteSubscription)) {
			addSubscription(serverAdapter, this::vnfPackageOnboardingSubscribe, remoteSubscription);
			addSubscription(serverAdapter, this::vnfPackageChangeSubscribe, remoteSubscription);
			extractEndpoint(server);
			return serversJpa.save(server);
		}
		return server;
	}

	private static void addSubscription(final ServerAdapter serverAdapter, final Function<ServerAdapter, Subscription> func, final Set<RemoteSubscription> remoteSubscription) {
		final Subscription subs = func.apply(serverAdapter);
		final RemoteSubscription rmt = reMap(subs, serverAdapter);
		remoteSubscription.add(rmt);
	}

	private void extractEndpoint(final Servers server) {
		final String prefix = convert(server.getSubscriptionType());
		final List<ApiVersionType> arr = getEnum(prefix);
		for (final ApiVersionType element : arr) {
			final ApiVersion version = getVersion(server, element);
			server.addVersion(version);
		}
	}

	private static String convert(final SubscriptionType subscriptionType) {
		return switch (subscriptionType) {
		case VNF -> "SOL_003";
		case NSD -> "SOL_005";
		default -> throw new IllegalArgumentException("Unexpected value: " + subscriptionType);
		};
	}

	private static RemoteSubscription reMap(final Subscription subscription, final ServerAdapter serverAdapter) {
		final Servers server = serverAdapter.getServer();
		return RemoteSubscription.builder()
				.remoteSubscriptionId(subscription.getId().toString())
				.subscriptionType(subscription.getSubscriptionType())
				.remoteServerId(server.getId())
				.build();
	}

	private Subscription vnfPackageOnboardingSubscribe(final ServerAdapter serverAdapter) {
		return vnfPackageSubscribe(serverAdapter, "VnfPackageOnboardingNotification", "/vnfpkgm/v1/notification/onboarding");
	}

	private Subscription vnfPackageChangeSubscribe(final ServerAdapter serverAdapter) {
		return vnfPackageSubscribe(serverAdapter, "VnfPackageChangeNotification", "/vnfpkgm/v1/notification/change");
	}

	private Subscription vnfPackageSubscribe(final ServerAdapter serverAdapter, final String eventName, final String url) {
		final FluxRest rest = serverAdapter.rest();
		final Servers server = serverAdapter.getServer();
		final List<FilterAttributes> filters = List.of(FilterAttributes.of(NOTIFICATION_TYPES_0, eventName));
		final Subscription subsOut = createSubscriptionWithFilter(ApiTypesEnum.SOL003, url, SubscriptionType.NSDVNF, filters, server.getLocalUser());
		final URI uri = serverAdapter.getUriFor(ApiVersionType.SOL003_VNFPKGM, SUBSCRIPTIONS);
		final HttpGateway hg = selectGateway(server);
		final Class<?> clazz = hg.getVnfPackageSubscriptionClass();
		final Class<?> clazzWire = hg.getPkgmSubscriptionRequest();
		final String v = hg.getHeaderVersion(ApiVersionType.SOL003_VNFPKGM).orElse(null);
		final Subscription res = postSubscription(rest, uri, subsOut, clazzWire, clazz, v);
		res.setSubscriptionType(SubscriptionType.VNF);
		return res;
	}

	private Subscription vnfIndicatorValueChangeSubscribe(final ServerAdapter serverAdapter) {
		final FluxRest rest = serverAdapter.rest();
		final Servers server = serverAdapter.getServer();
		final List<FilterAttributes> filters = List.of(FilterAttributes.of(NOTIFICATION_TYPES_0, "VnfIndicatorValueChangeNotification"));
		final Subscription subsOut = createSubscriptionWithFilter(ApiTypesEnum.SOL003, "/vnfind/v1/notification/value-change", SubscriptionType.VNFIND, filters, server.getLocalUser());
		final URI uri = serverAdapter.getUriFor(ApiVersionType.SOL003_VNFIND, SUBSCRIPTIONS);
		final HttpGateway hg = selectGateway(server);
		final Class<?> clazz = hg.getVnfIndicatorValueChangeSubscriptionClass();
		final Class<?> clazzWire = hg.getVnfIndicatorValueChangeSubscriptionRequest();
		final String v = hg.getHeaderVersion(ApiVersionType.SOL003_VNFIND).orElse(null);
		final Subscription res = postSubscription(rest, uri, subsOut, clazzWire, clazz, v);
		res.setSubscriptionType(SubscriptionType.VNFIND);
		return res;
	}

	private Subscription createSubscriptionWithFilter(final ApiTypesEnum apiType, final String url, final SubscriptionType subscriptionType, final List<FilterAttributes> filters, final LocalAuth localAuth) {
		final AuthentificationInformations auth = createAuthInformation(localAuth);
		return Subscription.builder()
				.api(apiType)
				.authentication(auth)
				// XXX: Mano property must be a URI.
				.callbackUri(URI.create(manoProperties.getFrontendUrl() + url))
				.subscriptionType(subscriptionType)
				.filters(filters)
				.build();
	}

	private Subscription postSubscription(final FluxRest rest, final URI uri, final Object subsOut, final Class<?> clazzWire, final Class<?> clazz, @Nullable final String version) {
		final Object wire = mapper.map(subsOut, clazzWire);
		final Object res = rest.post(uri, wire, clazz, version);
		return mapper.map(res, Subscription.class);
	}

	private static boolean isSubscribe(final SubscriptionType subscriptionType, final Set<RemoteSubscription> remoteSubscriptions) {
		return remoteSubscriptions.stream().anyMatch(x -> x.getSubscriptionType() == subscriptionType);
	}

	private Set<RemoteSubscription> removeStalledSubscription(final Set<RemoteSubscription> remoteSubscriptions) {
		return remoteSubscriptions.stream()
				.filter(this::checkRemoteSubscription)
				.collect(Collectors.toSet());

	}

	private boolean checkRemoteSubscription(final RemoteSubscription remoteSubscription) {
		final Servers server = serverService.findById(remoteSubscription.getRemoteServerId());
		final ServerAdapter sa = serverService.buildServerAdapter(server);
		final ManoClient mc = new ManoClient(mapper, sa);
		try {
			final Subscription res = mc.vnfPackage().subscription().find(getSafeUUID(remoteSubscription.getRemoteSubscriptionId()));
			return res != null;
		} catch (final WebClientResponseException.NotFound e) {
			LOG.trace("", e);
			return false;
		}
	}

	@Nullable
	private AuthentificationInformations createAuthInformation(final LocalAuth locAuth) {
		final SecutiryConfig sec = secutiryConfig.getIfAvailable();
		if (sec == null) {
			return null;
		}
		final AuthentificationInformations auth = new AuthentificationInformations();
		if (sec.getSecurityType() == SecurityType.OAUTH2) {
			final AuthParamOauth2 oauth2 = AuthParamOauth2.builder()
					.clientId(locAuth.getClientId())
					.clientSecret(locAuth.getClientSecret())
					.tokenEndpoint(locAuth.getTokenEndpoint())
					.build();
			auth.setAuthParamOauth2(oauth2);
			auth.setAuthType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS));
		} else {
			final AuthParamBasic basic = AuthParamBasic.builder()
					.userName("nfvo")
					.password(UUID.randomUUID().toString())
					.build();
			auth.setAuthParamBasic(basic);
			auth.setAuthType(List.of(AuthType.BASIC));
		}
		return auth;
	}

	static List<ApiVersionType> getEnum(final String prefix) {
		final ApiVersionType[] arr = ApiVersionType.values();
		return Arrays.stream(arr).filter(x -> x.name().startsWith(prefix)).toList();
	}

	private ApiVersion getVersion(final Servers server, final ApiVersionType type) {
		final Map<String, Object> uriVariables = Map.of("module", type.toString());
		final FluxRest rest = new FluxRest(server);
		final UriComponents uri = rest.uriBuilder().pathSegment("{module}/api_versions")
				.buildAndExpand(uriVariables);
		final ApiVersionInformation res = rest.get(uri.toUri(), ApiVersionInformation.class, null);
		return mapper.map(res, ApiVersion.class);
	}

	private HttpGateway selectGateway(final Servers server) {
		return httpGateway.stream()
				.filter(x -> x.getVersion().toString().equals(server.getVersion()))
				.findAny()
				.orElseThrow(() -> new GenericException("Unable to find version: " + server.getVersion()));
	}

}
