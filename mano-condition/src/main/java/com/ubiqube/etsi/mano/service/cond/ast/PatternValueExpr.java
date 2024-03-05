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
package com.ubiqube.etsi.mano.service.cond.ast;

import java.util.regex.Pattern;

import com.ubiqube.etsi.mano.service.cond.Visitor;

public class PatternValueExpr extends AbstractBooleanExpression {

	private final Pattern p;
	private final String pattern;

	public PatternValueExpr(final String textValue) {
		this.p = Pattern.compile(textValue);
		this.pattern = textValue;
	}

	@Override
	public <R, A> R accept(final Visitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	public Pattern getP() {
		return p;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return "PatternValueExpr [p=" + p + ", pattern=" + pattern + "]";
	}

}
