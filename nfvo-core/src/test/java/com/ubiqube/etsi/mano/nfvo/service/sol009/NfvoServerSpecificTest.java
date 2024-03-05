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
package com.ubiqube.etsi.mano.nfvo.service.sol009;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntity;
import com.ubiqube.etsi.mano.service.sol009.pkg.ToscaVersionManager;

@ExtendWith(MockitoExtension.class)
class NfvoServerSpecificTest {
	@Mock
	private ToscaVersionManager toscavm;

	private NfvoServerSpecific createService() {
		return new NfvoServerSpecific(toscavm);
	}

	@Test
	void test() {
		final NfvoServerSpecific srv = createService();
		final ManoEntity me = new ManoEntity();
		srv.setSpecificInformations(me);
		assertTrue(true);
	}

}
