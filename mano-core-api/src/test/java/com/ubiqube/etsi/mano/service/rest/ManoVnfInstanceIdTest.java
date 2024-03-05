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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;

@ExtendWith(MockitoExtension.class)
class ManoVnfInstanceIdTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoQueryBuilder manoQueryBuilder;

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(srv -> {
					final VnfInstantiate req = new VnfInstantiate();
					srv.instantiate(req);
				})),
				Arguments.of(args.of(srv -> {
					final ChangeExtVnfConnRequest req = new ChangeExtVnfConnRequest();
					srv.changeExtConn(req);
				})),
				Arguments.of(args.of(srv -> {
					final ChangeVnfFlavourData req = new ChangeVnfFlavourData();
					srv.changeFlavour(req);
				})),
				Arguments.of(args.of(srv -> {
					final VnfScaleToLevelRequest req = new VnfScaleToLevelRequest();
					srv.scaleToLevel(req);
				})),
				Arguments.of(args.of(srv -> {
					final VnfOperateRequest req = new VnfOperateRequest();
					srv.operate(req);
				})));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testGenericAction(final args func) throws Exception {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		final ChangeExtVnfConnRequest req = new ChangeExtVnfConnRequest();
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireInClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		func.func().accept(srv);
		assertTrue(true);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> provider2Class() {
		return Stream.of(
				Arguments.of(args.of(srv -> {
					final VnfScaleRequest req = new VnfScaleRequest();
					srv.scale(req);
				})),
				Arguments.of(args.of(srv -> {
					final VnfHealRequest req = new VnfHealRequest();
					srv.heal(req);
				})));
	}

	@ParameterizedTest
	@MethodSource("provider2Class")
	void testGenericAction2(final args func) throws Exception {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		final ChangeExtVnfConnRequest req = new ChangeExtVnfConnRequest();
		when(manoClient.createQuery(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireInClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		func.func().accept(srv);
		assertTrue(true);
	}

	@SuppressWarnings("null")
	private static Stream<Arguments> provider3Class() {
		return Stream.of(
				Arguments.of(args.of(srv -> srv.find())),
				Arguments.of(args.of(srv -> srv.patch(Map.of()))));
	}

	@ParameterizedTest
	@MethodSource("provider3Class")
	void testFind(final args arg) {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		arg.func().accept(srv);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		srv.delete();
		assertTrue(true);
	}

	@Test
	void testTerminate() {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		srv.terminate(CancelModeTypeEnum.GRACEFUL, 5);
		assertTrue(true);
	}

	@Test
	void testTerminateFailNoCrash() {
		final ManoVnfInstanceId srv = new ManoVnfInstanceId(manoClient, UUID.randomUUID());
		when(manoClient.createQuery(any())).thenReturn(manoQueryBuilder);
		srv.terminate(CancelModeTypeEnum.GRACEFUL, 5);
		assertTrue(true);
	}
}

record args(Consumer<ManoVnfInstanceId> func) {
	public static args of(final Consumer<ManoVnfInstanceId> f) {
		return new args(f);
	}
}
