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

import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.mockito.Mockito;

import com.ubiqube.etsi.mano.grammar.Node;

public class TestAntlrGrammar extends AbstractAntlrGrammar<TestParseTreeListener> {

	List<Node<String>> nodes = List.of();

	@Override
	protected Parser createParser(final CommonTokenStream tokens, final ParseTreeListener treeBuilder) {
		return Mockito.mock(Parser.class);
	}

	@Override
	protected TestParseTreeListener createTreeBuilder() {
		return Mockito.mock(TestParseTreeListener.class);
	}

	@Override
	protected List<Node<String>> getNodes(final TestParseTreeListener treeBuilder) {
		return nodes;
	}

	@Override
	protected Lexer createLexer(final String query) {
		return Mockito.mock(Lexer.class);
	}

}
