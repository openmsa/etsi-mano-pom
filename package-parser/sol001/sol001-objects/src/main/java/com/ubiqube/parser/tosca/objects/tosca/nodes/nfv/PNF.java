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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.LocationInfo;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

public class PNF extends Root {
	/**
	 * Identifier of this PNFD information element. It uniquely identifies the PNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("descriptor_id")
	private String descriptorId;

	/**
	 * Identifies the provider of the PNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("provider")
	private String provider;

	/**
	 * Identifier of this PNFD in a version independent manner. This attribute is
	 * invariant across versions of PNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("descriptor_invariant_id")
	private String descriptorInvariantId;

	/**
	 * Name to identify the PNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("name")
	private String name;

	/**
	 * Provides information about the geographical location (e.g. geographic
	 * coordinates or address of the building, etc.) of the PNF
	 */
	@Valid
	@JsonProperty("geographical_location_info")
	private LocationInfo geographicalLocationInfo;

	/**
	 * Identifies the version of the PNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("version")
	private String version;

	/**
	 * Describes the PNF function.
	 */
	@Valid
	@NotNull
	@JsonProperty("function_description")
	private String functionDescription;

	@Occurence({ "0", "1" })
	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	@NotNull
	public String getDescriptorId() {
		return this.descriptorId;
	}

	public void setDescriptorId(@NotNull final String descriptorId) {
		this.descriptorId = descriptorId;
	}

	@NotNull
	public String getProvider() {
		return this.provider;
	}

	public void setProvider(@NotNull final String provider) {
		this.provider = provider;
	}

	@NotNull
	public String getDescriptorInvariantId() {
		return this.descriptorInvariantId;
	}

	public void setDescriptorInvariantId(@NotNull final String descriptorInvariantId) {
		this.descriptorInvariantId = descriptorInvariantId;
	}

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public LocationInfo getGeographicalLocationInfo() {
		return this.geographicalLocationInfo;
	}

	public void setGeographicalLocationInfo(final LocationInfo geographicalLocationInfo) {
		this.geographicalLocationInfo = geographicalLocationInfo;
	}

	@NotNull
	public String getVersion() {
		return this.version;
	}

	public void setVersion(@NotNull final String version) {
		this.version = version;
	}

	@NotNull
	public String getFunctionDescription() {
		return this.functionDescription;
	}

	public void setFunctionDescription(@NotNull final String functionDescription) {
		this.functionDescription = functionDescription;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}
}
