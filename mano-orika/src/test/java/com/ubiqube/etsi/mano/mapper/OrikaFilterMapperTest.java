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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.mapper.object.Filter;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class OrikaFilterMapperTest {
	@Mock
	private Type<Filter> dest;

	@Test
	void test() {
		final OrikaFilterMapper srv = new OrikaFilterMapper();
		final Filter filter = new Filter();
		final Type<List<FilterAttributes>> dest = null;
		final MappingContext context = null;
		final List<FilterAttributes> res = srv.convertTo(filter, dest, context);
		assertNotNull(res);
		res.forEach(System.out::println);
	}

	@Test
	void testConvertFrom() {
		final OrikaFilterMapper srv = new OrikaFilterMapper();
		final List<FilterAttributes> source = List.of();
		when(dest.getRawType()).thenReturn(Filter.class);
		srv.convertFrom(source, (Type) dest, null);
		srv.hashCode();
		assertTrue(true);
	}

	@Test
	void testConvertFrom2() {
		final OrikaFilterMapper srv = new OrikaFilterMapper();
		final FilterAttributes f1 = new FilterAttributes("attr", "value");
		final List<FilterAttributes> source = List.of(f1);
		when(dest.getRawType()).thenReturn(Filter.class);
		srv.convertFrom(source, (Type) dest, null);
		srv.equals(srv);
		srv.equals(null);
		srv.equals("");
		final OrikaFilterMapper srv2 = new OrikaFilterMapper();
		srv.equals(srv2);
		assertTrue(true);
	}
}
