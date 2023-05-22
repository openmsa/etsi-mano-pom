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
package com.ubiqube.etsi.mano.alarm.service.transform;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Transform;
import com.ubiqube.etsi.mano.alarm.service.AlarmContext;
import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

/**
 *
 * @author Olivier Vignaud
 *
 */
@Service
public class Cpuz implements TransformFunction {

	@Override
	public String getName() {
		return "cpuz";
	}

	@Override
	public void apply(final AlarmContext ctx, final Transform transform) {
		final MetricChange val = ctx.get(transform.getValue());
		final MonitoringDataSlim latest = val.latest();
		final MonitoringDataSlim old = val.old();
		final Double oldValue = Objects.requireNonNull(old.getValue());
		final Double newValue = Objects.requireNonNull(latest.getValue());
		final double deltaCpu = newValue - oldValue;
		final int cpuNum = getCpuParam(transform);
		final long deltaSecond = Duration.between(old.getTime(), latest.getTime()).getSeconds();
		final double usageSecond = deltaCpu / 1_000_000_000L;
		final double percent = (usageSecond / (cpuNum * deltaSecond)) * 100;
		setCtx(ctx, transform, percent);
	}

	private static void setCtx(final AlarmContext ctx, final Transform transform, final double percent) {
		final String target = Optional.ofNullable(transform.getTarget()).orElseGet(transform::getValue);
		ctx.replace(target, percent);
	}

	private static int getCpuParam(final Transform transform) {
		return Optional.ofNullable(transform.getParameters().get("cpu"))
				.map(Integer::parseInt)
				.orElse(1);
	}
}
