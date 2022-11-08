package com.ubiqube.etsi.mano.service.cond;

import com.ubiqube.etsi.mano.service.cond.ast.ArrayValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
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

}
