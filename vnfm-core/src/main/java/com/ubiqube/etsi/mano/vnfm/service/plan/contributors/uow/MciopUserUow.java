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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.boot.K8sPkService;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

import jakarta.annotation.Nullable;

public class MciopUserUow extends AbstractVnfmUow<MciopUserTask> {
	private final MciopUserTask task;
	private final K8sServerInfoJpa serverInfoJpa;
	private final Vim vim;
	private final VimConnectionInformation vci;
	private final K8sPkService k8sPkService;

	public MciopUserUow(final VirtualTaskV3<MciopUserTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation,
			final K8sServerInfoJpa serverInfoJpa, final K8sPkService k8sPkService) {
		super(task, MciopUser.class);
		this.task = task.getTemplateParameters();
		this.vim = vim;
		this.vci = vimConnectionInformation;
		this.serverInfoJpa = serverInfoJpa;
		this.k8sPkService = k8sPkService;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String crt = k8sPkService.createCsr("CN=kubernetes-admin,O=system:masters");
		final String db = context.get(OsK8sInformationsNode.class, task.getParentVdu());
		final K8sServers server = serverInfoJpa.findById(UUID.fromString(db)).orElseThrow(() -> new GenericException("Unable to find k8s server: " + db));
		final K8sServers res = vim.cnf(vci).sign(crt, server);
		res.setUserKey(k8sPkService.getPrivateKey());
		serverInfoJpa.save(res);
		return res.getUserCrt();
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		// Nothing.
		return null;
	}

}
