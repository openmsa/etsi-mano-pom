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

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionListener;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfTaskJpa;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class OrchListenetImpl implements OrchExecutionListener<VnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(OrchListenetImpl.class);
	private final VnfBlueprint blueprint;
	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;
	private final VnfTaskJpa vnfTaskJpa;

	public OrchListenetImpl(final VnfBlueprint blueprint, final VnfLiveInstanceJpa vnfLiveInstanceJpa, final VnfTaskJpa vnfTaskJpa) {
		this.blueprint = blueprint;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.vnfTaskJpa = vnfTaskJpa;
	}

	@Override
	public void onStart(final UnitOfWorkV3<VnfTask> uaow) {
		LOG.info("Starting {}", uaow);
		final VnfTask resource = uaow.getVirtualTask().getTemplateParameters();
		resource.setStatus(PlanStatusType.STARTED);
		resource.setEndDate(LocalDateTime.now());
		saveTask(uaow);
	}

	@Override
	public void onTerminate(final UnitOfWorkV3<VnfTask> uaow, final @Nullable String res) {
		LOG.info("Terminate {} => {}", uaow.getVirtualTask(), res);
		final VnfTask resource = uaow.getVirtualTask().getTemplateParameters();
		resource.setVimResourceId(res);
		resource.setStatus(PlanStatusType.SUCCESS);
		resource.setEndDate(LocalDateTime.now());
		if ((resource.getChangeType() == ChangeType.ADDED) && (res != null) && (resource.getId() != null)) {
			final VnfLiveInstance vli = new VnfLiveInstance(blueprint.getInstance(), null, resource, blueprint, resource.getVimResourceId(), resource.getVimConnectionId());
			vnfLiveInstanceJpa.save(vli);
		}
		saveTask(uaow);
	}

	@Override
	public void onError(final UnitOfWorkV3<VnfTask> uaow, final RuntimeException e) {
		final VnfTask resource = uaow.getVirtualTask().getTemplateParameters();
		resource.setStatus(PlanStatusType.FAILED);
		resource.setEndDate(LocalDateTime.now());
		saveTask(uaow);
	}

	private void saveTask(final UnitOfWorkV3<VnfTask> uaow) {
		final VnfTask task = uaow.getVirtualTask().getTemplateParameters();
		final VnfTask res = vnfTaskJpa.save(task);
		uaow.getVirtualTask().setTemplateParameters(res);
	}
}
