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
package com.ubiqube.etsi.mano.vnfm.service.metrics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id = "mano-vnfm")
@Component
public class TestMetric {
	private final MetricsService metricsService;

	public TestMetric(final MetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@ReadOperation
	public Map<String, Object> test() {
		final Map<String, Object> ret = new HashMap<>();
		ret.put("key1", metricsService.getVnfmOnboardingCounter().count());
		metricsService.getVnfmOnboardingCounter().increment();
		return ret;
	}
}
