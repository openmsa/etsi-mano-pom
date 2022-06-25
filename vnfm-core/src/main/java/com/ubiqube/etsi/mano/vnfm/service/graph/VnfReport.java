package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.List;

import com.github.dexecutor.core.task.ExecutionResult;
import com.github.dexecutor.core.task.ExecutionResults;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWork;
import com.ubiqube.etsi.mano.service.event.Report;
import com.ubiqube.etsi.mano.service.event.ReportItem;

public class VnfReport implements Report {

	private final ExecutionResults<UnitOfWork<VnfTask>, String> results;

	public VnfReport(final ExecutionResults<UnitOfWork<VnfTask>, String> results) {
		this.results = results;
	}

	public List<ExecutionResult<UnitOfWork<VnfTask>, String>> getSkipped() {
		return results.getSkipped();
	}

	@Override
	public List<ReportItem> getSuccess() {
		return results.getSuccess().stream().map(this::map).toList();
	}

	@Override
	public List<ReportItem> getErrored() {
		return results.getErrored().stream().map(this::map).toList();
	}

	public List<ExecutionResult<UnitOfWork<VnfTask>, String>> getAll() {
		return results.getAll();
	}

	private ReportItem map(final ExecutionResult<UnitOfWork<VnfTask>, String> er) {
		final Task part = er.getId().getTask().getParameters();
		return new ReportItem(part);
	}

}
