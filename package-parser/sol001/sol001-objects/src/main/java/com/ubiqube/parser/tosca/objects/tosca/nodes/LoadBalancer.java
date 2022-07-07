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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.endpoint.Public;

public class LoadBalancer extends Root {
	@Valid
	@JsonProperty("algorithm")
	private String algorithm;

	/**
	 * Caps.The Floating (IP) clients on the public network can connect to.
	 */
	private Public client;

	@Occurence({ "0", "UNBOUNDED" })
	@Capability("tosca.capabilities.Endpoint")
	@Relationship("tosca.relationships.RoutesTo")
	@JsonProperty("application")
	private List<String> applicationReq;

	public String getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	public Public getClient() {
		return this.client;
	}

	public void setClient(final Public client) {
		this.client = client;
	}

	public List<String> getApplicationReq() {
		return this.applicationReq;
	}

	public void setApplicationReq(final List<String> applicationReq) {
		this.applicationReq = applicationReq;
	}
}
