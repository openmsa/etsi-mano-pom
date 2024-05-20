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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.SysConnectionJpa;
import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceInstanceNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceTemplateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NetworkPolicyNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.SapNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPostNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.service.mapping.SystemConnectionsMapping;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class SystemService {
	private final SystemsJpa systemJpa;
	private final SysConnectionJpa systemConnectionsJpa;
	private final Patcher patcher;
	private final SystemConnectionsMapping systemConnectionsMapping;

	public SystemService(final SystemsJpa systemJpa, final Patcher patcher, final SysConnectionJpa systemConnectionsJpa, final SystemConnectionsMapping systemConnectionsMapping) {
		this.systemJpa = systemJpa;
		this.patcher = patcher;
		this.systemConnectionsJpa = systemConnectionsJpa;
		this.systemConnectionsMapping = systemConnectionsMapping;
	}

	/**
	 * A VIM is a monolithic stack of sub-systems.
	 *
	 * @param vimConnectionInformation
	 * @return The registered system.
	 */
	public Systems registerVim(final VimConnectionInformation vimConnectionInformation) {
		if ("OPENSTACK_V3".equals(vimConnectionInformation.getVimType())) {
			return registerOpenStask(vimConnectionInformation);
		}
		throw new GenericException("Unable to find vim of type: " + vimConnectionInformation.getVimType());
	}

	private Systems registerOpenStask(final VimConnectionInformation vimConnectionInformation) {
		final Systems sys = new Systems();
		sys.setVimOrigin(vimConnectionInformation.getId());
		sys.setVimId(vimConnectionInformation.getVimId());
		final Class<?>[] sysDtr = { Compute.class,
				Network.class,
				VnfIndicator.class,
				DnsZone.class,
				DnsHost.class,
				Monitoring.class,
				VnfExtCp.class,
				VnfPortNode.class,
				Storage.class,
				AffinityRuleNode.class,
				SecurityGroupNode.class,
				NsdCreateNode.class,
				SapNode.class,
				VnfCreateNode.class,
				VnfInstantiateNode.class,
				VnfExtractorNode.class,
				OsContainerDeployableNode.class,
				OsContainerNode.class,
				OsK8sInformationsNode.class,
				MciopUser.class,
				HelmNode.class,
				VnffgLoadbalancerNode.class,
				VnffgPostNode.class,
				SubNetwork.class,
		};
		for (final Class<?> string : sysDtr) {
			sys.add(createSystem(string.getSimpleName(), vimConnectionInformation, "OPENSTACK_V3"));
		}
		final Class<?>[] sysContrail = {
				PtLinkNode.class,
				ServiceInstanceNode.class,
				ServiceTemplateNode.class,
				PortTupleNode.class,
				PortPairNode.class,
				NetworkPolicyNode.class,
		};
		if (vimConnectionInformation.getInterfaceInfo().getSdnEndpoint() != null) {
			for (final Class<?> string : sysContrail) {
				sys.add(createSystem(string.getSimpleName(), vimConnectionInformation, "CONTRAIL"));
			}
		}
		return systemJpa.save(sys);
	}

	private SystemConnections createSystem(final String moduleName, final VimConnectionInformation vimConnectionInformation, final String systemName) {
		final SystemConnections sc = systemConnectionsMapping.map(vimConnectionInformation);
		sc.setVimType(systemName);
		sc.setModuleName(moduleName);
		sc.setId(null);
		return sc;
	}

	public Iterable<Systems> findAll() {
		return systemJpa.findAll();
	}

	public void deleteByVimOrigin(final UUID id) {
		systemJpa.deleteByVimOrigin(id);
	}

	public List<Systems> findByModuleName(final UUID id, final String moduleName) {
		return systemJpa.findByIdAndSubSystemsModuleName(id, moduleName);
	}

	public ResponseEntity<SystemConnections> patchModule(final UUID id, final String body) {
		final SystemConnections sc = systemConnectionsJpa.findById(id).orElseThrow();
		patcher.patch(body, sc);
		final SystemConnections res = systemConnectionsJpa.save(sc);
		return ResponseEntity.ok(res);
	}
}