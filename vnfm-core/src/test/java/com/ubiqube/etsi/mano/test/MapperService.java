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
package com.ubiqube.etsi.mano.test;

import com.ubiqube.etsi.mano.vnfm.config.VnfmOrikaConfiguration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

public class MapperService {
	private static MapperService instance;
	private final MapperFacade mapper;

	private MapperService() {
		System.setProperty(OrikaSystemProperties.COMPILER_STRATEGY, EclipseJdtCompilerStrategy.class.getName());
		System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
		final VnfmOrikaConfiguration map = new VnfmOrikaConfiguration();
		final MapperFactory factory = new DefaultMapperFactory.Builder().build();
		map.configure(factory);
		mapper = factory.getMapperFacade();
	}

	public static MapperService getInstance() {
		if (null == instance) {
			instance = new MapperService();
		}
		return instance;
	}

	public MapperFacade getMapper() {
		return mapper;
	}
}
