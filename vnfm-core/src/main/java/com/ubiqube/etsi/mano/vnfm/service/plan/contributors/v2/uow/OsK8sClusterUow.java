/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import java.util.Optional;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OsK8sClusterUow extends AbstractUowV2<OsContainerDeployableTask> {

	private final VimConnectionInformation vimConnectionInformation;
	private final Vim vim;
	private final OsContainerDeployableTask task;
	private K8sServerInfoJpa serverInfoJpa;

	public OsK8sClusterUow(final VirtualTask<OsContainerDeployableTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsK8sInformationsNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final String srv = context.get(OsContainerNode.class, task.getAlias());
		final Optional<K8sServers> obj = serverInfoJpa.findByVimResourceId(srv);
		if (obj.isPresent()) {
			return obj.get().getId().toString();
		}
		final K8sServers status = vim.cnf(vimConnectionInformation).getClusterInformations(srv);
		status.setToscaName(task.getToscaName());
		final K8sServers n = serverInfoJpa.save(status);
		return n.getId().toString();
	}

	@Override
	public String rollback(final Context context) {
		serverInfoJpa.deleteByVimResourceId(task.getVimResourceId());
		return null;
	}

}
