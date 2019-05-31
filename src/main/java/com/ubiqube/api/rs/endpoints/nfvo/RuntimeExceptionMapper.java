package com.ubiqube.api.rs.endpoints.nfvo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

	@Override
	public Response toResponse(Throwable _exception) {
		LOG.error("An error has occured.", _exception);
		final int statusCode = 501;
		if (_exception instanceof WebApplicationException) {
			final WebApplicationException ex = (WebApplicationException) _exception;
			return ex.getResponse();
		}
		final String message = _exception.getMessage();
		return Response.status(statusCode)
				.entity(new ProblemDetails(statusCode, message))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

}
