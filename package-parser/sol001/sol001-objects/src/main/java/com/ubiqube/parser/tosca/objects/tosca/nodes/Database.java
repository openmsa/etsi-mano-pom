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
package com.ubiqube.parser.tosca.objects.tosca.nodes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Relationship;

public class Database extends Root {
	/**
	 * the optional password for the DB user account
	 */
	@Valid
	@JsonProperty("password")
	private String password;

	/**
	 * the port the underlying database service will listen to for data
	 */
	@Valid
	@JsonProperty("port")
	private Integer port;

	/**
	 * the logical name of the database
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * the optional user account name for DB administration
	 */
	@Valid
	@JsonProperty("user")
	private String user;

	/**
	 * Caps.
	 */
	private com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Database databaseEndpoint;

	@Node("tosca.nodes.DBMS")
	@Capability("tosca.capabilities.Compute")
	@Relationship("tosca.relationships.HostedOn")
	@JsonProperty("host")
	private String hostReq;

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Database getDatabaseEndpoint() {
		return this.databaseEndpoint;
	}

	public void setDatabaseEndpoint(final com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Database databaseEndpoint) {
		this.databaseEndpoint = databaseEndpoint;
	}

	public String getHostReq() {
		return this.hostReq;
	}

	public void setHostReq(final String hostReq) {
		this.hostReq = hostReq;
	}
}
