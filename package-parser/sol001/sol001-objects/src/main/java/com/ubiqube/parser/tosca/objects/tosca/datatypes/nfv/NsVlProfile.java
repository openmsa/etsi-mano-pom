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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes additional instantiation data for a given NsVirtualLink used in a
 * specific NS deployment flavour.
 */
public class NsVlProfile extends Root {
	/**
	 * Specifies the QoS requirements of a VL instantiated according to this
	 * profile.
	 */
	@Valid
	@JsonProperty("qos")
	private NsVirtualLinkQos qos;

	/**
	 * Specifies the service availability level for the VL instance created from
	 * this profile
	 */
	@Valid
	@JsonProperty("service_availability_level")
	@DecimalMin(value = "1", inclusive = true)
	private Integer serviceAvailabilityLevel;

	/**
	 * Specifies the minimum bitrate requirements for a VL instantiated according to
	 * this profile.
	 */
	@Valid
	@NotNull
	@JsonProperty("min_bitrate_requirements")
	private LinkBitrateRequirements minBitrateRequirements;

	/**
	 * Specifies the protocol data for a virtual link.
	 */
	@Valid
	@JsonProperty("virtual_link_protocol_data")
	private List<NsVirtualLinkProtocolData> virtualLinkProtocolData;

	/**
	 * Specifies the maximum bitrate requirements for a VL instantiated according to
	 * this profile.
	 */
	@Valid
	@NotNull
	@JsonProperty("max_bitrate_requirements")
	private LinkBitrateRequirements maxBitrateRequirements;

	public NsVirtualLinkQos getQos() {
		return this.qos;
	}

	public void setQos(final NsVirtualLinkQos qos) {
		this.qos = qos;
	}

	public Integer getServiceAvailabilityLevel() {
		return this.serviceAvailabilityLevel;
	}

	public void setServiceAvailabilityLevel(final Integer serviceAvailabilityLevel) {
		this.serviceAvailabilityLevel = serviceAvailabilityLevel;
	}

	@NotNull
	public LinkBitrateRequirements getMinBitrateRequirements() {
		return this.minBitrateRequirements;
	}

	public void setMinBitrateRequirements(
			@NotNull final LinkBitrateRequirements minBitrateRequirements) {
		this.minBitrateRequirements = minBitrateRequirements;
	}

	public List<NsVirtualLinkProtocolData> getVirtualLinkProtocolData() {
		return this.virtualLinkProtocolData;
	}

	public void setVirtualLinkProtocolData(
			final List<NsVirtualLinkProtocolData> virtualLinkProtocolData) {
		this.virtualLinkProtocolData = virtualLinkProtocolData;
	}

	@NotNull
	public LinkBitrateRequirements getMaxBitrateRequirements() {
		return this.maxBitrateRequirements;
	}

	public void setMaxBitrateRequirements(
			@NotNull final LinkBitrateRequirements maxBitrateRequirements) {
		this.maxBitrateRequirements = maxBitrateRequirements;
	}
}
