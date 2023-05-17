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