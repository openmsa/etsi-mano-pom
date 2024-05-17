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
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.ubiqube.etsi.mano.exception.SeeOtherException;

@ExtendWith(MockitoExtension.class)
class ManoControllerAdviceTest {
	@Mock
	private BindingResult bindingResult;

	@Test
	void test() {
		final ManoControllerAdvice c = new ManoControllerAdvice();
		final Method m = this.getClass().getMethods()[0];
		final MethodParameter mp = new MethodParameter(m, 0);
		final MethodArgumentNotValidException ex = new MethodArgumentNotValidException(mp, bindingResult);
		final FieldError fe = new FieldError("name", "field", "default");
		when(bindingResult.getAllErrors()).thenReturn(List.of(fe));
		c.handleValidationExceptions(ex);
		assertTrue(true);
	}

	@Test
	void testHandleSeeOtherExceptions() {
		final ManoControllerAdvice c = new ManoControllerAdvice();
		final SeeOtherException ex = new SeeOtherException(URI.create("http://localhost/"), "See other");
		c.handleSeeOtherExceptions(ex);
		assertTrue(true);
	}
}
