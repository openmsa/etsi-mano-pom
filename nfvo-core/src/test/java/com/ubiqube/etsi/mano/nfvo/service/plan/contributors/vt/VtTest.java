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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsSapTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsSfcTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdExtractorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPortPairTask;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgPostTask;
import com.ubiqube.etsi.mano.tf.entities.NetworkPolicyTask;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;
import com.ubiqube.etsi.mano.tf.entities.PtLinkTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceInstanceTask;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;

@SuppressWarnings("static-method")
class VtTest {
	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(() -> new NetworkPolicyVt(new NetworkPolicyTask()))),
				Arguments.of(args.of(() -> new NsCreateVt(new NsdTask()))),
				Arguments.of(args.of(() -> new NsExtratorVt(new NsdExtractorTask()))),
				Arguments.of(args.of(() -> new NsInstantiateVt(new NsdInstantiateTask()))),
				Arguments.of(args.of(() -> new NsSapVt(new NsSapTask()))),
				Arguments.of(args.of(() -> new NsVirtualLinkVt(new NsVirtualLinkTask()))),
				Arguments.of(args.of(() -> new NsVnfCreateVt(new NsVnfTask()))),
				Arguments.of(args.of(() -> new NsVnfExtractorVt(new NsVnfExtractorTask()))),
				Arguments.of(args.of(() -> new NsVnffgPortPairVt(new VnffgPortPairTask()))),
				Arguments.of(args.of(() -> new NsVnffgPostVt(new VnffgPostTask()))),
				Arguments.of(args.of(() -> new NsVnffgPreVt(new NsSfcTask()))),
				Arguments.of(args.of(() -> new NsVnfInstantiateVt(new NsVnfInstantiateTask()))),
				Arguments.of(args.of(() -> new PortTupleVt(new PortTupleTask()))),
				Arguments.of(args.of(() -> new PtLinkVt(new PtLinkTask()))),
				Arguments.of(args.of(() -> new ServiceInstanceVt(new ServiceInstanceTask()))),
				Arguments.of(args.of(() -> new ServiceTemplateVt(new ServiceTemplateTask()))),
				Arguments.of(args.of(() -> new VnffgLoadbalancerVt(new VnffgLoadbalancerTask()))));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testVt(final args arg) {
		final NsVtBase<?> vt = arg.func().get();
		final UUID id = UUID.randomUUID();
		vt.setAlias("alias");
		assertEquals("alias", vt.getAlias());
		vt.setDelete(true);
		assertTrue(vt.isDeleteTask());
		vt.setName("name");
		assertEquals("name", vt.getName());
		vt.setRank(123);
		assertEquals(123, vt.getRank());
		vt.setRemovedLiveInstanceId(id);
		vt.setSystemBuilder(null);
		vt.setVimConnectionId("vimConn");
		assertEquals("vimConn", vt.getVimConnectionId());
		vt.setVimResourceId("res");
		assertEquals("res", vt.getVimResourceId());
		assertNotNull(vt.getType());
		assertNotNull(vt.toString());
	}
}

record args(Supplier<NsVtBase> func) {
	public static args of(final Supplier<NsVtBase> f) {
		return new args(f);
	}
}
