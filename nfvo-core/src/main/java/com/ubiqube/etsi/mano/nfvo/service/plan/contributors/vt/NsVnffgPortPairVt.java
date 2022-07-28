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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.PortPairNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.service.graph.vt.NsVtBase;

/**
 *
 * @author olivier
 *
 */
public class NsVnffgPortPairVt extends NsVtBase<VnffgPortPairTask> {

	private final VnffgPortPairTask task;

	public NsVnffgPortPairVt(final VnffgPortPairTask nt) {
		super(nt);
		task = nt;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of(new NamedDependency(VnfInstantiateNode.class, task.getVnfAlias()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(PortPairNode.class, task.getAlias()));
	}

	@Override
	public String getFactoryProviderId() {
		return "VNFFG-PORT-PAIR";
	}

	@Override
	public String getVimProviderId() {
		return "VNFFG-PORT-PAIR";
	}

}
