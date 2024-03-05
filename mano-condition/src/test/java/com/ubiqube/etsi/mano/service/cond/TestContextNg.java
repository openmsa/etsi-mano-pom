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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.cond;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContextNg implements Context {
	private static final Logger LOG = LoggerFactory.getLogger(TestContextNg.class);

	private final Map<String, Object> map;

	public TestContextNg(final Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public Object lookup(final String name) {
		final Object val = map.get(name);
		LOG.debug("Lookup for {}={}", name, val);
		return val;
	}
}
