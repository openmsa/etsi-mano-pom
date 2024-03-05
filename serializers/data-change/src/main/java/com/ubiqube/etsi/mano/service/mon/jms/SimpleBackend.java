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
package com.ubiqube.etsi.mano.service.mon.jms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.mon.data.MonitoringDataSlim;

@Service
public class SimpleBackend implements DataBackend {

	private final Map<Key, MonitoringDataSlim> cache;

	public SimpleBackend() {
		cache = new ConcurrentHashMap<>();
	}

	@Override
	public MonitoringDataSlim getLastMetrics(final String key, final String masterJobId) {
		final Key k = new Key(key, masterJobId);
		return cache.get(k);
	}

	@Override
	public void updateMetric(final MonitoringDataSlim slim) {
		final Key k = new Key(slim.getKey(), slim.getMasterJobId());
		cache.put(k, slim);
	}

}

record Key(String master, String subObject) {
	//
}