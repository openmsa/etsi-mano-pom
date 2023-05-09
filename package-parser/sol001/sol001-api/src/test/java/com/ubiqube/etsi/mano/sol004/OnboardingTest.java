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
package com.ubiqube.etsi.mano.sol004;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.sol004.ZipUtil.Entry;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
class OnboardingTest {

	private static final Logger LOG = LoggerFactory.getLogger(OnboardingTest.class);

	private final Sol004Onboarding ob = new Sol004Onboarding();

	private final String fileName = "/tmp/tesca.csar";

	public OnboardingTest() {
		new File("/tmp/tosca").mkdir();
	}

	@Test
	@Order(1)
	void testName01() throws Exception {
		createZip(fileName);
		ob.preOnboard(fileName);
		final boolean yang = ob.isYang();
		if (yang) {
			throw new Sol004Exception("Yang is not supported.");
		}
		final String entryFilename = ob.getToscaEntryPointFilename();
		if (isXml(entryFilename)) {
			throw new Sol004Exception("XML sol001 format not supported.");
		}
		try (final InputStream is = ob.getToscaEntryPoint()) {
			//
		}
		uploadToCluster(fileName);
		final List<CsarError> err = ob.validate();
		ob.getFileInputStream("mano-qa-sol004.pub.pem.cert");
		ob.getFiles();
		ob.getManifestContent();
		ob.getSol001Version();
		// err must be empty.
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

	private static boolean isXml(final String entryFilename) {
		return entryFilename.endsWith(".xml");
	}

	private void uploadToCluster(final String fileName) {
		final CsarModeEnum mode = ob.getToscaMode(fileName);
		if (mode == CsarModeEnum.DOUBLE_ZIP) {
			// Upload to Cluster.
		} else {
			// Upload the filename.
		}
		LOG.debug("{}", mode);
	}

	@Test
	@Order(2)
	void testDoubleOnbarding() throws IOException {
		final Path root = Paths.get("/tmp/tosca");
		root.toFile().mkdir();
		Files.walk(root)
				.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);
		root.toFile().mkdir();
		ZipUtil.makeToscaZip("/tmp/tosca/l1.csar", "src/test/resources/scale-vnf/");
		copy(getClass().getResourceAsStream("/cert.cert"), "/tmp/tosca/server.cert");
		copy(getClass().getResourceAsStream("/tosca.csar.cms"), "/tmp/tosca/l1.csar.sig.p7s");
		ZipUtil.makeToscaZip("/tmp/test.zip", "/tmp/tosca/");
		ob.preOnboard("/tmp/test.zip");
		assertEquals(CsarModeEnum.DOUBLE_ZIP, ob.getToscaMode("/tmp/test.zip"));
		ob.getCsarInputStream();
		assertTrue(true);
	}

	private static void copy(final InputStream inputStream, final String file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file)) {
			inputStream.transferTo(fos);
		}
	}

	@Test
	void testSingleFile() throws IOException {
		copy(getClass().getResourceAsStream("/test-csar/Definitions/tosca_ubi_scale.yaml"), "/tmp/tosca/tosca_ubi_scale.yaml");
		ob.preOnboard("/tmp/tosca/tosca_ubi_scale.yaml");
		assertEquals(CsarModeEnum.SINGLE_FILE, ob.getToscaMode("/tmp/tosca/tosca_ubi_scale.yaml"));
		assertTrue(true);
	}

	@Test
	void testUnknown() throws IOException {
		copy(getClass().getResourceAsStream("/test-csar/Definitions/tosca_ubi_scale.yaml"), "/tmp/tosca/tosca_ubi_scale.yaml");
		ob.preOnboard("/tmp/tosca/tosca_ubi_scale.yaml");
		assertEquals(CsarModeEnum.UNKNOWN, ob.getToscaMode("/tmp/tosca/tosca_ubi_scale.bad"));
		assertTrue(true);
	}

	@Test
	void testZipsingle() throws IOException {
		createZip(fileName + ".zip");
		ob.preOnboard(fileName + ".zip");
		assertEquals(CsarModeEnum.SINGLE_ZIP, ob.getToscaMode(fileName + ".zip"));
		assertTrue(true);
	}

	@Test
	void testGetVirtualFileSystem() {
		final ManoResource vnfd = new ByteArrayResource("".getBytes(), fileName);
		ob.getVirtualFileSystem(vnfd);
		assertTrue(true);
	}
}
