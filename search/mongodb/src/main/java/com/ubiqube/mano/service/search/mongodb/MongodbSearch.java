/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.mano.service.search.mongodb;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.Node.Operand;
import com.ubiqube.mano.service.search.ManoSearch;
import com.ubiqube.mano.service.search.SearchException;

@Service
public class MongodbSearch implements ManoSearch {
	private final MongoTemplate mongoTemplate;

	public MongodbSearch(final MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public <T> List<T> getCriteria(final List<Node<?>> nodes, final Class<T> clazz) {
		final Query query = new Query();
		final Criteria base = new Criteria();
		final List<Criteria> criterias = convertNodeList(nodes);
		if (!criterias.isEmpty()) {
			base.andOperator(criterias.toArray(new Criteria[criterias.size()]));
		}
		query.addCriteria(base);
		return mongoTemplate.find(query, clazz);
	}

	private List<Criteria> convertNodeList(final List<Node<?>> nodes) {
		return nodes.stream()
				.map(x -> applyOp(Criteria.where(x.getName()), x.getOp(), (String) x.getValue()))
				.toList();
	}

	private Criteria applyOp(final Criteria crit, final Operand op, final String value) {
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

	private String escapeRegexp(final String value) {
		// /[-\/\\^$*+?.()|[\]{}]/g, '\\$&'
		return Pattern.quote(value);
	}

}
