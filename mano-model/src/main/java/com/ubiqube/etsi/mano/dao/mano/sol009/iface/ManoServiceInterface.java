/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.dao.mano.sol009.iface;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.sol009.peers.ClientInterfaceSecurityInfo;

import lombok.Data;

@Data
@Entity
public class ManoServiceInterface {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;

	private String type;

	private String standardVersion;

	private String providerSpecificApiVersion;

	private String apiVersion;

	@OneToOne
	private ApiEndpoint apiEndpoint;

	private Integer maxConcurrentIntOpNumber;

	@OneToMany
	private List<SupportedOperations> supportedOperations;

	private InterfaceState interfaceState;

	@OneToOne
	private ClientInterfaceSecurityInfo securityInfo;

	@ElementCollection
	private Map<String, String> metadata = null;

}
