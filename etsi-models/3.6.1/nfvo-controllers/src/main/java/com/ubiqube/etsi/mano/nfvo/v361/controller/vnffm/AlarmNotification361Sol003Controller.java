package com.ubiqube.etsi.mano.nfvo.v361.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnffm.AlarmNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmNotification361Sol003Controller implements AlarmNotification361Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmNotification361Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmCheck() {
		return fc.alarmCheck();
	}

	@Override
	public ResponseEntity<Void> alarmNotificationPost(@Valid final AlarmNotification body) {
		return fc.alarmNotification(body, "3.6.1");
	}

}
