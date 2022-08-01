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

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OsContainerDeployableUow2 extends AbstractUowV2<OsContainerDeployableTask> {
	private final Vim vim;
	private final VimConnectionInformation vci;
	private final OsContainerDeployableTask task;

	public OsContainerDeployableUow2(final VirtualTask<OsContainerDeployableTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsContainerDeployableNode.class);
		this.vim = vim;
		this.vci = vimConnectionInformation;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final String network = context.get(Network.class, task.getNetwork());
		final OsContainerDeployableUnit du = task.getOsContainerDeployableUnit();
		final CnfInformations ci = vci.getCnfInfo();
		return vim.cnf(vci).startK8s(ci.getClusterTemplate(), ci.getKeyPair(), du.getVduProfile().getMinNumberOfInstances(), task.getToscaName(), du.getVduProfile().getMinNumberOfInstances(), network);
	}

	@Override
	public String rollback(final Context context) {
		vim.cnf(vci).deleteK8s(task.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of(new NamedDependency(Network.class, task.getNetwork()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getToscaName()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		return List.of(new NamedDependency2d(Network.class, task.getNetwork(), Relation.ONE_TO_ONE));
	}

}
