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
package com.ubiqube.etsi.mano.service.search.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.service.search.SearchException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class JpaSearch implements ManoSearch {
	private final EntityManager em;

	public JpaSearch(final EntityManager em) {
		this.em = em;
	}

	@Override
	public <T> List<T> getCriteria(final List<GrammarNode> nodes, final Class<T> clazz) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<T> cq = cb.createQuery(clazz);
		final Root<T> itemRoot = cq.from(clazz);
		final Predicate pred = getCriteria(cb, nodes, itemRoot, Map.of());
		if (null != pred) {
			cq.where(pred);
		}
		return em.createQuery(cq).getResultList();
	}

	public <T> Predicate getCriteria(final CriteriaBuilder cb, final List<GrammarNode> nodes, final Root<T> root, final Map<String, From<?, ?>> joins) {
		final List<Predicate> predicates = new ArrayList<>();
		for (final GrammarNode node : nodes) {
			if (!(node instanceof final BooleanExpression be)) {
				throw new SearchException("COuld not handle class: " + node.getClass());
			}
			final Optional<Predicate> res = applyOp(be.getLeft(), be.getOp(), be.getRight(), joins);
			if (res.isPresent()) {
				predicates.add(res.get());
			}
		}
		if (!predicates.isEmpty()) {
			return cb.and(predicates.toArray(new Predicate[0]));
		}
		return null;
	}

	private Optional<Predicate> applyOp(final GrammarNode left, final GrammarOperandType op, final GrammarNode value, final Map<String, From<?, ?>> joins) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final Attr attr = getParent(left, joins);
		final From<?, ?> p = attr.parent.orElse(joins.get("ROOT"));
		final Predicate pred = switch (op) {
		case EQ -> cb.equal(p.get(attr.name), toValue(value));
		case NEQ -> cb.notEqual(p.get(attr.name), toValue(value));
		case GT -> cb.greaterThan(p.get(attr.name), toValue(value));
		case GTE -> cb.greaterThanOrEqualTo(p.get(attr.name), toValue(value));
		case LT -> cb.lessThan(p.get(attr.name), toValue(value));
		case LTE -> cb.lessThanOrEqualTo(p.get(attr.name), toValue(value));
		case CONT, NCONT, IN, NIN -> throw new SearchException("Unknown query Op: " + op);
		};
		return Optional.ofNullable(pred);
	}

	private static String toValue(final GrammarNode value) {
		if (!(value instanceof final GrammarValue gv)) {
			throw new SearchException("Could not handle Value of type: " + value.getClass());
		}
		return gv.getAsString();
	}

	private static Attr getParent(final GrammarNode name, final Map<String, From<?, ?>> joins) {
		if (!(name instanceof final GrammarLabel gl)) {
			throw new SearchException("Node is not a GrammarLabel node: " + name.getClass());
		}
		final Attr attr = new Attr();
		final List<String> arr = gl.getList();
		if (arr.size() > 1) {
			final String[] ro = new String[arr.size() - 1];
			System.arraycopy(arr, 0, ro, 0, ro.length);
			final String key = Arrays.asList(ro).stream().collect(Collectors.joining("."));
			attr.parent = Optional.ofNullable(joins.get(key));
		}
		attr.name = arr.get(arr.size() - 1);
		return attr;
	}

	private static class Attr {
		private String name;
		private Optional<From<?, ?>> parent = Optional.empty();
	}

	@Override
	public <T> void getByDistance(final Class<T> clazz, final double lat, final double lng) {
		// TODO Auto-generated method stub

	}

}
