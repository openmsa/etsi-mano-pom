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

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.CompileLogRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.CreateLoggingJobRequest;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LogReport;
import com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanologm.LoggingJob;

@RestController
public class LogJobs431Controller implements LogJobs431Api {

	@Override
	public ResponseEntity<List<LoggingJob>> logJobsGet(@Valid final String filter, @Valid final String allFields, @Valid final String fields, @Valid final String excludeFields, @Valid final String excludeDefault, @Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LogReport> logJobsLogJobIdCompileLogPost(final String logJobId, @Valid final CompileLogRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> logJobsLogJobIdDelete(final String logJobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LoggingJob> logJobsLogJobIdGet(final String logJobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LogReport> logJobsLogJobIdLogReportsLogReportIdGet(final String logJobId, final String logReportId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LoggingJob> logJobsPost(@Valid final CreateLoggingJobRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
