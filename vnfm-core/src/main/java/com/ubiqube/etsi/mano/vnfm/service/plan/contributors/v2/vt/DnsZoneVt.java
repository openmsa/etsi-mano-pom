package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;

/**
 *
 * @author olivier
 *
 */
public class DnsZoneVt extends VnfVtBase<DnsZoneTask> {

	private final DnsZoneTask task;

	protected DnsZoneVt(final DnsZoneTask nt) {
		super(nt);
		this.task = nt;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of();
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(DnsZone.class, task.getToscaName()));
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
