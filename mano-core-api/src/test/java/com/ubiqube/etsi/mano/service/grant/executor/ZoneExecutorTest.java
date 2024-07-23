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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class ZoneExecutorTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private Vim vimM;

	ZoneExecutor createService() {
		return new ZoneExecutor(vimManager);
	}

	@Test
	void test() throws Exception {
		final ZoneExecutor srv = createService();
		final VimConnectionInformation vim = new VimConnectionInformation();
		final GrantResponse grant = new GrantResponse();
		final Callable<String> func = srv.getZone(vim, grant);
		when(vimManager.getVimById(any())).thenReturn(vimM);
		when(vimM.getZoneAvailableList(vim)).thenReturn(List.of("abc"));
		final String res = func.call();
		assertEquals("abc", res);
		assertEquals(1, grant.getZones().size());
	}

}
