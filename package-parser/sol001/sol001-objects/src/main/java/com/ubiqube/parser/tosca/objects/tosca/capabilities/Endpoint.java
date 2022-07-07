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
package com.ubiqube.parser.tosca.objects.tosca.capabilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.network.PortSpec;

public class Endpoint extends Root {
	@Valid
	@JsonProperty("port_name")
	private String portName;

	@Valid
	@JsonProperty("protocol")
	private String protocol = "tcp";

	@JsonProperty("port")
	@Min(1)
	@Max(65535)
	private Integer port;

	@Valid
	@JsonProperty("initiator")
	private String initiator = "source";

	@Valid
	@JsonProperty("network_name")
	private String networkName = "PRIVATE";

	@Valid
	@JsonProperty("secure")
	private Boolean secure = false;

	@Valid
	@JsonProperty("ports")
	@Size(
			min = 1
	)
	private Map<String, PortSpec> ports;

	@Valid
	@JsonProperty("url_path")
	private String urlPath;

	public String getPortName() {
		return this.portName;
	}

	public void setPortName(final String portName) {
		this.portName = portName;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public String getInitiator() {
		return this.initiator;
	}

	public void setInitiator(final String initiator) {
		this.initiator = initiator;
	}

	public String getNetworkName() {
		return this.networkName;
	}

	public void setNetworkName(final String networkName) {
		this.networkName = networkName;
	}

	public Boolean getSecure() {
		return this.secure;
	}

	public void setSecure(final Boolean secure) {
		this.secure = secure;
	}

	public Map<String, PortSpec> getPorts() {
		return this.ports;
	}

	public void setPorts(final Map<String, PortSpec> ports) {
		this.ports = ports;
	}

	public String getUrlPath() {
		return this.urlPath;
	}

	public void setUrlPath(final String urlPath) {
		this.urlPath = urlPath;
	}
}
