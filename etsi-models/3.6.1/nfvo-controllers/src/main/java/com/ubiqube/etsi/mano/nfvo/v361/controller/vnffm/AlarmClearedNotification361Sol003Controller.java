package com.ubiqube.etsi.mano.nfvo.v361.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnffm.AlarmClearedNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmClearedNotification361Sol003Controller implements AlarmClearedNotification361Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmClearedNotification361Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmClearedCheck() {
		return fc.alarmClearedCheck();
	}

	@Override
	public ResponseEntity<Void> alarmClearedNotificationPost(@Valid final AlarmClearedNotification body) {
		return fc.alarmClearedNotification(body, "3.6.1");
	}

}
