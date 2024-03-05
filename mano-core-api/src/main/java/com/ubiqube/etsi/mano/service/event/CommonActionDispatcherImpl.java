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
package com.ubiqube.etsi.mano.service.event;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class CommonActionDispatcherImpl implements CommonActionDispatcher {
	private final CommonActionController controller;

	public CommonActionDispatcherImpl(final CommonActionController controller) {
		this.controller = controller;
	}

	@Override
	public void dispatch(final ActionType actionType, final UUID objectId, final Map<String, Object> parameters) {
		switch (actionType) {
		case REGISTER_SERVER -> controller.registerServer(objectId, parameters);
		default -> throw new IllegalArgumentException("Unexpected value: " + actionType);
		}

	}

}
