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
package com.ubiqube.parser.tosca.objects.tosca.nodes;

import com.ubiqube.parser.tosca.objects.tosca.capabilities.Compute;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.Endpoint;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Admin;

public class WebServer extends SoftwareComponent {
	/**
	 * Caps.
	 */
	private Endpoint dataEndpoint;

	/**
	 * Caps.
	 */
	private Admin adminEndpoint;

	/**
	 * Caps.
	 */
	private Compute host;

	public Endpoint getDataEndpoint() {
		return this.dataEndpoint;
	}

	public void setDataEndpoint(final Endpoint dataEndpoint) {
		this.dataEndpoint = dataEndpoint;
	}

	public Admin getAdminEndpoint() {
		return this.adminEndpoint;
	}

	public void setAdminEndpoint(final Admin adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
	}

	public Compute getHost() {
		return this.host;
	}

	public void setHost(final Compute host) {
		this.host = host;
	}
}
