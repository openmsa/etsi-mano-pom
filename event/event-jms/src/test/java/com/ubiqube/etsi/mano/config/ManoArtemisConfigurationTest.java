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
package com.ubiqube.etsi.mano.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.service.event.jms.config.ManoArtemisConfiguration;

import jakarta.jms.ConnectionFactory;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class ManoArtemisConfigurationTest {

	@Mock
	private ConnectionFactory conn;
	@Mock
	private MessageConverter messageConv;

	@Test
	void tesMessageConverter() throws Exception {
		final ManoArtemisConfiguration mac = new ManoArtemisConfiguration();
		final MessageConverter res = mac.jacksonJmsMessageConverter(new ObjectMapper());
		assertNotNull(res);
	}

	@Test
	void testName() throws Exception {
		final ManoArtemisConfiguration mac = new ManoArtemisConfiguration();
		final JmsListenerContainerFactory<DefaultMessageListenerContainer> res = mac.jmsListenerContainerFactory(conn, messageConv);
		assertNotNull(res);
	}
}
