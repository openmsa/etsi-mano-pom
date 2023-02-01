/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.annotation.Nonnull;

public class GrantMessage {
	@Nonnull
	private UUID objectId;
	@Nonnull
	private Map<String, Object> parameters = new HashMap<>();

	@SuppressWarnings("null")
	public GrantMessage() {
		objectId = UUID.randomUUID();
	}

	public GrantMessage(@Nonnull final UUID objectId, @Nonnull final Map<String, Object> parameters) {
		this.objectId = objectId;
		this.parameters = parameters;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public void setObjectId(final @Nonnull UUID objectId) {
		this.objectId = objectId;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final @Nonnull Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
