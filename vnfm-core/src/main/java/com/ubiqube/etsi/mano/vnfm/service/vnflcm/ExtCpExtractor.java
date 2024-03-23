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

import com.ubiqube.etsi.mano.dao.mano.ExtCpInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;

import jakarta.annotation.Nullable;

@Service
public class ExtCpExtractor implements VnfLcmExtractor {
	private static final String VIRTUAL_LINK = "virtual_link";
	private final VnfPackageJpa vnfPackageJpa;

	public ExtCpExtractor(final VnfPackageJpa vnfPackageJpa) {
		this.vnfPackageJpa = vnfPackageJpa;
	}

	@Override
	public void extract(final VnfInstance inst, final BlueprintParameters params, final List<VnfLiveInstance> vli) {
		final List<VnfLiveInstance> portVli = vli.stream()
				.filter(x -> x.getTask() instanceof VnfPortTask)
				.toList();
		final Set<ExtCpInfo> extCp = portVli.stream()
				.filter(x -> isPointingOut(x, inst.getVnfPkg().getVirtualLinks()))
				.map(x -> createExtCpInfo(inst, portVli, x))
				.collect(Collectors.toSet());
		params.setExtCpInfo(extCp);
	}

	private ExtCpInfo createExtCpInfo(final VnfInstance inst, final List<VnfLiveInstance> portVli, final VnfLiveInstance vli) {
		final VnfPortTask vpt = (VnfPortTask) vli.getTask();
		final VnfLinkPort vlp = vpt.getVnfLinkPort();
		final ExtCpInfo ret = new ExtCpInfo();
		ret.setId(vli.getId());
		ret.setAssociatedVnfcCpId(null); // This is one
		ret.setAssociatedVnfVirtualLinkId(getPort(portVli, vlp.getToscaName()));
		ret.setCpConfigId(null);
		if (null == vlp.getVirtualLink()) {
			final VnfPackage vnfPkg = vnfPackageJpa.findById(inst.getVnfPkg().getId()).orElseThrow();
			setFromExtCp(vnfPkg, ret, vlp.getToscaName());
		} else {
			ret.setCpdId(vlp.getVirtualLink());
		}
		ret.setCpProtocolInfo(null);
		ret.setExtLinkPortId(null);
		return ret;
	}

	private static void setFromExtCp(final VnfPackage vnfPkg, final ExtCpInfo ret, final String toscaName) {
		vnfPkg.getVirtualLinks().stream()
				.filter(x -> x.getValue().equals(toscaName))
				.findFirst()
				.ifPresent(x -> ret.setCpdId(buildVlName(x)));
	}

	private static boolean isPointingOut(final VnfLiveInstance vli, final Set<ListKeyPair> extVls) {
		final VnfPortTask task = (VnfPortTask) vli.getTask();
		return extVls.stream().anyMatch(x -> x.getValue().equals(task.getToscaName()));
	}

	private static @Nullable String getPort(final List<VnfLiveInstance> portVli, final String toscaName) {
		return portVli.stream()
				.filter(x -> toscaName.equals(((VnfPortTask) x.getTask()).getVnfLinkPort().getToscaName()))
				.findFirst()
				.map(x -> x.getId().toString())
				.orElse(null);
	}

	private static String buildVlName(final ListKeyPair x) {
		if (x.getIdx() == 0) {
			return VIRTUAL_LINK;
		}
		return VIRTUAL_LINK + "_" + x.getIdx();
	}

}
