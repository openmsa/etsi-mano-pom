package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.pm.PmType;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

public class VnfIndicatorUowV3 extends AbstractVnfmUowV3<VnfIndicatorTask> {
	
	public VnfIndicatorUowV3(VirtualTaskV3<VnfIndicatorTask> task, Class<? extends Node> node) {
		super(task, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(final Context3d context) {
		return "";
	}

	@Override
	public String rollback(Context3d context) {
		// TODO Auto-generated method stub
		return null;
	}

}
