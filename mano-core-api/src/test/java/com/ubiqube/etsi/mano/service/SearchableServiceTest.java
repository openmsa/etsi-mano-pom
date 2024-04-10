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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;

import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class SearchableServiceTest {
	@Mock
	private ManoSearchResponseService searchService;
	@Mock
	private EntityManager em;
	@Mock
	private GrammarParser grammarParser;
	@Mock
	private ManoSearch manoSearch;

	private SearchableService createService() {
		return new SearchableService(searchService, em, grammarParser, manoSearch);
	}

	@Test
	void testSearch() {
		final SearchableService srv = createService();
		when(grammarParser.parse(any())).thenReturn(new GrammarNodeResult(List.of()));
		srv.search(getClass(), new LinkedMultiValueMap<>(), null, null, null, null, List.of(), null);
		assertTrue(true);
	}

	@Test
	void testQuery() {
		final SearchableService srv = createService();
		when(grammarParser.parse(any())).thenReturn(new GrammarNodeResult(List.of()));
		srv.query(null, null);
		assertTrue(true);
	}
}
