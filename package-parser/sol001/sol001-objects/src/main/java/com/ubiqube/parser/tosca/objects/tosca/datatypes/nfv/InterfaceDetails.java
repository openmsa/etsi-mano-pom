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

import java.util.Map;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * information used to access an interface exposed by a VNF
 */
public class InterfaceDetails extends Root {
	/**
	 * Provides additional details that are specific to the type of interface
	 * considered.
	 */
	@Valid
	@JsonProperty("interface_specific_data")
	private Map<String, String> interfaceSpecificData;

	/**
	 * Provides components to build a Uniform Ressource Identifier (URI) where to
	 * access the interface end point.
	 */
	@Valid
	@JsonProperty("uri_components")
	private UriComponents uriComponents;

	public Map<String, String> getInterfaceSpecificData() {
		return this.interfaceSpecificData;
	}

	public void setInterfaceSpecificData(final Map<String, String> interfaceSpecificData) {
		this.interfaceSpecificData = interfaceSpecificData;
	}

	public UriComponents getUriComponents() {
		return this.uriComponents;
	}

	public void setUriComponents(final UriComponents uriComponents) {
		this.uriComponents = uriComponents;
	}
}
