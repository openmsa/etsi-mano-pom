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
package com.ubiqube.etsi.mano.service.event;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.springframework.http.MediaType;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.rest.FluxRest;

public class HelmUploader {

	private HelmUploader() {
		//
	}

	public static void uploadFile(final ManoResource mr, final ConnectionInformation ci, final String name) {
		final FluxRest fr = new FluxRest(ci.toServers());
		final URI uri = URI.create(ci.getUrl() + "/" + name);
		final String mimeType = makeMimeType(name);
		try (InputStream is = mr.getInputStream()) {
			fr.put(uri, is, String.class, MediaType.APPLICATION_JSON_VALUE);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static String makeMimeType(final String name) {
		if (name.endsWith(".tar.gz") || name.endsWith(".tgz")) {
			return "application/gzip";
		}
		if (name.endsWith(".tar")) {
			return "application/tar";
		}
		return "application/octet-stream";
	}
}
