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
package com.ubiqube.etsi.mano.vnfm.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.vnfm.controller.vnfpm.VnfmThresholdController;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;
import com.ubiqube.etsi.mano.vnfm.service.alarm.AlarmSystem;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmThresholdControllerDirectVim implements VnfmThresholdController {

	private final ThresholdJpa thresholdJpa;

	private final VnfBlueprintJpa vnfBlueprintJpa;

	private final SearchableService searchableService;

	private final AlarmSystem alarmSystem;

	public VnfmThresholdControllerDirectVim(final ThresholdJpa thresholdJpa, final VnfBlueprintJpa vnfBlueprintJpa, final SearchableService searchableService, final AlarmSystem alarmSystem) {
		this.thresholdJpa = thresholdJpa;
		this.vnfBlueprintJpa = vnfBlueprintJpa;
		this.searchableService = searchableService;
		this.alarmSystem = alarmSystem;
	}

	@Override
	public Threshold save(final Threshold res) {
		final Optional<VnfBlueprint> obp = vnfBlueprintJpa.findById(res.getObjectInstanceId());
		final VnfBlueprint bp = obp.orElseThrow(() -> new NotFoundException("Could not find VNF instance :" + res.getObjectInstanceId()));
		final UUID systemId = bp.getVimConnections().iterator().next().getId();
		alarmSystem.registerAlarm(res, systemId);
		return thresholdJpa.save(res);
	}

	@Override
	public void delete(final UUID id) {
		final Threshold obj = findById(id);
		alarmSystem.delete(obj);
		thresholdJpa.deleteById(id);
	}

	@Override
	public Threshold findById(final UUID id) {
		return thresholdJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find Threshold: " + id));
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<Threshold, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<?> frontClass) {
		return searchableService.search(Threshold.class, requestParams, mapper, excludeDefaults, mandatoryFields, makeLink, List.of(), frontClass);
	}

}
