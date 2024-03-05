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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.grammar.JsonBeanUtil;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class ManoSearchResponseServiceImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private JsonBeanUtil jsonBeanUtil;

	@Test
	void testName() throws Exception {
		final ManoSearchResponseServiceImpl msrs = new ManoSearchResponseServiceImpl(mapper, jsonBeanUtil);
		final MultiValueMap<String, String> paramaters = new LinkedMultiValueMap<>();
		msrs.search(paramaters, getClass(), null, Set.of(), List.of(), null, x -> {
			//
		});
		assertTrue(true);
	}
}
