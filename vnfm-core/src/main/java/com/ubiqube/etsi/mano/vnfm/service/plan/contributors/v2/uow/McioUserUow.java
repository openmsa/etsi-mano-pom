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

import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class McioUserUow extends AbstractUowV2<MciopTask> {

	private final MciopTask task;
	private final K8sServerInfoJpa serverInfoJpa;
	private final Vim vim;
	private final VimConnectionInformation vci;
	private final String userCn;

	public McioUserUow(final VirtualTask<MciopTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation, final K8sServerInfoJpa serverInfoJpa, final String userCn) {
		super(task, MciopUser.class);
		this.task = task.getParameters();
		this.vim = vim;
		this.vci = vimConnectionInformation;
		this.serverInfoJpa = serverInfoJpa;
		this.userCn = userCn;
	}

	@Override
	public String execute(final Context context) {
		final String db = context.get(OsK8sInformationsNode.class, task.getParentVdu());
		final K8sServers server = serverInfoJpa.findById(UUID.fromString(db)).orElseThrow(() -> new GenericException("Unable to find k8s server: " + db));
		final K8sServers res = vim.cnf(vci).sign(userCn, server);
		serverInfoJpa.save(res);
		return res.getUserCrt();
	}

	@Override
	public String rollback(final Context context) {
		// Nothing To rollback.
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of(new NamedDependency(OsContainerDeployableNode.class, task.getParentVdu()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getToscaName()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		return List.of(new NamedDependency2d(OsContainerDeployableNode.class, task.getParentVdu(), Relation.ONE_TO_ONE));
	}

}
