/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.visitor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsVlProfile;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.vim.L3Data;
import com.ubiqube.etsi.mano.dao.mano.vim.VlProtocolData;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class NsVirtualLinkVisitorTest {
	@Mock
	private NsPackageProvider packageProvider;

	@Test
	void test() {
		final NsVirtualLinkVisitor srv = new NsVirtualLinkVisitor();
		final NsdPackage nsPackage = new NsdPackage();
		final NsVirtualLink vl0 = new NsVirtualLink();
		final NsVlProfile profile = new NsVlProfile();
		final VlProtocolData pro0 = new VlProtocolData();
		final L3Data l3 = new L3Data();
		pro0.setL3ProtocolData(l3);
		profile.setVlProtocolData(Set.of(pro0));
		vl0.setNsVlProfile(profile);
		when(packageProvider.getNsVirtualLink(anyMap())).thenReturn(Set.of(vl0));
		srv.visit(nsPackage, packageProvider, Map.of());
		assertTrue(true);
	}

}
