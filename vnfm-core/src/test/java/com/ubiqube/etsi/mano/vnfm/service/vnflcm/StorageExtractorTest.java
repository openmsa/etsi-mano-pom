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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

class StorageExtractorTest {

	StorageExtractor createService() {
		return new StorageExtractor();
	}

	@Test
	void test() {
		final StorageExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		srv.extract(null, params, List.of());
		assertTrue(true);
	}

	@Test
	void test2() {
		final StorageExtractor srv = createService();
		final BlueprintParameters params = new BlueprintParameters();
		final VnfLiveInstance vli = new VnfLiveInstance();
		final VnfTask sto01 = new StorageTask();
		vli.setTask(sto01);
		final VnfInstance inst = new VnfInstance();
		vli.setVnfInstance(inst);
		srv.extract(inst, params, List.of(vli));
		assertTrue(true);
	}

}
