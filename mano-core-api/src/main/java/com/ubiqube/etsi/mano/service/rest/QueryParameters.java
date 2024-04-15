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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.rest;

import java.util.UUID;
import java.util.function.Function;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryParameters {
	@Nonnull
	private final ServerAdapter server;
	@Nullable
	private Function<HttpGateway, ?> requestObject;
	@Nullable
	private String fragment;
	@Nullable
	private ApiVersionType queryType;
	// Can be null.
	private UUID objectId;

	public QueryParameters(final ServerAdapter server) {
		this.server = server;
	}

	public <U, R> ManoQueryBuilder<U, R> createQuery() {
		return new ManoQueryBuilder<>(this);
	}

//	public <U, R> ManoQueryBuilder<U, R> createQuery(final Function<HttpGateway, U> func) {
//		this.requestObject = Objects.requireNonNull(func, "HttpGateway function cannot be null.");
//		return new ManoQueryBuilder<>(this);
//	}

}
