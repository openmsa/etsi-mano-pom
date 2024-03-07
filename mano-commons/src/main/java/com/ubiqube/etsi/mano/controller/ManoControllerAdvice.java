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
package com.ubiqube.etsi.mano.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ubiqube.etsi.mano.exception.SeeOtherException;
import com.ubiqube.etsi.mano.model.ProblemDetails;

/**
 *
 * @author ncuser
 *
 */
@RestControllerAdvice
public class ManoControllerAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(ManoControllerAdvice.class);

	@SuppressWarnings("static-method")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetails> handleValidationExceptions(final MethodArgumentNotValidException ex) {
		final StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			LOG.warn("{}", error);
			final String fieldName = ((FieldError) error).getField();
			final String objectName = ((FieldError) error).getObjectName();
			final String errorMessage = error.getDefaultMessage();
			errors.append(objectName).append(".").append(fieldName).append(": ").append(errorMessage).append("\n");
		});
		final ProblemDetails problemDetail = new ProblemDetails(400, errors.toString());
		return ResponseEntity.status(400)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.body(problemDetail);
	}

	@SuppressWarnings("static-method")
	@ResponseStatus(HttpStatus.SEE_OTHER)
	@ExceptionHandler(SeeOtherException.class)
	public ResponseEntity<ProblemDetails> handleSeeOtherExceptions(final SeeOtherException ex) {
		final ProblemDetails problemDetail = new ProblemDetails(303, "See: " + ex.getUri());
		return ResponseEntity.status(303)
				.location(ex.getUri())
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.body(problemDetail);
	}

}
