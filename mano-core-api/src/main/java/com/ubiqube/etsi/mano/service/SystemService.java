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
		final String[] sysDtr = { "COMPUTE", "NETWORK",
				"DNS",
				"MONITORING",
				"VNFEXTCP",
				"PORT",
				"STORAGE",
				"AFFINITY",
				"SECURITY-GROUP",
				"NSD",
				"SAP",
				"NSNETWORK",
				"VNF",
				"VNF-CREATE",
				"CNF",
				"HELM",
				"VNFFG-PORT-PAIR",
				"VNFFG-LOADBALANCER" };
		for (final String string : sysDtr) {
			sys.add(createSystem(string, vimConnectionInformation));
		}
		return systemJpa.save(sys);
	}

	private SystemConnections createSystem(final String string, final VimConnectionInformation vimConnectionInformation) {
		final SystemConnections sc = mapper.map(vimConnectionInformation, SystemConnections.class);
		sc.setVimType(string);
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