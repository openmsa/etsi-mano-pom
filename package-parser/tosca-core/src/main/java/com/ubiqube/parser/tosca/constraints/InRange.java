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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ubiqube.parser.tosca.ParseException;

public class InRange implements Constraint {

	private String min;
	private String max;

	public InRange() {
		//
	}

	public InRange(final ArrayNode key) {
		min = key.get(0).asText();
		max = key.get(1).asText();
	}

	public String getMin() {
		return min;
	}

	public void setMin(final String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(final String max) {
		this.max = max;
	}

	@Override
	public Object evaluate(final Object value) {
		if (value instanceof final Integer i) {
			return (Integer.valueOf(min) >= i) && (i <= (Integer.valueOf(max)));
		}
		if (value instanceof final Double d) {
			return (Double.valueOf(min) >= d) && (d <= (Double.valueOf(max)));
		}
		throw new ParseException("Could not evaluate inRange for type: " + value.getClass().getSimpleName());
	}

}
