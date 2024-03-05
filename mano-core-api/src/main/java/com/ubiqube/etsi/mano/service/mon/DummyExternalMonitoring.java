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
package com.ubiqube.etsi.mano.service.mon;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.cli.MetricsRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonPollingRemoteService;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.dto.ConnInfo;
import com.ubiqube.etsi.mano.service.mon.dto.PollingJob;

/**
 *
 * @author olivier
 *
 */
@Service
public class DummyExternalMonitoring implements ExternalMonitoring {

	private static final Logger LOG = LoggerFactory.getLogger(DummyExternalMonitoring.class);
	private final MonPollingRemoteService remoteService;
	private final MetricsRemoteService metricsRemoteService;

	public DummyExternalMonitoring(final MonPollingRemoteService remoteService, final MetricsRemoteService metricsRemoteService) {
		this.remoteService = remoteService;
		this.metricsRemoteService = metricsRemoteService;
	}

	@Override
	public UUID createBatch(final String resourceId, final Set<String> metrics, final Long pollingInterval, final VimConnectionInformation vimConnectionInformation) {
		LOG.info("registering {}", resourceId);
		final PollingJob pj = new PollingJob();
		pj.setInterval(pollingInterval);
		pj.setConnection(convert(vimConnectionInformation));
		final List<Metric> lst = metrics.stream().map(x -> new Metric(x, x)).toList();
		pj.setMetrics(lst);
		pj.setResourceId(resourceId);
		final ResponseEntity<BatchPollingJob> ret = remoteService.register(pj);
		return Objects.requireNonNull(ret.getBody()).getId();
	}

	private static ConnInfo convert(final VimConnectionInformation vimConnectionInformation) {
		final ConnInfo ci = new ConnInfo();
		// Not sure every VIMId are UUID.
		ci.setConnId(UUID.fromString(vimConnectionInformation.getVimId()));
		ci.setAccessInfo(vimConnectionInformation.getAccessInfo());
		ci.setExtra(vimConnectionInformation.getExtra());
		ci.setIgnoreSsl(true);
		ci.setInterfaceInfo(vimConnectionInformation.getInterfaceInfo());
		ci.setName(vimConnectionInformation.getVimId());
		ci.setType(vimConnectionInformation.getVimType());
		return ci;
	}

	@Override
	public void deleteResources(final String resourceId) {
		LOG.info("Deleting {}", resourceId);
		remoteService.delete(getSafeUUID(resourceId));
	}

	@Override
	public List<TelemetryMetricsResult> searchMetric(final MultiValueMap<String, String> params) {
		return Optional.ofNullable(metricsRemoteService.searchApi(params))
				.map(HttpEntity::getBody)
				.orElseGet(List::of);
	}

}
