/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.service.cond;

public class AstUtils {

	private AstUtils() {
		//
	}

	public static String tojavaOp(final Operator op) {
		return switch (op) {
		case EQUAL -> "==";
		case GREATER_OR_EQUAL -> ">=";
		case GREATER_THAN -> ">";
		case LESS_OR_EQUAL -> "<=";
		case LESS_THAN -> "<";
		case OR -> "||";
		case AND -> "&&";
		case NOT -> "!=";
		default -> throw new IllegalArgumentException("Unexpected value: " + op);
		};
	}

	public static Operator invert(final Operator operator) {
		return switch (operator) {
		case AND, OR -> throw new AstException("Illegal operator: " + operator);
		case LESS_THAN -> Operator.GREATER_OR_EQUAL;
		case LESS_OR_EQUAL -> Operator.GREATER_THAN;
		case GREATER_OR_EQUAL -> Operator.LESS_THAN;
		case GREATER_THAN -> Operator.LESS_OR_EQUAL;
		case EQUAL -> Operator.NOT;
		case NOT -> Operator.EQUAL;
		default -> throw new IllegalArgumentException("Unexpected value: " + operator);
		};
	}
}
