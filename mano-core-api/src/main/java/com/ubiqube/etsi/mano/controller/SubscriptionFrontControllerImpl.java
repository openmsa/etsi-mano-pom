/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.controller;

import static com.ubiqube.etsi.mano.Constants.getSingleField;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ubiqube.etsi.mano.dao.mano.Subscription;
import com.ubiqube.etsi.mano.dao.mano.subs.SubscriptionType;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.utils.Version;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class SubscriptionFrontControllerImpl implements SubscriptionFrontController {

	private static final String VERSION = "Version";

	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionFrontControllerImpl.class);

	private final SubscriptionService subscriptionService;

	private final MapperFacade mapper;

	private final ServerService serverService;

	public SubscriptionFrontControllerImpl(final SubscriptionService subscriptionService, final MapperFacade mapper, final ServerService serverService) {
		this.subscriptionService = subscriptionService;
		this.mapper = mapper;
		this.serverService = serverService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLinks, final SubscriptionType type) {
		final String filter = getSingleField(requestParams, "filter");
		final List<Subscription> list = subscriptionService.query(filter, type);
		final List<U> pkgms = mapper.mapAsList(list, clazz);
		pkgms.stream().forEach(makeLinks);
		return ResponseEntity.ok(pkgms);
	}

	@Override
	public <U> ResponseEntity<U> create(final Object subscriptionRequest, final Class<U> clazz, final Consumer<U> makeLinks, final Function<U, String> getSelfLink, final SubscriptionType type) {
		Subscription subscription = mapper.map(subscriptionRequest, Subscription.class);
		subscription.setSubscriptionType(type);
		setVersion(subscription);
		subscription = subscriptionService.save(subscription, type);
		final U res = mapper.map(subscription, clazz);
		makeLinks.accept(res);
		final String link = getSelfLink.apply(res);
		final URI location = URI.create(link);
		return ResponseEntity.created(location).body(res);
	}

	private void setVersion(final Subscription subscription) {
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof final ServletRequestAttributes sv) {
			final Optional<Version> ver = serverService.convertManoVersionToFe(subscription.getSubscriptionType(), sv.getRequest().getHeader(VERSION));
			if (ver.isPresent()) {
				subscription.setVersion(ver.get().toString());
			} else {
				LOG.warn("Unable to find version: {}/{}", subscription.getSubscriptionType(), sv.getRequest().getHeader(VERSION));
				subscription.setVersion("2.6.1");
			}
		} else {
			final String req = Optional.ofNullable(requestAttributes).map(RequestAttributes::getClass).map(Class::toString).orElse("[No Request]");
			LOG.warn("Unknow request {}", req);
		}
	}

	private static void setVersion(final HttpServletRequest request, final Subscription subscription) {
		final String hdr = request.getHeader(VERSION);
		subscription.setVersion(hdr);
	}

	@Override
	public ResponseEntity<Void> deleteById(@NotNull final String subscriptionId, final SubscriptionType type) {
		subscriptionService.delete(UUID.fromString(subscriptionId), type);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Class<U> clazz, final Consumer<U> makeLinks, final SubscriptionType type) {
		final Subscription subscription = subscriptionService.findById(UUID.fromString(subscriptionId), type);
		final U res = mapper.map(subscription, clazz);
		makeLinks.accept(res);
		return ResponseEntity.ok(res);
	}

}
