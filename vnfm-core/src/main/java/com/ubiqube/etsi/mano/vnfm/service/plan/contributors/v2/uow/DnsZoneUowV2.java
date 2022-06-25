package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author olivier
 *
 */
public class DnsZoneUowV2 extends AbstractUowV2<DnsZoneTask> {
	private final DnsZoneTask task;
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;

	protected DnsZoneUowV2(final VirtualTask<DnsZoneTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, DnsZone.class);
		this.task = task.getParameters();
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public String execute(final Context context) {
		return vim.dns(vimConnectionInformation).createDnsZone(task.getDomainName());
	}

	@Override
	public String rollback(final Context context) {
		vim.dns(vimConnectionInformation).deleteDnsZone(task.getVimResourceId());
		return null;
	}

}
