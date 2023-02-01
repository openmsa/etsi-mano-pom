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
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * data type describes the source and destination VNFDs as well as source
 * deployment flavour for a change current VNF Package.
 */
public class VnfPackageChangeSelector extends Root {
	/**
	 * Identifier of the source VNFD and the source VNF package.
	 */
	@Valid
	@NotNull
	@JsonProperty("source_descriptor_id")
	private String sourceDescriptorId;

	/**
	 * Identifier of the deployment flavour in the source VNF package for which this
	 * data type applies.
	 */
	@Valid
	@NotNull
	@JsonProperty("source_flavour_id")
	private String sourceFlavourId;

	/**
	 * Identifier of the destination VNFD and the destination VNF package.
	 */
	@Valid
	@NotNull
	@JsonProperty("destination_descriptor_id")
	private String destinationDescriptorId;

	@NotNull
	public String getSourceDescriptorId() {
		return this.sourceDescriptorId;
	}

	public void setSourceDescriptorId(@NotNull final String sourceDescriptorId) {
		this.sourceDescriptorId = sourceDescriptorId;
	}

	@NotNull
	public String getSourceFlavourId() {
		return this.sourceFlavourId;
	}

	public void setSourceFlavourId(@NotNull final String sourceFlavourId) {
		this.sourceFlavourId = sourceFlavourId;
	}

	@NotNull
	public String getDestinationDescriptorId() {
		return this.destinationDescriptorId;
	}

	public void setDestinationDescriptorId(@NotNull final String destinationDescriptorId) {
		this.destinationDescriptorId = destinationDescriptorId;
	}
}
