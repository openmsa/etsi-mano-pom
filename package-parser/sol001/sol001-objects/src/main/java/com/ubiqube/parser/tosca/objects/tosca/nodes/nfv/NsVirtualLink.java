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
package com.ubiqube.parser.tosca.objects.tosca.nodes.nfv;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualLinkable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ConnectivityType;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NsVlProfile;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * node definition of Virtual Links
 */
public class NsVirtualLink extends Root {
	/**
	 * Specifies instantiation parameters for a virtual link of a particular NS
	 * deployment flavour.
	 */
	@Valid
	@NotNull
	@JsonProperty("vl_profile")
	private NsVlProfile vlProfile;

	/**
	 * Test access facilities available on the VL
	 */
	@Valid
	@JsonProperty("test_access")
	private List<String> testAccess;

	/**
	 * Human readable information on the purpose of the virtual link (e.g. VL for
	 * control plane traffic).
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	@Valid
	@NotNull
	@JsonProperty("connectivity_type")
	private ConnectivityType connectivityType;

	/**
	 * Caps.
	 */
	private VirtualLinkable virtualLinkable;

	@NotNull
	public NsVlProfile getVlProfile() {
		return this.vlProfile;
	}

	public void setVlProfile(@NotNull final NsVlProfile vlProfile) {
		this.vlProfile = vlProfile;
	}

	public List<String> getTestAccess() {
		return this.testAccess;
	}

	public void setTestAccess(final List<String> testAccess) {
		this.testAccess = testAccess;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public ConnectivityType getConnectivityType() {
		return this.connectivityType;
	}

	public void setConnectivityType(@NotNull final ConnectivityType connectivityType) {
		this.connectivityType = connectivityType;
	}

	public VirtualLinkable getVirtualLinkable() {
		return this.virtualLinkable;
	}

	public void setVirtualLinkable(final VirtualLinkable virtualLinkable) {
		this.virtualLinkable = virtualLinkable;
	}
}
