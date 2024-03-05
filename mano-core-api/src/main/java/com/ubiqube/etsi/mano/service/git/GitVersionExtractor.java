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
package com.ubiqube.etsi.mano.service.git;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class GitVersionExtractor {
	private static final String DEFAULT_GIT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	public GitVersion extract(final InputStream stream) {
		final Properties props = new Properties();
		try {
			props.load(stream);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return map(props);
	}

	public GitVersion extract(final URL url) {
		try (final InputStream stream = url.openStream()) {
			return extract(stream);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	GitVersion map(final Properties p) {
		final String version = p.getProperty("git.build.version");
		final String abbrev = p.getProperty("git.commit.id.abbrev");
		final String describe = p.getProperty("git.commit.id.describe");
		final boolean dirty = Boolean.parseBoolean(p.getProperty("git.dirty"));
		final long commitCount = Long.parseLong(p.getProperty("git.total.commit.count"));
		final ZonedDateTime date = mapDate(p.getProperty("git.commit.time"));
		return GitVersion.of(version, abbrev, describe, dirty, commitCount, date);
	}

	private static ZonedDateTime mapDate(final String date) {
		final SimpleDateFormat smf = new SimpleDateFormat(DEFAULT_GIT_DATE_FORMAT);
		try {
			final Date dte = smf.parse(date);
			return ZonedDateTime.ofInstant(dte.toInstant(), ZoneId.systemDefault());
		} catch (final ParseException e) {
			throw new GenericException(e);
		}
	}

}
