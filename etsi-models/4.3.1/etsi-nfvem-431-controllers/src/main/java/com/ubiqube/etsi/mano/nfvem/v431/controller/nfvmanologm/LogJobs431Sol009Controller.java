/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvem.v431.controller.nfvmanologm;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.nfvmanologm.LogJobsFrontController;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.CompileLogRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.CreateLoggingJobRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LogReport;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJob;

@RestController
public class LogJobs431Sol009Controller implements LogJobs431Sol009Api {
	private final LogJobsFrontController logJobsFrontController;

	public LogJobs431Sol009Controller(final LogJobsFrontController logJobsFrontController) {
		this.logJobsFrontController = logJobsFrontController;
	}

	@Override
	public ResponseEntity<String> logJobsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return logJobsFrontController.search(requestParams, LogReport.class, nextpageOpaqueMarker);
	}

	@Override
	public ResponseEntity<LogReport> logJobsLogJobIdCompileLogPost(final String logJobId, @Valid final CompileLogRequest body) {
		return logJobsFrontController.compile(logJobId, body, LogReport.class);
	}

	@Override
	public ResponseEntity<Void> logJobsLogJobIdDelete(final String logJobId) {
		return logJobsFrontController.delete(logJobId);
	}

	@Override
	public ResponseEntity<LoggingJob> logJobsLogJobIdGet(final String logJobId) {
		return logJobsFrontController.findById(logJobId, LoggingJob.class);
	}

	@Override
	public ResponseEntity<LogReport> logJobsLogJobIdLogReportsLogReportIdGet(final String logJobId, final String logReportId) {
		return logJobsFrontController.findLogReport(logJobId, logReportId, LogReport.class);
	}

	@Override
	public ResponseEntity<LoggingJob> logJobsPost(@Valid final CreateLoggingJobRequest body) {
		return logJobsFrontController.create(body, LoggingJob.class);
	}

}
