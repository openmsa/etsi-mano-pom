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
package com.ubiqube.parser.tosca.sol006;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.ubiqube.parser.tosca.sol006.statement.Statement;

public class KeywordToObject {
	private final Map<String, Class<? extends Statement>> yangStatements;

	public KeywordToObject() {
		yangStatements = new HashMap<>();
		final Reflections refl = new Reflections("com.ubiqube.parser.tosca.sol006.statement");
		final Set<Class<? extends Statement>> allClasses = refl.getSubTypesOf(Statement.class);
		for (final Class<? extends Statement> class1 : allClasses) {
			final Statement clazz = createClass(class1);
			final String yt = clazz.getYangName();
			yangStatements.put(yt, class1);
		}
	}

	public Class<? extends Statement> getImplementation(final String yangType) {
		return yangStatements.get(yangType);
	}

	private static Statement createClass(final Class<? extends Statement> x) {
		try {
			return x.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
