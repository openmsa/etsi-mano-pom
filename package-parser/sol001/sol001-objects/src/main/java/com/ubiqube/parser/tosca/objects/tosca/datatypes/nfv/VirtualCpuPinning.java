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
package com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv;

import java.util.List;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Supports the specification of requirements related to the virtual CPU pinning
 * configuration of a virtual compute resource
 */
public class VirtualCpuPinning extends Root {
	/**
	 * Indicates the policy for CPU pinning. The policy can take values of "static"
	 * or "dynamic". In case of "dynamic" the allocation of virtual CPU cores to
	 * logical CPU cores is decided by the VIM. (e.g. SMT (Simultaneous
	 * Multi-Threading) requirements). In case of "static" the allocation is
	 * requested to be according to the virtual_cpu_pinning_rule.
	 */
	@Valid
	@JsonProperty("virtual_cpu_pinning_policy")
	private String virtualCpuPinningPolicy;

	/**
	 * Provides the list of rules for allocating virtual CPU cores to logical CPU
	 * cores/threads
	 */
	@Valid
	@JsonProperty("virtual_cpu_pinning_rule")
	private List<String> virtualCpuPinningRule;

	public String getVirtualCpuPinningPolicy() {
		return this.virtualCpuPinningPolicy;
	}

	public void setVirtualCpuPinningPolicy(final String virtualCpuPinningPolicy) {
		this.virtualCpuPinningPolicy = virtualCpuPinningPolicy;
	}

	public List<String> getVirtualCpuPinningRule() {
		return this.virtualCpuPinningRule;
	}

	public void setVirtualCpuPinningRule(final List<String> virtualCpuPinningRule) {
		this.virtualCpuPinningRule = virtualCpuPinningRule;
	}
}
