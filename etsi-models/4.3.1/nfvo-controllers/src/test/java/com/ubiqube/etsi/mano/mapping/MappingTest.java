package com.ubiqube.etsi.mano.mapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.nfvo.v431.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.nfvo.v431.service.OrikaConfigurationNfvo431;
import com.ubiqube.etsi.mano.test.TestHelper;
import com.ubiqube.etsi.mano.vnfm.v431.model.grant.GrantRequest;
import com.ubiqube.etsi.mano.vnfm.v431.model.vnflcm.VnfLcmOpOcc;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
class MappingTest extends TestHelper {

	public MappingTest() {
		super(new OrikaConfigurationNfvo431());
	}

	@Test
	void testVnfPkgInfo() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(VnfPkgInfo.class, VnfPackage.class, ignore);
		assertTrue(true);
	}

	@Test
	void testVnfInstance() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.VnfInstance.class, VnfInstance.class, ignore);
		assertTrue(true);
	}

	@Test
	void testLcmOpOccs() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		ignore.add("getResourceChanges");
		doTest(VnfLcmOpOcc.class, VnfBlueprint.class, ignore);
		assertTrue(true);
	}

	@Test
	void testGrant() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(GrantRequest.class, GrantResponse.class, ignore);
		assertTrue(true);
	}

}
