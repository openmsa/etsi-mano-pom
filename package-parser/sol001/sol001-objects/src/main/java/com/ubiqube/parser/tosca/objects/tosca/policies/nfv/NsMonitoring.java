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
package com.ubiqube.parser.tosca.objects.tosca.policies.nfv;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NsMonitoringParameter;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * Policy type is used to identify information to be monitored during the
 * lifetime of a network service instance as defined in ETSI GS NFV-IFA 014
 * [2].tosca.nodes.nfv.NS
 */
public class NsMonitoring extends Root {
	/**
	 * Specifies a virtualised resource related performance metric to be monitored
	 * on the NS level.
	 */
	@Valid
	@NotNull
	@JsonProperty("ns_monitoring_parameters")
	@Size(min = 1)
	private Map<String, NsMonitoringParameter> nsMonitoringParameters;

	@Valid
	private List<String> targets;

	@NotNull
	public Map<String, NsMonitoringParameter> getNsMonitoringParameters() {
		return this.nsMonitoringParameters;
	}

	public void setNsMonitoringParameters(
			@NotNull final Map<String, NsMonitoringParameter> nsMonitoringParameters) {
		this.nsMonitoringParameters = nsMonitoringParameters;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
