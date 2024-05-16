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
package com.ubiqube.etsi.mano.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.mapper.object.Filter;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

class DotMapperTest {

	DotMapper createService() {
		return new DotMapper();
	}

	@Test
	void testAttrToObject1() {
		final DotMapper srv = createService();
		final Filter filter = new Filter();
		srv.AttrToObject(List.of(), filter);
	}

	@Test
	void testAttrToObject2() {
		final FilterAttributes attr = new FilterAttributes("attr", "abc");
		final DotMapper srv = createService();
		final Filter filter = new Filter();
		srv.AttrToObject(List.of(attr), filter);
	}

	@Test
	void testObjectToAttrNull() {
		final DotMapper srv = createService();
		final Filter filter = new Filter();
		final List<FilterAttributes> res = srv.objectToAttr(filter);
		assertNotNull(res);
	}

	@Test
	void testObjectToAttr() {
		final DotMapper srv = createService();
		final Filter filter = new Filter();
		filter.setAttr("abc");
		final List<FilterAttributes> res = srv.objectToAttr(filter);
		assertNotNull(res);
		assertEquals(1, res.size());
		final FilterAttributes elem = res.get(0);
		assertEquals("attr", elem.getAttribute());
		assertEquals("abc", elem.getValue());
	}
}
