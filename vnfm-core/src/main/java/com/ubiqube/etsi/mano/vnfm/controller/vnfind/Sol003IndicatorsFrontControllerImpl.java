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

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.em.v431.model.vnfind.VnfIndicator;
import com.ubiqube.etsi.mano.service.mon.MonitoringManager;
import com.ubiqube.etsi.mano.vnfm.fc.vnfind.IndicatorsFrontController;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class Sol003IndicatorsFrontControllerImpl implements IndicatorsFrontController {
	private final MonitoringManager monitoringManager;
	private final MapperFacade mapper;

	public Sol003IndicatorsFrontControllerImpl(final MonitoringManager monitoringManager, final MapperFacade mapper) {
		this.monitoringManager = monitoringManager;
		this.mapper = mapper;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		final List<VnfIndicator> res = monitoringManager.search(filter, nextpageOpaqueMarker);
		final List<U> ret = mapper.mapAsList(res, clazz);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<List<U>> findByVnfInstanceId(final String vnfInstanceId, final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		final ResponseEntity<List<VnfIndicator>> res = monitoringManager.findByVnfInstanceId(vnfInstanceId, filter, nextpageOpaqueMarker);
		final List<U> ret = mapper.mapAsList(res.getBody(), clazz);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> findByVnfInstanceIdAndIndicatorId(final String vnfInstanceId, final String indicatorId, final Class<U> clazz, final Consumer<U> makeLink) {
		final List<VnfIndicator> res = monitoringManager.search(vnfInstanceId, indicatorId);
		final U ret = mapper.map(res.get(0), clazz);
		return ResponseEntity.ok(ret);
	}

}
