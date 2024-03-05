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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.AbstractVnfmContributor;

public class TestVnfmContributor extends AbstractVnfmContributor<Object> {

	private final List<SclableResources<Object>> list;

	protected TestVnfmContributor(final VnfLiveInstanceJpa vnfInstanceJpa, final List<SclableResources<Object>> list) {
		super(vnfInstanceJpa);
		this.list = list;
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		return list;
	}

}
