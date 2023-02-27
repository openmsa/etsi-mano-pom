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
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes requested additional capability for a particular VDU
 */
public class RequestedAdditionalCapability extends Root {
	/**
	 * Identifies the preferred version of the requested additional capability.
	 */
	@Valid
	@JsonProperty("preferred_requested_additional_capability_version")
	private String preferredRequestedAdditionalCapabilityVersion;

	/**
	 * Identifies specific attributes, dependent on the requested additional
	 * capability type.
	 */
	@Valid
	@NotNull
	@JsonProperty("target_performance_parameters")
	private Map<String, String> targetPerformanceParameters;

	/**
	 * Identifies a requested additional capability for the VDU.
	 */
	@Valid
	@NotNull
	@JsonProperty("requested_additional_capability_name")
	private String requestedAdditionalCapabilityName;

	/**
	 * Indicates whether the requested additional capability is mandatory for
	 * successful operation.
	 */
	@Valid
	@NotNull
	@JsonProperty("support_mandatory")
	private Boolean supportMandatory;

	/**
	 * Identifies the minimum version of the requested additional capability.
	 */
	@Valid
	@JsonProperty("min_requested_additional_capability_version")
	private String minRequestedAdditionalCapabilityVersion;

	public String getPreferredRequestedAdditionalCapabilityVersion() {
		return this.preferredRequestedAdditionalCapabilityVersion;
	}

	public void setPreferredRequestedAdditionalCapabilityVersion(
			final String preferredRequestedAdditionalCapabilityVersion) {
		this.preferredRequestedAdditionalCapabilityVersion = preferredRequestedAdditionalCapabilityVersion;
	}

	@NotNull
	public Map<String, String> getTargetPerformanceParameters() {
		return this.targetPerformanceParameters;
	}

	public void setTargetPerformanceParameters(
			@NotNull final Map<String, String> targetPerformanceParameters) {
		this.targetPerformanceParameters = targetPerformanceParameters;
	}

	@NotNull
	public String getRequestedAdditionalCapabilityName() {
		return this.requestedAdditionalCapabilityName;
	}

	public void setRequestedAdditionalCapabilityName(
			@NotNull final String requestedAdditionalCapabilityName) {
		this.requestedAdditionalCapabilityName = requestedAdditionalCapabilityName;
	}

	@NotNull
	public Boolean getSupportMandatory() {
		return this.supportMandatory;
	}

	public void setSupportMandatory(@NotNull final Boolean supportMandatory) {
		this.supportMandatory = supportMandatory;
	}

	public String getMinRequestedAdditionalCapabilityVersion() {
		return this.minRequestedAdditionalCapabilityVersion;
	}

	public void setMinRequestedAdditionalCapabilityVersion(
			final String minRequestedAdditionalCapabilityVersion) {
		this.minRequestedAdditionalCapabilityVersion = minRequestedAdditionalCapabilityVersion;
	}
}
