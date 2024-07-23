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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.vim.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.service.event.images.SoftwareImageService;
import com.ubiqube.etsi.mano.service.grant.GrantSupport;
import com.ubiqube.etsi.mano.service.vim.Vim;

@ExtendWith(MockitoExtension.class)
class SoftwareImageExecutorTest {
	@Mock
	private SoftwareImageService imageService;
	@Mock
	private GrantSupport grantSupport;
	@Mock
	private Vim vim;

	SoftwareImageExecutor createService() {
		return new SoftwareImageExecutor(imageService, grantSupport);
	}

	@Test
	void test() {
		final SoftwareImageExecutor srv = createService();
		final VimConnectionInformation vimInfo = new VimConnectionInformation();
		final GrantResponse grant = new GrantResponse();
		srv.getSoftwareImageSafe(vimInfo, vim, grant);
	}

	@Test
	void test001() {
		final SoftwareImageExecutor srv = createService();
		final VimConnectionInformation vimInfo = new VimConnectionInformation();
		final GrantResponse grant = new GrantResponse();
		final VnfStorage vnfStorage = new VnfStorage();
		final SoftwareImage sw = new SoftwareImage();
		vnfStorage.setSoftwareImage(sw);
		when(grantSupport.getVnfStorage(any())).thenReturn(Set.of(vnfStorage));
		when(imageService.getImage(any(), any(), any(), any())).thenReturn(sw);
		srv.getSoftwareImageSafe(vimInfo, vim, grant);
	}

}
