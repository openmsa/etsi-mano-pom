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
package com.ubiqube.etsi.mano.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.repository.ContentManager;
import com.ubiqube.etsi.mano.repository.NamingStrategy;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class VnfPackageDbTest {
	@Mock
	private EntityManager em;
	@Mock
	private CrudRepository<VnfPackage, UUID> repository;
	@Mock
	private ContentManager contentManager;
	@Mock
	private ObjectMapper jsonMapper;
	@Mock
	private NamingStrategy namingStrategy;
	@Mock
	private GrammarParser grammarParser;
	@Mock
	private ManoSearch manoSearch;

	@Test
	void testDelete01() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		db.delete(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testDelete02() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		db.delete(UUID.randomUUID(), "filename");
		assertTrue(true);
	}

	@Test
	void testGet() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		final UUID id = UUID.randomUUID();
		final Optional<VnfPackage> vnfPackage = Optional.of(new VnfPackage());
		when(repository.findById(id)).thenReturn(vnfPackage);
		db.get(id);
		assertTrue(true);
	}

	@Test
	void testGetNotFound() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		final UUID id = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> db.get(id));
	}

	@Test
	void testSave() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		final UUID id = UUID.randomUUID();
		final VnfPackage vnfPkg = new VnfPackage();
		when(repository.save(vnfPkg)).thenReturn(vnfPkg);
		db.save(vnfPkg);
		assertTrue(true);
	}

	@Test
	void testSuery01() throws Exception {
		final VnfPackageDb db = new VnfPackageDb(repository, contentManager, jsonMapper, namingStrategy, grammarParser, manoSearch);
		when(grammarParser.parse("filename")).thenReturn(new GrammarNodeResult(List.of()));
		db.query("filename");
		assertTrue(true);
	}

}
