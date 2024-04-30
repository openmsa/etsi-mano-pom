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

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.service.HttpGateway;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoQueryBuilder<U, R> {
	@Nonnull
	private final QueryParameters client;
	@Nullable
	private Function<HttpGateway, ParameterizedTypeReference<List<Class<?>>>> inClassList;
	@Nullable
	private BiFunction<HttpGateway, Object, Object> wireInClass;
	@Nullable
	private BiFunction<HttpGateway, U, R> outClass;
	@Nullable
	private Function<HttpGateway, Class<?>> wireOutClass;

	public ManoQueryBuilder(final QueryParameters manoClient) {
		this.client = manoClient;
	}

	public ManoQueryBuilder<U, R> setInClassList(final Function<HttpGateway, ParameterizedTypeReference<List<Class<?>>>> func) {
		this.inClassList = func;
		return this;
	}

	/**
	 * Set input mano class mapper to wire class.
	 *
	 * @param func Function to map input to wire object.
	 * @return An object to send on the wire.
	 */
	public ManoQueryBuilder<U, R> setWireInClass(final BiFunction<HttpGateway, Object, Object> func) {
		this.wireInClass = func;
		return this;
	}

	public ManoQueryBuilder<U, R> setOutClass(final BiFunction<HttpGateway, U, R> mapper) {
		this.outClass = mapper;
		return this;
	}

	public ManoQueryBuilder<U, R> setWireOutClass(final Function<HttpGateway, Class<?>> func) {
		this.wireOutClass = func;
		return this;
	}

	public void delete() {
		final ServerAdapter server = client.getServer();
		final HttpGateway httpGateway = server.httpGateway();
		final URI uri = buildUri(server);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		server.rest().delete(uri, Object.class, version);
	}

	public @Nullable ResponseEntity<R> getRaw() {
		final ServerAdapter server = client.getServer();
		final HttpGateway httpGateway = server.httpGateway();
		final URI uri = buildUri(server);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		return (ResponseEntity<R>) server.rest().getWithReturn(uri, this.wireOutClass.apply(httpGateway), version);
	}

	public @Nullable R getSingle() {
		final ServerAdapter server = client.getServer();
		final HttpGateway httpGateway = server.httpGateway();
		final URI uri = buildUri(server);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		final ResponseEntity<U> resp = (ResponseEntity<U>) server.rest().getWithReturn(uri, this.wireOutClass.apply(httpGateway), version);
		if (null == resp) {
			return null;
		}
		return outClass.apply(httpGateway, resp.getBody());
	}

	public List<R> getList() {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final ParameterizedTypeReference<List<Class<?>>> clazz = this.inClassList.apply(httpGateway);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		final List<U> resp = (List<U>) server.rest().get(uri, clazz, version);
		return resp.stream().map(x -> outClass.apply(httpGateway, x)).toList();
	}

	public R post(final Object req) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final Object reqMap = remapRequest(req);
		final Class<?> clazz = wireOutClass.apply(httpGateway);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		final U res = (U) server.rest().post(uri, reqMap, clazz, version);
		return outClass.apply(httpGateway, res);
	}

	public @Nullable ResponseEntity<R> postRaw(final Object req) {
		final ServerAdapter server = client.getServer();
		final HttpGateway httpGateway = server.httpGateway();
		final URI uri = buildUri(server);
		final Object reqMap = remapRequest(req);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		return (ResponseEntity<R>) server.rest().postWithReturn(uri, reqMap, this.wireOutClass.apply(httpGateway), version);
	}

	public @Nullable <T> ResponseEntity<T> postRaw() {
		final ServerAdapter server = client.getServer();
		final HttpGateway httpGateway = server.httpGateway();
		final URI uri = buildUri(server);
		final Object reqMap = Objects.requireNonNull(wireInClass).apply(httpGateway, client.getRequestObject());
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		return (ResponseEntity<T>) server.rest().postWithReturn(uri, reqMap, this.wireOutClass.apply(httpGateway), version);
	}

	private Object remapRequest(final Object req) {
		final HttpGateway httpGateway = client.getServer().httpGateway();
		return wireInClass.apply(httpGateway, req);
	}

	public R post() {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final Object reqMap = Objects.requireNonNull(wireInClass).apply(httpGateway, client.getRequestObject());
		final Class<?> clazz = wireOutClass.apply(httpGateway);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		final U res = (U) server.rest().post(uri, reqMap, clazz, version);
		return outClass.apply(httpGateway, res);
	}

	private URI buildUri(final ServerAdapter server) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId())
				.map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return server.getUriFor(client.getQueryType(), client.getFragment(), uriParams);
	}

	public void download(final Path file) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		server.rest().download(uri, file, version);
	}

	public void download(final Path url, final Consumer<InputStream> tgt) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		server.rest().doDownload(uri.toString() + "/" + url, tgt, version);
	}

	public void upload(final Path path, final String accept) {
		final MultiValueMap<String, String> headers = new HttpHeaders();
		upload(path, accept, headers);
	}

	public void upload(final Path path, final String accept, final MultiValueMap<String, String> headers) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		if (null != version) {
			headers.add("Version", version);
		}
		server.rest().upload(uri, path, accept, headers);
	}

	public R patch(@Nullable final String ifMatch, final Map<String, Object> patch) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(server);
		final HttpGateway httpGateway = server.httpGateway();
		final Class<?> clazz = wireOutClass.apply(httpGateway);
		final String version = httpGateway.getHeaderVersion(client.getQueryType()).orElse(null);
		final U res = (U) server.rest().patch(uri, clazz, ifMatch, patch, version);
		return outClass.apply(httpGateway, res);
	}

}
