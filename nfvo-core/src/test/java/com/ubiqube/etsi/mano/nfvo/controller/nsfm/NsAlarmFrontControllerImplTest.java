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
package com.ubiqube.etsi.mano.nfvo.controller.nsfm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.nfvo.service.NfvoAlarmService;

import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsAlarmFrontControllerImplTest {
	@Mock
	private MapperFacade mapper;
	@Mock
	private NfvoAlarmService alarmNfvoController;

	@Test
	void testFindById() {
		final NsAlarmFrontControllerImpl srv = new NsAlarmFrontControllerImpl(mapper, alarmNfvoController);
		final Consumer<String> makeLinks = x -> {
		};
		srv.findById(UUID.randomUUID(), null, makeLinks);
		assertTrue(true);
	}

	@Test
	void testPatch() {
		final NsAlarmFrontControllerImpl srv = new NsAlarmFrontControllerImpl(mapper, alarmNfvoController);
		srv.patch(UUID.randomUUID().toString(), null, null, null);
		assertTrue(true);
	}

	@Test
	void testSearh() {
		final NsAlarmFrontControllerImpl srv = new NsAlarmFrontControllerImpl(mapper, alarmNfvoController);
		srv.search(null, null, null, null);
		assertTrue(true);
	}
}
