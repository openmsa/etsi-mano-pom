package com.ubiqube.etsi.mano.vnfm.v261.controller.vnfpm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnf.VnfPerformanceNotificationFrontController;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsperfo.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsperfo.ThresholdCrossedNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class PerformanceInformationAvailableNotification261Sol003Controller implements PerformanceInformationAvailableNotification261Sol003Api {
	private final VnfPerformanceNotificationFrontController fc;

	public PerformanceInformationAvailableNotification261Sol003Controller(final VnfPerformanceNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> availableCheck() {
		return fc.availableCheck();
	}

	@Override
	public ResponseEntity<Void> availabePost(@Valid final PerformanceInformationAvailableNotification body) {
		return fc.availablePost(body, "2.6.1");
	}

	@Override
	public ResponseEntity<Void> crossedCheck() {
		return fc.crossedCheck();
	}

	@Override
	public ResponseEntity<Void> crossedPost(@Valid final ThresholdCrossedNotification body) {
		return fc.crossedPost(body, "2.6.1");
	}

}
