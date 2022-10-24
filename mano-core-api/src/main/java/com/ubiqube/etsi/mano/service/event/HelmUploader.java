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

	public static void uploadFile(final ManoResource mr, final ConnectionInformation ci, final String name) {
		final FluxRest fr = FluxRest.of(ci);
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
