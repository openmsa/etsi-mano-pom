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

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.admin.ManoAdmin;
import com.ubiqube.etsi.mano.service.rest.grant.ManoGrant;
import com.ubiqube.etsi.mano.service.rest.nspkg.ManoNsPackage;
import com.ubiqube.etsi.mano.service.rest.vnffm.ManoVnfFm;
import com.ubiqube.etsi.mano.service.rest.vnfind.ManoVnfIndicator;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfInstance;
import com.ubiqube.etsi.mano.service.rest.vnflcm.ManoVnfLcmOpOccs;
import com.ubiqube.etsi.mano.service.rest.vnfpkg.ManoVnfPackage;
import com.ubiqube.etsi.mano.service.rest.vnfpm.ManoVnfPm;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoClient {
	@Nonnull
	private final MapperFacade mapper;
	@Nonnull
	private final ServerAdapter server;
	@Nullable
	private Function<HttpGateway, ?> requestObject;
	@Nullable
	private String setFragment;
	@Nullable
	private ApiVersionType setQueryType;
	@Nullable
	private UUID objectId;

	public ManoClient(final MapperFacade mapper, final ServerAdapter server) {
		this.mapper = mapper;
		this.server = server;
	}

	public void setQueryType(final ApiVersionType sol003Vnflcm) {
		this.setQueryType = sol003Vnflcm;
	}

	public void setObjectId(final UUID vnfInstanceId) {
		this.objectId = vnfInstanceId;
	}

	public ManoQueryBuilder createQuery() {
		return new ManoQueryBuilder(mapper, this);
	}

	public ManoQueryBuilder createQuery(final Function<HttpGateway, ?> func) {
		this.requestObject = Objects.requireNonNull(func, "HttpGateway function cannot be null.");
		return new ManoQueryBuilder(mapper, this);
	}

	public void setFragment(final String string) {
		this.setFragment = string;
	}

	public ServerAdapter getServer() {
		return server;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public <T> Function<HttpGateway, T> getRequestObject() {
		return (Function<HttpGateway, T>) Objects.requireNonNull(requestObject);
	}

	public String getSetFragment() {
		return Objects.requireNonNull(setFragment);
	}

	/**
	 *
	 * @return Cannot be null.
	 */
	public UUID getObjectId() {
		return Objects.requireNonNull(objectId);
	}

	public ApiVersionType getQueryType() {
		return Objects.requireNonNull(setQueryType);
	}

	/**
	 * Public methods.
	 *
	 * @return An instance.
	 */
	public ManoVnfInstance vnfInstance() {
		return new ManoVnfInstance(this);
	}

	public ManoGrant grant() {
		return new ManoGrant(this);
	}

	public ManoVnfPackage vnfPackage() {
		return new ManoVnfPackage(this);
	}

	public MapperFacade getMapper() {
		return mapper;
	}

	public ManoVnfLcmOpOccs vnfLcmOpOccs() {
		return new ManoVnfLcmOpOccs(this);
	}

	public ManoNsPackage nsPackage() {
		return new ManoNsPackage(this);
	}

	public ManoAdmin admin() {
		return new ManoAdmin(this);
	}

	public ManoVnfPm vnfPm() {
		return new ManoVnfPm(this);
	}

	public ManoVnfFm vnfFm() {
		return new ManoVnfFm(this);
	}

	public ManoVnfIndicator vnfIndicator() {
		return new ManoVnfIndicator(this);
	}
}
