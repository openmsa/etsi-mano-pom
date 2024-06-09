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
package com.ubiqube.etsi.mano.service.grant.executor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;

class ServerGroupExecutorTest {

	ServerGroupExecutor createService() {
		return new ServerGroupExecutor();
	}

	@Test
	void test() throws Exception {
		final ServerGroupExecutor srv = createService();
		final GrantResponse grant = new GrantResponse();
		final Callable<ZoneGroupInformation> res = srv.getServerGroup(grant);
		final ZoneGroupInformation sg = res.call();
		assertNotNull(sg);
	}

}
