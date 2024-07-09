package com.ubiqube.etsi.mano.service.grant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ubiqube.etsi.mano.exception.GenericException;

public interface K8sResourceLoader {

	String getSuperType();

	String getType();

	default String getVersionedResource(final String version, final String filename) {
		return "/%s/%s/v%s/%s".formatted(getSuperType(), getType(), version, filename);
	}

	default List<String> getK8sDocuments(final String version) {
		final String resourcePath = getVersionedResource(version, "filelist.txt");
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			in.transferTo(baos);
			return loadFiles(version, baos.toString());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private List<String> loadFiles(final String version, final String string) {
		final String[] lines = string.split(System.getProperty("line.separator"));
		return Arrays.stream(lines).map(x -> {
			final String resourcePath = getVersionedResource(version, x);
			return loadDoc(resourcePath);
		}).toList();
	}

	private String loadDoc(final String resourcePath) {
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Objects.requireNonNull(in, () -> "Could not load: " + resourcePath);
			in.transferTo(baos);
			return baos.toString();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}
