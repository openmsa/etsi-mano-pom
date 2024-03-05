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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class NfvoApiVersionTest {
	@Mock
	private ApplicationContext appCtx;
	@Mock
	private HttpServletRequest req;

	@Test
	void testApiMajorVersionGet_NoRequestMapping() {
		baseMock();
		final NfvoApiVersion srv = new NfvoApiVersion(appCtx);
		srv.apiMajorVersionsGet("vrqan", req);
		assertTrue(true);
	}

	@Test
	void testApiMajorVersionGet() {
		final String[] arr = { TestRequestMapping.class.getName() };
		when(appCtx.getBeanNamesForAnnotation(Controller.class)).thenReturn(arr);
		final Object obj = new TestRequestMapping();
		when(appCtx.getBean((String) any())).thenReturn(obj);
		when(req.getRequestURI()).thenReturn("http://localhost/vrqan/v2/test");
		final NfvoApiVersion srv = new NfvoApiVersion(appCtx);
		srv.apiMajorVersionsGet("vrqan", req);
		assertTrue(true);
	}

	@Test
	void testApiMajorVersionsV1Get() {
		baseMock();
		final NfvoApiVersion srv = new NfvoApiVersion(appCtx);
		srv.apiMajorVersionsV1Get("vrqan", req, 2);
		assertTrue(true);
	}

	private void baseMock() {
		final String[] arr = { TestController.class.getName() };
		when(appCtx.getBeanNamesForAnnotation(Controller.class)).thenReturn(arr);
		final Object obj = new TestController();
		when(appCtx.getBean((String) any())).thenReturn(obj);
		when(req.getRequestURI()).thenReturn("http://localhost/vrqan/v2/test");
	}

}
