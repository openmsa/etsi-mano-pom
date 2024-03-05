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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.TemplateExtractorV3;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;

public abstract class AbstractNsdContributorV3<U> implements TemplateExtractorV3<U, NsBlueprint, NsdPackage> {
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	protected AbstractNsdContributorV3(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	protected static <U extends NsTask> U createTask(final Supplier<U> newInstance) {
		final U task = newInstance.get();
		task.setStartDate(LocalDateTime.now());
		task.setChangeType(ChangeType.ADDED);
		task.setStatus(PlanStatusType.NOT_STARTED);
		return task;
	}

	protected int countLive(final NsdInstance nsdInstance, final Class<?> clazz, final String toscaName) {
		return Optional.ofNullable(nsLiveInstanceJpa.countByNsdInstanceIdAndClassAndToscaName(nsdInstance, clazz, toscaName))
				.orElse(0);
	}

	protected SclableResources<U> create(final Class<? extends Node> clazz, final Class<?> task, final String toscaName, final int want, final U param, final NsdInstance inst, final NsBlueprint parameters) {
		int w = want;
		if (parameters.getOperation() == PlanOperationType.TERMINATE) {
			w = 0;
		}
		return SclableResources.of(clazz, toscaName, countLive(inst, task, toscaName), w, param);
	}
}
