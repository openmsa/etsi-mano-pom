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
package com.ubiqube.etsi.mano.vnfm.controller.vnfind;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.service.mon.MonitoringManager;
import com.ubiqube.etsi.mano.vnfm.fc.vnfind.IndicatorsFrontController;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class Sol003IndicatorsFrontControllerImpl implements IndicatorsFrontController {
	private final MonitoringManager monitoringManager;

	public Sol003IndicatorsFrontControllerImpl(final MonitoringManager monitoringManager) {
		this.monitoringManager = monitoringManager;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Function<VnfIndicator, U> mapper, final Consumer<U> makeLink) {
		final List<VnfIndicator> res = monitoringManager.search(filter, nextpageOpaqueMarker);
		final List<U> ret = res.stream().map(mapper::apply).toList();
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<List<U>> findByVnfInstanceId(final String vnfInstanceId, final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Function<VnfIndicator, U> mapper, final Consumer<U> makeLink) {
		final ResponseEntity<List<VnfIndicator>> res = monitoringManager.findByVnfInstanceId(vnfInstanceId, filter, nextpageOpaqueMarker);
		final List<U> ret = res.getBody().stream().map(mapper::apply).toList();
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> findByVnfInstanceIdAndIndicatorId(final String vnfInstanceId, final String indicatorId, final Function<VnfIndicator, U> mapper, final Consumer<U> makeLink) {
		final List<VnfIndicator> res = monitoringManager.search(vnfInstanceId, indicatorId);
		final U ret = mapper.apply(res.get(0));
		return ResponseEntity.ok(ret);
	}

}
