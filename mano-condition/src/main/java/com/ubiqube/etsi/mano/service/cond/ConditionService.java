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
package com.ubiqube.etsi.mano.service.cond;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.cond.visitor.BooleanListExprRemoverVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.EvaluatorVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.ForwardLeftVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.OptimizeVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.RemoveSpecialOpVisitor;

public class ConditionService {
	private static final Logger LOG = LoggerFactory.getLogger(ConditionService.class);

	private final ObjectMapper mapper;

	public ConditionService() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
	}

	@SuppressWarnings("static-method")
	public boolean evaluate(final Node node, final Context ctx) {
		final EvaluatorVisitor eval = new EvaluatorVisitor();
		return node.accept(eval, ctx);
	}

	public Node parse(final String str) {
		JsonNode actualObj;
		try {
			actualObj = mapper.readTree(str);
		} catch (final JsonProcessingException e) {
			throw new AstException(e);
		}
		final List<BooleanExpression> res = JsonToExpression.parseCondition(actualObj);
		final BooleanListExpr r = new BooleanListExpr(BooleanOperatorEnum.AND, res);
		Node root = r;
		root = applyOptimizer(new ForwardLeftVisitor(), root);
		root = applyOptimizer(new OptimizeVisitor(), root);
		root = applyOptimizer(new OptimizeVisitor(), root);
		root = applyOptimizer(new BooleanListExprRemoverVisitor(), root);
		return applyOptimizer(new RemoveSpecialOpVisitor(), root);
	}

	private static <A> Node applyOptimizer(final Visitor<Node, A> v, final Node node) {
		final Node ret = node.accept(v, null);
		if (LOG.isTraceEnabled()) {
			final PrintVisitor visitor = new PrintVisitor();
			final String str = ret.accept(visitor, 0);
			LOG.trace("{}", str);
		}
		if (ret == null) {
			return new NoopNode();
		}
		return ret;
	}
}
