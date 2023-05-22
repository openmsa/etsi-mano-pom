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
