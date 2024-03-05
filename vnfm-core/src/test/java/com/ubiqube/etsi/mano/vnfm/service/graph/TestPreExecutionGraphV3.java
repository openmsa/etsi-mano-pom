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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

public class TestPreExecutionGraphV3 implements PreExecutionGraphV3<VnfTask> {
	private final List<VirtualTaskV3<VnfTask>> tasks;

	public TestPreExecutionGraphV3() {
		this.tasks = List.of();
	}

	public TestPreExecutionGraphV3(final List<VirtualTaskV3<VnfTask>> tasks) {
		this.tasks = tasks;
	}

	@Override
	public List<VirtualTaskV3<VnfTask>> getPreTasks() {
		return tasks;
	}

	@Override
	public void toDotFile(final String filename) {
		//
	}

}
