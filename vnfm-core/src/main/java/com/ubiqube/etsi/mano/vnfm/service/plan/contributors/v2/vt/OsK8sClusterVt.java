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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt;

import java.util.Arrays;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OsK8sClusterVt extends VnfVtBase<K8sInformationsTask> {

	private final K8sInformationsTask p;

	public OsK8sClusterVt(final K8sInformationsTask nt) {
		super(nt);
		this.p = nt;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return Arrays.asList(new NamedDependency(OsContainerDeployableNode.class, p.getAlias()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return Arrays.asList(new NamedDependency(OsK8sInformationsNode.class, p.getAlias()));
	}

	@Override
	public String getFactoryProviderId() {
		return "CNF-INFO";
	}

	@Override
	public String getVimProviderId() {
		return "OPENSTACK_V3";
	}

}
