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
package com.ubiqube.parser.tosca.constraints;

import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.databind.node.TextNode;
import com.ubiqube.parser.tosca.ParseException;

public class Equal implements Constraint {
	Object value;

	public Equal() {
		//
	}

	public Equal(final Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "equal " + value;
	}

	@Override
	public Object evaluate(final Object valueIn) {
		if (valueIn instanceof final Integer i) {
			final Integer cv = castValue(Integer.class, x -> Integer.valueOf(x.toString()));
			if (null == cv) {
				return Boolean.FALSE;
			}
			return cv.equals(i);
		}
		if (valueIn instanceof final Double d) {
			final Double cv = castValue(Double.class, x -> Double.valueOf(x.toString()));
			if (null == cv) {
				return Boolean.FALSE;
			}
			return cv.equals(d);
		}
		if (valueIn instanceof final String s) {
			return s.equals(value);
		}
		if (valueIn instanceof final List<?> l) {
			return l.equals(value);
		}
		if (valueIn instanceof final Boolean b) {
			return b.equals(value);
		}
		if (valueIn == null) {
			return value == null;
		}
		throw new ParseException("Could not evaluate inRange for type: " + value.getClass().getSimpleName());
	}

	private <U> U castValue(final Class<U> clazz, final Function<Object, U> func) {
		if ((null == value) || (clazz == value.getClass())) {
			return (U) value;
		}
		if (value instanceof final TextNode tn) {
			return func.apply(tn.asText());
		}
		throw new ParseException("Could not cast value of type: " + value.getClass().getSimpleName());
	}
}
