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
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.rest.grant.ManoGrant;

@ExtendWith(MockitoExtension.class)
class ManoGrantTest {
	@Mock
	private QueryParameters manoClient;
	@Mock
	private ManoQueryBuilder manoQueryBuilder;

	@Test
	void testWithNoLocation() throws Exception {
		final ManoGrant mg = new ManoGrant(manoClient);
		final GrantResponse grant = new GrantResponse();
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireInClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		final Object obj = "";
		when(manoQueryBuilder.postRaw()).thenReturn(ResponseEntity.ok(obj));
		assertThrows(GenericException.class, () -> mg.create(grant));
	}

	@Test
	void testCreate() throws Exception {
		final ManoGrant mg = new ManoGrant(manoClient);
		final GrantResponse grant = new GrantResponse();
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireInClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		final Object obj = "";
		final ResponseEntity<Object> resp = ResponseEntity
				.ok().location(URI.create("http://www.my-mano/grant/6311357c-c1a6-41ed-b431-c8f750509d3b"))
				.body(obj);
		when(manoQueryBuilder.postRaw()).thenReturn(resp);
		assertThrows(NullPointerException.class, () -> mg.create(grant));
	}

	@Test
	void testFind() {
		final ManoGrant mg = new ManoGrant(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		final ResponseEntity<Object> resp = ResponseEntity.ok()
				.build();
		when(manoQueryBuilder.getRaw()).thenReturn(resp);
		when(manoQueryBuilder.getSingle()).thenReturn(new GrantResponse());
		final GrantResponse res = mg.find();
		assertTrue(res.getAvailable());
	}

	@Test
	void testFindNotFinished() {
		final ManoGrant mg = new ManoGrant(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		final ResponseEntity<Object> resp = ResponseEntity
				.status(202)
				.build();
		when(manoQueryBuilder.getRaw()).thenReturn(resp);
		final GrantResponse res = mg.find();
		assertFalse(res.getAvailable());
	}
}
