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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.grammar;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class Node<U> {

	public enum Operand {
		EQ("eq"),
		NEQ("neq"),
		GT("gt"),
		LT("lt"),
		GTE("gte"),
		LTE("lte"),
		CONT("cont"),
		NCONT("ncont"),
		IN("in"),
		NIN("nin");

		public final String op;

		Operand(final String _op) {
			op = _op;
		}
	}

	@Nullable
	private String name;
	private Operand op;
	@Nonnull
	private List<U> value = new ArrayList<>();

	public Node() {
		// Nothing.
	}

	public Node(final @Nullable String name, @Nullable final Operand op, final List<U> value) {
		this.name = name;
		this.op = op;
		this.value = new ArrayList<>(value);
	}

	public static <U> Node<U> of(final String name, final Operand op, final List<U> value) {
		return new Node<>(name, op, value);
	}

	public static <U> Node<U> of(final String name, final Operand op, final U value) {
		return new Node<>(name, op, List.of(value));
	}

	public @Nullable String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public @Nullable Operand getOp() {
		return op;
	}

	public void setOp(final Operand op) {
		this.op = op;
	}

	public @Nullable U getValue() {
		if (null == value) {
			return null;
		}
		if (value.size() > 1) {
			throw new GenericException("Calling a multivalue.");
		}
		if (value.isEmpty()) {
			return null;
		}
		return value.get(0);
	}

	public List<U> getValues() {
		if (null == value) {
			return List.of();
		}
		return value;
	}

	public void setValue(final List<U> value) {
		this.value = value;
	}

	public void addValue(final U inValue) {
		value.add(inValue);
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", op=" + op + ", value=" + value + "]";
	}

}
