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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.lcmgrant.GrantManagement;
import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;

@ExtendWith(MockitoExtension.class)
class VnfResourceAllocateTest {
	@Mock

	private GrantManagement grantManagement;

	@Test
	void test() {
		final VnfResourceAllocate srv = new VnfResourceAllocate(grantManagement);
		final GrantInterface req = new GrantResponse();
		final GrantResponse resp = new GrantResponse();
		resp.setAvailable(false);
		final GrantResponse resp2 = new GrantResponse();
		resp2.setAvailable(true);
		when(grantManagement.post(req)).thenReturn(resp);
		when(grantManagement.get(any())).thenReturn(resp, resp2);
		srv.sendSyncGrantRequest(req);
		assertTrue(true);
	}

}
