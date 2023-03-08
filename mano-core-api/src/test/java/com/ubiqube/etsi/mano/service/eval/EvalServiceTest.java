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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.eval;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;

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
		System.out.println(res2);
	}
}
