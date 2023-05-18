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
package com.ubiqube.etsi.mano.alarm.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.support.converter.MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.ConnectionFactory;

@ExtendWith(MockitoExtension.class)
class JmsConfigurationTest {
	@Mock
	private ConnectionFactory conn;
	@Mock
	private MessageConverter message;
	@Mock
	private DefaultJmsListenerContainerFactoryConfigurer configurer;

	@Test
	void test() {
		final JmsConfiguration srv = new JmsConfiguration();
		final ObjectMapper mapper = new ObjectMapper();
		srv.jacksonJmsMessageConverter(mapper);
		assertTrue(true);
	}

	@Test
	void test2() {
		final JmsConfiguration srv = new JmsConfiguration();
		final ObjectMapper mapper = new ObjectMapper();
		srv.queueJmsTemplate(conn, message);
		assertTrue(true);
	}

	@Test
	void test3() {
		final JmsConfiguration srv = new JmsConfiguration();
		final ObjectMapper mapper = new ObjectMapper();
		srv.serialzeDataFactory(conn, configurer);
		assertTrue(true);
	}
}
