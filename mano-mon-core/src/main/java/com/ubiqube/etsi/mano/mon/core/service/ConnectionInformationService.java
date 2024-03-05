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
package com.ubiqube.etsi.mano.mon.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.core.repository.ConnectionInformationRepository;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;

@Service
public class ConnectionInformationService {
	private final ConnectionInformationRepository connRepository;

	public ConnectionInformationService(final ConnectionInformationRepository connRepository) {
		this.connRepository = connRepository;
	}

	public Optional<MonConnInformation> findByConnId(final UUID connId) {
		return connRepository.findByConnId(connId);
	}

	public MonConnInformation save(final MonConnInformation connInfo) {
		return connRepository.save(connInfo);
	}

	public List<MonConnInformation> findAll() {
		final List<MonConnInformation> ret = new ArrayList<>();
		final Iterable<MonConnInformation> ite = connRepository.findAll();
		ite.forEach(ret::add);
		return ret;
	}

	public void deleteById(final UUID id) {
		connRepository.deleteById(id);
	}
}
