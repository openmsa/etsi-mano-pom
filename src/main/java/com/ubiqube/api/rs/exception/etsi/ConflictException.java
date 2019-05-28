package com.ubiqube.api.rs.exception.etsi;

import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ConflictException extends WebApplicationException {

	public ConflictException() {
		super();
	}

	public ConflictException(String _detail) {
		super(Response.serverError().status(Status.CONFLICT).type(MediaType.APPLICATION_JSON_TYPE).entity(new ProblemDetails(409, _detail)).build());
	}
}
