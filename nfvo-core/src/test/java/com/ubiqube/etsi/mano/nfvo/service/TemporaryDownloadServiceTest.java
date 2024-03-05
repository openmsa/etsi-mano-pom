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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload;
import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload.ObjectType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.jpa.TemporaryDownloadJpa;
import com.ubiqube.etsi.mano.repository.NsdRepository;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;

@ExtendWith(MockitoExtension.class)
class TemporaryDownloadServiceTest {
	@Mock
	private TemporaryDownloadJpa temporaryJpa;
	@Mock
	private NsdRepository nsdRepo;
	@Mock
	private VnfPackageRepository vnfRepo;

	@Test
	void testExpose() {
		final TemporaryDownloadService tds = new TemporaryDownloadService(temporaryJpa, nsdRepo, vnfRepo);
		final UUID id = UUID.randomUUID();
		tds.exposeDocument(null, id);
		assertTrue(true);
	}

	@Test
	void test_NotFound() {
		final TemporaryDownloadService tds = new TemporaryDownloadService(temporaryJpa, nsdRepo, vnfRepo);
		final UUID id = UUID.randomUUID();
		final String strId = id.toString();
		assertThrows(NotFoundException.class, () -> tds.getDocument(strId));
	}

	@Test
	void test_UnknownType() {
		final TemporaryDownloadService tds = new TemporaryDownloadService(temporaryJpa, nsdRepo, vnfRepo);
		final UUID id = UUID.randomUUID();
		final TemporaryDownload temp = new TemporaryDownload();
		temp.setObjectId(id);
		when(temporaryJpa.findByIdAndExpirationDateAfter(anyString(), any())).thenReturn(Optional.of(temp));
		final String sid = id.toString();
		assertThrows(GenericException.class, () -> tds.getDocument(sid));
	}

	@Test
	void test_Vnfd() {
		final TemporaryDownloadService tds = new TemporaryDownloadService(temporaryJpa, nsdRepo, vnfRepo);
		final UUID id = UUID.randomUUID();
		final TemporaryDownload temp = new TemporaryDownload();
		temp.setObjectId(id);
		temp.setObjectType(ObjectType.VNFD);
		when(temporaryJpa.findByIdAndExpirationDateAfter(anyString(), any())).thenReturn(Optional.of(temp));
		tds.getDocument(id.toString());
		assertTrue(true);
	}

	@Test
	void test_Nsd() {
		final TemporaryDownloadService tds = new TemporaryDownloadService(temporaryJpa, nsdRepo, vnfRepo);
		final UUID id = UUID.randomUUID();
		final TemporaryDownload temp = new TemporaryDownload();
		temp.setObjectId(id);
		temp.setObjectType(ObjectType.NSD);
		when(temporaryJpa.findByIdAndExpirationDateAfter(anyString(), any())).thenReturn(Optional.of(temp));
		tds.getDocument(id.toString());
		assertTrue(true);
	}
}
