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
package com.ubiqube.etsi.mano.alarm.service.aggregate;

import java.time.OffsetDateTime;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Aggregates;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;
import com.ubiqube.etsi.mano.alarm.service.MetricKey;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class Mean implements AggregateFunction {

	@Override
	public String getName() {
		return "mean";
	}

	@Override
	public void apply(final AlarmContext ctx, final Aggregates aggregate) {
		final OptionalDouble res = aggregate.getParameters().stream()
				.map(ctx::get)
				.map(MetricChange::latest)
				.mapToDouble(MonitoringDataSlim::getValue)
				.average();
		if (res.isPresent()) {
			final MonitoringDataSlim latest = new TelemetryMetricsResult("", "", "", res.getAsDouble(), "", OffsetDateTime.now(), true);
			final MetricChange mc = new MetricChange(latest, null);
			ctx.put(MetricKey.of(latest, aggregate.getName()), mc);
		}
	}

}
