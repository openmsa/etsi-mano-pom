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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.JujuInformations;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.JujuCloudService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;
import com.ubiqube.etsi.mano.service.juju.entities.JujuModel;
import com.ubiqube.etsi.mano.service.juju.entities.JujuRegion;
import com.ubiqube.etsi.mano.service.vim.CnfK8sParams;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class OsContainerUow extends AbstractVnfmUow<OsContainerTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final OsContainerTask task;
	private final JujuCloudService jujuCloudService;

	public OsContainerUow(final VirtualTaskV3<OsContainerTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation,
			final JujuCloudService jujuCloudService) {
		super(task, OsContainerNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
		this.jujuCloudService = jujuCloudService;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final CnfInformations cnfi = vimConnectionInformation.getCnfInfo();
		final JujuInformations jujui = vimConnectionInformation.getJujuInfo();
		final OsContainer osc = task.getOsContainer();
		osc.getCpuResourceLimit();
		osc.getEphemeralStorageResourceLimit();
		osc.getExtendedResourceRequests();
		osc.getHugePagesResources();
		osc.getMemoryResourceLimit();
		osc.getRequestedCpuResources();
		osc.getRequestedEphemeralStorageResources();
		osc.getRequestedMemoryResources();
		if (cnfi != null) {
			final CnfK8sParams params = CnfK8sParams.builder()
					.clusterTemplate(cnfi.getClusterTemplate())
					.dnsServer(cnfi.getDnsServer())
					.externalNetworkId("")
					.keypair(cnfi.getKeyPair())
					.masterFlavor(cnfi.getMasterFlavorId())
					.name(task.getAlias())
					.networkDriver("flannel")
					.serverType("vm")
					.volumeSize(10)
					.build();
			vim.cnf(vimConnectionInformation).createContainer(params);
		}
		if (jujui != null) {
			final String type = vimConnectionInformation.getVimType();
			final String nameofCloud = vimConnectionInformation.getVimId();
			final Map<String, String> accessInfo = vimConnectionInformation.getAccessInfo();
			final String password = accessInfo.get("password");
			final String credName = "admin-" + accessInfo.get("projectId");
			final String username = accessInfo.get("username");
			final Map<String, String> interfaceInfo = vimConnectionInformation.getInterfaceInfo();
			final String endpoint = interfaceInfo.get("endpoint");
			final String controllerName = vimConnectionInformation.getVimId() + "-" + vimConnectionInformation.getJujuInfo().getRegion().toLowerCase();
			final String imageId = vimConnectionInformation.getJujuInfo().getImageId();
			final String constraints = vimConnectionInformation.getJujuInfo().getConstraints();
			final String region = vimConnectionInformation.getJujuInfo().getRegion();
			final String networkId = vimConnectionInformation.getJujuInfo().getNetworkId();
			final String charName = vimConnectionInformation.getJujuInfo().getCharmName();
			final String appName = vimConnectionInformation.getJujuInfo().getAppName();
			final String osSeries = vimConnectionInformation.getJujuInfo().getOsSeries();
			final JujuRegion region2 = new JujuRegion(region, endpoint);
			final List<JujuRegion> regions = new ArrayList<>();
			regions.add(region2);
			final JujuCredential jujuCredential = new JujuCredential(credName, "userpass", username, password, "admin");
			final JujuModel model = new JujuModel("k8s-ubi-model-kt", charName, appName);
			final List<JujuModel> models = new ArrayList<>();
			models.add(model);
			final List<String> constraints2 = new ArrayList<>();
			constraints2.add(constraints);
			final JujuMetadata metadata = new JujuMetadata(controllerName, imageId, "~/simplestreams", osSeries, endpoint, constraints2, networkId, region, models);
			final JujuCloud jCloud = new JujuCloud(nameofCloud, type, "userpass", regions, jujuCredential, metadata);
			jujuCloudService.saveCloud(jCloud);
			final boolean isSuccess = jujuCloudService.jujuInstantiate(jCloud.getId());
			return isSuccess ? "SUCCESS" : "FAIL";
		}
		return null;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		vim.cnf(vimConnectionInformation).deleteContainer(task.getVimResourceId());
		final List<JujuCloud> jClouds = jujuCloudService.findByMetadataName(vimConnectionInformation.getVimId() + "-" + vimConnectionInformation.getJujuInfo().getRegion().toLowerCase(), "PASS");
		if (!jClouds.isEmpty()) {
			final boolean isSuccess = jujuCloudService.jujuTerminate(jClouds.get(0).getId());
			return isSuccess ? "SUCCESS" : "FAIL";
		}
		return null;
	}

}