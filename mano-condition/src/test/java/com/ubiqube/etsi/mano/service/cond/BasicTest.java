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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;

class BasicTest {
	private static final Logger LOG = LoggerFactory.getLogger(BasicTest.class);

	ConditionService cs = new ConditionService();

	@Test
	void test_A_15_3_1() throws Exception {
		final String condStr = extractConditions("/a-15.3.1.yml");
		final Node root = cs.parse(condStr);
		final PrintVisitor visitor = new PrintVisitor();
		final String str = root.accept(visitor, 0);
		LOG.debug("{}", str);
		final Map<String, Object> map = Map.of("call_proc_scale_level", 1,
				"vnf_2_utilization_vnf_indicator", 80,
				"vnf_1_utilization_vnf_indicator", 80);
		final TestContextNg ctx = new TestContextNg(map);
		final boolean res = cs.evaluate(root, ctx);
		assertTrue(res);
	}

	@Test
	void test_A_15_3_2() throws Exception {
		final String condStr = extractConditions("/a-15.3.1.yml");
		final Node root = cs.parse(condStr);
		final PrintVisitor visitor = new PrintVisitor();
		final String str = root.accept(visitor, 0);
		LOG.debug("{}", str);
		final Map<String, Object> map = Map.of("call_proc_scale_level", 2,
				"vnf_2_utilization_vnf_indicator", 79,
				"vnf_1_utilization_vnf_indicator", 0);
		final TestContextNg ctx = new TestContextNg(map);
		final boolean res = cs.evaluate(root, ctx);
		assertFalse(res);
	}

	private String extractConditions(final String file) throws IOException, JsonProcessingException {
		final ObjectMapper omYaml = new ObjectMapper(new YAMLFactory());
		final ObjectMapper omJson = new ObjectMapper();
		final InputStream is = this.getClass().getResourceAsStream(file);
		final JsonNode tree = omYaml.readTree(is.readAllBytes());
		final JsonNode conds = tree.get("condition");
		return omJson.writeValueAsString(conds);
	}
}
