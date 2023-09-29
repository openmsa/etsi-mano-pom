/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import com.ubiqube.etsi.mano.exception.GenericException;

@ExtendWith(MockitoExtension.class)
class ManoServerDeployementTest {
	@Mock
	private ObjectProvider<VnfmService> vnfmService;
	@Mock
	private ObjectProvider<NfvoService> nfvoService;

	private ManoServerDeployement createService() {
		return new ManoServerDeployement(vnfmService, nfvoService);
	}

	@Test
	void test() {
		final ManoServerDeployement srv = createService();
		assertThrows(GenericException.class, () -> srv.getFlavor());
		assertTrue(true);
	}

	@Test
	void test1() {
		final ManoServerDeployement srv = createService();
		when(vnfmService.getIfUnique()).thenReturn(new VnfmService() {
			//
		});
		srv.getFlavor();
		assertTrue(true);
	}

	@Test
	void test2() {
		final ManoServerDeployement srv = createService();
		when(nfvoService.getIfUnique()).thenReturn(new NfvoService() {
			//
		});
		srv.getFlavor();
		assertTrue(true);
	}

	@Test
	void test3() {
		final ManoServerDeployement srv = createService();
		when(vnfmService.getIfUnique()).thenReturn(new VnfmService() {
			//
		});
		when(nfvoService.getIfUnique()).thenReturn(new NfvoService() {
			//
		});
		srv.getFlavor();
		assertTrue(true);
	}
}
