package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class KuryrCni implements CniInstaller {

	@Override
	public String getType() {
		return "kuryr";
	}

	@Override
	public List<String> getK8sDocuments(final String version) {
		return List.of();
	}

}
