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
			Objects.requireNonNull(in, () -> "Could not find resource: " + resourcePath);
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
