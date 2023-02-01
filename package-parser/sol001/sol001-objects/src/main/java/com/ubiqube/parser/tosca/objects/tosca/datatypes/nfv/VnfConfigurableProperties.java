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

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * indicates configuration properties for a given VNF (e.g. related to auto
 * scaling and auto healing).
 */
public class VnfConfigurableProperties extends Root {
	/**
	 * Contains information enabling access to the NFV-MANO interfaces produced by
	 * the VNFM (e.g. URIs and credentials), If the property is not present, then
	 * configuring this VNF property is not supported.
	 */
	@Valid
	@JsonProperty("vnfm_interface_info")
	private VnfmInterfaceInfo vnfmInterfaceInfo;

	/**
	 * It permits to enable (TRUE)/disable (FALSE) the auto-scaling functionality.
	 * If the property is not present, then configuring this VNF property is not
	 * supported
	 */
	@Valid
	@JsonProperty("is_autoscale_enabled")
	private Boolean isAutoscaleEnabled;

	/**
	 * Contains information to enable discovery of the authorization server
	 * protecting access to VNFM interfaces. If the property is not present, then
	 * configuring this VNF property is not supported.
	 */
	@Valid
	@JsonProperty("vnfm_oauth_server_info")
	private OauthServerInfo vnfmOauthServerInfo;

	/**
	 * It permits to enable (TRUE)/disable (FALSE) the auto-healing functionality.
	 * If the property is not present, then configuring this VNF property is not
	 * supported
	 */
	@Valid
	@JsonProperty("is_autoheal_enabled")
	private Boolean isAutohealEnabled;

	/**
	 * Contains information to enable discovery of the authorization server to
	 * validate the access tokens provided by the VNFM when the VNFM accesses the
	 * VNF interfaces, if that functionality (token introspection) is supported by
	 * the authorization server. If the property is not present, then configuring
	 * this VNF property is not supported.
	 */
	@Valid
	@JsonProperty("vnf_oauth_server_info")
	private OauthServerInfo vnfOauthServerInfo;

	public VnfmInterfaceInfo getVnfmInterfaceInfo() {
		return this.vnfmInterfaceInfo;
	}

	public void setVnfmInterfaceInfo(final VnfmInterfaceInfo vnfmInterfaceInfo) {
		this.vnfmInterfaceInfo = vnfmInterfaceInfo;
	}

	public Boolean getIsAutoscaleEnabled() {
		return this.isAutoscaleEnabled;
	}

	public void setIsAutoscaleEnabled(final Boolean isAutoscaleEnabled) {
		this.isAutoscaleEnabled = isAutoscaleEnabled;
	}

	public OauthServerInfo getVnfmOauthServerInfo() {
		return this.vnfmOauthServerInfo;
	}

	public void setVnfmOauthServerInfo(final OauthServerInfo vnfmOauthServerInfo) {
		this.vnfmOauthServerInfo = vnfmOauthServerInfo;
	}

	public Boolean getIsAutohealEnabled() {
		return this.isAutohealEnabled;
	}

	public void setIsAutohealEnabled(final Boolean isAutohealEnabled) {
		this.isAutohealEnabled = isAutohealEnabled;
	}

	public OauthServerInfo getVnfOauthServerInfo() {
		return this.vnfOauthServerInfo;
	}

	public void setVnfOauthServerInfo(final OauthServerInfo vnfOauthServerInfo) {
		this.vnfOauthServerInfo = vnfOauthServerInfo;
	}
}
