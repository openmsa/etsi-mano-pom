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
package com.ubiqube.etsi.mano.nfvo.service.event;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsOrchestrationAdapter;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsWorkflow;
import com.ubiqube.etsi.mano.service.NsScaleStrategy;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.event.AbstractGenericAction;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoActions extends AbstractGenericAction {
	public NfvoActions(final NsWorkflow workflow, final VimResourceService vimResourceService, final NsOrchestrationAdapter orchestrationAdapter, final NsScaleStrategy nsScaleStrategy) {
		super(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy);
	}

	public void heal(@NotNull final UUID objectId) {
		//
	}

	@Override
	protected void mergeVirtualLinks(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		// Nothing to merge.
	}

	public void update(@NotNull final UUID objectId) {
		instantiate(objectId);
	}

}
