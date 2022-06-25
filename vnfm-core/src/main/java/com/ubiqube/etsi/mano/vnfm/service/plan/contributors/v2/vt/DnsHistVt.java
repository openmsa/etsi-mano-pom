package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;

/**
 *
 * @author olivier
 *
 */
public class DnsHistVt extends VnfVtBase<DnsHostTask> {

	private final DnsHostTask task;

	protected DnsHistVt(final DnsHostTask nt) {
		super(nt);
		this.task = nt;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of(new NamedDependency(Compute.class, task.getParentAlias()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(DnsHost.class, task.getToscaName()));
	}

	@Override
	public String getFactoryProviderId() {
		return null;
	}

	@Override
	public String getVimProviderId() {
		return null;
	}

}
