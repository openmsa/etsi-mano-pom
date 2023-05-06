/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.em.client.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ubiqube.etsi.mano.vnfm.property.EmProperty;

import io.micrometer.observation.ObservationRegistry;

/**
 *
 * @author Olivier Vignaud
 *
 */
@ExtendWith(MockitoExtension.class)
class ClientConfigObservabilityBeanTest {
	@Mock
	private ConfigurableApplicationContext confAppContext;
	@Mock
	private ObservationRegistry observability;
	@Mock
	private HttpServiceProxyFactory proxy;

	@Test
	void test() {
		final EmProperty props = new EmProperty();
		props.setVersion("1.2.3");
		when(confAppContext.getBean(ObservationRegistry.class)).thenReturn(observability);
		final ClientConfigObservabilityBean srv = new ClientConfigObservabilityBean(confAppContext, props);
		srv.createProxyFactory();
		assertTrue(true);
	}

	@Test
	void testVnfInd() {
		final TestAbstractClientConfigBean srv = new TestAbstractClientConfigBean(proxy);
		srv.vnfIndRemoteService();
		srv.vnfIndSubscriptionRemoteService();
		assertTrue(true);
	}
}
