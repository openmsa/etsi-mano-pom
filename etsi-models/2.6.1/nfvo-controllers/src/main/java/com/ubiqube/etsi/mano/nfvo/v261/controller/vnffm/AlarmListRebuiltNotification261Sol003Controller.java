package com.ubiqube.etsi.mano.nfvo.v261.controller.vnffm;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnffm.VnffmNotificationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vrqan.AlarmListRebuiltNotification;

/**
 *
 * @author olivier
 *
 */
@RestController
public class AlarmListRebuiltNotification261Sol003Controller implements AlarmListRebuiltNotification261Sol003Api {
	private final VnffmNotificationFrontController fc;

	public AlarmListRebuiltNotification261Sol003Controller(final VnffmNotificationFrontController fc) {
		this.fc = fc;
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltNotificationCheck() {
		return fc.alarmRebuiltCheck();
	}

	@Override
	public ResponseEntity<Void> alarmListRebuiltNotificationPost(@Valid final AlarmListRebuiltNotification body) {
		return fc.alarmRebuiltNotification(body, "2.6.1");
	}

}
