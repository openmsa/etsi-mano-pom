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
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;

@Service
public class ExtVirtualLinkInfoExtractor implements VnfLcmExtractor {

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream().filter(x -> x.getTask() instanceof VnfPortTask).toList();
		final Set<ExtVirtualLinkDataEntity> obj = vli.stream().map(x -> {
			final VnfPortTask vpt = (VnfPortTask) x.getTask();
			return createExtVl(x, vpt);
		}).collect(Collectors.toSet());
		params.setExtVirtualLinkInfo(obj);
	}

	private static ExtVirtualLinkDataEntity createExtVl(final VnfLiveInstance vli, final VnfPortTask vpt) {
		final ExtVirtualLinkDataEntity elie = new ExtVirtualLinkDataEntity();
		elie.setId(vli.getId());
		elie.setResourceId(vli.getResourceId());
		elie.setResourceProviderId(vpt.getResourceProviderId());
		elie.setVimConnectionId(vli.getVimConnectionId());
		elie.setVnfInstance(vli.getVnfInstance());
		return elie;
	}

}
