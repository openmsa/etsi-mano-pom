package com.ubiqube.etsi.mano.nfvo.notification.vnfind;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.ind.VnfIndiValueChangeNotification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NSVnfNotification {
	
	String nsInstanceId;
	
	String vnfInstanceId;
	
	List<VnfIndiValueChangeNotification> vnfIndiValueChangeNotifications;

}
