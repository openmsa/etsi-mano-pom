package com.ubiqube.parser.tosca.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ubiqube.parser.tosca.InputBean;
import com.ubiqube.parser.tosca.ParseException;
import com.ubiqube.parser.tosca.ToscaContext;
import com.ubiqube.parser.tosca.constraints.Constraint;

public class ParameterChecker {
	private List<String> errors;

	public Map<String, String> checkParameters(final ToscaContext root, final Map<String, String> inParameters) {
		errors = new ArrayList<>();
		if (inParameters.isEmpty()) {
			return inParameters;
		}
		final Map<String, InputBean> inputs = root.getTopologies().getInputs();
		if (inputs.isEmpty()) {
			throw new ParseException("Unknow parameters : " + inParameters.keySet().stream().collect(Collectors.joining(",")));
		}
		inParameters.entrySet().forEach(x -> check(x.getKey(), x.getValue(), inputs.get(x.getKey())));
		return inParameters;
	}

	private void check(final String key, final String value, final InputBean inputBean) {
		Objects.requireNonNull(inputBean, "No input definitions for element: " + key);
		final Object val = (null == value) ? inputBean.getDef() : value;
		final List<Constraint> constraint = inputBean.getConstraints();
		if ((constraint == null) || constraint.isEmpty()) {
			return;
		}
		final Object newValue = ValueConverter.convert(inputBean.getType(), val);
		constraint.forEach(x -> checkConstraint(key, x, newValue));
	}

	private void checkConstraint(final String key, final Constraint constraint, final Object value) {
		if (!ConstraintValidator.isValid(constraint, value)) {
			errors.add("Field " + key + " doesn't match constraint " + constraint);
		}
	}

	public List<String> getErrors() {
		return errors;
	}

}
