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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import java.lang.String;
import java.util.List;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Representation of the object described by the mciop artifact, capable of being profiled by the properties of the MciopProfile information element defined in ETSI GS NFV-IFA 011 [1].
 */
public class Mciop extends Root {
	@Occurence({ "1", "UNBOUNDED" })
	@Node("tosca.nodes.nfv.Vdu.OsContainerDeployableUnit")
	@Capability("tosca.capabilities.nfv.AssociableVdu")
	@Relationship("tosca.relationships.nfv.MciopAssociates")
	@JsonProperty("associatedVdu")
	private List<String> associatedVduReq;

	public List<String> getAssociatedVduReq() {
		return this.associatedVduReq;
	}

	public void setAssociatedVduReq(final List<String> associatedVduReq) {
		this.associatedVduReq = associatedVduReq;
	}
}
