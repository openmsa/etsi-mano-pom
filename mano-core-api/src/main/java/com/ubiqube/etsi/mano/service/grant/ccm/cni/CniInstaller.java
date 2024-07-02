package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import java.util.List;

/**
 * As in K8s there is many CNI, let's abstract a bit this question.
 */
public interface CniInstaller {

	String getType();

	List<String> getK8sDocuments(String version);
}
