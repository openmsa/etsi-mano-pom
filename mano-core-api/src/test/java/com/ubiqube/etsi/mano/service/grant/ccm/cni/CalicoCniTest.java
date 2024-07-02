package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class CalicoCniTest {

	CalicoCni createService() {
		return new CalicoCni();
	}

	@Test
	void test() {
		final CalicoCni srv = createService();
		final List<String> res = srv.getK8sDocuments("3.28.0");
		assertNotNull(res);
		assertEquals("calico", srv.getType());
	}

}
