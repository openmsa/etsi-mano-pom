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
package com.ubiqube.etsi.mano.service.grant.ccm;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterMachine;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterOptionVersion;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.service.grant.ccm.cni.CniInstaller;
import com.ubiqube.etsi.mano.service.grant.ccm.csi.CsiInstaller;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vim.k8s.model.K8sParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsMachineParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsParams;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.api.model.storage.StorageClass;
import io.fabric8.kubernetes.api.model.storage.StorageClassBuilder;

@Service
public class CapiCcmServerService implements CcmServerService {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CapiCcmServerService.class);

	private final CapiServerService capiServerService;
	private final OsClusterService osClusterService;
	private final CapiServerMapping mapper;
	private final List<CniInstaller> cniInstallers;
	private final List<CsiInstaller> csiInstallers;
	private final List<CcmInstaller> ccmInstallers;

	public CapiCcmServerService(final CapiServerService capiServerService, final OsClusterService osClusterService, final CapiServerMapping mapper, final List<CniInstaller> cniInstallers, final List<CsiInstaller> csiInstallers, final List<CcmInstaller> ccmInstallers) {
		this.capiServerService = capiServerService;
		this.osClusterService = osClusterService;
		this.mapper = mapper;
		this.cniInstallers = cniInstallers;
		this.csiInstallers = csiInstallers;
		this.ccmInstallers = ccmInstallers;
	}

	@Override
	public void terminateCluster(final String vnfInstanceId) {
		// Nothing.
	}

	@Override
	public K8s createCluster(final VimConnectionInformation vimConn, final String vnfInstanceId) {
		final Iterable<CapiServer> ite = capiServerService.findAll();
		if (!ite.iterator().hasNext()) {
			throw new GenericException("Unable to find a CAPI connection.");
		}
		final CapiServer capiSrv = ite.iterator().next();
		LOG.info("Using capi cluster: {}", capiSrv.getName());
		final K8s k8s = mapper.map(capiSrv);
		return deployServer(capiSrv, k8s, "default", vimConn.getVimId(), vimConn);
	}

	private K8s deployServer(final CapiServer capiSrv, final K8s k8s, final String ns, final String clusterName, final VimConnectionInformation vci) {
		final CnfInformations cnfInfo = vci.getCnfInfo();
		final ClusterMachine master = cnfInfo.getMaster();
		final ClusterMachine worker = cnfInfo.getWorker();
		final K8sParams params = K8sParams.builder()
				.clusterName(clusterName)
				.clusterNetworkCidr(List.of("192.168.0.0/16"))
				.k8sVersion(cnfInfo.getK8sVersion())
				.openStackParams(OsParams.builder()
						.cidr("10.6.0.0/24")
						.controlPlane(OsMachineParams.builder()
								.flavor(master.getFlavor())
								.image(master.getImage())
								.replica(master.getMinNumberInstance())
								.build())
						.dns(List.of(cnfInfo.getDnsServer()))
						.domainZone("nova")
						.extNetId(cnfInfo.getExtNetworkId())
						.sshKey(cnfInfo.getKeyPair())
						.worker(OsMachineParams.builder()
								.flavor(worker.getFlavor())
								.image(worker.getImage())
								.replica(worker.getMinNumberInstance())
								.build())
						.build())
				.serviceDomain("cluster.local")
				.build();
		osClusterService.createCluster(vci, k8s, params);
		final Optional<K8s> opt = osClusterService.getKubeConfig(k8s, ns, clusterName);
		if (opt.isPresent()) {
			final K8s cluster = opt.get();
			deployCloudConfig(cluster, vci);
			createCni(cluster, cnfInfo);
			createCcm(cluster, cnfInfo);
			createCsi(cluster, cnfInfo);
			final StorageClass sc = createStorageClass("cinder.csi.openstack.org");
			osClusterService.applyStorageClass(cluster, sc);
			return cluster;
		}
		throw new GenericException("Unable to find cluster: " + ns + "/" + clusterName);
	}

	private void createCsi(final K8s cluster, final CnfInformations cnfInfo) {
		final ClusterOptionVersion csi = cnfInfo.getCsi();
		if ((null == csi) || (csi.getModule() == null)) {
			return;
		}
		final List<String> csiDocs = getCsiInstallDocuments(csi.getModule(), csi.getVersion());
		LOG.info("Deploying CSI: {} {} => {} Docs", csi.getModule(), csi.getVersion(), csiDocs.size());
		osClusterService.apply(cluster, csiDocs);
	}

	private void createCcm(final K8s cluster, final CnfInformations cnfInfo) {
		final ClusterOptionVersion ccm = cnfInfo.getCcm();
		if ((null == ccm) || (ccm.getModule() == null)) {
			return;
		}
		final List<String> ccmDocs = getCcmInstallDocuments(ccm.getModule(), ccm.getVersion());
		LOG.info("Deploying CCM: {} {} => {} docs.", ccm.getModule(), ccm.getVersion(), ccmDocs.size());
		osClusterService.apply(cluster, ccmDocs);
	}

	private void createCni(final K8s cluster, final CnfInformations cnfInfo) {
		final ClusterOptionVersion cni = cnfInfo.getCni();
		if ((null == cni) || (cni.getModule() == null)) {
			return;
		}
		final List<String> cniDocs = getCniInstallDocuments(cni.getModule(), cni.getVersion());
		LOG.info("Deploying CNI: {} {} => {} docs.", cni.getModule(), cni.getVersion(), cniDocs.size());
		osClusterService.apply(cluster, cniDocs);
	}

	private void deployCloudConfig(final K8s k8sCfg, final VimConnectionInformation vci) {
		LOG.info("Deploy cloud-config for CCM & CSI on target system.");
		final String str = toIni(vci);
		final String b64 = Base64.getEncoder().encodeToString(str.getBytes());
		final Secret secret = new SecretBuilder()
				.withNewMetadata()
				.withName("cloud-config")
				.withNamespace("kube-system")
				.endMetadata()
				.addToData("cloud.conf", b64)
				.build();
		osClusterService.applySecret(k8sCfg, secret);
	}

	private static StorageClass createStorageClass(final String provisioner) {
		return new StorageClassBuilder()
				.withNewMetadata()
				.withName("mano-provisioner")
				.addToAnnotations("storageclass.kubernetes.io/is-default-class", "true")
				.endMetadata()
				.withProvisioner(provisioner)
				.build();
	}

	/**
	 * https://github.com/kubernetes/cloud-provider-openstack/blob/master/docs/openstack-cloud-controller-manager/using-openstack-cloud-controller-manager.md#global
	 *
	 * @return A ini file.
	 */
	public static String toIni(final VimConnectionInformation vci) {
		final KeystoneAuthV3 ai = (KeystoneAuthV3) vci.getAccessInfo();
		final OpenstackV3InterfaceInfo ii = (OpenstackV3InterfaceInfo) vci.getInterfaceInfo();
		final StringBuilder sb = new StringBuilder();
		sb.append("[Global]\n")
				.append("username = ").append(ai.getUsername()).append("\n")
				.append("password = ").append(ai.getPassword()).append("\n")
				.append("auth-url = ").append(ii.getEndpoint()).append("\n");
		if (ai.getProjectId() != null) {
			sb.append("tenant-id = ").append(ai.getProjectId()).append("\n");
		}
		if (ai.getUserDomain() != null) {
			sb.append("domain-name = ").append(ai.getUserDomain()).append("\n");
		}
		if (ai.getProject() != null) {
			sb.append("tenant-name = ").append(ai.getProject()).append("\n");
		}
		if (ai.getRegion() != null) {
			sb.append("region = ").append(ai.getRegion()).append("\n");
		}
		if (ai.getProjectDomain() != null) {
			sb.append("tenant-domain-name = ").append(ai.getProjectDomain()).append("\n");
		}
		return sb.toString();
	}

	private List<String> getCsiInstallDocuments(final String stack, final String version) {
		return csiInstallers.stream()
				.filter(x -> stack.equals(x.getType()))
				.map(x -> x.getK8sDocuments(version))
				.flatMap(List::stream)
				.toList();
	}

	private List<String> getCniInstallDocuments(final String stack, final String version) {
		return cniInstallers.stream()
				.filter(x -> stack.equals(x.getType()))
				.map(x -> x.getK8sDocuments(version))
				.flatMap(List::stream)
				.toList();
	}

	private List<String> getCcmInstallDocuments(final String stack, final String version) {
		return ccmInstallers.stream()
				.filter(x -> stack.equals(x.getType()))
				.map(x -> x.getK8sDocuments(version))
				.flatMap(List::stream)
				.toList();
	}

}
