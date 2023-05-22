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

import com.ubiqube.etsi.mano.alarm.entities.alarm.Metrics;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

/**
 *
 * @author Olivier Vignaud
 *
 */
public record MetricKey(String objectId, String key, String alarmKey) {

	public static MetricKey of(final Metrics m) {
		return new MetricKey(m.getObjectId(), m.getKey(), m.getLabel());
	}
	//

	public static MetricKey of(final MonitoringDataSlim latest, final String alarmKey) {
		return new MetricKey(latest.getMasterJobId(), latest.getKey(), alarmKey);
	}
}