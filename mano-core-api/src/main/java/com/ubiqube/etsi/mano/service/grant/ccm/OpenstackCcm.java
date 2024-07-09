package com.ubiqube.etsi.mano.service.grant.ccm;

import org.springframework.stereotype.Service;

@Service
public class OpenstackCcm implements CcmInstaller {

	@Override
	public String getType() {
		return "openstack";
	}

}
