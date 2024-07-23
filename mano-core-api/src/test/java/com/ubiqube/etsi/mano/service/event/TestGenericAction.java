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
package com.ubiqube.etsi.mano.service.event;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;

public class TestGenericAction extends AbstractGenericActionV3 {

	protected TestGenericAction(final WorkflowV3 workflow, final VimResourceService vimResourceService, final OrchestrationAdapter<?, ?> orchestrationAdapter, final NsScaleStrategyV3 nsScaleStrategy) {
		super(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, null);
	}

	@Override
	protected void mergeVirtualLinks(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		//
	}

}
