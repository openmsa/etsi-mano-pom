/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.maven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class MavenReader {

	public MavenArtifact extractVersion(final String path) {
		final MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model;
		try (FileInputStream fis = new FileInputStream(path)) {
			model = reader.read(fis);
		} catch (IOException | XmlPullParserException e) {
			throw new GenericException(e);
		}
		final String version = Optional.ofNullable(model.getVersion()).orElseGet(() -> Optional.ofNullable(model.getParent()).map(Parent::getVersion).orElse(null));
		final String group = Optional.ofNullable(model.getGroupId()).orElseGet(() -> Optional.ofNullable(model.getParent()).map(Parent::getGroupId).orElse(null));
		return MavenArtifact.of(group, model.getArtifactId(), version);
	}

}
