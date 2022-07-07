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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the service identifying port properties exposed by the VirtualCp
 */
public class ServicePortData extends Root {
	/**
	 * The L4 protocol for this port exposed by the VirtualCp.
	 */
	@Valid
	@NotNull
	@JsonProperty("protocol")
	private String protocol;

	/**
	 * Specifies whether the port attribute value is allowed to be configurable.
	 */
	@Valid
	@NotNull
	@JsonProperty("portConfigurable")
	private Boolean portConfigurable;

	/**
	 * The L4 port number exposed by the VirtualCp.
	 */
	@Valid
	@NotNull
	@JsonProperty("port")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer port;

	/**
	 * The name of the port exposed by the VirtualCp.
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	@NotNull
	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(@NotNull final String protocol) {
		this.protocol = protocol;
	}

	@NotNull
	public Boolean getPortConfigurable() {
		return this.portConfigurable;
	}

	public void setPortConfigurable(@NotNull final Boolean portConfigurable) {
		this.portConfigurable = portConfigurable;
	}

	@NotNull
	public Integer getPort() {
		return this.port;
	}

	public void setPort(@NotNull final Integer port) {
		this.port = port;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}
}
