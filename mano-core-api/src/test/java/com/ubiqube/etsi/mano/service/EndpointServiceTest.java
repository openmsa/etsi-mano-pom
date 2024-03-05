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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.ubiqube.etsi.mano.service.objects.ControllerA;
import com.ubiqube.etsi.mano.service.objects.ControllerB;
import com.ubiqube.etsi.mano.service.objects.ControllerC;

@ExtendWith(MockitoExtension.class)
class EndpointServiceTest {

	@Mock
	private ApplicationContext context;

	@Test
	void testExtractVersion001() throws Exception {
		final EndpointService es = new EndpointService(context);
		final String[] values = {
				ControllerA.class.getCanonicalName()
		};
		when(context.getBeanNamesForAnnotation(Controller.class)).thenReturn(values);
		when(context.getBean(ControllerA.class.getCanonicalName())).thenReturn(new ControllerA());
		es.extractVersions();
		assertTrue(true);
	}

	@Test
	void testExtractVersion002() throws Exception {
		final EndpointService es = new EndpointService(context);
		final String[] values = {
				ControllerB.class.getCanonicalName(),
				ControllerC.class.getCanonicalName()
		};
		when(context.getBeanNamesForAnnotation(Controller.class)).thenReturn(values);
		when(context.getBean(ControllerB.class.getCanonicalName())).thenReturn(new ControllerB());
		when(context.getBean(ControllerC.class.getCanonicalName())).thenReturn(new ControllerC());
		es.extractVersions();
		assertNotNull(es.getEndpoints());
	}
}
