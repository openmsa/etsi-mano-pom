package com.ubiqube.etsi.mano.vnfm.v361.controller.vnfpm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnf.VnfPerformanceNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnfpm.ThresholdCrossedNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfpmCrossedNotification361Sol003Controller implements VnfpmCrossedNotification361Sol003Api {
	private final VnfPerformanceNotificationFrontController fc;

	public VnfpmCrossedNotification361Sol003Controller(final VnfPerformanceNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> crossedCheck() {
		return fc.crossedCheck();
	}

	@Override
	public ResponseEntity<Void> crossedNotificationPost(@Valid final ThresholdCrossedNotification body) {
		return fc.crossedPost(body, "3.6.1");
	}

}
