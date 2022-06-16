package com.ubiqube.etsi.mano.nfvo.v361.controller.nsd;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.CreateNsdInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.NsdInfo;
import com.ubiqube.etsi.mano.nfvo.v361.model.nsd.NsdInfoModifications;

@RestController
public class NsDescriptors361Sol005Controller implements NsDescriptors361Sol005Api {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public NsDescriptors361Sol005Controller(final ObjectMapper objectMapper, final HttpServletRequest request) {
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
	public ResponseEntity<NsdInfoModifications> nsDescriptorsNsdInfoIdPatch(final String nsdInfoId, @Valid final NsdInfoModifications body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<NsdInfo> nsDescriptorsPost(final String contentType, @Valid final CreateNsdInfoRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
