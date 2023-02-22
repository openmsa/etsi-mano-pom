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
