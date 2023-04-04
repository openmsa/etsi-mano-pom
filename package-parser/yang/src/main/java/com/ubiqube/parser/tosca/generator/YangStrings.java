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
package com.ubiqube.parser.tosca.generator;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.ubiqube.etsi.mano.yang.YangStatementParser;
import com.ubiqube.etsi.mano.yang.YangStatementParser.ArgumentContext;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Concatenation;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.DoubleQuoted;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Identifier;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Single;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.SingleQuoted;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Unquoted;

public class YangStrings {
	private YangStrings() {
		// Nothing.
	}

	public static IrArgument createConcatenation(final ArgumentContext argument) {
		final List<Single> parts = new ArrayList<>();
		for (final ParseTree child : argument.children) {
			final Token token = ((TerminalNode) child).getSymbol();
			switch (token.getType()) {
			// Separator, just skip it over
			// Operator, which we are handling by concat, skip it over
			case YangStatementParser.SEP, YangStatementParser.PLUS, YangStatementParser.DQUOT_END, YangStatementParser.SQUOT_END:
				// Quote stops, skip them over because we either already added the content, or
				// would be appending
				// an empty string
				break;
			case YangStatementParser.SQUOT_STRING:
				return createSingleQuoted(token);
			case YangStatementParser.DQUOT_STRING:
				return createDoubleQuoted(token);
			default:
				throw new YangException("Unexpected token " + token);
			}
		}
		return switch (parts.size()) {
		/* A concatenation of empty strings, fall back to a single unquoted string */
		case 0 -> SingleQuoted.EMPTY;
		/*
		 * A single string concatenated with empty string(s), use just the significant
		 * portion
		 */
		case 1 -> parts.get(0);
		/*
		 * Perform concatenation of single-quoted strings. For double-quoted strings
		 * this may not be as nice, but for single-quoted strings we do not need further
		 * validation in in the reactor and can use them as raw literals. This saves
		 * some indirection overhead (on memory side) and can slightly improve execution
		 * speed when we process the same IR multiple times.
		 */
		default -> new Concatenation(parts);
		};
	}

	public static IrArgument createSingleQuoted(final Token token) {
		return new SingleQuoted(token.getText());
	}

	public static IrArgument createDoubleQuoted(final Token token) {
		return new DoubleQuoted(token.getText());
	}

	public static IrArgument createQuoted(final ArgumentContext argument) {
		final ParseTree child = argument.getChild(0);
		final Token token = ((TerminalNode) child).getSymbol();
		return switch (token.getType()) {
		case YangStatementParser.DQUOT_STRING -> createDoubleQuoted(token);
		case YangStatementParser.SQUOT_STRING -> createSingleQuoted(token);
		default -> throw new YangException("Unexpected token " + token);
		};
	}

	public static IrArgument createSimple(final ArgumentContext argument) {
		final ParseTree child = argument.getChild(0);
		if (child instanceof final TerminalNode tn) {
			final Token token = tn.getSymbol();
			return switch (token.getType()) {
			// This is as simple as it gets: we are dealing with an identifier here.
			case YangStatementParser.IDENTIFIER -> new Identifier(token.getText());
			// This is an empty string, the difference between double and single quotes does
			// not exist. Single
			// quotes have more stringent semantics, hence use those.
			case YangStatementParser.DQUOT_END, YangStatementParser.SQUOT_END -> SingleQuoted.EMPTY;
			default -> throw new YangException("Unexpected token " + token);
			};
		}
		return new Unquoted(child.getText());
	}

}
