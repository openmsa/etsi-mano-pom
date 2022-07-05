package com.ubiqube.etsi.mano.nfvo.v261.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnffm.AlarmNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmNotification261Sol003Controller implements AlarmNotification261Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmNotification261Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmCheck() {
		return fc.alarmCheck();
	}

	@Override
	public ResponseEntity<Void> alarmNotificationPost(@Valid final AlarmNotification body) {
		return fc.alarmNotification(body, "2.6.1");
	}

}
