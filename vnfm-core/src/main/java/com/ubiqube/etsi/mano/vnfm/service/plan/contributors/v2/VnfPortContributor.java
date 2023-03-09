/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfPortContributor extends AbstractContributorV3Base<VnfPortTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfPortContributor.class);

	public VnfPortContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
	}

	private static @Nullable ExtManagedVirtualLinkDataEntity findExtManagedInfo(final VnfBlueprint plan, final String vlName) {
		return plan.getParameters().getExtManagedVirtualLinks().stream()
				.filter(x -> x.getVnfVirtualLinkDescId().equals(vlName))
				.findFirst()
				.orElse(null);
	}

	private static Optional<String> findVlName(final VnfPackage vnfPackage, final String toscaName) {
		return vnfPackage.getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.filter(x -> x.getValue().equals(toscaName))
				.map(VnfPortContributor::getVl)
				.findFirst();
	}

	private static String getVl(final ListKeyPair kp) {
		if (kp.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + kp.getIdx();
	}

	@Override
	public List<SclableResources<VnfPortTask>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		return bundle.getVnfLinkPort().stream().map(x -> {
			final VnfPortTask task = createTask(VnfPortTask::new);
			task.setChangeType(ChangeType.ADDED);
			task.setType(ResourceTypeEnum.LINKPORT);
			task.setToscaName(x.getToscaName());
			task.setVnfLinkPort(x);
			// task.setCompute(compute);
			if (task.getVnfLinkPort().getVirtualLink() != null) {
				return null;
			}
			final Optional<String> vlName = findVlName(bundle, x.getToscaName());
			if (vlName.isEmpty()) {
				return null;
			}
			final ExtManagedVirtualLinkDataEntity external = findExtManagedInfo(parameters, vlName.get());
			if (null == external) {
				LOG.warn("Impossible to find VL {}, will try after GrantRequest", vlName.get());
			}
			task.setExternal(external);
			return create(VnfPortNode.class, task.getToscaName(), 1, task, parameters.getInstance());
		})
				.filter(Objects::nonNull)
				.toList();
	}

}
