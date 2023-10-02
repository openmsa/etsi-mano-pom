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
package com.ubiqube.etsi.mano.service.sol009;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntity;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntityComponent;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoService;
import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.git.GitPropertyManager;
import com.ubiqube.etsi.mano.service.git.GitVersion;

@Service
public class PeerEntityService {

	private final Optional<NfvoService> nfvoService;
	private final GitPropertyManager gitPropertyManager;
	private final SpecificServerInfo specificServerInfo;
	private final String name;

	public PeerEntityService(final Optional<NfvoService> nfvoService, @Value("${mano.config.type}") final String name, final GitPropertyManager gitPropertyManager,
			final SpecificServerInfo specificServerInfo) {
		this.nfvoService = nfvoService;
		this.name = name;
		this.gitPropertyManager = gitPropertyManager;
		this.specificServerInfo = specificServerInfo;
	}

	public ManoEntity getMe() {
		final ManoEntity me = new ManoEntity();
		me.setDescription(buildSexcription());
		me.setManoConfigurableParams(null);
		final ManoEntityComponent comp = new ManoEntityComponent();
		comp.setId(null);
		comp.setManoServiceIds(List.of());
		final List<ManoEntityComponent> comps = List.of(comp);
		me.setManoEntityComponents(comps);

		final ManoService srv = new ManoService();
		srv.setDescription("");
		srv.setId(null);
		srv.setManoServiceInterfaceIds(Set.of(""));
		srv.setName("");
		final List<ManoService> srvs = List.of(srv);
		me.setManoServices(srvs);
		me.setName(name);
		me.setProvider("ubiqube");
		me.setSoftwareInfo(Map.of());
		final GitVersion gitVer = gitPropertyManager.getLocalPRoperty();
		me.setSoftwareVersion(gitVer.version());
		specificServerInfo.setSpecificInformations(me);
		// me.setVimSpecificInfo()
		return me;
	}

	private String buildSexcription() {
		if (nfvoService.isPresent()) {
			return "nfvo ubiqube";
		}
		return "vnfm ubiqube";
	}

}
