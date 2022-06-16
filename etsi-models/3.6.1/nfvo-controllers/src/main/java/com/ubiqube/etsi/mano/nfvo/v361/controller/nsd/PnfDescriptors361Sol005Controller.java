package com.ubiqube.etsi.mano.nfvo.v361.controller.nsd;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.CreatePnfdInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.PnfdInfo;

@RestController
public class PnfDescriptors361Sol005Controller implements PnfDescriptors361Sol005Api {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public PnfDescriptors361Sol005Controller(final ObjectMapper objectMapper, final HttpServletRequest request) {
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
	public ResponseEntity<Void> pnfDescriptorsPnfdInfoIdPnfdGet(final String pnfdInfoId, @Valid final String includeSignatures) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PnfdInfo> pnfDescriptorsPost(final String contentType, @Valid final CreatePnfdInfoRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
