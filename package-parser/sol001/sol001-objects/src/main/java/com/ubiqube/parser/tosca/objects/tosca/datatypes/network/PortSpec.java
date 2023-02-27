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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.network;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;
import com.ubiqube.parser.tosca.scalar.Range;

public class PortSpec extends Root {
	@Valid
	@JsonProperty("protocol")
	private String protocol = "tcp";

	@Valid
	@JsonProperty("target_range")
	@Min(1)
	@Max(65535)
	private Range targetRange;

	@JsonProperty("source")
	@Min(1)
	@Max(65535)
	private Integer source;

	@JsonProperty("target")
	@Min(1)
	@Max(65535)
	private Integer target;

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public Range getTargetRange() {
		return this.targetRange;
	}

	public void setTargetRange(final Range targetRange) {
		this.targetRange = targetRange;
	}

	public Integer getSource() {
		return this.source;
	}

	public void setSource(final Integer source) {
		this.source = source;
	}

	public Integer getTarget() {
		return this.target;
	}

	public void setTarget(final Integer target) {
		this.target = target;
	}
}
