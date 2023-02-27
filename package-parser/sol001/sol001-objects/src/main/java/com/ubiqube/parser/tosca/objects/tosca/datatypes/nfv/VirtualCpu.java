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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;
import com.ubiqube.parser.tosca.scalar.Frequency;

/**
 * Supports the specification of requirements related to virtual CPU(s) of a
 * virtual compute resource
 */
public class VirtualCpu extends Root {
	/**
	 * CPU core oversubscription policy e.g. the relation of virtual CPU cores to
	 * physical CPU cores/threads.
	 */
	@Valid
	@JsonProperty("virtual_cpu_oversubscription_policy")
	private String virtualCpuOversubscriptionPolicy;

	/**
	 * CPU architecture type. Examples are x86, ARM
	 */
	@Valid
	@JsonProperty("cpu_architecture")
	private String cpuArchitecture;

	/**
	 * Minimum virtual CPU clock rate
	 */
	@Valid
	@JsonProperty("virtual_cpu_clock")
	private Frequency virtualCpuClock;

	/**
	 * The hardware platform specific VDU CPU requirements. A map of strings that
	 * contains a set of key-value pairs describing VDU CPU specific hardware
	 * platform requirements.
	 */
	@Valid
	@JsonProperty("vdu_cpu_requirements")
	private Map<String, String> vduCpuRequirements;

	/**
	 * Number of virtual CPUs
	 */
	@Valid
	@NotNull
	@JsonProperty("num_virtual_cpu")
	@DecimalMin("0")
	private Integer numVirtualCpu;

	/**
	 * The virtual CPU pinning configuration for the virtualised compute resource.
	 */
	@Valid
	@JsonProperty("virtual_cpu_pinning")
	private VirtualCpuPinning virtualCpuPinning;

	public String getVirtualCpuOversubscriptionPolicy() {
		return this.virtualCpuOversubscriptionPolicy;
	}

	public void setVirtualCpuOversubscriptionPolicy(final String virtualCpuOversubscriptionPolicy) {
		this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
	}

	public String getCpuArchitecture() {
		return this.cpuArchitecture;
	}

	public void setCpuArchitecture(final String cpuArchitecture) {
		this.cpuArchitecture = cpuArchitecture;
	}

	public Frequency getVirtualCpuClock() {
		return this.virtualCpuClock;
	}

	public void setVirtualCpuClock(final Frequency virtualCpuClock) {
		this.virtualCpuClock = virtualCpuClock;
	}

	public Map<String, String> getVduCpuRequirements() {
		return this.vduCpuRequirements;
	}

	public void setVduCpuRequirements(final Map<String, String> vduCpuRequirements) {
		this.vduCpuRequirements = vduCpuRequirements;
	}

	@NotNull
	public Integer getNumVirtualCpu() {
		return this.numVirtualCpu;
	}

	public void setNumVirtualCpu(@NotNull final Integer numVirtualCpu) {
		this.numVirtualCpu = numVirtualCpu;
	}

	public VirtualCpuPinning getVirtualCpuPinning() {
		return this.virtualCpuPinning;
	}

	public void setVirtualCpuPinning(final VirtualCpuPinning virtualCpuPinning) {
		this.virtualCpuPinning = virtualCpuPinning;
	}
}
