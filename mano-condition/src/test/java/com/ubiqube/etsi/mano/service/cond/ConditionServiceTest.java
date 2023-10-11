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
package com.ubiqube.etsi.mano.service.cond;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ConditionServiceTest {

	ConditionService createService() {
		return new ConditionService();
	}

	@Test
	void test() {
		final ConditionService srv = createService();
		// [{"utilization_vnf_indicator":[{"greater_or_equal":2.0},{"less_or_equal":10.0}]}]
		final Node r2 = srv.parse("[{\"utilization_vnf_indicator\":[{\"greater_or_equal\":2.0},{\"less_or_equal\":10.0}]}]");
		assertNotNull(r2);
		// Hard to assert because it could be optimized.
		// GenericCondition [left=GenericCondition [left=utilization_vnf_indicator,
		// op=GREATER_OR_EQUAL, right=NumberValueExpr [value=2.0]], op=AND,
		// right=GenericCondition [left=utilization_vnf_indicator, op=LESS_OR_EQUAL,
		// right=NumberValueExpr [value=10.0]]]
	}

}
