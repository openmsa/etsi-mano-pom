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
package com.ubiqube.etsi.mano.nfvo.service.system;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.VnfContextExtractorUow;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;

/**
 *
 * @author olivier
 *
 */
@Service
public class NsVnfContextExtractorSystem extends AbstractVimSystemV3<NsVnfExtractorTask> {
	private final VnfmInterface vnfm;
	private final NsdPackageJpa nsdPackageJpa;

	public NsVnfContextExtractorSystem(final VnfmInterface vnfm, final VimManager vimManager, final NsdPackageJpa nsdPackageJpa) {
		super(vimManager);
		this.vnfm = vnfm;
		this.nsdPackageJpa = nsdPackageJpa;
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<NsVnfExtractorTask>> getImplementation(final OrchestrationServiceV3<NsVnfExtractorTask> orchestrationService, final VirtualTaskV3<NsVnfExtractorTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final NsdPackage pack = nsdPackageJpa.findById(UUID.fromString(virtualTask.getTemplateParameters().getNsdId()))
				.orElseThrow(() -> new GenericException("Unable to find package [" + virtualTask.getTemplateParameters().getNsdId() + "]"));
		return orchestrationService.systemBuilderOf(new VnfContextExtractorUow(virtualTask, vnfm, pack));
	}

	@Override
	public Class<? extends Node> getType() {
		return VnfExtractorNode.class;
	}
}
