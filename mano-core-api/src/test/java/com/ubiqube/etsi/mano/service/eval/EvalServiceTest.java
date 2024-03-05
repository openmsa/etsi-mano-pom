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
package com.ubiqube.etsi.mano.service.eval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.Visitor;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

@ExtendWith(MockitoExtension.class)
class EvalServiceTest {
	@Mock
	private ContextBuilderService ctx;

	@Test
	void testNullFilter() throws Exception {
		final EvalService ev = new EvalService(ctx);
		final RequestObject obj = new RequestObject();
		assertThrows(NullPointerException.class, () -> ev.convertRequestToNode(obj));
	}

	@Test
	void testEmptyFilter() throws Exception {
		final EvalService ev = new EvalService(ctx);
		final RequestObject obj = new RequestObject();
		final SubscriptionFilter filter = new SubscriptionFilter();
		obj.setFilter(filter);
		final Node res = ev.convertRequestToNode(obj);
		assertTrue(res instanceof NoopNode);
	}

	@Test
	void testFilter001() throws Exception {
		final EvalService ev = new EvalService(ctx);
		final RequestObject obj = new RequestObject();
		final SubscriptionFilter filter = new SubscriptionFilter();
		filter.setNotificationTypes("notif");
		obj.setFilter(filter);
		final Node res = ev.convertRequestToNode(obj);
		assertTrue(res instanceof GenericCondition);
		assertRightNeverNull(res);
		final GenericCondition gc0 = (GenericCondition) res;
		assertEquals("notificationTypes", gc0.getLeft().toString());
		assertEquals(Operator.EQUAL, gc0.getOp());
		final Node r0 = gc0.getRight();
		assertTrue(r0 instanceof TestValueExpr);
		final TestValueExpr tve = (TestValueExpr) r0;
		assertEquals("notif", tve.value());
	}

	@Test
	void testconvertRequestToString001() throws Exception {
		final EvalService ev = new EvalService(ctx);
		final RequestObject obj = new RequestObject();
		final SubscriptionFilter filter = new SubscriptionFilter();
		filter.setNotificationTypes("notif");
		filter.setBooleanValue(Boolean.TRUE);
		filter.setDoubleValue(123D);
		filter.setNsInstanceIds(List.of());
		filter.setSubscriptionType(SubscriptionType.ALARM);
		obj.setFilter(filter);
		final String res = ev.convertRequestToString(obj);
		assertNotNull(res);
		final Node res2 = ev.convertStringToNode(res);
		assertRightNeverNull(res2);
	}

	@Test
	void testEvaluate() {
		final EvalService ev = new EvalService(ctx);
		final Node left = LabelExpression.of("notificationTypes");
		final Node right = new TestValueExpr("notif");
		final Node nodes = new GenericCondition(left, Operator.EQUAL, right);
		final UUID id = UUID.randomUUID();
		final SubscriptionType subscriptionType = SubscriptionType.VNF;
		final Context localCtx = new TestContext(Map.of());
		when(ctx.build(subscriptionType, id, "notif")).thenReturn(localCtx);
		final boolean res = ev.evaluate(nodes, id, subscriptionType, "notif");
		assertTrue(res);
	}

	private void assertRightNeverNull(final Node res) {
		final Visitor visitor = new RightVisitor();
		res.accept(visitor, null);
	}

}
