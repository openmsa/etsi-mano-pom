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

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.event.flavor.FlavorManager;
import com.ubiqube.etsi.mano.service.grant.GrantSupport;

@ExtendWith(MockitoExtension.class)
class FlavorExecutorTest {
	@Mock
	private FlavorManager flavorManager;
	@Mock
	private GrantSupport grantSupport;

	FlavorExecutor createService() {
		return new FlavorExecutor(flavorManager, grantSupport);
	}

	@Test
	void test() {
		final FlavorExecutor srv = createService();
		final VimConnectionInformation vim = new VimConnectionInformation<>();
		srv.getFlavors(vim, UUID.randomUUID());
	}

}
