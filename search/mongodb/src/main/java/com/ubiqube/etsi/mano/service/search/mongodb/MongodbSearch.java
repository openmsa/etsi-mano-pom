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
package com.ubiqube.etsi.mano.service.search.mongodb;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.ManoSearch;
import com.ubiqube.etsi.mano.service.search.SearchException;

@Service
public class MongodbSearch implements ManoSearch {
	private final MongoTemplate mongoTemplate;

	public MongodbSearch(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public <T> List<T> getCriteria(final List<GrammarNode> nodes, final Class<T> clazz) {
		final Query query = new Query();
		final Criteria base = new Criteria();
		final List<Criteria> criterias = convertNodeList(nodes);
		if (!criterias.isEmpty()) {
			base.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		}
		query.addCriteria(base);
		return mongoTemplate.find(query, clazz);
	}

	private static List<Criteria> convertNodeList(final List<GrammarNode> nodes) {
		return nodes.stream()
				.filter(BooleanExpression.class::isInstance)
				.map(BooleanExpression.class::cast)
				.map(x -> applyOp(Criteria.where(toKey(x.getLeft())), x.getOp(), x.getRight()))
				.toList();
	}

	private static String toKey(final GrammarNode left) {
		if (!(left instanceof final GrammarLabel gl)) {
			throw new SearchException("");
		}
		return gl.getAsString();
	}

	private static Criteria applyOp(final Criteria crit, final GrammarOperandType op, final GrammarNode value) {
		return switch (op) {
		case EQ -> crit.is(value);
		case NEQ -> crit.ne(value);
		case GT -> crit.gt(value);
		case GTE -> crit.gte(value);
		case LT -> crit.lt(value);
		case LTE -> crit.lte(value);
		case CONT -> crit.regex(".*" + escapeRegexp(value) + ".*");
		case NCONT -> crit.regex(".*" + escapeRegexp(value) + ".*").not();
		case IN, NIN -> throw new SearchException("Unknown query Op: " + op);
		default -> throw new SearchException("Unknown query Op: " + op);
		};
	}

	private static String escapeRegexp(final GrammarNode v) {
		if (!(v instanceof final GrammarValue gv)) {
			throw new SearchException("");
		}
		final String value = gv.getAsString();
		// /[-\/\\^$*+?.()|[\]{}]/g, '\\$&'
		return Pattern.quote(value);
	}

	@Override
	public <T> void getByDistance(final Class<T> clazz, final double lat, final double lng) {
		// TODO Auto-generated method stub

	}

}
