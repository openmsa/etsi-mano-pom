/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.alarm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.service.mon.cli.MonSearchRemoteService;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class MetricService {

	private final MonSearchRemoteService searchService;

	public MetricService(final MonSearchRemoteService searchService) {
		this.searchService = searchService;
	}

	public List<MonitoringDataSlim> findLatest(final Metrics x) {
		final ResponseEntity<List<MonitoringDataSlim>> res = searchService.findByObjectIdAndKey(x.getObjectId(), x.getKey());
		return res.getBody();
	}

}
