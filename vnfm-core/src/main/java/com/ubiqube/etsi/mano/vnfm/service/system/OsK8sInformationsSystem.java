package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.OsK8sClusterInfoUowV3;

@Service
public class OsK8sInformationsSystem extends AbstractVimSystemV3<K8sInformationsTask> {
	private final K8sServerInfoJpa serverInfoJpa;
	private final Vim vim;

	protected OsK8sInformationsSystem(final Vim vim, final VimManager vimManager, final K8sServerInfoJpa serverInfoJpa) {
		super(vimManager);
		this.vim = vim;
		this.serverInfoJpa = serverInfoJpa;
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return OsK8sInformationsNode.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<K8sInformationsTask>> getImplementation(final OrchestrationServiceV3<K8sInformationsTask> orchestrationService, final VirtualTaskV3<K8sInformationsTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new OsK8sClusterInfoUowV3(virtualTask, vim, vimConnectionInformation, serverInfoJpa));
	}

}
