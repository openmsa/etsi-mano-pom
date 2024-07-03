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
package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class CalicoCni implements CniInstaller {

	@Override
	public String getType() {
		return "calico";
	}

	@Override
	public List<String> getK8sDocuments(final String version) {
		final String resourcePath = "/cni/calico/v%S".formatted(version);
		try (final InputStream in = this.getClass().getResourceAsStream(resourcePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			in.transferTo(baos);
			return List.of(baos.toString());
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}
