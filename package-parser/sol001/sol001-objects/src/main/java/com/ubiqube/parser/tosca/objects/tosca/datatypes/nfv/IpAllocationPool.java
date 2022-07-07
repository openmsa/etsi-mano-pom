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
import java.lang.String;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Specifies a range of IP addresses
 */
public class IpAllocationPool extends Root {
	/**
	 * The IP address to be used as the first one in a pool of addresses derived from the cidr block full IP range
	 */
	@Valid
	@NotNull
	@JsonProperty("start_ip_address")
	private String startIpAddress;

	/**
	 * The IP address to be used as the last one in a pool of addresses derived from the cidr block full IP range
	 */
	@Valid
	@NotNull
	@JsonProperty("end_ip_address")
	private String endIpAddress;

	@NotNull
	public String getStartIpAddress() {
		return this.startIpAddress;
	}

	public void setStartIpAddress(@NotNull final String startIpAddress) {
		this.startIpAddress = startIpAddress;
	}

	@NotNull
	public String getEndIpAddress() {
		return this.endIpAddress;
	}

	public void setEndIpAddress(@NotNull final String endIpAddress) {
		this.endIpAddress = endIpAddress;
	}
}
