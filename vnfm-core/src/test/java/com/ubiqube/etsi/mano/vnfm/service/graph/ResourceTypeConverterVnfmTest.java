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

import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

class ResourceTypeConverterVnfmTest {

	ResourceTypeConverterVnfm createService() {
		return new ResourceTypeConverterVnfm();
	}

	@ParameterizedTest
	@EnumSource(value = ResourceTypeEnum.class)
	void testToResourceHolder(final ResourceTypeEnum p) {
		final ResourceTypeConverterVnfm srv = createService();
		assertNotNull(srv);
		srv.toResourceHolder(p);
	}

	@ParameterizedTest
	@EnumSource(value = ResourceTypeEnum.class)
	void testToVt(final ResourceTypeEnum p) {
		final ResourceTypeConverterVnfm srv = createService();
		assertNotNull(srv);
		final Function<VnfTask, VirtualTaskV3<? extends VnfTask>> res = srv.toVt(p);
		if (null != res) {
			assertNotNull(res.apply(null));
		}
	}

}
