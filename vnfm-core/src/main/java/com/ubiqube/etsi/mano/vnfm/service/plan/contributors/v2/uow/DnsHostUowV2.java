package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author olivier
 *
 */
public class DnsHostUowV2 extends AbstractUowV2<DnsHostTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final DnsHostTask task;

	protected DnsHostUowV2(final VirtualTask<DnsHostTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, DnsHost.class);
		this.task = task.getParameters();
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public String execute(final Context context) {
		return vim.dns(vimConnectionInformation).createDnsRecordSet(task.getZoneId(), task.getHostname(), task.getNetworkName());
	}

	@Override
	public String rollback(final Context context) {
		vim.dns(vimConnectionInformation).deleteDnsRecordSet(task.getVimResourceId(), task.getZoneId(), task.getIps());
		return null;
	}

}
