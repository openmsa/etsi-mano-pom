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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the additional service data of the VirtualCp used to expose
 * properties of the VirtualCp to NFV-MANO.
 */
public class AdditionalServiceData extends Root {
	/**
	 * Service matching information exposed by the VirtualCp.
	 */
	@Valid
	@JsonProperty("serviceData")
	private String serviceData;

	/**
	 * Service port numbers exposed by the VirtualCp.
	 */
	@Valid
	@NotNull
	@JsonProperty("portData")
	private List<ServicePortData> portData;

	public String getServiceData() {
		return this.serviceData;
	}

	public void setServiceData(final String serviceData) {
		this.serviceData = serviceData;
	}

	@NotNull
	public List<ServicePortData> getPortData() {
		return this.portData;
	}

	public void setPortData(@NotNull final List<ServicePortData> portData) {
		this.portData = portData;
	}
}
