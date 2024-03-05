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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext implements Context {
	private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);

	private final Map<String, Object> map = new HashMap<>();

	public TestContext() {
		map.put("my_attribute", "my_attribute");
		map.put("my_second", "6.7");
		map.put("my_other_attribute", "my_other_attribute");
		map.put("another", "a string with some space");
		map.put("aaa", "Hello");
		map.put("aab", List.of("A string", "Another string"));
		map.put("aac", "1.234");
		map.put("aad", 1.234);
		map.put("four", 4L);
		map.put("five", 5L);
	}

	@Override
	public Object lookup(final String name) {
		final Object val = map.get(name);
		LOG.debug("Lookup for {}={}", name, val);
		return val;
	}

}
