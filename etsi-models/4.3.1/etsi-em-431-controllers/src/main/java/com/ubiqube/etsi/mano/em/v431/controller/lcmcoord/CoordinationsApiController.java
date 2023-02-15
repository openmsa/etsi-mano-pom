package com.ubiqube.etsi.mano.em.v431.controller.lcmcoord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.em.v431.model.lcmcoord.LcmCoord;
import com.ubiqube.etsi.mano.em.v431.model.lcmcoord.LcmCoordRequest;

import jakarta.validation.Valid;

@RestController
public class CoordinationsApiController implements CoordinationsApi {

	@Override
	public ResponseEntity<Void> coordinationsCoordinationIdCancelPost(final String coordinationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LcmCoord> coordinationsCoordinationIdGet(final String coordinationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<LcmCoord> coordinationsPost(@Valid final LcmCoordRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

}
