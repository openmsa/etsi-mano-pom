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
package com.ubiqube.etsi.mano.service.mapping;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

@Service
public class VnfGrantMapper implements GrantMapper {
	private final VnfGrantMapping vnfGrantMapping;

	public VnfGrantMapper(final VnfGrantMapping vnfGrantMapping) {
		this.vnfGrantMapping = vnfGrantMapping;
	}

	@Override
	public GrantResponse mapToGrantResponse(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		return vnfGrantMapping.mapToGrantResponse((VnfBlueprint) plan);
	}

}
