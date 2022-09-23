package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt;

import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;

public class MciopUserVt extends VnfVtBase<MciopUserTask> {

	public MciopUserVt(final MciopUserTask nt) {
		super(nt);
	}

	@Override
	public Class<? extends Node> getType() {
		return MciopUser.class;
	}

}
