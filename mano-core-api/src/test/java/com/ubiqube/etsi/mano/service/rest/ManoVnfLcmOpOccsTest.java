/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

@ExtendWith(MockitoExtension.class)
class ManoVnfLcmOpOccsTest {
	@Mock
	private ManoClient client;
	@Mock
	private ManoQueryBuilder query;

	private ManoVnfLcmOpOccs createService() {
		return new ManoVnfLcmOpOccs(client);
	}

	@Test
	void testList() {
		final ManoVnfLcmOpOccs srv = new ManoVnfLcmOpOccs(client);
		when(client.createQuery()).thenReturn(query);
		when(query.setInClassList(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		srv.list();
		assertTrue(true);
	}

	@Test
	void testFind() {
		final ManoVnfLcmOpOccs srv = new ManoVnfLcmOpOccs(client);
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		srv.find();
		assertTrue(true);
	}

	@Test
	void testWaitOnboarding() {
		final ManoVnfLcmOpOccs srv = createService();
		// Find
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		final VnfBlueprint vnfBlue = new VnfBlueprint();
		vnfBlue.setOperationStatus(OperationStatusType.COMPLETED);
		when(query.getSingle()).thenReturn(vnfBlue);
		srv.waitOnboading();
		assertTrue(true);
	}

	@ParameterizedTest()
	@EnumSource(value = OperationStatusType.class)
	void testWaitOnboardingAllBreakingStatus(final OperationStatusType param) {
		final ManoVnfLcmOpOccs srv = createService();
		// Find
		when(client.createQuery()).thenReturn(query);
		when(query.setWireOutClass(any())).thenReturn(query);
		when(query.setOutClass(any())).thenReturn(query);
		final VnfBlueprint vnfBlue = new VnfBlueprint();
		vnfBlue.setOperationStatus(param);
		final VnfBlueprint vnfBlue2 = new VnfBlueprint();
		vnfBlue2.setOperationStatus(OperationStatusType.COMPLETED);
		when(query.getSingle()).thenReturn(vnfBlue).thenReturn(vnfBlue2);
		srv.waitOnboading();
		assertTrue(true);
	}

}
