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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.vim;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.service.rest.FluxRest;

@Service
public class CirConnectionManager {

	private final ConnectionInformationJpa cirConnectionInformationJpa;

	public CirConnectionManager(final ConnectionInformationJpa cirConnectionInformationJpa) {
		this.cirConnectionInformationJpa = cirConnectionInformationJpa;
	}

	public ConnectionInformation register(final ConnectionInformation vci) {
		checkConnectivity(vci);
		return save(vci);
	}

	public void deleteVim(final UUID id) {
		cirConnectionInformationJpa.deleteById(id);
	}

	public ConnectionInformation findVimById(final UUID id) {
		return cirConnectionInformationJpa.findById(id).orElseThrow();
	}

	public ConnectionInformation save(final ConnectionInformation vim) {
		return cirConnectionInformationJpa.save(vim);
	}

	public Iterable<ConnectionInformation> findAll() {
		return cirConnectionInformationJpa.findAll();
	}

	@SuppressWarnings("static-method")
	public boolean checkConnectivity(final ConnectionInformation vci) {
		final FluxRest fr = new FluxRest(vci.toServers());
		final URI url = vci.getUrl();
		fr.get(url, String.class, null);
		return true;
	}

	public void checkConnectivity(final UUID id) {
		final ConnectionInformation vci = findVimById(id);
		checkConnectivity(vci);
	}

}
