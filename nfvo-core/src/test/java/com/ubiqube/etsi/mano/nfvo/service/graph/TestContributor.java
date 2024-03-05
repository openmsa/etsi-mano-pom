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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.graph;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3.AbstractNsdContributorV3;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;

public class TestContributor extends AbstractNsdContributorV3<TestNsTask> {

	private final List<SclableResources<TestNsTask>> resources;

	public TestContributor(final NsLiveInstanceJpa nsLiveInstanceJpa, final List<SclableResources<TestNsTask>> resources) {
		super(nsLiveInstanceJpa);
		this.resources = resources;
	}

	@Override
	public List<SclableResources<TestNsTask>> contribute(final NsdPackage bundle, final NsBlueprint parameters) {
		return resources;
	}

}
