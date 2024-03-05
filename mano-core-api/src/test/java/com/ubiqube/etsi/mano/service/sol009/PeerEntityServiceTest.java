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
package com.ubiqube.etsi.mano.service.sol009;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.git.GitPropertyManager;
import com.ubiqube.etsi.mano.service.git.GitVersion;

@ExtendWith(MockitoExtension.class)
class PeerEntityServiceTest {
	@Mock
	private NfvoService nfvoService;
	@Mock
	private GitPropertyManager gitPropertyManager;
	@Mock
	private SpecificServerInfo specificServerInfo;

	private PeerEntityService createService() {
		return new PeerEntityService(Optional.ofNullable(nfvoService), "name", gitPropertyManager, specificServerInfo);
	}

	@Test
	void test() {
		final PeerEntityService srv = createService();
		final GitVersion version = GitVersion.of(null, null, null, false, 0, null);
		when(gitPropertyManager.getLocalPRoperty()).thenReturn(version);
		srv.getMe();
		assertTrue(true);
	}

}
