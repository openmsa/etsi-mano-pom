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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VnfmRegisterService implements CommandLineRunner {

	private final EventManager eventManager;
	private final ServersJpa serversJpa;

	public VnfmRegisterService(final EventManager eventManager, final ServersJpa serversJpa) {
		this.eventManager = eventManager;
		this.serversJpa = serversJpa;
	}

	@Override
	public void run(final @Nullable String... args) throws Exception {
		final List<Servers> servers = serversJpa.findByServerStatusIn(Arrays.asList(PlanStatusType.FAILED));
		servers.forEach(x -> eventManager.sendAction(ActionType.REGISTER_SERVER, x.getId()));
	}

}
