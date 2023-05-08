/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.tosca;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ubiqube.etsi.mano.sol004.ZipUtil;
import com.ubiqube.etsi.mano.sol004.ZipUtil.Entry;
import com.ubiqube.etsi.mano.sol004.vfs.DoubleZip;

class DoubleZipResolverTest {

	@Test
	void test() throws IOException {
		new File("/tmp/tosca/").mkdir();
		createZip("/tmp/tosca/test.csar");
		ZipUtil.makeToscaZip("/tmp/test.zip", "/tmp/tosca/");
		final DoubleZip dz = new DoubleZip(new File("/tmp/test.zip"), "test.csar");
		final DoubleZipResolver srv = new DoubleZipResolver(dz);
		srv.getContent("mano-qa-sol004.pub.pem.cert");
		assertTrue(true);
	}

	@ParameterizedTest
	@ValueSource(strings = { "http://localhost//mano-qa-sol004.pub.pem.cert",
			"/bad/mano-qa-sol004.pub.pem.cert",
			"/mano-qa-sol004.pub.pem.cert",
			"mano-qa-sol004.pub.pem.cert" })
	void testResolvPath4(final String value) throws IOException {
		new File("/tmp/tosca/").mkdir();
		createZip("/tmp/tosca/test.csar");
		ZipUtil.makeToscaZip("/tmp/test.zip", "/tmp/tosca/");
		final DoubleZip dz = new DoubleZip(new File("/tmp/test.zip"), "test.csar");
		final DoubleZipResolver srv = new DoubleZipResolver(dz);
		srv.resolvePath(value);
		assertTrue(true);
	}

	private static void createZip(final String fn) throws IOException {
		ZipUtil.makeToscaZip(fn,
				Entry.of("test-csar/mano-qa-sol004.pub.pem.cert", "mano-qa-sol004.pub.pem.cert"),
				Entry.of("test-csar/Definitions/etsi_nfv_sol001_vnfd_types.yaml", "Definitions/etsi_nfv_sol001_vnfd_types.yaml"),
				Entry.of("test-csar/Definitions/etsi_nfv_sol001_vnfd_types.yaml.sig.p7s", "Definitions/etsi_nfv_sol001_vnfd_types.yaml.sig.p7s"),
				Entry.of("test-csar/Definitions/tosca_ubi_scale.yaml", "Definitions/tosca_ubi_scale.yaml"),
				Entry.of("test-csar/Definitions/tosca_ubi_scale.yaml.sig.p7s", "Definitions/tosca_ubi_scale.yaml.sig.p7s"),
				Entry.of("test-csar/TOSCA-Metadata/TOSCA.meta", "TOSCA-Metadata/TOSCA.meta"),
				Entry.of("test-csar/TOSCA-Metadata/TOSCA.meta.sig.p7s", "TOSCA-Metadata/TOSCA.meta.sig.p7s"),
				Entry.of("test-csar/TOSCA-Metadata/manifest.mf", "TOSCA-Metadata/manifest.mf"));
	}

}
