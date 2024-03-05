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
package com.ubiqube.etsi.mano.service.event;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResult;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;

public class TestOrchExecutionResults<U> implements OrchExecutionResults<U> {

	private final List<OrchExecutionResult<U>> success = new ArrayList<>();
	private final List<OrchExecutionResult<U>> error = new ArrayList<>();

	@Override
	public List<OrchExecutionResult<U>> getSuccess() {
		return success;
	}

	@Override
	public List<OrchExecutionResult<U>> getErrored() {
		return error;
	}

	@Override
	public void addAll(final OrchExecutionResults<U> convertResults) {
		// TODO Auto-generated method stub

	}

	void addSuccess(final OrchExecutionResult<U> elem) {
		success.add(elem);
	}

	void addError(final OrchExecutionResult<U> elem) {
		error.add(elem);
	}
}
