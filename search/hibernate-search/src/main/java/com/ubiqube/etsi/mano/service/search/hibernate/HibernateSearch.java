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
package com.ubiqube.etsi.mano.service.search.hibernate;

import java.util.List;

import org.hibernate.search.engine.search.common.ValueConvert;
import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.dsl.SearchQuerySelectStep;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.common.EntityReference;
import org.hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.service.search.SearchException;

import jakarta.persistence.EntityManager;

@Service
public class HibernateSearch implements ManoSearch {

	private final EntityManager entityManager;
	private final GrammarParser grammarParser;

	public HibernateSearch(final EntityManager entityManager, final GrammarParser grammarParser) {
		this.entityManager = entityManager;
		this.grammarParser = grammarParser;
	}

	@Override
	public <T> List<T> getCriteria(final List<GrammarNode> nodes, final Class<T> clazz) {
		final SearchSession session = Search.session(entityManager);
		final SearchQuerySelectStep<?, EntityReference, T, SearchLoadingOptionsStep, ?, ?> ss = session.search(clazz);
		final SearchPredicateFactory pf = session.scope(clazz).predicate();
		final List<SearchPredicate> sp = convertNodeList(nodes, pf);
		return ss.where(f -> f.bool(b -> {
			b.must(f.matchAll());
			for (final SearchPredicate predicate : sp) {
				b.must(predicate);
			}
		})).fetchAllHits();
	}

	public <T> List<T> getCriteria(final String filter, final Class<T> clazz) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return getCriteria(nodes.getNodes(), clazz);
	}

	private static List<SearchPredicate> convertNodeList(final List<GrammarNode> nodes, final SearchPredicateFactory pf) {
		return nodes.stream()
				.filter(BooleanExpression.class::isInstance)
				.map(BooleanExpression.class::cast)
				.map(x -> applyOp(x.getLeft(), x.getOp(), x.getRight(), pf))
				.toList();
	}

	private static SearchPredicate applyOp(final GrammarNode name, final GrammarOperandType op, final GrammarNode value, final SearchPredicateFactory pf) {
		return switch (op) {
		case EQ -> pf.match().field(toField(name)).matching(toValue(value), ValueConvert.NO).toPredicate();
		case NEQ -> pf.matchAll().except(pf.match().field(toField(name)).matching(toValue(value))).toPredicate();
		case GT -> pf.range().field(toField(name)).greaterThan(toValue(value)).toPredicate();
		case GTE -> pf.range().field(toField(name)).atLeast(toValue(value)).toPredicate();
		case LT -> pf.range().field(toField(name)).lessThan(toValue(value)).toPredicate();
		case LTE -> pf.range().field(toField(name)).atMost(toValue(value)).toPredicate();
		case CONT -> pf.match().field(toField(name)).matching(toValue(value)).toPredicate();
		case NCONT -> pf.matchAll().except(pf.match().field(toField(name)).matching(toValue(value))).toPredicate();
		case IN, NIN -> throw new SearchException("Unknown query Op: " + op);
		default -> throw new SearchException("Unknown query Op: " + op);
		};
	}

	private static Object toValue(final GrammarNode value) {
		if (!(value instanceof final GrammarValue gv)) {
			throw new GrammarException("");
		}
		return gv.getAsString();
	}

	private static String toField(final GrammarNode name) {
		if (!(name instanceof final GrammarLabel gl)) {
			throw new GrammarException("");
		}
		return gl.getAsString();
	}

	@Override
	public <T> void getByDistance(final Class<T> clazz, final double lat, final double lng) {
		final SearchSession session = Search.session(entityManager);
		final SearchQuerySelectStep<?, EntityReference, T, SearchLoadingOptionsStep, ?, ?> ss = session.search(clazz);
		final SearchPredicateFactory pf = session.scope(clazz).predicate();
		final SearchPredicate pr = pf.spatial().within().fields("").circle(lat, lng, 10000).toPredicate();
		ss.where(pr);
	}
}
