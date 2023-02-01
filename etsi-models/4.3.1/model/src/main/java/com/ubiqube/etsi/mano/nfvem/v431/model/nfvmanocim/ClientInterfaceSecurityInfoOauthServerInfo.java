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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OAuth 2.0 authorization server information and configuration.
 */
@Schema(description = "OAuth 2.0 authorization server information and configuration. ")
@Validated

public class ClientInterfaceSecurityInfoOauthServerInfo {
	@JsonProperty("dynamicDiscovery")
	private ClientInterfaceSecurityInfoOauthServerInfoDynamicDiscovery dynamicDiscovery = null;

	@JsonProperty("providedConfiguration")
	private ClientInterfaceSecurityInfoOauthServerInfoProvidedConfiguration providedConfiguration = null;

	@JsonProperty("tlsCipherSuites")
	@Valid
	private List<String> tlsCipherSuites = null;

	public ClientInterfaceSecurityInfoOauthServerInfo dynamicDiscovery(final ClientInterfaceSecurityInfoOauthServerInfoDynamicDiscovery dynamicDiscovery) {
		this.dynamicDiscovery = dynamicDiscovery;
		return this;
	}

	/**
	 * Get dynamicDiscovery
	 *
	 * @return dynamicDiscovery
	 **/
	@Schema(description = "")

	@Valid
	public ClientInterfaceSecurityInfoOauthServerInfoDynamicDiscovery getDynamicDiscovery() {
		return dynamicDiscovery;
	}

	public void setDynamicDiscovery(final ClientInterfaceSecurityInfoOauthServerInfoDynamicDiscovery dynamicDiscovery) {
		this.dynamicDiscovery = dynamicDiscovery;
	}

	public ClientInterfaceSecurityInfoOauthServerInfo providedConfiguration(final ClientInterfaceSecurityInfoOauthServerInfoProvidedConfiguration providedConfiguration) {
		this.providedConfiguration = providedConfiguration;
		return this;
	}

	/**
	 * Get providedConfiguration
	 *
	 * @return providedConfiguration
	 **/
	@Schema(description = "")

	@Valid
	public ClientInterfaceSecurityInfoOauthServerInfoProvidedConfiguration getProvidedConfiguration() {
		return providedConfiguration;
	}

	public void setProvidedConfiguration(final ClientInterfaceSecurityInfoOauthServerInfoProvidedConfiguration providedConfiguration) {
		this.providedConfiguration = providedConfiguration;
	}

	public ClientInterfaceSecurityInfoOauthServerInfo tlsCipherSuites(final List<String> tlsCipherSuites) {
		this.tlsCipherSuites = tlsCipherSuites;
		return this;
	}

	public ClientInterfaceSecurityInfoOauthServerInfo addTlsCipherSuitesItem(final String tlsCipherSuitesItem) {
		if (this.tlsCipherSuites == null) {
			this.tlsCipherSuites = new ArrayList<>();
		}
		this.tlsCipherSuites.add(tlsCipherSuitesItem);
		return this;
	}

	/**
	 * List of cipher suites that shall be declared as supported by the API consumer
	 * when performing the SSL or TLS negotiation with the authorization server.
	 * Valid values of cipher suites are defined in IETF RFC 8447.
	 *
	 * @return tlsCipherSuites
	 **/
	@Schema(description = "List of cipher suites that shall be declared as supported by the API consumer when performing the SSL or TLS negotiation with the authorization server. Valid values of cipher suites are defined in IETF RFC 8447. ")

	public List<String> getTlsCipherSuites() {
		return tlsCipherSuites;
	}

	public void setTlsCipherSuites(final List<String> tlsCipherSuites) {
		this.tlsCipherSuites = tlsCipherSuites;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ClientInterfaceSecurityInfoOauthServerInfo clientInterfaceSecurityInfoOauthServerInfo = (ClientInterfaceSecurityInfoOauthServerInfo) o;
		return Objects.equals(this.dynamicDiscovery, clientInterfaceSecurityInfoOauthServerInfo.dynamicDiscovery) &&
				Objects.equals(this.providedConfiguration, clientInterfaceSecurityInfoOauthServerInfo.providedConfiguration) &&
				Objects.equals(this.tlsCipherSuites, clientInterfaceSecurityInfoOauthServerInfo.tlsCipherSuites);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dynamicDiscovery, providedConfiguration, tlsCipherSuites);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ClientInterfaceSecurityInfoOauthServerInfo {\n");

		sb.append("    dynamicDiscovery: ").append(toIndentedString(dynamicDiscovery)).append("\n");
		sb.append("    providedConfiguration: ").append(toIndentedString(providedConfiguration)).append("\n");
		sb.append("    tlsCipherSuites: ").append(toIndentedString(tlsCipherSuites)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
