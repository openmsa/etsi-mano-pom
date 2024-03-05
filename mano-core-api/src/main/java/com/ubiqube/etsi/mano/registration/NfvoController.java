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
package com.ubiqube.etsi.mano.registration;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.registration.model.RegState;
import com.ubiqube.etsi.mano.registration.model.RegistrationState;
import com.ubiqube.etsi.mano.registration.repository.ResgistrationSateRepository;

/**
 *
 * @author olivier
 *
 */
public class NfvoController {
	ObjectMapper objectMapper;

	ResgistrationSateRepository regRepository;

	public void onEvent(final String payload) {
		final PayLoad p = deserailize(payload);
		if (p.getCommand() == Command.CLUSTER_JOIN) {
			onClusterJoin(p.getValue());
		}
	}

	private void onClusterJoin(final String value) {
		final Optional<RegistrationState> res = regRepository.findByServerId(value);
		if (res.isPresent()) {
			return;
		}
		final RegistrationState reg = new RegistrationState();
		reg.setServerId(value);
		reg.setState(RegState.DISCOVERED);
		regRepository.save(reg);
	}

	private PayLoad deserailize(final String payload) {
		try {
			return objectMapper.readValue(payload, PayLoad.class);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
	}
}
