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
package com.ubiqube.etsi.mano.service.eval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.jpa.NsdPackageDb;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.cond.Context;

@ExtendWith(MockitoExtension.class)
class ContextBuilderServiceTest {
	@Mock
	private VnfPackageService vnfPackageService;
	@Mock
	private NsdPackageDb nsdService;
	@Mock
	private VnfInstanceJpa instanceService;

	@Test
	void testNoResult() throws Exception {
		final ContextBuilderService ctx = new ContextBuilderService(vnfPackageService, nsdService, instanceService);
		final UUID id = UUID.randomUUID();
		assertThrows(NullPointerException.class, () -> ctx.build(SubscriptionType.VNF, id, "myEvent"));
	}

	@Test
	void testBadEvent() throws Exception {
		final ContextBuilderService ctx = new ContextBuilderService(vnfPackageService, nsdService, instanceService);
		final UUID id = UUID.randomUUID();
		assertThrows(IllegalArgumentException.class, () -> ctx.build(SubscriptionType.MEOPKG, id, "myEvent"));
	}

	@Test
	void test001() throws Exception {
		final ContextBuilderService ctx = new ContextBuilderService(vnfPackageService, nsdService, instanceService);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		when(vnfPackageService.findById(id)).thenReturn(vnfPkg);
		final Context ctxRes = ctx.build(SubscriptionType.VNF, id, "myEvent");
		assertNotNull(ctxRes);
		final Object nt = ctxRes.lookup("notificationTypes");
		assertNotNull(nt);
		assertEquals("myEvent", nt);
	}
}
