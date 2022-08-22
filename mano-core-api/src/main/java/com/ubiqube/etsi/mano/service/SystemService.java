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
package com.ubiqube.etsi.mano.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
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
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class SystemService {
	private final MapperFacade mapper;
	private final SystemsJpa systemJpa;

	public SystemService(final MapperFacade mapper, final SystemsJpa systemJpa) {
		this.mapper = mapper;
		this.systemJpa = systemJpa;
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
				HelmNode.class,
				VnffgLoadbalancerNode.class,
				VnffgPostNode.class,
				VnffgPostNode.class,
				PortPairNode.class,
				SubNetwork.class
		};
		for (final Class<?> string : sysDtr) {
			sys.add(createSystem(string.getSimpleName(), vimConnectionInformation));
		}
		return systemJpa.save(sys);
	}

	private SystemConnections createSystem(final String moduleName, final VimConnectionInformation vimConnectionInformation) {
		final SystemConnections sc = mapper.map(vimConnectionInformation, SystemConnections.class);
		sc.setVimType(vimConnectionInformation.getVimType());
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
}