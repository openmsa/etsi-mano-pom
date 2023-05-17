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
		final String target = Optional.ofNullable(transform.getTarget()).orElseGet(transform::getTarget);
		ctx.replace(target, percent);
	}

	private static int getCpuParam(final Transform transform) {
		return transform.getParameters().stream()
				.filter(x -> x.getKey().equals("cpu"))
				.map(x -> x.getValue())
				.mapToInt(Integer::parseInt)
				.findFirst().orElse(1);
	}
}
