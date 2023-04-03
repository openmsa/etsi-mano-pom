/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.yang;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ubiqube.etsi.mano.yang.YangStatementLexer;
import com.ubiqube.etsi.mano.yang.YangStatementParser;
import com.ubiqube.etsi.mano.yang.YangStatementParser.ArgumentContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.FileContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.StatementContext;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword.Unqualified;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

class Yang2Test {

	@ParameterizedTest
	@ValueSource(strings = { "3.3.1/etsi-nfv-nsd.yang", "3.3.1/etsi-nfv-ns.yang", "3.3.1/etsi-nfv-vnfd.yang", "3.3.1/etsi-nfv-vnf.yang",
			"4.3.1/etsi-nfv-nsd.yang", "4.3.1/etsi-nfv-ns.yang", "4.3.1/etsi-nfv-vnfd.yang", "4.3.1/etsi-nfv-vnf.yang",
			"3.3.1/ietf-yang-types.yang", "3.3.1/ietf-inet-types.yang", "4.3.1/etsi-nfv-descriptors.yang", "4.3.1/etsi-nfv-common.yang" })
	void testName(final String param) throws Exception {
		final InputStream stream = new FileInputStream("src/main/resources/" + param);
		final YangStatementLexer lexer = new YangStatementLexer(CharStreams.fromStream(stream));
		final YangStatementParser parser = new YangStatementParser(new CommonTokenStream(lexer));
		final FileContext result = parser.file();
		final StatementContext stmt = result.statement();
		final IrStatement res = statementOf(stmt);
		YangTest3.process(res);
		assertTrue(true);
	}

	private IrStatement statementOf(final StatementContext stmt) {
		final ParseTree firstChild = stmt.getChild(0);
		final ParseTree keywordStart = firstChild.getChild(0);
		System.out.println("" + keywordStart + " c=" + firstChild.getChildCount());
		final Token keywordToken = ((TerminalNode) keywordStart).getSymbol();
		final IrKeyword keyword = new Unqualified(keywordToken.getText());
		final IrArgument argument = createArgument(stmt);
		if (1 != firstChild.getChildCount()) {
			throw new ParseCancellationException();
		}
		final List<IrStatement> statements = createStatements(stmt);
		final int line = keywordToken.getLine();
		final int column = keywordToken.getCharPositionInLine();
		return new IrStatement(keyword, argument, statements, line, column);
	}

	private static IrArgument createArgument(final StatementContext stmt) {
		final ArgumentContext argument = stmt.argument();
		if (argument == null) {
			return null;
		}
		return switch (argument.getChildCount()) {
		case 0 -> throw new RuntimeException("Unexpected shape of " + argument);
		case 1 -> EClipseProblem.createSimple(argument);
		case 2 -> EClipseProblem.createQuoted(argument);
		default -> EClipseProblem.createConcatenation(argument);
		};
	}

	private List<IrStatement> createStatements(final StatementContext stmt) {
		final List<StatementContext> statements = stmt.statement();
		return statements.stream().map(this::statementOf).toList();
	}

}
