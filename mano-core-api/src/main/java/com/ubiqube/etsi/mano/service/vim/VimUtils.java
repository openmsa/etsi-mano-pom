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
package com.ubiqube.etsi.mano.service.vim;

import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;

public class VimUtils {

	private VimUtils() {
		// Nothing.
	}

	public static void copy(final ResourceQuota pr, final VrQan vrqan) {
		vrqan.setFloatingIpUsed(pr.getFloatingIpUsed());
		vrqan.setFloatingIpMax(pr.getFloatingIpMax());
		vrqan.setFloatingFree(pr.getFloatingFree());
		vrqan.setSecurityGroupsUsed(pr.getSecurityGroupsUsed());
		vrqan.setSecurityGroupsMax(pr.getSecurityGroupsMax());
		vrqan.setSecurityGroupsFree(pr.getSecurityGroupsFree());
		vrqan.setRamUsed(pr.getRamUsed());
		vrqan.setRamMax(pr.getRamMax());
		vrqan.setRamFree(pr.getRamFree());
		vrqan.setKeyPairsUsed(pr.getKeyPairsUsed());
		vrqan.setKeyPairsMax(pr.getKeyPairsMax());
		vrqan.setKeyPairsFree(pr.getKeyPairsFree());
		vrqan.setInstanceUsed(pr.getInstanceUsed());
		vrqan.setInstanceMax(pr.getInstanceMax());
		vrqan.setInstanceFree(pr.getInstanceFree());
		vrqan.setVcpuUsed(pr.getVcpuUsed());
		vrqan.setVcpuMax(pr.getVcpuMax());
		vrqan.setVcpuFree(pr.getVcpuFree());
	}

	public static VrQan compare(final ResourceQuota pr, final VrQan vrqan) {
		final VrQan diff = new VrQan();
		diff.setFloatingIpUsed(compare(vrqan.getFloatingIpUsed(), pr.getFloatingIpUsed()));
		diff.setFloatingIpMax(compare(vrqan.getFloatingIpMax(), pr.getFloatingIpMax()));
		diff.setFloatingFree(compare(vrqan.getFloatingFree(), pr.getFloatingFree()));
		diff.setSecurityGroupsUsed(compare(vrqan.getSecurityGroupsUsed(), pr.getSecurityGroupsUsed()));
		diff.setSecurityGroupsMax(compare(vrqan.getSecurityGroupsMax(), pr.getSecurityGroupsMax()));
		diff.setSecurityGroupsFree(compare(vrqan.getSecurityGroupsFree(), pr.getSecurityGroupsFree()));
		diff.setRamUsed(compare(vrqan.getRamUsed(), pr.getRamUsed()));
		diff.setRamMax(compare(vrqan.getRamMax(), pr.getRamMax()));
		diff.setRamFree(compare(vrqan.getRamFree(), pr.getRamFree()));
		diff.setKeyPairsUsed(compare(vrqan.getKeyPairsUsed(), pr.getKeyPairsUsed()));
		diff.setKeyPairsMax(compare(vrqan.getKeyPairsMax(), pr.getKeyPairsMax()));
		diff.setKeyPairsFree(compare(vrqan.getKeyPairsFree(), pr.getKeyPairsFree()));
		diff.setInstanceUsed(compare(vrqan.getInstanceUsed(), pr.getInstanceUsed()));
		diff.setInstanceMax(compare(vrqan.getInstanceMax(), pr.getInstanceMax()));
		diff.setInstanceFree(compare(vrqan.getInstanceFree(), pr.getInstanceFree()));
		diff.setVcpuUsed(compare(vrqan.getVcpuUsed(), pr.getVcpuUsed()));
		diff.setVcpuMax(compare(vrqan.getVcpuMax(), pr.getVcpuMax()));
		diff.setVcpuFree(compare(vrqan.getVcpuFree(), pr.getVcpuFree()));
		return diff;
	}

	private static long compare(final long old, final long ne) {
		return old - ne;
	}

	private static int compare(final int old, final int ne) {
		return old - ne;
	}
}
