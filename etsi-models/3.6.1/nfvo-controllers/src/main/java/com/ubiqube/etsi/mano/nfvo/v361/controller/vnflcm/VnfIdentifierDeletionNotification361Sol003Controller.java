package com.ubiqube.etsi.mano.nfvo.v361.controller.vnflcm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfLcmNotificationFrontController;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.VnfIdentifierDeletionNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIdentifierDeletionNotification361Sol003Controller implements VnfIdentifierDeletionNotification261Sol003Api {
	private final VnfLcmNotificationFrontController fc;

	public VnfIdentifierDeletionNotification361Sol003Controller(final VnfLcmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> deletionCheck() {
		return fc.deletionCheck();
	}

	@Override
	public ResponseEntity<Void> deletionNotificationPost(@Valid final VnfIdentifierDeletionNotification body) {
		return fc.deletionNotification(body, "3.6.1");
	}

}
