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
package com.ubiqube.etsi.mano.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class MetricGroupService {

	private static final Logger LOG = LoggerFactory.getLogger(MetricGroupService.class);

	private Map<String, List<String>> mapping;

	public MetricGroupService() {
		try (InputStream is = this.getClass().getResourceAsStream("/metric-group.json")) {
			final ObjectMapper mapper = new ObjectMapper();
			final TypeReference<Map<String, List<String>>> tf = new TypeReference<>() {
				//
			};
			mapping = mapper.readValue(is, tf);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	public List<String> findByGroupName(final String name) {
		final List<String> res = mapping.get(name);
		if (null == res) {
			LOG.warn("No group {}", name);
			return List.of();
		}
		return res;
	}
}
