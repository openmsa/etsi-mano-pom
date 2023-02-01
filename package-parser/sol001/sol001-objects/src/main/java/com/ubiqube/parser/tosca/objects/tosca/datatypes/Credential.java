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
package com.ubiqube.parser.tosca.objects.tosca.datatypes;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credential extends Root {
	@Valid
	@JsonProperty("protocol")
	private String protocol;

	@Valid
	@JsonProperty("keys")
	private Map<String, String> keys;

	@Valid
	@NotNull
	@JsonProperty("token_type")
	private String tokenType = "password";

	@Valid
	@JsonProperty("user")
	private String user;

	@Valid
	@NotNull
	@JsonProperty("token")
	private String token;

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public Map<String, String> getKeys() {
		return this.keys;
	}

	public void setKeys(final Map<String, String> keys) {
		this.keys = keys;
	}

	@NotNull
	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(@NotNull final String tokenType) {
		this.tokenType = tokenType;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	@NotNull
	public String getToken() {
		return this.token;
	}

	public void setToken(@NotNull final String token) {
		this.token = token;
	}
}
