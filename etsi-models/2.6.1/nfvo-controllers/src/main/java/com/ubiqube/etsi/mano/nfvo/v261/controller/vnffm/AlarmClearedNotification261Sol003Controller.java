package com.ubiqube.etsi.mano.nfvo.v261.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vrqan.AlarmClearedNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmClearedNotification261Sol003Controller implements AlarmClearedNotification261Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmClearedNotification261Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmClearedNotificationCheck() {
		return fc.alarmClearedCheck();
	}

	@Override
	public ResponseEntity<Void> AlarmClearedNotificationPost(@Valid final AlarmClearedNotification body) {
		return fc.alarmClearedNotification(body, "2.6.1");
	}

}
