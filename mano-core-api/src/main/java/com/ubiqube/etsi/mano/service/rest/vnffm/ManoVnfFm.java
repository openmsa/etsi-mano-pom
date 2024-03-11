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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.rest.vnffm;

import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;
import com.ubiqube.etsi.mano.service.rest.ManoClient;

public class ManoVnfFm {

	private final ManoClient manoClient;

	public ManoVnfFm(final ManoClient manoClient) {
		this.manoClient = manoClient;
	}

	public List<Alarm> find() {
		return List.of();
	}

	public ManoVnfFmId id(final UUID id) {
		return new ManoVnfFmId(manoClient, id);
	}

	public ManoVnfFmSubscription subscription() {
		return new ManoVnfFmSubscription(manoClient);
	}
}
