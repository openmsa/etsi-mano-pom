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
package com.ubiqube.etsi.mano.mon.core.service;

import java.util.Objects;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Message;

public class ExpirityJmsTemplate extends JmsTemplate {

	public ExpirityJmsTemplate() {
		setExplicitQosEnabled(true);
	}

	public ExpirityJmsTemplate(final ConnectionFactory connectionFactory) {
		super(connectionFactory);
		setExplicitQosEnabled(true);
	}

	public void convertAndSend(final String destinationName, final Object message, final long timeToLive) throws JmsException {
		final MessageConverter messageConverter = Objects.requireNonNull(getMessageConverter());
		send(destinationName, session -> {
			final Message msg = messageConverter.toMessage(message, session);
			if (isExplicitQosEnabled() && (timeToLive > 0)) {
				msg.setJMSExpiration(timeToLive);
			}
			return msg;
		});
	}

}
