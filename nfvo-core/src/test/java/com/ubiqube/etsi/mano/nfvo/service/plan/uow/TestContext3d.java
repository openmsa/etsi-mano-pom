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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import java.util.List;

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;

public class TestContext3d implements Context3d {

	@Override
	public String get(Class<? extends Node> class1, String toscaName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getParent(Class<? extends Node> class1, String toscaName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Class<? extends Node> class1, String name, String resourceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> get(Class<? extends Node> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOptional(Class<? extends Node> class1, String parentToscaName) {
		// TODO Auto-generated method stub
		return null;
	}

}
