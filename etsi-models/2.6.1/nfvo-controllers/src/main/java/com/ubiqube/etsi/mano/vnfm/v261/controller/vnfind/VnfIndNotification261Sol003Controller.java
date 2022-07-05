package com.ubiqube.etsi.mano.vnfm.v261.controller.vnfind;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnfind.VnfIndicatorNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfind.VnfIndicatorValueChangeNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIndNotification261Sol003Controller implements VnfIndNotification261Sol003Api {
	private final VnfIndicatorNotificationFrontController fc;

	public VnfIndNotification261Sol003Controller(final VnfIndicatorNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> valueChangeCheck() {
		return fc.valueChangeCheck();
	}

	@Override
	public ResponseEntity<Void> valueChangePost(@Valid final VnfIndicatorValueChangeNotification body) {
		return fc.valueChangeNotification(body, "3.6.1");
	}

	@Override
	public ResponseEntity<Void> supportedCheck() {
		return fc.supportedChangeCheck();
	}

	@Override
	public ResponseEntity<Void> supportedChangePost(@Valid final VnfIndicatorValueChangeNotification body) {
		return fc.supportedChangeNotification(body, "3.6.1");
	}

}
