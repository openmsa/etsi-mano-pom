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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * A mapping between the identifier of a components or property in the source
 * VNFD and the identifier of the corresponding component or property in the
 * destination VNFD.
 */
public class VnfPackageChangeComponentMapping extends Root {
	/**
	 * The type of component or property. Possible values differentiate whether
	 * changes concern to some VNF component (e.g. VDU, internal VLD, etc.) or
	 * property (e.g. a Scaling Aspect, etc.).
	 */
	@Valid
	@NotNull
	@JsonProperty("component_type")
	private String componentType;

	/**
	 * Identifier of the component or property in the destination VNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("destination_id")
	private String destinationId;

	/**
	 * Human readable description of the component changes.
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	/**
	 * Identifier of the component or property in the source VNFD.
	 */
	@Valid
	@NotNull
	@JsonProperty("source_id")
	private String sourceId;

	@NotNull
	public String getComponentType() {
		return this.componentType;
	}

	public void setComponentType(@NotNull final String componentType) {
		this.componentType = componentType;
	}

	@NotNull
	public String getDestinationId() {
		return this.destinationId;
	}

	public void setDestinationId(@NotNull final String destinationId) {
		this.destinationId = destinationId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public String getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(@NotNull final String sourceId) {
		this.sourceId = sourceId;
	}
}
