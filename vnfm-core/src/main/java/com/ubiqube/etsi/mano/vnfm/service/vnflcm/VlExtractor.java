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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.service.vnflcm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimResource;
import com.ubiqube.etsi.mano.dao.mano.VirtualLinkInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;

@Service
public class VlExtractor implements VnfLcmExtractor {

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vliAll) {
		final List<VnfLiveInstance> vli = vliAll.stream()
				.filter(x -> x.getTask() instanceof NetworkTask).toList();
		final Set<VirtualLinkInfo> obj = vli.stream()
				.map(x -> {
					final NetworkTask nt = (NetworkTask) x.getTask();
					return createVirtualLinkInfo(x, nt);
				}).collect(Collectors.toSet());
		params.setVirtualLinkResourceInfo(obj);
	}

	private static VirtualLinkInfo createVirtualLinkInfo(final VnfLiveInstance vli, final NetworkTask nt) {
		final VirtualLinkInfo ret = new VirtualLinkInfo();
		ret.setId(vli.getId());
		ret.setVnfVirtualLinkDescId(nt.getToscaName());
		final VimResource vimResource = createResource(vli, nt);
		ret.setNetworkResource(vimResource);
		ret.setVnfLinkPorts(null);
		return ret;
	}

	private static VimResource createResource(final VnfLiveInstance vli, final NetworkTask nt) {
		final VimResource vimResource = new VimResource();
		vimResource.setResourceId(vli.getResourceId());
		vimResource.setResourceProviderId(nt.getResourceProviderId());
		vimResource.setVimConnectionId(vli.getVimConnectionId());
		vimResource.setVimLevelResourceType(null);
		return vimResource;
	}

}
