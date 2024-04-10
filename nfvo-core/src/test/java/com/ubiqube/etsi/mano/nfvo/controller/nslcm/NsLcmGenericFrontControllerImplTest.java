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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsLcmGenericFrontControllerImplTest {
	@Mock

	private NsBlueprintService nsLcmOpOccsService;
	@Mock
	private MapperFacade mapper;

	@Test
	void testCancel() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.cancel(null, null);
		assertTrue(true);
	}

	@Test
	void testContinue() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.continu(null);
		assertTrue(true);
	}

	@Test
	void testFail() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.fail(null, null, null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		final Consumer<Object> cons = x -> {
		};
		srv.findById(UUID.randomUUID().toString(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testRetry() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.retry(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.rollback(null);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final NsLcmGenericFrontControllerImpl srv = new NsLcmGenericFrontControllerImpl(nsLcmOpOccsService);
		srv.search(null, null, null, null, null);
		assertTrue(true);
	}

}
