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
package com.ubiqube.parser.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ArtifactDownloader {
	private static final String JAR_PATH = "./target/test-classes/tosca-class-%s-2.0.0-SNAPSHOT.jar";
	private static final String ARTIFACT_NAME = "tosca-class-%s";
	private static final String ARTIFACT_URL = """
			http://nexus.ubiqube.com/service/rest/v1/search/assets/download\
			?sort=version\
			&repository=maven-snapshots\
			&maven.groupId=com.ubiqube.mano.sol001\
			&maven.artifactId=%s\
			&maven.extension=jar\
			&maven.classifier=""";

	private ArtifactDownloader() {
		// Nothing.
	}

	/**
	 * Download artifact from nexus into the classpath.
	 *
	 * @param version Version w/o dot.
	 * @throws MalformedURLException
	 */
	public static void prepareArtifact(final String version) throws MalformedURLException {
		final String artifact = String.format(ARTIFACT_NAME, version);
		final String urlStr = String.format(ARTIFACT_URL, artifact);
		System.out.println(urlStr);
		final URL url = new URL(urlStr);
		try (InputStream stream = url.openStream();
				FileOutputStream fos = new FileOutputStream(String.format(JAR_PATH, version))) {
			stream.transferTo(fos);
		} catch (final IOException e) {
			throw new TestException(e);
		}
	}

}
