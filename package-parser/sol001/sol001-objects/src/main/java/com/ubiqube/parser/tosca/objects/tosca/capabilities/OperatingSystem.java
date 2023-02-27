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
package com.ubiqube.parser.tosca.objects.tosca.capabilities;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.scalar.Version;

public class OperatingSystem extends Root {
	@Valid
	@JsonProperty("type")
	private String type;

	@Valid
	@JsonProperty("distribution")
	private String distribution;

	@Valid
	@JsonProperty("version")
	private Version version;

	@Valid
	@JsonProperty("architecture")
	private String architecture;

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getDistribution() {
		return this.distribution;
	}

	public void setDistribution(final String distribution) {
		this.distribution = distribution;
	}

	public Version getVersion() {
		return this.version;
	}

	public void setVersion(final Version version) {
		this.version = version;
	}

	public String getArchitecture() {
		return this.architecture;
	}

	public void setArchitecture(final String architecture) {
		this.architecture = architecture;
	}
}
