package com.ubiqube.etsi.mano.nfvo.v361.controller.vnflcm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfLcmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnflcm.VnfLcmOperationOccurrenceNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfLcmOperationOccurrenceNotification361Sol003Controller implements VnfLcmOperationOccurrenceNotification261Sol003Api {
	private final VnfLcmNotificationFrontController fc;

	public VnfLcmOperationOccurrenceNotification361Sol003Controller(final VnfLcmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> vnflcmopoccCheck() {
		return fc.vnflcmopoccCheck();
	}

	@Override
	public ResponseEntity<Void> lcmOperationOccurrenceNotificationPost(@Valid final VnfLcmOperationOccurrenceNotification body) {
		return fc.vnflcmopoccNotification(body, "3.6.1");
	}

}
