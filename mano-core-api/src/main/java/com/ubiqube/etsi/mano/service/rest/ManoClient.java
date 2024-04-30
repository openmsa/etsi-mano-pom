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

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoClient {
	@Nonnull
	private final QueryParameters params;

	public ManoClient(final ServerAdapter server) {
		this.params = new QueryParameters(server);
	}

	/**
	 * Public methods.
	 *
	 * @return An instance.
	 */
	public ManoVnfInstance vnfInstance() {
		return new ManoVnfInstance(params);
	}

	public ManoGrant grant() {
		return new ManoGrant(params);
	}

	public ManoVnfPackage vnfPackage() {
		return new ManoVnfPackage(params);
	}

	public ManoVnfLcmOpOccs vnfLcmOpOccs() {
		return new ManoVnfLcmOpOccs(params);
	}

	public ManoNsPackage nsPackage() {
		return new ManoNsPackage(params);
	}

	public ManoAdmin admin() {
		return new ManoAdmin(params);
	}

	public ManoVnfPm vnfPm() {
		return new ManoVnfPm(params);
	}

	public ManoVnfFm vnfFm() {
		return new ManoVnfFm(params);
	}

	public ManoVnfIndicator vnfIndicator() {
		return new ManoVnfIndicator(params);
	}

	public ManoVrQan vrQan() {
		return new ManoVrQan(params);
	}
}
