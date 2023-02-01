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
package com.ubiqube.etsi.mano.utils;

import java.net.URI;

import jakarta.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UriConverter implements AttributeConverter<URI, String> {

	private static final Logger LOG = LoggerFactory.getLogger(UriConverter.class);

	@Override
	public String convertToDatabaseColumn(final URI attribute) {
		if (null == attribute) {
			return null;
		}
		return attribute.toString();
	}

	@Override
	public URI convertToEntityAttribute(final String dbData) {
		if (null == dbData) {
			return null;
		}
		try {
			return URI.create(dbData);
		} catch (final IllegalArgumentException e) {
			LOG.trace("", e);
			return null;
		}
	}

}
