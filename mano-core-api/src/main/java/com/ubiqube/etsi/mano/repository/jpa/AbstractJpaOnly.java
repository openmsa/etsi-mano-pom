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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.repository.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.repository.CrudRepositoryNg;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.validation.constraints.NotNull;

public abstract class AbstractJpaOnly<U> implements CrudRepositoryNg<U> {
	private final org.springframework.data.repository.CrudRepository<U, UUID> repository;
	private final GrammarParser grammarParser;
	private final ManoSearch manoSearch;

	protected AbstractJpaOnly(final org.springframework.data.repository.CrudRepository<U, UUID> repository, final GrammarParser grammarParser,
			final ManoSearch manoSearch) {
		this.manoSearch = manoSearch;
		this.repository = repository;
		this.grammarParser = grammarParser;
	}

	@Override
	public U get(final UUID id) {
		final Optional<U> entity = repository.findById(id);
		return entity.orElseThrow(() -> new NotFoundException(getFrontClass().getSimpleName() + " entity " + id + " not found."));
	}

	protected abstract Class<U> getFrontClass();

	@Override
	public final void delete(final UUID id) {
		repository.deleteById(id);
	}

	@Override
	public final U save(final U entity) {
		return repository.save(entity);
	}

	@Override
	public @NotNull List<U> query(final String filter) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return manoSearch.getCriteria(nodes.getNodes(), getFrontClass());
	}

}
