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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.vim.k8s.K8s;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CapiServerMapping {
	@Mapping(target = "apiUrl", source = "url")
	@Mapping(target = "caData", source = "certificateAuthorityData")
	@Mapping(target = "clientCrt", source = "clientCertificateData")
	@Mapping(target = "clientKey", source = "clientKeyData")
	@Mapping(target = "namespace", constant = "default")
	K8s map(CapiServer capiSrv);

	@Mapping(target = "certificateAuthorityData", source = "caData")
	@Mapping(target = "clientCertificateData", source = "clientCrt")
	@Mapping(target = "clientKeyData", source = "clientKey")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", ignore = true)
	@Mapping(target = "url", source = "apiUrl")
	CapiServer map(K8s srv);

}
