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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.mon;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJobCriteria;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;

import jakarta.validation.constraints.NotNull;

@Service
public class MonitoringManager {

	private static final Logger LOG = LoggerFactory.getLogger(MonitoringManager.class);

	private final ExternalMonitoring em;

	private final VnfInstanceService vnfInstanceService;

	public MonitoringManager(final ExternalMonitoring em, final VnfInstanceService vnfInstanceService) {
		this.em = em;
		this.vnfInstanceService = vnfInstanceService;
	}

	/**
	 * Register a PMjob into the targeted monitoring system.
	 *
	 * @param pmJob
	 */
	public void create(@NotNull final PmJob pmJob) {
		pmJob.getObjectInstanceIds().forEach(x -> {
			final PmJobCriteria crit = pmJob.getCriteria();
			final List<VnfLiveInstance> lives = vnfInstanceService.findByVnfInstanceId(getSafeUUID(x));
			lives.forEach(z -> {
				if (pmJob.getSubObjectInstanceIds().contains(z.getTask().getToscaName())) {
					LOG.debug("Registrating: {}", z.getResourceId());
					em.createBatch(z.getResourceId(), crit.getPerformanceMetric(), crit.getCollectionPeriod());
				}
			});
		});
	}

	public void delete(final PmJob pmJob) {
		pmJob.getObjectInstanceIds().forEach(x -> {
			final List<VnfLiveInstance> lives = vnfInstanceService.findByVnfInstanceId(getSafeUUID(x));
			lives.forEach(z -> deleteResource(z.getResourceId()));
		});
	}

	public void deleteResource(final String id) {
		em.deleteResources(id);
	}

	public void addResource(final PmJob pmJob, final String resourceId) {
		final PmJobCriteria crit = pmJob.getCriteria();
		em.createBatch(resourceId, crit.getPerformanceMetric(), crit.getCollectionPeriod());
	}
}
