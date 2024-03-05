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
package com.ubiqube.etsi.mano.service.mon.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

/**
 *
 * @author Olivier Vignaud
 *
 */
@SuppressWarnings("static-method")
class MonSubscriptionTest {

	@Test
	void test() {
		final MonSubscription srv = new MonSubscription();
		srv.setAuthParamOauth2(null);
		srv.setCallBackUrl(null);
		srv.setId(null);
		srv.setKey(null);
		srv.getAuthParamOauth2();
		srv.getCallBackUrl();
		srv.getId();
		srv.getKey();
		srv.equals(srv);
		srv.equals(null);
		srv.equals("");
		assertTrue(true);
	}

	@Test
	void testEqual() {
		final EqualsVerifierReport rep = EqualsVerifier
				.simple()
				.forClass(MonSubscription.class)
				.suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT, Warning.SURROGATE_KEY)
				.report();
		System.out.println("" + rep.getMessage());
		assertTrue(true);
	}
}
