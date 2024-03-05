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

import com.ubiqube.etsi.mano.mon.api.MonApi;
import com.ubiqube.etsi.mano.mon.core.mapper.ConnectionInfoMapper;
import com.ubiqube.etsi.mano.mon.core.mapper.PollingJobMapper;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.MonConnInformation;
import com.ubiqube.etsi.mano.service.mon.dto.PollingJob;

import jakarta.annotation.Nonnull;

@Service
public class MonApiImpl implements MonApi {
	private final PollingJobService pollingJobRepository;

	private final ConnectionInformationService connRepository;

	public MonApiImpl(final PollingJobService pollingJobRepository, final ConnectionInformationService connRepository) {
		this.pollingJobRepository = pollingJobRepository;
		this.connRepository = connRepository;
	}

	@Override
	public BatchPollingJob register(final @Nonnull PollingJob pj) {
		final BatchPollingJob polling = PollingJobMapper.INSTANCE.fromDto(pj);
		final Optional<MonConnInformation> conn = connRepository.findByConnId(pj.getConnection().getConnId());
		if (conn.isEmpty()) {
			final MonConnInformation connInfo = ConnectionInfoMapper.INSTANCE.fromDto(pj.getConnection());
			final MonConnInformation newConn = connRepository.save(connInfo);
			polling.setConnection(newConn);
		} else {
			polling.setConnection(conn.get());
		}

		return pollingJobRepository.save(polling);
	}

	@Override
	public void delete(final @Nonnull UUID id) {
		pollingJobRepository.deleteById(id);
	}

	@Override
	public List<BatchPollingJob> list() {
		final List<BatchPollingJob> ret = new ArrayList<>();
		final Iterable<BatchPollingJob> ite = pollingJobRepository.findAll();
		ite.forEach(ret::add);
		return ret;
	}

	@Override
	public List<MonConnInformation> listConnections() {
		return connRepository.findAll();
	}

	@Override
	public void deleteConnection(@Nonnull final UUID id) {
		connRepository.deleteById(id);
	}
}
