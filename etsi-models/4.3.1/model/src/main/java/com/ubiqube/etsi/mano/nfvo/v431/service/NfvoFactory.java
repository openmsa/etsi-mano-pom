package com.ubiqube.etsi.mano.nfvo.v431.service;

import java.util.UUID;

import com.ubiqube.etsi.mano.service.event.model.EventMessage;

public interface NfvoFactory {
	Object createNotificationVnfPackageOnboardingNotification(final UUID subscriptionId, EventMessage eventMessage);

	Object createVnfPackageChangeNotification(final UUID subscriptionId, EventMessage eventMessage);

}
