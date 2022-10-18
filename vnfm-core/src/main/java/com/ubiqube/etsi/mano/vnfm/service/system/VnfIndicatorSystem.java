package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.VnfMonitoringService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.VnfIndicatorUowV3;

@Service
public class VnfIndicatorSystem extends AbstractVimSystemV3<VnfIndicatorTask> {
	private final VnfMonitoringService vnfMonitoringService;

	public VnfIndicatorSystem(final VnfMonitoringService vnfMonitoringService, final VimManager vimManager) {
		super(vimManager);
		this.vnfMonitoringService = vnfMonitoringService;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<VnfIndicatorTask>> getImplementation(final OrchestrationServiceV3<VnfIndicatorTask> orchestrationService, final VirtualTaskV3<VnfIndicatorTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new VnfIndicatorUowV3(virtualTask, getType()));
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return VnfIndicator.class;
	}

}
