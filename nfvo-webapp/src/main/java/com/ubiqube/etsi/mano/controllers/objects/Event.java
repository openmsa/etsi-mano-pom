package com.ubiqube.etsi.mano.controllers.objects;

import java.util.UUID;

import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

import lombok.Data;

@Data
public class Event {
	private NotificationEvent notificationEvent;
	private UUID objectId;
	private String vnfPkgId;
}
