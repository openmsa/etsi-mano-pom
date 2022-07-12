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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VnfMonitoringParameter;
import com.ubiqube.parser.tosca.objects.tosca.policies.Root;

/**
 * Policy type is used to identify information to be monitored during the
 * lifetime of a VNF instance as defined in ETSI GS NFV-IFA 014
 * [2].tosca.nodes.nfv.VNF
 */
public class VnfMonitoring extends Root {
	/**
	 * Specifies a virtualised resource related performance metric to be monitored
	 * on the NS level.
	 */
	@Valid
	@NotNull
	@JsonProperty("vnf_monitoring_parameters")
	@Size(min = 1)
	private Map<String, VnfMonitoringParameter> vnfMonitoringParameters;

	@Valid
	private List<String> targets;

	@NotNull
	public Map<String, VnfMonitoringParameter> getVnfMonitoringParameters() {
		return this.vnfMonitoringParameters;
	}

	public void setVnfMonitoringParameters(
			@NotNull final Map<String, VnfMonitoringParameter> vnfMonitoringParameters) {
		this.vnfMonitoringParameters = vnfMonitoringParameters;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
