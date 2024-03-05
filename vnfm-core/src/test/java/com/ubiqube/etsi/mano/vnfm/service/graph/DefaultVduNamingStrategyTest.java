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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;

class DefaultVduNamingStrategyTest {
	DefaultVduNamingStrategy srv = new DefaultVduNamingStrategy();

	@Test
	void test() {
		final VnfInstance inst = TestFactory.createVnfInstance();
		assertNotNull(srv.getOsContainerAlias(inst, null));
		final ComputeTask comp = new ComputeTask();
		final VnfLinkPort link = new VnfLinkPort();
		assertNotNull(srv.nameConnectionPort(link, comp));
		final VnfBlueprint bp = TestFactory.createBlueprint();
		bp.setVnfInstance(inst);
		assertNotNull(srv.nameSingleResource(bp, null));
		final VnfLcmOpOccs opoccs = new VnfLcmOpOccs();
		opoccs.setVnfInstance(inst);
		assertNotNull(srv.nameVdu(opoccs, null, 0));
		assertNotNull(srv.nameVdu(bp, null, 0));
		assertNotNull(srv.osContainerName(inst, null));
	}

}
