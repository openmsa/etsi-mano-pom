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

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.parser.tosca.sol006.statement.NamedStatement;
import com.ubiqube.parser.tosca.sol006.statement.Statement;

public class ClassUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

	private static final Pattern CAMELCASE_VARIABLES = Pattern.compile("-(?<grab>.)");

	private static final Pattern CAMELCASE_CLASS = Pattern.compile("(^|-)(?<grab>.)");

	private ClassUtils() {
		// Nothing.
	}

	static String toJavaClassName(final String name) {
		return transformList(CAMELCASE_CLASS, name);
	}

	public static String toJavaVariableName(final String name) {
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

	static String getPackage(final Statement container) {
		Statement parent = container.getParent();
		final LinkedList<NamedStatement> st = new LinkedList<>();
		while (parent != null) {
			if (parent instanceof final NamedStatement ns) {
				st.push(ns);
			} else {
				LOG.warn("Unknown named node: {}", parent.getClass().getSimpleName());
			}
			parent = parent.getParent();
		}
		final Statement elem = st.pop();
		return makePackageString(elem.getNamespace(), elem.getLatestRevision().getValue(), st);
	}

	private static String makePackageString(final String namespace, final String value, final LinkedList<NamedStatement> st) {
		final String base = namespace.replace(":", ".")
				.replace("-", ".")
				.concat(".v").concat(value.replace("-", ""));
		if (st.isEmpty()) {
			return base;
		}
		return base + "." + st.stream()
				.map(x -> x.getName())
				.map(ClassUtils::normalizePackageName)
				.collect(Collectors.joining("."));
	}

	static String normalizePackageName(final String pkg) {
		return pkg.replace(":", ".")
				.replace("-", ".")
				.replace(".int.", "._int.")
				.replace("int.", "_int.")
				.replace(".interface.", "._interface.")
				.replace(".package.", "._package.");
	}

}
