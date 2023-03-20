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
package com.ubiqube.etsi.mano.dao.mano;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.AffinityOrAntiAffinityRule.AffinityOrAntiAffiintyEnum;
import com.ubiqube.etsi.mano.dao.mano.AffinityOrAntiAffinityRule.ScopeEnum;

@SuppressWarnings("static-method")
class AffinityOrAntiAffinityRuleTest {

	@Test
	void test() {
		final AffinityOrAntiAffiintyEnum aff = AffinityOrAntiAffinityRule.AffinityOrAntiAffiintyEnum.fromValue(null);
		assertNull(aff);
	}

	@Test
	void test_001() {
		final AffinityOrAntiAffiintyEnum aff = AffinityOrAntiAffinityRule.AffinityOrAntiAffiintyEnum.fromValue("AFFINITY");
		assertNotNull(aff);
	}

	@Test
	void test_002() {
		final ScopeEnum aff = AffinityOrAntiAffinityRule.ScopeEnum.fromValue(null);
		assertNull(aff);
	}

	@Test
	void test_003() {
		final ScopeEnum aff = AffinityOrAntiAffinityRule.ScopeEnum.fromValue("ZONE_GROUP");
		assertNotNull(aff);
	}
}
