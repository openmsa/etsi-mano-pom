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

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class ManoErrorControllerTest {
	@Mock
	private ErrorAttributes errorAttr;
	@Mock
	private HttpServletRequest req;

	@Test
	void test() {
		final ManoErrorController c = new ManoErrorController(errorAttr);
		c.getError(req);
		assertTrue(true);
	}

	@Test
	void test2() {
		final ManoErrorController c = new ManoErrorController(errorAttr);
		when(req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(345);
		final Map<String, Object> map = Map.of("message", "value",
				"exception", MethodArgumentNotValidException.class.getName());
		when(errorAttr.getErrorAttributes(any(), any())).thenReturn(map);
		c.getError(req);
		assertTrue(true);
	}
}
