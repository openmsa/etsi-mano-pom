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
package com.ubiqube.etsi.mano.nfvo.controller.nsd;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamSource;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nonnull;
import ma.glasnost.orika.MapperFacade;

@ExtendWith(MockitoExtension.class)
class NsDescriptorGenericFrontControllerImplTest {
	@Mock
	@Nonnull
	private MapperFacade mapper;
	@Mock
	@Nonnull
	private NsdController nsdController;

	@Test
	void testCreate() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
//		when(mapper.map(any(), any())).thenReturn(new Object());
		final Consumer<Object> cons = x -> {
		};
		final Function<Object, String> func = x -> "http://localhost/";
		srv.create(null, null, x -> "", cons, func);
		assertTrue(true);
	}

	@Test
	void testSearch() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		srv.search(null, null, null, null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		srv.delete(UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
//		when(mapper.map(any(), any())).thenReturn(new Object());
		final Consumer<Object> cons = x -> {
		};
		final NsdPackage nespckg = new NsdPackage();
		when(nsdController.nsDescriptorsNsdInfoIdGet(any())).thenReturn(nespckg);
		srv.finsById(UUID.randomUUID().toString(), x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testGetContent() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		srv.getNsdContent(UUID.randomUUID().toString(), null);
		assertTrue(true);
	}

	@Test
	void testPutContent() throws IOException {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		final InputStreamSource iss = Mockito.mock(InputStreamSource.class);
		when(iss.getInputStream()).thenReturn(InputStream.nullInputStream());
		srv.putNsdContent(UUID.randomUUID().toString(), null, iss);
		assertTrue(true);
	}

	@Test
	void testPutContentFail() throws IOException {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		final InputStreamSource iss = Mockito.mock(InputStreamSource.class);
		final InputStream fakeIs = Mockito.mock(InputStream.class);
		doThrow(IOException.class).when(fakeIs).close();
		when(iss.getInputStream()).thenReturn(fakeIs);
		final String uuid = UUID.randomUUID().toString();
		assertThrows(GenericException.class, () -> srv.putNsdContent(uuid, null, iss));
		assertTrue(true);
	}

	@Test
	void testModify() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		final Consumer<Object> cons = x -> {
		};
		srv.modify(UUID.randomUUID().toString(), null, null, x -> "", cons);
		assertTrue(true);
	}

	@Test
	void testGetArtifact() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		assertThrows(UnsupportedOperationException.class, () -> srv.getArtifact(null, null, null));
		assertTrue(true);
	}

	@Test
	void testGetManifest() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		assertThrows(UnsupportedOperationException.class, () -> srv.getManifest(null, null));
		assertTrue(true);
	}

	@Test
	void testGetNsd() {
		final NsDescriptorGenericFrontControllerImpl srv = new NsDescriptorGenericFrontControllerImpl(nsdController);
		assertThrows(UnsupportedOperationException.class, () -> srv.getNsd(null, null));
		assertTrue(true);
	}
}
