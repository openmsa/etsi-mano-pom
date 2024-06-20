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
package com.ubiqube.etsi.mano.service.grant.ccm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.vim.k8s.K8s;

/**
 * This pseudo service is added for debugging purpose.
 */
public class NoopCcmServerService implements CcmServerService {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(NoopCcmServerService.class);

	@Override
	public void terminateCluster(final String vnfInstanceId) {
		LOG.warn("Noop: create cluster {}", vnfInstanceId);
	}

	@Override
	public K8s createCluster(final VimConnectionInformation vci, final String name) {
		LOG.warn("Noop: Delete cluster: {}", name);
		return null;
	}

}
