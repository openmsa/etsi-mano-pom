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
package com.ubiqube.parser.tosca.walker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.javapoet.TypeName;
import com.ubiqube.parser.tosca.sol006.statement.ContainerStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafListStatement;
import com.ubiqube.parser.tosca.sol006.statement.LeafStatement;
import com.ubiqube.parser.tosca.sol006.statement.ListStatement;
import com.ubiqube.parser.tosca.sol006.statement.Statement;
import com.ubiqube.parser.tosca.sol006.statement.UsesStatement;

public class JavaPoetListener implements WalkerListener {
	private final Path targetFolder;

	// private final LinkedList<Context> stack = new LinkedList<>();

	private final Map<String, TypeName> cache = new HashMap<>();

	private static final Pattern CAMELCASE_VARIABLES = Pattern.compile("-(?<grab>.)");
	private static final Pattern CAMELCASE_CLASS = Pattern.compile("(^|-)(?<grab>.)");

	public JavaPoetListener(final String path) {
		targetFolder = Paths.get(path);
	}

	@Override
	public void startContainer(final ContainerStatement container) {
		final String pkg = getPackage(container);
		System.out.println(" " + pkg + " " + toJavaClassName(container.getName()));
	}

	private static String toJavaClassName(final String name) {
		return transformList(CAMELCASE_CLASS, name);
	}

	private static String toJavaVariableName(final String name) {
		return transformList(CAMELCASE_VARIABLES, name);
	}

	private static String transformList(final Pattern pattern, final String name) {
		final Matcher m = pattern.matcher(name);
		final StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, m.group("grab").toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private static String getPackage(final Statement container) {
		Statement parent = container.getParent();
		final LinkedList<Statement> st = new LinkedList<>();
		while (parent != null) {
			st.push(parent);
			parent = parent.getParent();
		}
		final Statement elem = st.pop();
		return makePAckageStrin(elem.getNamespace(), elem.getLatestRevision().getValue(), st);
	}

	private static String makePAckageStrin(final String namespace, final String value, final LinkedList<Statement> st) {
		final String base = namespace.replace(":", ".")
				.replace("-", ".")
				.concat(".v").concat(value.replace("-", ""));

		return base + "==>" + st;
	}

	@Override
	public void endContainer(final ContainerStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listStart(final ListStatement x) {
		final String pkg = getPackage(x);
		System.out.println(" " + pkg + " " + toJavaClassName(x.getName()));

	}

	@Override
	public void listEnd(final ListStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leafStart(final LeafStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leafEnd(final LeafStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leafListStart(final LeafListStatement x) {
		final String pkg = getPackage(x);
		System.out.println(" " + pkg + " " + x.getName());
	}

	@Override
	public void leafListEnd(final LeafListStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void usesStart(final UsesStatement x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void usesEnd(final UsesStatement x) {
		// TODO Auto-generated method stub

	}

}
