package com.ubiqube.etsi.mano.service.grant.ccm;

import com.ubiqube.etsi.mano.service.grant.K8sResourceLoader;

public interface CcmInstaller extends K8sResourceLoader {

	@Override
	default String getSuperType() {
		return "ccm";
	}

}
