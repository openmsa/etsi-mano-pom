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
package com.ubiqube.yang;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.yang.YangStatementLexer;
import com.ubiqube.etsi.mano.yang.YangStatementParser;
import com.ubiqube.etsi.mano.yang.YangStatementParser.FileContext;
import com.ubiqube.parser.tosca.generator.YangLoader;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Concatenation;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Single;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword.Unqualified;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;
import com.ubiqube.parser.tosca.sol006.ir.YangRoot;
import com.ubiqube.parser.tosca.sol006.statement.ModuleStatement;

class YangTest3 {
	@Test
	void testName() throws Exception {
		final InputStream stream = new FileInputStream("src/main/resources/4.3.1/etsi-nfv-vnfd.yang");
		final YangStatementLexer lexer = new YangStatementLexer(CharStreams.fromStream(stream));
		final YangStatementParser parser = new YangStatementParser(new CommonTokenStream(lexer));
		final FileContext result = parser.file();
	}

	@Test
	void testName2() throws Exception {
		YangLoader.loadDirectory(Paths.get("src/main/resources/4.3.1/"));
	}

	private void doParse(final List<ParseTree> children) {
		for (final ParseTree parseTree : children) {
			System.out.println("c=" + parseTree.getChildCount());
		}
	}

	public static void process(final IrStatement res) {
		System.out.println("" + res.getKeyword());
		final YangRoot yr = new YangRoot();
		yr.load(res);
		final List<ModuleStatement> module = yr.getModule();
		module.forEach(x -> doImport(x));
	}

	private static Object doImport(final ModuleStatement module) {
		module.getImp().forEach(x -> {
			System.out.println("" + x.getPrefix());
		});
		return null;
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

	private static boolean isBuiltin(final IrStatement stmt, final String localName) {
		final IrKeyword keyword = stmt.getKeyword();
		return (keyword instanceof Unqualified) && localName.equals(keyword.identifier());
	}

	private static String argumentToString(final IrArgument arg) {
		if (arg instanceof final Single s) {
			// Need to unescape.
			return s.string();
		}
		final Concatenation c = (Concatenation) arg;
		return concatStrings(c.parts());
	}

	private static String concatStrings(final List<? extends Single> parts) {
		final StringBuilder sb = new StringBuilder();
		for (final Single part : parts) {
			final String str = part.string();
			sb.append(str);
		}
		return sb.toString();
	}

}
