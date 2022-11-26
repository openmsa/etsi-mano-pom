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
package com.ubiqube.etsi.mano.service.cond;

import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.AttrHolderExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.LengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MaxLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.MinLengthValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.PatternValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.RangeValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;

public interface Visitor<R, A> {

	R visit(BooleanValueExpr booleanValueExpr);

	R visit(BooleanListExpr booleanListExpr, A arg);

	R visit(AttrHolderExpr expr, A args);

	R visit(BooleanExpression be, A arg);

	R visit(RangeValueExpr rangeValueExpr, A arg);

	R visit(LengthValueExpr lengthValueExpr, A arg);

	R visit(MinLengthValueExpr minLengthValueExpr, A arg);

	R visit(MaxLengthValueExpr maxLengthValueExpr, A arg);

	R visit(PatternValueExpr patternValueExpr, A arg);

	R visit(GenericCondition genericCondition, A arg);

	R visit(TestValueExpr testValueExpr, A arg);

	R visit(NumberValueExpr numberValueExpr, A arg);

	R visit(ArrayValueExpr arrayValueExpr, A arg);

	R visit(final LabelExpression expr, final A arg);

}
