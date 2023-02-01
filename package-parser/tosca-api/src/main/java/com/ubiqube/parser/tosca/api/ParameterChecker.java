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
