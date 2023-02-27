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

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Node;
import com.ubiqube.parser.tosca.annotations.Occurence;
import com.ubiqube.parser.tosca.annotations.Relationship;

/**
 * describes network connectivity between a VNFC instance based on this VDU and
 * an internal VL through a trunk port
 */
public class VduSubCp extends VduCp {
	/**
	 * Specifies the segmentation ID for the subport, which is used to differentiate
	 * the traffics on different networks coming in and out of the trunk port.
	 */
	@Valid
	@JsonProperty("segmentation_id")
	@DecimalMin(value = "0", inclusive = true)
	private Integer segmentationId;

	/**
	 * Specifies the encapsulation type for the traffics coming in and out of the
	 * trunk subport.
	 */
	@Valid
	@JsonProperty("segmentation_type")
	private String segmentationType;

	@Occurence({ "1", "1" })
	@Node("tosca.nodes.nfv.VduCp")
	@Capability("tosca.capabilities.nfv.TrunkBindable")
	@Relationship("tosca.relationships.nfv.TrunkBindsTo")
	@JsonProperty("trunk_binding")
	private String trunkBindingReq;

	public Integer getSegmentationId() {
		return this.segmentationId;
	}

	public void setSegmentationId(final Integer segmentationId) {
		this.segmentationId = segmentationId;
	}

	public String getSegmentationType() {
		return this.segmentationType;
	}

	public void setSegmentationType(final String segmentationType) {
		this.segmentationType = segmentationType;
	}

	public String getTrunkBindingReq() {
		return this.trunkBindingReq;
	}

	public void setTrunkBindingReq(final String trunkBindingReq) {
		this.trunkBindingReq = trunkBindingReq;
	}
}
