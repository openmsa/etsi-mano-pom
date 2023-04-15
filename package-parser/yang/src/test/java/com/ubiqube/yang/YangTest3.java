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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.yang.YangStatementLexer;
import com.ubiqube.etsi.mano.yang.YangStatementParser;
import com.ubiqube.etsi.mano.yang.YangStatementParser.FileContext;
import com.ubiqube.parser.tosca.generator.YangException;
import com.ubiqube.parser.tosca.generator.YangLoader;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Concatenation;
import com.ubiqube.parser.tosca.sol006.ir.IrArgument.Single;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword;
import com.ubiqube.parser.tosca.sol006.ir.IrKeyword.Unqualified;
import com.ubiqube.parser.tosca.sol006.ir.IrStatement;
import com.ubiqube.parser.tosca.sol006.ir.YangRoot;
import com.ubiqube.parser.tosca.sol006.statement.ImportStatement;
import com.ubiqube.parser.tosca.sol006.statement.IncludeStatement;
import com.ubiqube.parser.tosca.sol006.statement.ModuleStatement;
import com.ubiqube.parser.tosca.sol006.statement.NamedStatement;
import com.ubiqube.parser.tosca.sol006.statement.SubMouduleStatement;
import com.ubiqube.parser.tosca.walker.ClassCollectorListener;
import com.ubiqube.parser.tosca.walker.JavaPoetListener;

class YangTest3 {
	private static final Logger LOG = LoggerFactory.getLogger(YangTest3.class);

	@Test
	void testName() throws Exception {
		final InputStream stream = new FileInputStream("src/main/resources/4.3.1/etsi-nfv-vnfd.yang");
		final YangStatementLexer lexer = new YangStatementLexer(CharStreams.fromStream(stream));
		final YangStatementParser parser = new YangStatementParser(new CommonTokenStream(lexer));
		final FileContext result = parser.file();
	}

	@Test
	void testName2() throws Exception {
		final YangLoader yl = new YangLoader();
		final List<YangRoot> res = yl.loadDirectory(Paths.get("src/main/resources/4.3.1/"));
		final YangRoot yr = mergeRoot(res);
		yr.rebuildNameSpaces();
		final List<ModuleStatement> r = findRoots(yr);
		System.out.println("" + r.size());
		final ModuleStatement root = r.get(0);
		resolvInclude(yr, root);
		makeClassUniq(root);
		Recurse.doIt(root, new JavaPoetListener("src/main/javapoet"));
	}

	private void makeClassUniq(final ModuleStatement root) {
		final List<Entry<String, List<NamedStatement>>> res = getDuplicated(root);
		res.forEach(x -> print2(x));
		System.out.println("==================================");
		final List<Entry<String, List<NamedStatement>>> res2 = getDuplicated(root);
		res2.forEach(x -> print(x));
	}

	private List<Entry<String, List<NamedStatement>>> getDuplicated(final ModuleStatement root) {
		final ClassCollectorListener list = new ClassCollectorListener();
		Recurse.doIt(root, list);
		final Map<String, List<NamedStatement>> all = list.getMap().getMap();
		return all.entrySet().stream().filter(x -> x.getValue().size() > 1).toList();
	}

	private Object print(final Entry<String, List<NamedStatement>> x) {
		x.getValue().stream().forEach(y -> {
			if (!(y.getParent() instanceof final NamedStatement ns)) {
				throw new YangException(y.getName() + ", have a parent of type " + y.getName() + "/" + x.getClass().getSimpleName());
			}
			if (y.getName().equals(ns.getName())) {
				LOG.warn("Parent = child name {}", y.getName());
			} else {
				final String className = y.getName() + "-" + ns.getName();
				y.setClassName(className);
				System.out.println(y.getName() + " " + ns.getName());
			}
		});
		return null;
	}

	private Object print2(final Entry<String, List<NamedStatement>> x) {
		final int max = x.getValue().size();
		if (max > 20) {
			System.out.println(x.getKey());
		}
		for (int i = 0; i < max; i++) {
			final NamedStatement elem = x.getValue().get(i);
			elem.setClassName(elem.getClassName() + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i));
		}
		return null;
	}

	private static void resolvInclude(final YangRoot res, final ModuleStatement root) {
		root.getInclude().forEach(x -> {
			final SubMouduleStatement r = findSubModuleByName(res, x.getName());
			merge(root, r);
		});
	}

	private static void merge(final ModuleStatement root, final SubMouduleStatement r) {
		addAll(root.getGrouping(), r.getGrouping());
		addAll(root.getTypeDef(), r.getTypedef());
	}

	private static <U extends NamedStatement> void addAll(final List<U> target, final List<U> toAdd) {
		toAdd.forEach(x -> {
			checkUniqueness(target, x.getName());
			target.add(x);
		});
	}

	private static void checkUniqueness(final List<? extends NamedStatement> target, final String name) {
		if (target.stream().anyMatch(x -> x.getName().equals(name))) {
			throw new YangException("");
		}
	}

	private static SubMouduleStatement findSubModuleByName(final YangRoot res, final String name) {
		return res.getSubmodule().stream()
				.filter(x -> x.getName().equals(name))
				.findFirst()
				.orElseThrow();
	}

	private List<ModuleStatement> findRoots(final YangRoot yr) {
		final List<ModuleStatement> mods = yr.getModule();
		final List<ModuleStatement> res = new ArrayList<>(mods);
		final List<ModuleStatement> no = mods.stream().filter(x -> isRoot(x)).toList();
		no.forEach(x -> {
			final List<IncludeStatement> incs = x.getInclude();
			incs.stream().forEach(y -> removeInclude(y, res));
			final List<ImportStatement> imps = x.getImp();
			imps.stream().forEach(y -> removeImport(y, res));
		});
		return mods.stream().filter(x -> x.getName().equals("etsi-nfv-descriptors")).toList();
	}

	private static void removeImport(final ImportStatement y, final List<ModuleStatement> res) {
		final List<ModuleStatement> toRemove = res.stream().filter(x -> isMatching(x, y)).toList();
		toRemove.forEach(x -> res.remove(x));
	}

	private static boolean isMatching(final ModuleStatement x, final ImportStatement y) {
		return x.getName().equals(y.getName());
	}

	private static void removeInclude(final IncludeStatement y, final List<ModuleStatement> res) {
		final List<ModuleStatement> toRemove = res.stream().filter(x -> isMatching(x, y)).toList();
		toRemove.forEach(x -> res.remove(x));
	}

	private static boolean isMatching(final ModuleStatement x, final IncludeStatement y) {
		return x.getName().equals(y.getName());
	}

	private boolean isRoot(final ModuleStatement x) {
		if (null != x.getBelongsTo()) {
			return false;
		}
		return true;
	}

	private YangRoot mergeRoot(final List<YangRoot> res) {
		final YangRoot yr = new YangRoot();
		res.forEach(x -> {
			yr.getModule().addAll(x.getModule());
			yr.getSubmodule().addAll(x.getSubmodule());
		});
		return yr;
	}

	private void doParse(final List<ParseTree> children) {
		for (final ParseTree parseTree : children) {
			System.out.println("c=" + parseTree.getChildCount());
		}
	}

	public static void process(final IrStatement res) {
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
