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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageVnfPackageJpa;

@Service
public class NsdPackageVnfPackageService {

	private final NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa;

	public NsdPackageVnfPackageService(final NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa) {
		this.nsdPackageVnfPackageJpa = nsdPackageVnfPackageJpa;
	}

	public Set<NsdPackageVnfPackage> findByNsdPackage(final NsdPackage nsdPackage) {
		return nsdPackageVnfPackageJpa.findByNsdPackage(nsdPackage);
	}

	public Set<NsdPackageVnfPackage> findByVnfdId(final String vnfdId) {
		return nsdPackageVnfPackageJpa.findByVnfdId(vnfdId);
	}
}
