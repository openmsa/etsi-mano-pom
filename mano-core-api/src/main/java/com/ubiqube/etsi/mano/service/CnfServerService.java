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
package com.ubiqube.etsi.mano.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.jpa.CnfServerJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class CnfServerService {
	private final CnfServerJpa cnfServerJpa;

	public CnfServerService(final CnfServerJpa cnfServerJpa) {
		this.cnfServerJpa = cnfServerJpa;
	}

	public Iterable<CnfServer> findAll() {
		return cnfServerJpa.findAll();
	}

	public CnfServer save(final CnfServer in) {
		return cnfServerJpa.save(in);
	}

	public Optional<CnfServer> findById(final UUID id) {
		return cnfServerJpa.findById(id);
	}

}
