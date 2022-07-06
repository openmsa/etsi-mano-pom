package com.ubiqube.etsi.mano.nfvo.v361.controller.vnflcm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfLcmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnflcm.VnfIdentifierCreationNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIdentifierCreationNotification361Sol003Controller implements VnfIdentifierCreationNotification361Sol003Api {
	private final VnfLcmNotificationFrontController fc;

	public VnfIdentifierCreationNotification361Sol003Controller(final VnfLcmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> creationCheck() {
		return fc.creationCheck();
	}

	@Override
	public ResponseEntity<Void> creationNotificationPost(@Valid final VnfIdentifierCreationNotification body) {
		return fc.creationNotification(body, "3.6.1");
	}

}
