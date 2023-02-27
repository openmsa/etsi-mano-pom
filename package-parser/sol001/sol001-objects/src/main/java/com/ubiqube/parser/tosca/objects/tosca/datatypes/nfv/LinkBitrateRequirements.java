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

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes the requirements in terms of bitrate for a virtual link
 */
public class LinkBitrateRequirements extends Root {
	/**
	 * Specifies the throughput requirement in bits per second of the link (e.g.
	 * bitrate of E-Line, root bitrate of E-Tree, aggregate capacity of E-LAN).
	 */
	@Valid
	@NotNull
	@JsonProperty("root")
	@DecimalMin(value = "0", inclusive = true)
	private Integer root;

	/**
	 * Specifies the throughput requirement in bits per second of leaf connections
	 * to the link when applicable to the connectivity type (e.g. for E-Tree and E
	 * LAN branches).
	 */
	@Valid
	@JsonProperty("leaf")
	@DecimalMin(value = "0", inclusive = true)
	private Integer leaf;

	@NotNull
	public Integer getRoot() {
		return this.root;
	}

	public void setRoot(@NotNull final Integer root) {
		this.root = root;
	}

	public Integer getLeaf() {
		return this.leaf;
	}

	public void setLeaf(final Integer leaf) {
		this.leaf = leaf;
	}
}
