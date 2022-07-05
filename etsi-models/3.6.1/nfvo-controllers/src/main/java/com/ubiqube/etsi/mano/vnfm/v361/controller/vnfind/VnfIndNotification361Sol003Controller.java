package com.ubiqube.etsi.mano.vnfm.v361.controller.vnfind;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnfind.VnfIndicatorNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnfind.VnfIndicatorValueChangeNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfIndNotification361Sol003Controller implements VnfIndNotification361Sol003Api {
	private VnfIndicatorNotificationFrontController fc;

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
