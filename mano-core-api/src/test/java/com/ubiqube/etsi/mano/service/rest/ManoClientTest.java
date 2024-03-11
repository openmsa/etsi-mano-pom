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

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ma.glasnost.orika.MapperFacade;

/**
 * Basic test as there is nothing to test.
 *
 * @author olivier
 *
 */
class ManoClientTest {

	private MapperFacade mapper;

	@SuppressWarnings("null")
	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of(ManoClient::admin)),
				Arguments.of(args.of(ManoClient::getMapper)),
				Arguments.of(args.of(ManoClient::getObjectId)),
				Arguments.of(args.of(ManoClient::getQueryType)),
				Arguments.of(args.of(ManoClient::getRequestObject)),
				Arguments.of(args.of(ManoClient::getServer)),
				Arguments.of(args.of(ManoClient::getSetFragment)),
				Arguments.of(args.of(ManoClient::grant)),
				Arguments.of(args.of(ManoClient::nsPackage)),
				Arguments.of(args.of(ManoClient::vnfInstance)),
				Arguments.of(args.of(ManoClient::vnfLcmOpOccs)),
				Arguments.of(args.of(srv -> srv.vnfLcmOpOccs(null))),
				Arguments.of(args.of(ManoClient::vnfPackage)),
				Arguments.of(args.of(ManoClient::vnfPm)),
				Arguments.of(args.of(ManoClient::vnfIndicator)),
				Arguments.of(args.of(srv -> srv.createQuery(x -> ""))),
				Arguments.of(args.of(ManoClient::createQuery)));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testName(final args arg) throws Exception {
		final ServerAdapter serverAdapter = new ServerAdapter(null, null, null);
		final ManoClient mc = new ManoClient(mapper, serverAdapter);
		arg.func().accept(mc);
		assertTrue(true);
	}

	record args(Consumer<ManoClient> func) {
		public static args of(final Consumer<ManoClient> f) {
			return new args(f);
		}
	}
}
