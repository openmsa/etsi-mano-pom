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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ToscaEntity;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.orchestrator.Bundle;
import com.ubiqube.etsi.mano.orchestrator.PlanContributor;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public abstract class AbstractNsContributor<U extends NsTask, T extends VirtualTask<U>> implements PlanContributor<U, T, NsBlueprint> {
	protected AbstractNsContributor() {
		// Nothing.
	}

	protected static <U extends NsTask> U createTask(final Supplier<U> newInstance, final ToscaEntity toscaEntity) {
		final U task = newInstance.get();
		task.setStartDate(LocalDateTime.now());
		task.setStatus(PlanStatusType.NOT_STARTED);
		task.setToscaName(toscaEntity.getToscaName());
		task.setAlias(toscaEntity.getToscaName());
		return task;
	}

	@NotNull
	protected static <U extends NsTask> U createTask(final Supplier<U> newInstance) {
		final U task = newInstance.get();
		task.setStartDate(LocalDateTime.now());
		task.setStatus(PlanStatusType.NOT_STARTED);
		return task;
	}

	protected static <U extends NsTask> U createDeleteTask(final Supplier<U> newInstance, final NsLiveInstance vli) {
		final NsTask t = vli.getNsTask();
		final U task = newInstance.get();
		task.setStartDate(LocalDateTime.now());
		task.setChangeType(ChangeType.REMOVED);
		task.setStatus(PlanStatusType.NOT_STARTED);
		task.setToscaName(t.getToscaName());
		task.setAlias(t.getAlias());
		task.setVimResourceId(vli.getResourceId());
		task.setVimConnectionId(t.getVimConnectionId());
		task.setRemovedLiveInstance(vli.getId());
		return task;
	}

	@Transactional
	@Override
	@NotNull
	public final List<T> contribute(final Bundle bundle, final NsBlueprint blueprint) {
		final List<T> r = switch (blueprint.getOperation()) {
		case TERMINATE -> onTerminate(blueprint.getInstance());
		case INSTANTIATE -> onInstantiate((NsBundleAdapter) bundle, blueprint);
		case SCALE, SCALE_TO_LEVEL -> onScale((NsBundleAdapter) bundle, blueprint);
		case UPDATE -> onUpdate((NsBundleAdapter) bundle, blueprint);
		default -> onOther((NsBundleAdapter) bundle, blueprint);
		};
		final List<U> rr = r.stream().map(VirtualTask::getParameters).toList();
		final Set<NsTask> nTask = new LinkedHashSet<>(blueprint.getTasks());
		nTask.addAll(rr);
		blueprint.setTasks(nTask);
		return r;
	}

	@NotNull
	protected abstract List<T> onScale(NsBundleAdapter bundle, NsBlueprint blueprint);

	@NotNull
	protected abstract List<T> onInstantiate(NsBundleAdapter bundle, NsBlueprint blueprint);

	@NotNull
	protected abstract List<T> onOther(NsBundleAdapter bundle, NsBlueprint blueprint);

	@NotNull
	protected abstract List<T> onTerminate(NsdInstance instance);

	@NotNull
	protected List<T> onUpdate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return List.of();
	}
}
