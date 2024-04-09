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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultConstructorObjectFactory;
import ma.glasnost.orika.metadata.Type;

public class OrikaFilterMapper extends BidirectionalConverter<Object, List<FilterAttributes>> {

	private static final Logger LOG = LoggerFactory.getLogger(OrikaFilterMapper.class);
	private final DotMapper dotMapper;

	public OrikaFilterMapper() {
		this.dotMapper = new DotMapper();
	}

	@Override
	public List<FilterAttributes> convertTo(final Object source, final Type<List<FilterAttributes>> destinationTypeIn, final MappingContext mappingContext) {
		return dotMapper.objectToAttr(source);
	}

	@Override
	public Object convertFrom(final List<FilterAttributes> source, final Type<Object> destinationTypeIn, final MappingContext mappingContext) {
		LOG.info("B to A => ");
		// Create an empty object.
		final DefaultConstructorObjectFactory<Object> objectFactory = new DefaultConstructorObjectFactory<>(destinationTypeIn.getRawType());
		final Object ret = objectFactory.create(source, mappingContext);
		return dotMapper.AttrToObject(source, ret);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		return super.equals(other);
	}
}
