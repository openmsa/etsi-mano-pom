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
package com.ubiqube.etsi.mano.vnfm.service.sol009;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ManoEntity;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.ResoruceMgmtModeSupportEnum;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.SupportedPackageFormats;
import com.ubiqube.etsi.mano.dao.mano.sol009.entity.VnfmSpecificInfo;
import com.ubiqube.etsi.mano.dao.mano.sol009.peers.PeerEntityEnum;
import com.ubiqube.etsi.mano.service.sol009.SpecificServerInfo;
import com.ubiqube.etsi.mano.service.sol009.pkg.ToscaVersionManager;

@Service
public class VnfmSpecificServerInfo implements SpecificServerInfo {
	private final ToscaVersionManager toscaVersionManager;

	public VnfmSpecificServerInfo(final ToscaVersionManager toscaVersionManager) {
		this.toscaVersionManager = toscaVersionManager;
	}

	@Override
	public void setSpecificInformations(final ManoEntity me) {
		final List<SupportedPackageFormats> supNsd = toscaVersionManager.extractAndSetPackageFormat();
		final VnfmSpecificInfo vnfm = new VnfmSpecificInfo();
		vnfm.setManagedVnfInstanceInfos(List.of(""));
		vnfm.setResoruceMgmtModeSupport(ResoruceMgmtModeSupportEnum.BOTH);
		vnfm.setSupportedVnfdFormats(supNsd);
		me.setVnfmSpecificInfo(vnfm);
		me.setType(PeerEntityEnum.VNFM);
	}

}
