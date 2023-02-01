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

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Supports the specification of requirements related to extended resources of a
 * container.
 */
public class ExtendedResourceData extends Root {
	/**
	 * Requested amount of the indicated extended resource.
	 */
	@Valid
	@NotNull
	@JsonProperty("amount")
	private Integer amount;

	/**
	 * The hardware platform specific extended resource. A map of string that
	 * contains one single key-value pair that describes one hardware platform
	 * specific container requirement.
	 */
	@Valid
	@NotNull
	@JsonProperty("extended_resource")
	@Size(min = 1)
	@Size(max = 1)
	private Map<String, String> extendedResource;

	@NotNull
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(@NotNull final Integer amount) {
		this.amount = amount;
	}

	@NotNull
	public Map<String, String> getExtendedResource() {
		return this.extendedResource;
	}

	public void setExtendedResource(@NotNull final Map<String, String> extendedResource) {
		this.extendedResource = extendedResource;
	}
}
