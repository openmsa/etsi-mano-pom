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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.dao.mano.dto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.dao.mano.common.FailureDetails;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionInformationDto {
	private UUID id;

	private String name;

	private ConnectionType connType;

	private AuthentificationInformations authentification;

	private Map<String, String> extra;

	private String url;

	private boolean ignoreSsl;

	private PlanStatusType serverStatus;

	private FailureDetails failureDetails;

	private Set<String> capabilities;

	private GeoPoint geoloc;

}
