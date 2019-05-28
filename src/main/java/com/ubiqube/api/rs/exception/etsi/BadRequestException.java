package com.ubiqube.api.rs.exception.etsi;

import com.ubiqube.api.ejb.nfvo.vnf.ProblemDetails;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {

	public BadRequestException() { super(); }

	public BadRequestException(String _detail) {
		super(Response.serverError().status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(new ProblemDetails(400, _detail)).build());
	}
}
