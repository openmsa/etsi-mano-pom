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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.VduProfile;
import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vim.k8s.model.K8sParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsMachineParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsParams;
import com.ubiqube.etsi.mano.vnfm.jpa.CapiServerJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.AbstractVnfmUow;

public class OsCapiClusterUow extends AbstractVnfmUow<OsContainerDeployableTask> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(OsCapiClusterUow.class);

	private final OsClusterService osCluster;
	private final OsContainerDeployableTask task;
	private final VimConnectionInformation vci;
	private final CapiServerJpa capiServerJpa;
	private final CapiServerMapping mapper = Mappers.getMapper(CapiServerMapping.class);

	public OsCapiClusterUow(final VirtualTaskV3<OsContainerDeployableTask> task, final OsClusterService osCluster, final VimConnectionInformation vci, final CapiServerJpa capiServerJpa) {
		super(task, OsContainerDeployableNode.class);
		this.task = task.getTemplateParameters();
		this.osCluster = osCluster;
		this.vci = vci;
		this.capiServerJpa = capiServerJpa;
	}

	@Override
	public String execute(final Context3d context) {
		final String network = Optional.ofNullable(task.getNetwork()).map(x -> context.get(Network.class, x)).orElse(null);
		final CnfInformations cnfInfo = vci.getCnfInfo();
		final String clusterName = buildClusterName(task.getToscaName(), task.getVnfInstId());
		final VduProfile profile = task.getOsContainerDeployableUnit().getVduProfile();
		final K8sParams k8sParams = K8sParams.builder()
				.clusterName(clusterName)
				.clusterNetworkCidr(List.of("192.168.0.0/16"))
				.k8sVersion(cnfInfo.getK8sVersion())
				.openStackParams(OsParams.builder()
						.cidr("10.6.0.0/24")
						.controlPlane(OsMachineParams.builder()
								.flavor(cnfInfo.getMasterFlavorId())
								.image(cnfInfo.getImages())
								.replica(1)
								.build())
						.dns(List.of(cnfInfo.getDnsServer()))
						.domainZone("nova")
						.extNetId(network)
						.sshKey(cnfInfo.getKeyPair())
						.worker(OsMachineParams.builder()
								.flavor(cnfInfo.getWorkerFlavorId())
								.image(cnfInfo.getImages())
								.replica(profile.getMinNumberOfInstances())
								.build())
						.build())
				.serviceDomain("cluster.local")
				.build();
		final CapiServer capiSrv = capiServerJpa.findAll().iterator().next();
		final K8s k8sConfig = mapper.map(capiSrv);
		try {
			osCluster.createCluster(vci, k8sConfig, k8sParams);
		} catch (final Throwable e) {
			LOG.error("", e);
			throw new GenericException(e);
		}
		return clusterName;
	}

	private static String buildClusterName(final String toscaName, final String vnfInstId) {
		final String newName = toscaName.toLowerCase().replace('_', '-');
		return "%s-%s".formatted(newName, vnfInstId);
	}

	@Override
	public String rollback(final Context3d context) {
		final String clusterName = buildClusterName(task.getToscaName(), task.getVnfInstId());
		final CapiServer capiSrv = capiServerJpa.findAll().iterator().next();
		final K8s k8sConfig = mapper.map(capiSrv);
		osCluster.deleteCluster(k8sConfig, "default", clusterName);
		return null;
	}

}
