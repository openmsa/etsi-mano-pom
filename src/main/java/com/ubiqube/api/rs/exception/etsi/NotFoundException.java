package com.ubiqube.api.rs.exception.etsi;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;

public class NotFoundException extends WebApplicationException {

	/**
	 * Default Serial.
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String _detail) {
		super(Response.serverError().status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ProblemDetails(404, _detail)).build());
	}
}
