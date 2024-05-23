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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VnfStorage;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class OsContainerContributor extends AbstractVnfmContributor<Object> {
	protected OsContainerContributor(final VnfLiveInstanceJpa vnfInstanceJpa) {
		super(vnfInstanceJpa);
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final List<SclableResources<Object>> ret = new ArrayList<>();
		final VnfPackage vnfPackage = bundle;
		vnfPackage.getOsContainerDeployableUnits().forEach(x -> {
			x.getVirtualStorageReq().forEach(y -> {
				final StorageTask st = createTask(StorageTask::new);
				st.setType(ResourceTypeEnum.STORAGE);
				st.setVnfStorage(findStorage(vnfPackage, y));
				st.setToscaName(y);
				ret.add(create(Storage.class, st.getClass(), st.getToscaName(), 1, st, parameters.getInstance(), parameters));
			});
			final OsContainerDeployableTask t = createTask(OsContainerDeployableTask::new);
			t.setBlueprint(parameters);
			t.setOsContainerDeployableUnit(x);
			t.setToscaName(x.getName());
			t.setNetwork("public");
			t.setVnfInstId(parameters.getVnfInstance().getId().toString());
			t.setType(ResourceTypeEnum.OS_CONTAINER_DEPLOYABLE);
			ret.add(create(OsContainerDeployableNode.class, t.getClass(), t.getToscaName(), 1, t, parameters.getInstance(), parameters));
			final K8sInformationsTask inf = createTask(K8sInformationsTask::new);
			inf.setToscaName(x.getName());
			inf.setBlueprint(parameters);
			inf.setVnfInstId(parameters.getVnfInstance().getId().toString());
			inf.setType(ResourceTypeEnum.OS_CONTAINER_INFO);
			ret.add(create(OsK8sInformationsNode.class, inf.getClass(), inf.getToscaName(), 1, inf, parameters.getInstance(), parameters));
			final MciopUserTask mciop = createTask(MciopUserTask::new);
			mciop.setToscaName(x.getName());
			mciop.setParentVdu(x.getName());
			mciop.setType(ResourceTypeEnum.MCIOP_USER);
			ret.add(create(MciopUser.class, mciop.getClass(), mciop.getToscaName(), 1, mciop, parameters.getInstance(), parameters));

		});
		vnfPackage.getOsContainer().forEach(x -> {
			final OsContainerTask t = createTask(OsContainerTask::new);
			t.setToscaName(x.getName());
			t.setBlueprint(parameters);
			t.setType(ResourceTypeEnum.OS_CONTAINER);
			t.setChangeType(ChangeType.ADDED);
			t.setOsContainer(x);
			final String deploy = findDu(vnfPackage.getOsContainerDeployableUnits(), x.getName());
			t.setDeployableName(deploy);
			ret.add(create(OsContainerNode.class, t.getClass(), t.getToscaName(), 1, t, parameters.getInstance(), parameters));
		});

		vnfPackage.getMciops().forEach(x -> x.getAssociatedVdu().forEach(y -> {
			final HelmTask t = createTask(HelmTask::new);
			t.setBlueprint(parameters);
			t.setMciop(x);
			t.setAlias(x.getToscaName());
			t.setToscaName(x.getToscaName());
			t.setType(ResourceTypeEnum.HELM);
			t.setParentVdu(y);
			t.setVnfPackageId(vnfPackage.getId());
			ret.add(create(HelmNode.class, t.getClass(), t.getToscaName(), 1, t, parameters.getInstance(), parameters));
		}));
		return ret;
	}

	private static String findDu(final Set<OsContainerDeployableUnit> osContainerDeployableUnits, final String name) {
		return osContainerDeployableUnits.stream()
				.filter(x -> x.getContainerReq().contains(name))
				.map(OsContainerDeployableUnit::getName)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find Deployable unit [" + name + "]"));
	}

	private static VnfStorage findStorage(final VnfPackage vnfPackage, final String namey) {
		return vnfPackage.getVnfStorage().stream()
				.filter(x -> x.getToscaName().equals(namey))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find storage [" + namey + "]"));
	}
}
