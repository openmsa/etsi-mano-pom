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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2;

import java.util.List;

import javax.annotation.Priority;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(100)
public class VnfExtCpContributor extends AbstractContributorV3Base<ExternalCpTask> {

	public VnfExtCpContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<ExternalCpTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		return bundle.getVnfExtCp().stream().map(vnfExtCp -> {
			final ExternalCpTask task = createTask(ExternalCpTask::new);
			task.setToscaName(vnfExtCp.getToscaName());
			task.setChangeType(ChangeType.ADDED);
			task.setType(ResourceTypeEnum.LINKPORT);
			task.setVnfExtCp(vnfExtCp);
			task.setPort(true);
			return create(VnfExtCp.class, task.getToscaName(), 1, task, parameters.getInstance());
		})
				.toList();
	}

}
