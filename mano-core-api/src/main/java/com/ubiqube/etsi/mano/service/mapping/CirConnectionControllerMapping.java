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
package com.ubiqube.etsi.mano.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.dto.ConnectionInformationDto;
import com.ubiqube.etsi.mano.docker.RegistryInformations;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CirConnectionControllerMapping extends StringToUriMapping {

	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "version", ignore = true)
	ConnectionInformation map(ConnectionInformationDto o);

	@Mapping(target = "password", source = "authentification.authParamBasic.password")
	@Mapping(target = "server", source = "url")
	@Mapping(target = "username", source = "authentification.authParamBasic.userName")
	RegistryInformations map(ConnectionInformation cirConn);
}
