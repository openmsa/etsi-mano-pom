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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.mon.core.repository.PollingJobRepository;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;

/**
 *
 * @author olivier
 *
 */
@Service
public class PollingJobService {
	private final PollingJobRepository pollingJobRepository;

	public PollingJobService(final PollingJobRepository pollingJobRepository) {
		this.pollingJobRepository = pollingJobRepository;
	}

	public BatchPollingJob save(final BatchPollingJob polling) {
		return pollingJobRepository.save(polling);
	}

	public void deleteById(final UUID id) {
		pollingJobRepository.deleteById(id);
	}

	public Iterable<BatchPollingJob> findAll() {
		return pollingJobRepository.findAll();
	}

	public List<BatchPollingJob> findRunnableJobs(final ZonedDateTime now) {
		return pollingJobRepository.findRunnableJobs(now);
	}
}
