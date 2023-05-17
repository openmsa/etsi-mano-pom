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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.ubiqube.etsi.mano.alarm.AlarmException;
import com.ubiqube.etsi.mano.service.cond.BasicContext;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.mon.jms.MetricChange;

/**
 *
 * @author Olivier Vignaud
 *
 */
public class AlarmContext {
	private final Map<MetricKey, MetricChange> map;

	public AlarmContext() {
		map = new HashMap<>();
	}

	public void put(final MetricKey of, final MetricChange mc) {
		map.put(of, mc);
	}

	public Context getEvaluationContext() {
		final Map<String, Object> m = map.entrySet().stream().collect(Collectors.toMap(x -> x.getKey().alarmKey(), x -> x.getValue().latest().getValue()));
		return new BasicContext(m);
	}

	public MetricChange get(final String value) {
		return findUniqMetric(value).getValue();
	}

	public void replace(final String value, final double percent) {
		final Entry<MetricKey, MetricChange> res = findUniqMetric(value);
		res.getValue().latest().setValue(percent);
		map.put(res.getKey(), res.getValue());
	}

	private Entry<MetricKey, MetricChange> findUniqMetric(final String value) {
		final List<Entry<MetricKey, MetricChange>> res = findMetric(value);
		if (res.size() == 1) {
			return res.get(0);
		}
		throw new AlarmException("Multiple element found: " + res.size());
	}

	private List<Entry<MetricKey, MetricChange>> findMetric(final String value) {
		return map.entrySet().stream()
				.filter(x -> x.getKey().alarmKey().equals(value))
				.toList();
	}

}
