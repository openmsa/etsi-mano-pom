package com.ubiqube.etsi.mano.nfvo.v361.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.model.vnffm.AlarmListRebuiltNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmListRebuiltNotification361Sol003Controller implements AlarmListRebuiltNotification361Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmListRebuiltNotification361Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltCheck() {
		return fc.alarmRebuiltCheck();
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltNotificationPost(@Valid final AlarmListRebuiltNotification body) {
		return fc.alarmRebuiltNotification(body, "3.6.1");
	}

}
