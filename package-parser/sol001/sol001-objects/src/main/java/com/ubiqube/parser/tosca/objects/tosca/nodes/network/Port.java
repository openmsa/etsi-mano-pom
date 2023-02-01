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
package com.ubiqube.parser.tosca.objects.tosca.nodes.network;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.annotations.Capability;
import com.ubiqube.parser.tosca.annotations.Relationship;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

public class Port extends Root {
	@Valid
	@JsonProperty("ip_range_end")
	private String ipRangeEnd;

	@Valid
	@JsonProperty("ip_range_start")
	private String ipRangeStart;

	@Valid
	@JsonProperty("ip_address")
	private String ipAddress;

	@Valid
	@JsonProperty("is_default")
	private Boolean isDefault = false;

	@Valid
	@NotNull
	@JsonProperty("order")
	@DecimalMin(value = "0", inclusive = true)
	private Integer order = 0;

	@Capability("tosca.capabilities.network.Linkable")
	@Relationship("tosca.relationships.network.LinksTo")
	@JsonProperty("link")
	private String linkReq;

	@Capability("tosca.capabilities.network.Bindable")
	@Relationship("tosca.relationships.network.BindsTo")
	@JsonProperty("binding")
	private String bindingReq;

	public String getIpRangeEnd() {
		return this.ipRangeEnd;
	}

	public void setIpRangeEnd(final String ipRangeEnd) {
		this.ipRangeEnd = ipRangeEnd;
	}

	public String getIpRangeStart() {
		return this.ipRangeStart;
	}

	public void setIpRangeStart(final String ipRangeStart) {
		this.ipRangeStart = ipRangeStart;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(final Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@NotNull
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(@NotNull final Integer order) {
		this.order = order;
	}

	public String getLinkReq() {
		return this.linkReq;
	}

	public void setLinkReq(final String linkReq) {
		this.linkReq = linkReq;
	}

	public String getBindingReq() {
		return this.bindingReq;
	}

	public void setBindingReq(final String bindingReq) {
		this.bindingReq = bindingReq;
	}
}
