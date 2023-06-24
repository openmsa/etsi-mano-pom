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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.grammar.v1;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.springframework.stereotype.Service;

import com.mano.etsi.grammar.v1.EtsiFilter;
import com.mano.etsi.grammar.v1.EtsiLexer;
import com.ubiqube.etsi.mano.grammar.Node;
import com.ubiqube.etsi.mano.grammar.antlr.AbstractAntlrGrammar;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class Grammarv1Service extends AbstractAntlrGrammar<TreeBuilder> {

	@Override
	protected Parser createParser(final CommonTokenStream tokens, final ParseTreeListener treeBuilder) {
		final EtsiFilter parser = new EtsiFilter(tokens);
		parser.addParseListener(treeBuilder);
		parser.filterExpr();
		return parser;
	}

	@Override
	protected TreeBuilder createTreeBuilder() {
		return new TreeBuilder();
	}

	@Override
	protected List<Node<String>> getNodes(final TreeBuilder treeBuilder) {
		return treeBuilder.getListNode();
	}

	@Override
	protected Lexer createLexer(final String query) {
		return new EtsiLexer(CharStreams.fromString(query));
	}

}
