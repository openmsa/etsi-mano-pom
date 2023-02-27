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
package com.ubiqube.parser.tosca.objects.tosca.nodes.nfv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * node definition of NFP
 */
public class NFP extends Root {
	@Occurence({ "1", "UNBOUNDED" })
	@Node("tosca.nodes.nfv.NfpPosition")
	@Capability("tosca.capabilities.nfv.Forwarding")
	@Relationship("tosca.relationships.nfv.ForwardTo")
	@JsonProperty("nfp_position")
	private List<String> nfpPositionReq;

	public List<String> getNfpPositionReq() {
		return this.nfpPositionReq;
	}

	public void setNfpPositionReq(final List<String> nfpPositionReq) {
		this.nfpPositionReq = nfpPositionReq;
	}
}
