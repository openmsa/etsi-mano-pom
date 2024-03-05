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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;

@ExtendWith(MockitoExtension.class)
class VnfmNfvoTest {
	@Mock
	private VnfInstanceLcm lcm;
	@Mock
	private GrantsResponseJpa grantsResponseJpa;

	@Test
	void testCreateVnfInstance() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.createVnfInstance(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testVnfInstatiate() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.vnfInstatiate(null, UUID.randomUUID().toString(), null);
		assertTrue(true);
	}

	@Test
	void testVnfLcmOpOccsGet() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.vnfLcmOpOccsGet(null, null);
		assertTrue(true);
	}

	@Test
	void testVnfTerminate() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.vnfTerminate(null, UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testVnfScale() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.vnfScale(null, null, null);
		assertTrue(true);
	}

	@Test
	void testVnfHeal() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.vnfHeal(null, null, null);
		assertTrue(true);
	}

	@Test
	void testGetVnfInstance() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.getVnfInstance(null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() throws Exception {
		final VnfmNfvo srv = new VnfmNfvo(lcm, grantsResponseJpa);
		srv.delete(null, UUID.randomUUID().toString());
		assertTrue(true);
	}

}
