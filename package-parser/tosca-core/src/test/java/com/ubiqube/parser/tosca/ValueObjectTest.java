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
package com.ubiqube.parser.tosca;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("static-method")
class ValueObjectTest {
	private static final Logger LOG = LoggerFactory.getLogger(ValueObjectTest.class);

	@Test
	void test001() throws Exception {
		final ValueObject res = ValueObject.listOf("string");
		LOG.debug(res.toString());
		assertTrue(true);
	}

	@Test
	void test002() throws Exception {
		final ValueObject res = ValueObject.mapOf("string");
		LOG.debug(res.toString());
		assertTrue(true);
	}

	@Test
	void test003() throws Exception {
		final ValueObject res = new ValueObject("string");
		res.setDef("");
		res.setDescription("");
		final EntrySchema EntrySchema = new EntrySchema();
		res.setEntrySchema(null);
		res.setConstraints(null);
		res.setRequired(false);
		res.setStatus("");
		LOG.debug(res.toString());
		assertTrue(true);
	}

	@Test
	void testName() throws Exception {
		TestBean.testClass(ValueObject.class);
	}
}
