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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;

/**
 *
 * @author olivier
 *
 */
@Service
public class PollingService {
	private static final Logger LOG = LoggerFactory.getLogger(PollingService.class);

	private final PollingJobService pollingJobService;

	private final BusHandlerService busService;

	public PollingService(final PollingJobService pollingJobService, final BusHandlerService busService) {
		this.pollingJobService = pollingJobService;
		this.busService = busService;
	}

	@Scheduled(fixedRate = 10_000)
	public void run() {
		LOG.trace("Monitoring tick.");
		final List<BatchPollingJob> jobs = pollingJobService.findRunnableJobs(ZonedDateTime.now().plusSeconds(5));
		jobs.forEach(this::run);
	}

	private void run(final BatchPollingJob batchpollingjob) {
		LOG.trace("polling {}", batchpollingjob);
		busService.emit(batchpollingjob);
		batchpollingjob.setLastRun(ZonedDateTime.now());
		pollingJobService.save(batchpollingjob);
	}
}
