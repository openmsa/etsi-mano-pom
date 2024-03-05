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
package com.ubiqube.etsi.mano.service.mon.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.postgresql.util.PGobject;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import jakarta.annotation.Nullable;

public class CustomHint implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(final RuntimeHints hints, final @Nullable ClassLoader classLoader) {
		hints.reflection().registerType(ActiveMQConnectionFactory.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
		// Postgresql
		hints.reflection().registerType(PGobject.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS);
	}

}
