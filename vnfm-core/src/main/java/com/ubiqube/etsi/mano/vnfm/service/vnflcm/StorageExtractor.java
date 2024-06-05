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

import com.ubiqube.etsi.mano.dao.mano.VimResource;
import com.ubiqube.etsi.mano.dao.mano.VirtualStorageResourceInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;

@Service
public class StorageExtractor implements VnfLcmExtractor {

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vli) {
		final List<VnfLiveInstance> storageVli = vli.stream().filter(x -> x.getTask() instanceof StorageTask).toList();
		final Set<VirtualStorageResourceInfo> storages = storageVli.stream()
				.map(StorageExtractor::createVirtualStorageResourceInfo)
				.collect(Collectors.toSet());
		params.setVirtualStorageResourceInfo(storages);
	}

	private static VirtualStorageResourceInfo createVirtualStorageResourceInfo(final VnfLiveInstance vli) {
		final VirtualStorageResourceInfo ret = new VirtualStorageResourceInfo();
		ret.setReservationId(null);
		final VimResource vimResource = createResource(vli);
		ret.setId(vli.getId());
		ret.setStorageResource(vimResource);
		ret.setVirtualStorageDescId(vli.getTask().getToscaName());
		ret.setVnfdId(vli.getVnfInstance().getVnfdId());
		ret.setZoneId(null);
		return ret;
	}

	private static VimResource createResource(final VnfLiveInstance x) {
		final VimResource vimResource = new VimResource();
		vimResource.setResourceId(x.getResourceId());
		vimResource.setVimConnectionId(x.getVimConnectionId());
		vimResource.setResourceProviderId(x.getTask().getResourceProviderId());
		return vimResource;
	}
}
