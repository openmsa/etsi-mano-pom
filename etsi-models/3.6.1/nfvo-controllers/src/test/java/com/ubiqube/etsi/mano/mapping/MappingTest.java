package com.ubiqube.etsi.mano.mapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.VnfLcmOpOcc;
import com.ubiqube.etsi.mano.nfvo.v361.OrikaConfigurationNfvo361;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.test.TestHelper;
import com.ubiqube.etsi.mano.vnfm.v361.model.grant.GrantRequest;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
class MappingTest extends TestHelper {

	public MappingTest() {
		super(new OrikaConfigurationNfvo361());
	}

	@Test
	void testVnfPkgInfo() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getSoftwareImages");
		ignore.add("getLinks");
		ignore.add("getChecksum");
		doTest(VnfPkgInfo.class, VnfPackage.class, ignore);
		assertTrue(true);
	}

	@Test
	void testVnfInstance() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		ignore.add("getExtLinkPortId");
		ignore.add("getExtManagedVirtualLinkInfo");
		ignore.add("getInstantiatedVnfInfo");
		doTest(com.ubiqube.etsi.mano.em.v361.model.vnflcm.VnfInstance.class, VnfInstance.class, ignore);
		assertTrue(true);
	}

	@Test
	void testLcmOpOccs() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		ignore.add("getChangedInfo");
		ignore.add("getChangedExtConnectivity");
		ignore.add("getResourceChanges");
		ignore.add("getAffectedVipCps");
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
