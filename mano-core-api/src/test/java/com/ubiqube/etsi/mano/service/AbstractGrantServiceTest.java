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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;

@ExtendWith(MockitoExtension.class)
class AbstractGrantServiceTest {
	private final MapperFacade mapper;
	@Mock
	private ResourceAllocate nfvo;
	@Mock
	private VimManager vimManager;
	@Mock
	private ConnectionInformationJpa connectionJpa;

	public AbstractGrantServiceTest() {
		final DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().compilerStrategy(new EclipseJdtCompilerStrategy()).build();
		mapper = mapperFactory.getMapperFacade();
	}

	@Test
	void testBasic() throws Exception {
		final TestAbstractGrantService srv = new TestAbstractGrantService(mapper, nfvo, vimManager, connectionJpa);
		final Blueprint<? extends VimTask, ? extends Instance> bp = new TestBluePrint();
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		bp.setVimConnections(Set.of(vim01));
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		srv.allocate(bp);
		assertTrue(true);
	}

	void testOneTask() throws Exception {
		final TestAbstractGrantService srv = new TestAbstractGrantService(mapper, nfvo, vimManager, connectionJpa);
		final TestBluePrint bp = new TestBluePrint();
		final TestTask task = new TestTask();
		task.setChangeType(ChangeType.ADDED);
		bp.setTasks(Set.of(task));
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		bp.setVimConnections(Set.of(vim01));
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		srv.allocate(bp);
		assertTrue(true);
	}
}
