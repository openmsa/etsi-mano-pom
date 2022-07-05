package com.ubiqube.etsi.mano.nfvo.v361.controller.vnfpm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnf.VnfPerformanceNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnfpm.PerformanceInformationAvailableNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfpmAvailableNotification361Sol003Controller implements VnfpmAvailableNotification361Sol003Api {
	private final VnfPerformanceNotificationFrontController fc;

	public VnfpmAvailableNotification361Sol003Controller(final VnfPerformanceNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> availableCheck() {
		return fc.availableCheck();
	}

	@Override
	public ResponseEntity<Void> availableNotificationPost(@Valid final PerformanceInformationAvailableNotification body) {
		return fc.availablePost(body, "3.6.1");
	}

}
