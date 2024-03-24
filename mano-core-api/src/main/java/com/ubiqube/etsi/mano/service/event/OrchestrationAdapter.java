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

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;

public interface OrchestrationAdapter<B extends VimTask, V extends Instance> {

	void createLiveInstance(Instance instance, String il, Task task, Blueprint<? extends Task, ? extends Instance> blueprint);

	void deleteLiveInstance(UUID removedLiveInstanceId);

	Blueprint<B, V> getBluePrint(UUID blueprintId);

	Instance getInstance(UUID blueprintId);

	PackageBase getPackage(Instance instance);

	Instance getInstance(Blueprint<B, V> blueprint);

	/**
	 * Parameterized type pose some problem on this function.
	 *
	 * @param blueprint
	 * @return A blueprint.
	 */
	Blueprint<B, V> save(Blueprint blueprint);

	Instance save(Instance instance);

	Blueprint<B, V> updateState(Blueprint<B, V> localPlan, OperationStatusType processing);

	void fireEvent(WorkflowEvent instantiateProcessing, UUID id);

	void consolidate(Instance inst);
}
