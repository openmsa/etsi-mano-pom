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
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.AdditionalServiceData;

/**
 * Describes a virtual connection point allowing access to a number of VNFC instances (based on their respective VDUs).
 */
public class VirtualCp extends Cp {
	/**
	 * References the VDU(s) which implement this service
	 */
	@Valid
	@NotNull
	@JsonProperty("additionalServiceData")
	private List<AdditionalServiceData> additionalServiceData;

	@Occurence({ "0", "1" })
	@Capability("tosca.capabilities.nfv.VirtualLinkable")
	@Relationship("tosca.relationships.nfv.VirtualLinksTo")
	@JsonProperty("virtual_link")
	private String virtualLinkReq;

	@Occurence({ "1", "UNBOUNDED" })
	@Capability("tosca.capabilities.Node")
	@Relationship("tosca.relationships.DependsOn")
	@JsonProperty("target")
	private List<String> targetReq;

	@NotNull
	public List<AdditionalServiceData> getAdditionalServiceData() {
		return this.additionalServiceData;
	}

	public void setAdditionalServiceData(
			@NotNull final List<AdditionalServiceData> additionalServiceData) {
		this.additionalServiceData = additionalServiceData;
	}

	public String getVirtualLinkReq() {
		return this.virtualLinkReq;
	}

	public void setVirtualLinkReq(final String virtualLinkReq) {
		this.virtualLinkReq = virtualLinkReq;
	}

	public List<String> getTargetReq() {
		return this.targetReq;
	}

	public void setTargetReq(final List<String> targetReq) {
		this.targetReq = targetReq;
	}
}
