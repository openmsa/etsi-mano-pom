package com.ubiqube.etsi.mano.service.grant.ccm;

import com.ubiqube.etsi.mano.service.grant.ResourceLoader;

public interface CcmInstaller extends ResourceLoader {

	@Override
	default String getSuperType() {
		return "ccm";
	}

}
