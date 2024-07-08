package com.ubiqube.etsi.mano.service.grant.ccm;

import java.util.List;

public interface CcmInstaller {

	String getType();

	List<String> getK8sDocuments(String version);

}
