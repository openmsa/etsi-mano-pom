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
package com.ubiqube.etsi.mano.grammar.antlr;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.Node.Operand;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class AbstractAntlrGrammarTest {

	@ParameterizedTest
	@ValueSource(strings = { "", "Hello!" })
	void testEmptyQuery(final String param) {
		final TestAntlrGrammar g = new TestAntlrGrammar();
		g.parse(param);
		assertNotNull(g);
	}

	@Test
	void testNullQuery() {
		final TestAntlrGrammar g = new TestAntlrGrammar();
		g.parse(null);
		assertNotNull(g);
	}

	@Test
	void testQueryWrongNode() {
		final TestAntlrGrammar g = new TestAntlrGrammar();
		g.nodes = List.of(new Node<>());
		assertThrows(GrammarException.class, () -> g.parse("Hello!"));
	}

	@Test
	void testQueryNode() {
		final TestAntlrGrammar g = new TestAntlrGrammar();
		g.nodes = List.of(new Node<>("", Operand.EQ, List.of()));
		g.parse("Hello!");
		assertNotNull(g);
	}
}
