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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.config.properties.ManoProperties;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class VnfmPmGenericFrontControllerImplTest {
	@Mock
	private VnfmPmController vnfPmController;
	@Mock
	private MapperFacade mapper;

	@Test
	void testSearch() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmGenericFrontControllerImpl srv = createService();
		srv.search(null, null, null);
		assertTrue(true);
	}

	private VnfmPmGenericFrontControllerImpl createService() {
		return new VnfmPmGenericFrontControllerImpl(vnfPmController);
	}

	@Test
	void testDeleteById() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmGenericFrontControllerImpl srv = createService();
		srv.deleteById(null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmGenericFrontControllerImpl srv = createService();
		final Consumer<String> cons = x -> {
		};
		srv.findById(null, null, cons);
		assertTrue(true);
	}

	@Test
	void testFindReportById() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmGenericFrontControllerImpl srv = createService();
		final String is = UUID.randomUUID().toString();
		srv.findReportById(is, is, null);
		assertTrue(true);
	}

	@Test
	void testPmJobsPost() {
		final ManoProperties props = new ManoProperties();
		final VnfmPmGenericFrontControllerImpl srv = createService();
		final Consumer<String> cons = x -> {
		};
		final Function<String, String> func = x -> "";
		final Function<PmJob, String> mapper = x -> null;
		srv.pmJobsPost(null, mapper, cons, func);
		assertTrue(true);
	}
}
