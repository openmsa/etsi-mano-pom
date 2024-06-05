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
package com.ubiqube.etsi.mano.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;

@Service
public class CapiServerService {
	private final CapiServerJpa capiServerJpa;

	public CapiServerService(final CapiServerJpa capiServerJpa) {
		this.capiServerJpa = capiServerJpa;
	}

	public Iterable<CapiServer> findAll() {
		return capiServerJpa.findAll();
	}

	public CapiServer save(final CapiServer srv) {
		return capiServerJpa.save(srv);
	}

	public void deleteById(final UUID id) {
		capiServerJpa.deleteById(id);
	}

}
