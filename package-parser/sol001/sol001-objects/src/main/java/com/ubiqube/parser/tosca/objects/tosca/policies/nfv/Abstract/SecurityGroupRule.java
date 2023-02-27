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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv.Abstract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * The Abstract.SecurityGroupRule type represents an abstract policy type
 * without any target requirements
 */
public class SecurityGroupRule extends Root {
	/**
	 * Indicates the protocol carried over the IP layer. Permitted values include
	 * any protocol defined in the IANA protocol registry, e.g. TCP, UDP, ICMP, etc.
	 */
	@Valid
	@NotNull
	@JsonProperty("protocol")
	private String protocol = "tcp";

	/**
	 * Indicates the protocol carried over the Ethernet layer.
	 */
	@Valid
	@NotNull
	@JsonProperty("ether_type")
	private String etherType = "ipv4";

	/**
	 * Human readable description of the security group rule.
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	/**
	 * Indicates maximum port number in the range that is matched by the security
	 * group rule. If a value is provided at design-time, this value may be
	 * overridden at run-time based on other deployment requirements or constraints.
	 */
	@Valid
	@NotNull
	@JsonProperty("port_range_max")
	@DecimalMin(value = "0", inclusive = true)
	@DecimalMax("65535")
	private Integer portRangeMax = 65535;

	/**
	 * The direction in which the security group rule is applied. The direction of
	 * 'ingress' or 'egress' is specified against the associated CP. I.e., 'ingress'
	 * means the packets entering a CP, while 'egress' means the packets sent out of
	 * a CP.
	 */
	@Valid
	@NotNull
	@JsonProperty("direction")
	private String direction = "ingress";

	/**
	 * Indicates minimum port number in the range that is matched by the security
	 * group rule. If a value is provided at design-time, this value may be
	 * overridden at run-time based on other deployment requirements or constraints.
	 */
	@Valid
	@NotNull
	@JsonProperty("port_range_min")
	@DecimalMin(value = "0", inclusive = true)
	@DecimalMax("65535")
	private Integer portRangeMin = 0;

	@NotNull
	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(@NotNull final String protocol) {
		this.protocol = protocol;
	}

	@NotNull
	public String getEtherType() {
		return this.etherType;
	}

	public void setEtherType(@NotNull final String etherType) {
		this.etherType = etherType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public Integer getPortRangeMax() {
		return this.portRangeMax;
	}

	public void setPortRangeMax(@NotNull final Integer portRangeMax) {
		this.portRangeMax = portRangeMax;
	}

	@NotNull
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(@NotNull final String direction) {
		this.direction = direction;
	}

	@NotNull
	public Integer getPortRangeMin() {
		return this.portRangeMin;
	}

	public void setPortRangeMin(@NotNull final Integer portRangeMin) {
		this.portRangeMin = portRangeMin;
	}
}
