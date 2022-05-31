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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.yang.YangStatementLexer;
import com.ubiqube.etsi.mano.yang.YangStatementParser;
import com.ubiqube.etsi.mano.yang.YangStatementParser.ArgumentContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.FileContext;
import com.ubiqube.etsi.mano.yang.YangStatementParser.StatementContext;
import com.ubiqube.parser.tosca.sol006.IrVisitor;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Concatenation;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.DoubleQuoted;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Identifier;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Single;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.SingleQuoted;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Unquoted;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword.Unqualified;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;

public class Yang2Test {

	@Test
	void testName() throws Exception {
		final InputStream stream = new FileInputStream("src/main/resources/etsi-nfv-vnf.yang");
		final YangStatementLexer lexer = new YangStatementLexer(CharStreams.fromStream(stream));
		final YangStatementParser parser = new YangStatementParser(new CommonTokenStream(lexer));
		final FileContext result = parser.file();
		final StatementContext stmt = result.statement();
		final IrStatement res = statementOf(stmt);
		getLastRevision(res);
		final IrVisitor visitor = new IrVisitor();
		// visitor.visit(res);
		parseImports(res);
	}

	private String getLastRevision(final IrStatement stmt) {
		return stmt.getStatements().stream().filter(x -> isBuiltin(x, "revision"))
				.map(IrStatement::getArgument)
				.map(this::argumentToString)
				.sorted(Comparator.reverseOrder())
				.findFirst()
				.orElse(null);
	}

	private Set<ModuleImport> parseImports(final IrStatement module) {
		final Set<ModuleImport> result = new HashSet<>();
		for (final IrStatement substatement : module.getStatements()) {
			if (isBuiltin(substatement, "import")) {
				final String importedModuleName = argumentToString(substatement.getArgument());
				final String revisionDateStr = getRevisionDateString(substatement);
				System.out.println("import " + importedModuleName + " => " + revisionDateStr);
			}
		}
		return null;
	}

	private String getRevisionDateString(final IrStatement importStatement) {
		String revisionDateStr = null;
		for (final IrStatement substatement : importStatement.getStatements()) {
			if (isBuiltin(substatement, "revision-date")) {
				revisionDateStr = argumentToString(substatement.getArgument());
			}
		}
		return revisionDateStr;
	}

	private String argumentToString(final IrArgument arg) {
		if (arg instanceof final Single s) {
			// Need to unescape.
			return s.string();
		}
		final Concatenation c = (Concatenation) arg;
		return concatStrings(c.parts());
	}

	private String concatStrings(final List<? extends Single> parts) {
		final StringBuilder sb = new StringBuilder();
		for (final Single part : parts) {
			final String str = part.string();
			sb.append(str);
		}
		return sb.toString();
	}

	private static boolean isBuiltin(final IrStatement stmt, final String localName) {
		final IrKeyword keyword = stmt.getKeyword();
		return keyword instanceof Unqualified && localName.equals(keyword.identifier());
	}

	private IrStatement statementOf(final StatementContext stmt) {
		final ParseTree firstChild = stmt.getChild(0);
		final ParseTree keywordStart = firstChild.getChild(0);
		System.out.println("" + keywordStart + " c=" + firstChild.getChildCount());
		final Token keywordToken = ((TerminalNode) keywordStart).getSymbol();
		final IrKeyword keyword = new Unqualified(keywordToken.getText());
		System.out.println("" + keyword);
		final IrArgument argument = createArgument(stmt);
		if (1 != firstChild.getChildCount()) {
			throw new ParseCancellationException();
		}
		final List<IrStatement> statements = createStatements(stmt);
		final int line = keywordToken.getLine();
		final int column = keywordToken.getCharPositionInLine();
		System.out.println("======================");
		return new IrStatement(keyword, argument, statements, line, column);
	}

	private static IrArgument createArgument(final StatementContext stmt) {
		final ArgumentContext argument = stmt.argument();
		if (argument == null) {
			return null;
		}
		switch (argument.getChildCount()) {
		case 0:
			throw new RuntimeException("Unexpected shape of " + argument);
		case 1:
			return createSimple(argument);
		case 2:
			return createQuoted(argument);
		default:
			return createConcatenation(argument);
		}
	}

	private static IrArgument createSimple(final ArgumentContext argument) {
		final ParseTree child = argument.getChild(0);
		if (child instanceof TerminalNode) {
			final Token token = ((TerminalNode) child).getSymbol();
			switch (token.getType()) {
			case YangStatementParser.IDENTIFIER:
				// This is as simple as it gets: we are dealing with an identifier here.
				// return idenArguments.computeIfAbsent(strOf(token), Identifier::new);
				return new Identifier(token.getText());
			case YangStatementParser.DQUOT_END:
			case YangStatementParser.SQUOT_END:
				// This is an empty string, the difference between double and single quotes does
				// not exist. Single
				// quotes have more stringent semantics, hence use those.
				// return SingleQuoted.EMPTY;

				return SingleQuoted.EMPTY;
			default:
				throw new RuntimeException("Unexpected token " + token);
			}
		}

		// verify(child instanceof UnquotedStringContext, "Unexpected shape of %s",
		// argument);
		// TODO: check non-presence of quotes and create a different subclass, so that
		// ends up treated as if it
		// was single-quoted, i.e. bypass the check implied by
		// IRArgument.Single#needQuoteCheck().
		// return uquotArguments.computeIfAbsent(strOf(child), Unquoted::new);
		return new Unquoted(child.getText());
	}

	private static IrArgument createQuoted(final ArgumentContext argument) {
		final ParseTree child = argument.getChild(0);
		// verify(child instanceof TerminalNode, "Unexpected literal %s", child);
		final Token token = ((TerminalNode) child).getSymbol();
		switch (token.getType()) {
		case YangStatementParser.DQUOT_STRING:
			return createDoubleQuoted(token);
		case YangStatementParser.SQUOT_STRING:
			return createSingleQuoted(token);
		default:
			throw new RuntimeException("Unexpected token " + token);
		}
	}

	private static IrArgument createSingleQuoted(final Token token) {
		// return squotArguments.computeIfAbsent(strOf(token), SingleQuoted::new);
		return new SingleQuoted(token.getText());
	}

	private static IrArgument createDoubleQuoted(final Token token) {
		return new DoubleQuoted(token.getText());
	}

	private List<IrStatement> createStatements(final StatementContext stmt) {
		final List<StatementContext> statements = stmt.statement();
		return statements.stream().map(this::statementOf).toList();
	}

	private static IrArgument createConcatenation(final ArgumentContext argument) {
		final List<Single> parts = new ArrayList<>();
		for (final ParseTree child : argument.children) {
			// verify(child instanceof TerminalNode, "Unexpected argument component %s",
			// child);
			final Token token = ((TerminalNode) child).getSymbol();
			switch (token.getType()) {
			case YangStatementParser.SEP:
				// Separator, just skip it over
			case YangStatementParser.PLUS:
				// Operator, which we are handling by concat, skip it over
			case YangStatementParser.DQUOT_END:
			case YangStatementParser.SQUOT_END:
				// Quote stops, skip them over because we either already added the content, or
				// would be appending
				// an empty string
				break;
			case YangStatementParser.SQUOT_STRING:
				return createSingleQuoted(token);
			case YangStatementParser.DQUOT_STRING:
				return createDoubleQuoted(token);
			default:
				throw new RuntimeException("Unexpected token " + token);
			}
		}
		switch (parts.size()) {
		case 0:
			// A concatenation of empty strings, fall back to a single unquoted string
			return SingleQuoted.EMPTY;
		case 1:
			// A single string concatenated with empty string(s), use just the significant
			// portion
			return parts.get(0);
		default:
			// TODO: perform concatenation of single-quoted strings. For double-quoted
			// strings this may not be as
			// nice, but for single-quoted strings we do not need further validation in in
			// the reactor and can
			// use them as raw literals. This saves some indirection overhead (on memory
			// side) and can
			// slightly improve execution speed when we process the same IR multiple times.
			return new Concatenation(parts);
		}
	}
}
