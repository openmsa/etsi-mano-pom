package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt;

import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;

public class VnfIndicatorVt extends VnfVtBase<VnfIndicatorTask> {
	public VnfIndicatorVt(final VnfIndicatorTask task) {
		super(task);
	}

	@Override
	public Class<? extends Node> getType() {
		return VnfIndicator.class;
	}

}
