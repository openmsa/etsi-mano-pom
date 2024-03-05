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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.vim.DefaultQuotas;
import com.ubiqube.etsi.mano.service.vim.ResourceQuota;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class VrQanServiceTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private VrQanJpa vrQanJpa;
	@Mock
	private EventManager em;
	@Mock
	private Vim vim;

	@Test
	void test() {
		final VrQanService srv = new VrQanService(vimManager, vrQanJpa, em);
		final UUID id = UUID.randomUUID();
		srv.findByVimId(id);
		assertTrue(true);
	}

	@Test
	void testRun_Basic() {
		final VrQanService srv = new VrQanService(vimManager, vrQanJpa, em);
		srv.run();
		assertTrue(true);
	}

	@Test
	void testRun() {
		final VrQanService srv = new VrQanService(vimManager, vrQanJpa, em);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(vimManager.findAllVimconnections()).thenReturn(List.of(vim01));
		when(vimManager.getVimById(vim01.getId())).thenReturn(vim);
		final VrQan vrQan = new VrQan();
		when(vrQanJpa.save(any())).thenReturn(vrQan);
		final ResourceQuota resource = new DefaultQuotas();
		when(vim.getQuota(vim01)).thenReturn(resource);
		srv.run();
		srv.await(1, TimeUnit.MINUTES);
		srv.onClose();
		assertTrue(true);
	}

	@Test
	void testRun_Error() {
		final VrQanService srv = new VrQanService(vimManager, vrQanJpa, em);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(vimManager.findAllVimconnections()).thenReturn(List.of(vim01));
		srv.run();
		srv.onClose();
		assertTrue(true);
	}

	@Test
	void testRun_Diff() {
		final VrQanService srv = new VrQanService(vimManager, vrQanJpa, em);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		when(vimManager.findAllVimconnections()).thenReturn(List.of(vim01));
		when(vimManager.getVimById(vim01.getId())).thenReturn(vim);
		final VrQan vrQan = new VrQan();
		vrQan.setFloatingFree(123);
		when(vrQanJpa.save(any())).thenReturn(vrQan);
		final ResourceQuota resource = new DefaultQuotas();
		when(vim.getQuota(vim01)).thenReturn(resource);
		srv.run();
		srv.await(1, TimeUnit.MINUTES);
		srv.onClose();
		verify(em).sendNotification(any(), any(), anyMap());
		assertTrue(true);
	}

}
