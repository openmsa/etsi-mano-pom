package com.ubiqube.parser.tosca.api;

import com.ubiqube.parser.tosca.constraints.Constraint;

public class ConstraintValidator {

	private ConstraintValidator() {
		//
	}

	public static boolean isValid(final Constraint constraint, final Object value) {
		return (Boolean) constraint.evaluate(value);
	}

}
