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
package com.ubiqube.etsi.mano.vnfm.service.alarm;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.mon.VimMonitoring;

public class VimAlarmSystem {
	private final VimManager vimManager;

	public VimAlarmSystem(final VimManager vimManager) {
		this.vimManager = vimManager;
	}

	public void createAlarm(final Threshold res, final UUID systemId, final VimConnectionInformation vimConn) {
		final Vim vim = vimManager.getVimById(systemId);
		final VimMonitoring mon = vim.getMonitoring(vimConn);
		res.getSubObjectInstanceIds().stream().forEach(x -> {
			final String resource = mon.registerAlarm(x.getId(), res.getCriteria().getPerformanceMetric(),
					res.getCriteria().getSimpleThresholdDetails().getThresholdValue(), res.getCriteria().getSimpleThresholdDetails().getHysteresis(), "callback url");
			x.setResource(resource);
			x.setSystemId(systemId);
		});
	}

	public void delete(final Threshold res) {
		res.getSubObjectInstanceIds().forEach(x -> {
			final Vim vim = vimManager.getVimById(x.getSystemId());
			final VimMonitoring mon = vim.getMonitoring(vimManager.findVimById(x.getSystemId()));
			mon.removeAlarm(x.getResource());
		});
	}
}
