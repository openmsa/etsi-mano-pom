/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.controller.nslcm;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.lcmgrant.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.service.VnfLcmService;

@Service
public class VnfLcmControllerImpl implements VnfLcmController {
	private final VnfLcmService vnfLcmOpOccsRepository;
	private final VnfInstanceLcm vnfInstanceLcm;

	public VnfLcmControllerImpl(final VnfLcmService _vnfLcmOpOccsRepository, final VnfInstanceLcm _vnfInstanceLcm) {
		vnfLcmOpOccsRepository = _vnfLcmOpOccsRepository;
		vnfInstanceLcm = _vnfInstanceLcm;
	}

	@Override
	public List<VnfBlueprint> vnfLcmOpOccsGet(final String filter) {
		return vnfLcmOpOccsRepository.query(filter);
	}

	@Override
	public VnfBlueprint vnfLcmOpOccsVnfLcmOpOccIdGet(final UUID id) {
		return vnfLcmOpOccsRepository.findById(id);
	}
}
