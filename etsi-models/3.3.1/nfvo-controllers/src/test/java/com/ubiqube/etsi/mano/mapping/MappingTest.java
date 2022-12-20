/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.mapping;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.v331.OrikaConfigurationNfvo331;
import com.ubiqube.etsi.mano.nfvo.v331.model.nsd.NsdInfo;
import com.ubiqube.etsi.mano.nfvo.v331.model.nslcm.NsInstance;
import com.ubiqube.etsi.mano.nfvo.v331.model.nslcm.NsLcmOpOcc;
import com.ubiqube.etsi.mano.nfvo.v331.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.test.TestHelper;
import com.ubiqube.etsi.mano.vnfm.v331.model.grant.GrantRequest;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
class MappingTest extends TestHelper {

	public MappingTest() {
		super(new OrikaConfigurationNfvo331());
	}

	@Test
	void testNsdInfo() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(NsdInfo.class, NsdPackage.class, ignore);
	}

	@Test
	void testNsLcmOpOcc() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		ignore.add("getResourceChanges");
		doTest(NsLcmOpOcc.class, NsBlueprint.class, ignore);
	}

	@Test
	void testGrant() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(GrantRequest.class, GrantResponse.class, ignore);
	}

	@Test
	void testNsdInstance() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		// Complex, probably data are in tasks.
		ignore.add("getVnfInstance");
		// Reconstructed at view level thru DTO
		ignore.add("getVirtualLinkInfo");
		ignore.add("getPnfInfo");
		doTest(NsInstance.class, NsdInstance.class, ignore);
	}

	@Test
	void testVnfPkgInfoNfvo() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(VnfPkgInfo.class, VnfPackage.class, ignore);
	}

	@Test
	void testVnfInstance() throws Exception {
		final Set<String> ignore = new HashSet<>();
		ignore.add("getLinks");
		doTest(com.ubiqube.etsi.mano.em.v331.model.vnflcm.VnfInstance.class, VnfInstance.class, ignore);
	}

}
