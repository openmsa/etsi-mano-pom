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

@ExtendWith(MockitoExtension.class)
class ManoOnboardedTest {
	@Mock
	private ManoClient manoClient;
	@Mock
	private ManoQueryBuilder manoQueryBuilder;

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(srv -> srv.vnfd(null))),
				Arguments.of(args.of(srv -> srv.manifest(null))),
				Arguments.of(args.of(srv -> srv.artifacts(null, "aa"))),
				Arguments.of(args.of(srv -> srv.packageContent(null))));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testName(final args arg) throws Exception {
		final ManoOnboarded srv = new ManoOnboarded(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		arg.func().accept(srv);
		assertTrue(true);
	}

	@Test
	void testFind() {
		final ManoOnboarded srv = new ManoOnboarded(manoClient, UUID.randomUUID());
		when(manoClient.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		srv.find();
		assertTrue(true);
	}

	record args(Consumer<ManoOnboarded> func) {
		public static args of(final Consumer<ManoOnboarded> f) {
			return new args(f);
		}
	}
}
