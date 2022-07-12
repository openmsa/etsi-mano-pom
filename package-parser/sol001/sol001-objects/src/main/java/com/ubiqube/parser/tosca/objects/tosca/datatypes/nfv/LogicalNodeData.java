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

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Describes compute, memory and I/O requirements associated with a particular
 * VDU.
 */
public class LogicalNodeData extends Root {
	/**
	 * The logical node-level compute, memory and I/O requirements. A map of strings
	 * that contains a set of key-value pairs that describes hardware platform
	 * specific deployment requirements, including the number of CPU cores on this
	 * logical node, a memory configuration specific to a logical node or a
	 * requirement related to the association of an I/O device with the logical
	 * node.
	 */
	@Valid
	@JsonProperty("logical_node_requirements")
	private Map<String, String> logicalNodeRequirements;

	public Map<String, String> getLogicalNodeRequirements() {
		return this.logicalNodeRequirements;
	}

	public void setLogicalNodeRequirements(final Map<String, String> logicalNodeRequirements) {
		this.logicalNodeRequirements = logicalNodeRequirements;
	}
}
