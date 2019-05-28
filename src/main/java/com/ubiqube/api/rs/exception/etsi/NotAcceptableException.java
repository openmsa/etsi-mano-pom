package com.ubiqube.api.rs.exception.etsi;

import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotAcceptableException extends WebApplicationException {

	public NotAcceptableException() {
		super();
	}

	public NotAcceptableException(String _detail) {
		super(Response.serverError().status(Status.NOT_ACCEPTABLE).type(MediaType.APPLICATION_JSON_TYPE).entity(new ProblemDetails(406, _detail)).build());
	}
}
