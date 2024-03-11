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
package com.ubiqube.etsi.mano.service.rest.vnfpkg;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Consumer;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.ManoClient;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoOnboarded {
	private final ManoClient client;

	public ManoOnboarded(final ManoClient client, final UUID vnfdId) {
		this.client = client;
		client.setObjectId(vnfdId);
		client.setQueryType(ApiVersionType.SOL003_VNFPKGM);
		client.setFragment("/onboarded_vnf_packages/{id}");
	}

	public VnfPackage find() {
		return client.createQuery()
				.setWireOutClass(HttpGateway::getVnfPackageClass)
				.setOutClass(VnfPackage.class)
				.getSingle();
	}

	public void vnfd(final Consumer<InputStream> tgt) {
		client.createQuery()
				.download(Paths.get("vnfd"), tgt);
	}

	public void packageContent(final Consumer<InputStream> tgt) {
		client.createQuery()
				.download(Paths.get("package_content"), tgt);
	}

	public void manifest(final Consumer<InputStream> tgt) {
		client.createQuery()
				.download(Paths.get("manifest"), tgt);
	}

	public void artifacts(final Consumer<InputStream> tgt, final String artifacts) {
		client.createQuery()
				.download(Paths.get("artifacts", artifacts), tgt);
	}
}
