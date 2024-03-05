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
package com.ubiqube.etsi.mano.nfvo.service.system;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.NsdPackageJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.TestNsVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVirtualLinkVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfExtractorVt;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfInstantiateVt;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;

@ExtendWith(MockitoExtension.class)
class NetworkPolicySystemTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private OrchestrationServiceV3 orchestrationService;
	@Mock
	private SystemConnections sysConn;

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of("NetworkPolicySystem", () -> new NetworkPolicySystem(Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("NsSapSystem", () -> new NsSapSystem(Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("NsVnfCreateSystem", () -> new NsVnfCreateSystem(null, Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("PortTupleSystem", () -> new PortTupleSystem(Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("PtLinkSystem", () -> new PtLinkSystem(Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("ServiceInstanceSystem", () -> new ServiceInstanceSystem(Mockito.mock(VimManager.class)))),
				Arguments.of(args.of("ServiceTemplateSystem", () -> new ServiceTemplateSystem(Mockito.mock(VimManager.class))))
		// Arguments.of(args.of("NsVnfContextExtractorSystem", () -> new
		// NsVnfContextExtractorSystem(Mockito.mock(VnfmInterface.class),
		// Mockito.mock(VimManager.class), null)))
		);
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void test(final args arg) {
		final AbstractVimSystemV3 sys = arg.func().get();
		final VirtualTaskV3 task = new TestNsVt(null);
		sys.getImplementation(orchestrationService, task, sysConn);
		assertNotNull(sys.getVimType());
		assertNotNull(sys.getType());
		assertTrue(true);
	}

	@Test
	void testNsVirtualLinkSystem() {
		final NsVirtualLinkSystem sys = new NsVirtualLinkSystem(null, vimManager);
		final VimConnectionInformation vimConn = null;
		final NsVirtualLinkTask nt = new NsVirtualLinkTask();
		final VirtualTaskV3<NsVirtualLinkTask> task = new NsVirtualLinkVt(nt);
		sys.getImplementation(orchestrationService, task, vimConn);
		assertNotNull(sys.getVimType());
		assertNotNull(sys.getType());
		assertTrue(true);
	}

	@Test
	void testNsVnfInstantiateSystem() {
		final VnfmInterface vnfm = Mockito.mock(VnfmInterface.class);
		final NsVnfInstantiateSystem sys = new NsVnfInstantiateSystem(vnfm, vimManager);
		final VimConnectionInformation vimConn = null;
		final NsVnfInstantiateTask nt = new NsVnfInstantiateTask();
		final VirtualTaskV3<NsVnfInstantiateTask> task = new NsVnfInstantiateVt(nt);
		sys.getImplementation(orchestrationService, task, vimConn);
		assertNotNull(sys.getVimType());
		assertNotNull(sys.getType());
		assertTrue(true);
	}

	@Test
	void testNsVnfContextExtractorSystem() {
		final VnfmInterface vnfm = Mockito.mock(VnfmInterface.class);
		final NsdPackageJpa nsdJpa = Mockito.mock(NsdPackageJpa.class);
		final NsVnfContextExtractorSystem sys = new NsVnfContextExtractorSystem(vnfm, vimManager, nsdJpa);
		final NsVnfExtractorTask nt = new NsVnfExtractorTask();
		nt.setNsdId(UUID.randomUUID().toString());
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(nt);
		final VimConnectionInformation vimConn = null;
		final NsdPackage nsPkg = new NsdPackage();
		when(nsdJpa.findById(any())).thenReturn(Optional.of(nsPkg));
		sys.getImplementation(orchestrationService, task, vimConn);
		assertNotNull(sys.getVimType());
		assertNotNull(sys.getType());
		assertTrue(true);
	}

	@Test
	void testNsVnfContextExtractorSystem_NotFound() {
		final VnfmInterface vnfm = Mockito.mock(VnfmInterface.class);
		final NsdPackageJpa nsdJpa = Mockito.mock(NsdPackageJpa.class);
		final NsVnfContextExtractorSystem sys = new NsVnfContextExtractorSystem(vnfm, vimManager, nsdJpa);
		final NsVnfExtractorTask nt = new NsVnfExtractorTask();
		nt.setNsdId(UUID.randomUUID().toString());
		final VirtualTaskV3<NsVnfExtractorTask> task = new NsVnfExtractorVt(nt);
		final VimConnectionInformation vimConn = null;
		assertThrows(GenericException.class, () -> sys.getImplementation(orchestrationService, task, vimConn));
		assertNotNull(sys.getVimType());
		assertNotNull(sys.getType());
		assertTrue(true);
	}

	record args(String name, Supplier<AbstractVimSystemV3> func) {
		public static args of(final String name, final Supplier<AbstractVimSystemV3> f) {
			return new args(name, f);
		}
	}
}
