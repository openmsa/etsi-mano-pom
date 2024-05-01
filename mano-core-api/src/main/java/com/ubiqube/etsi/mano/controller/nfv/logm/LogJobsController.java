/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.controller.nfv.logm;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nfvmanologm.LogJobsFrontController;
import com.ubiqube.etsi.mano.dao.mano.sol009.logm.CompileLogRequest;
import com.ubiqube.etsi.mano.dao.mano.sol009.logm.CreateLoggingJobRequest;
import com.ubiqube.etsi.mano.dao.mano.sol009.logm.LogReport;
import com.ubiqube.etsi.mano.dao.mano.sol009.logm.LoggingJob;

@Service
public class LogJobsController implements LogJobsFrontController {

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<LoggingJob, U> mapper, final String nextpageOpaqueMarker) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> compile(final String logJobId, final CompileLogRequest req, final Function<LogReport, U> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<Void> delete(final String logJobId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String logJobId, final Function<LoggingJob, U> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> findLogReport(final String logJobId, final String logReportId, final Function<LogReport, U> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> create(final CreateLoggingJobRequest request, final Function<LoggingJob, U> mapper) {
		throw new UnsupportedOperationException();
	}

}
