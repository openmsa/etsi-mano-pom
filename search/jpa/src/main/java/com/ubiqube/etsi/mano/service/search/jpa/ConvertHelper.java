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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.search.jpa;

import java.util.UUID;

import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.SearchException;

public class ConvertHelper {

	@SuppressWarnings("unchecked")
	public static Comparable convertComparable(final GrammarValue gv, final Class<?> clazz) {
		if (clazz.isEnum()) {
			try {
				return Enum.valueOf((Class<? extends Enum>) clazz, gv.getAsString());
			} catch (final IllegalArgumentException e) {
				throw new GrammarException("Unable to find correct value for " + clazz.getSimpleName() + " field and value: " + gv.getAsString());
			}
		}
		if (clazz.isAssignableFrom(String.class)) {
			return gv.getAsString();
		}
		if (clazz.isAssignableFrom(Long.class)) {
			return Long.valueOf(gv.getAsString());
		}
		if (clazz.isAssignableFrom(Integer.class)) {
			return Integer.valueOf(gv.getAsString());
		}
		if (clazz.isAssignableFrom(UUID.class)) {
			return UUID.fromString(gv.getAsString());
		}
		throw new SearchException("Unknown class: " + clazz.getCanonicalName());
	}
}
