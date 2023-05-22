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
