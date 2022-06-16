package com.ubiqube.etsi.mano.nfvo.v361.controller.nslcm;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.nfvo.v361.model.nslcm.CreateNsRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.nslcm.NsInstance;

@RestController
public class NsInstances361Sol005Controller implements NsInstances361Sol005Api {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public NsInstances361Sol005Controller(final ObjectMapper objectMapper, final HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public Optional<ObjectMapper> getObjectMapper() {
		return Optional.ofNullable(objectMapper);
	}

	@Override
	public Optional<HttpServletRequest> getRequest() {
		return Optional.ofNullable(request);
	}

	@Override
	public ResponseEntity<NsInstance> nsInstancesPost(final String contentType, @Valid final CreateNsRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
