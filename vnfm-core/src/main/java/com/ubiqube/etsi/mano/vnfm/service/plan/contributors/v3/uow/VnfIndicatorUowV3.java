/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

import jakarta.annotation.Nullable;

public class VnfIndicatorUowV3 extends AbstractVnfmUowV3<VnfIndicatorTask> {

	public VnfIndicatorUowV3(final VirtualTaskV3<VnfIndicatorTask> task, final Class<? extends Node> node) {
		super(task, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		return "";
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		// TODO Auto-generated method stub
		return null;
	}

}
