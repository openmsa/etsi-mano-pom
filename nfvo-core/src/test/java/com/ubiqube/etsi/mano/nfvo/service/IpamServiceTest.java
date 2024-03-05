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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ipam.Networks;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.jpa.ipam.NetworksJpa;

@ExtendWith(MockitoExtension.class)
class IpamServiceTest {
	@Mock
	private NetworksJpa networksJpa;

	@Test
	void testNetworkNotFound() throws Exception {
		final IpamService ip = new IpamService(networksJpa);
		final VimConnectionInformation conn = new VimConnectionInformation();
		final BiFunction<VimConnectionInformation, Networks, String> func = (x, y) -> {
			return null;
		};
		assertThrows(NotFoundException.class, () -> ip.reserveNetwork(conn, func));
	}

	@Test
	void testOk() throws Exception {
		final IpamService ip = new IpamService(networksJpa);
		final VimConnectionInformation conn = new VimConnectionInformation();
		final BiFunction<VimConnectionInformation, Networks, String> func = (x, y) -> {
			return null;
		};
		final Networks net = new Networks();
		when(networksJpa.findFirstFreeNetwork(any())).thenReturn(Optional.of(net));
		ip.reserveNetwork(conn, func);
		assertTrue(true);
	}

	@Test
	void testTaskFail() throws Exception {
		final IpamService ip = new IpamService(networksJpa);
		final VimConnectionInformation conn = new VimConnectionInformation();
		final BiFunction<VimConnectionInformation, Networks, String> func = (x, y) -> {
			throw new IllegalArgumentException();
		};
		final Networks net = new Networks();
		when(networksJpa.findFirstFreeNetwork(any())).thenReturn(Optional.of(net));
		assertThrows(IllegalArgumentException.class, () -> ip.reserveNetwork(conn, func));
	}
}
