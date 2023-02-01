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

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.parser.tosca.objects.tosca.capabilities.nfv.VirtualLinkable;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.ConnectivityType;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NfviMaintenanceInfo;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VirtualLinkMonitoringParameter;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.VlProfile;
import com.ubiqube.parser.tosca.objects.tosca.nodes.Root;

/**
 * Describes the information about an internal VNF VL
 */
public class VnfVirtualLink extends Root {
	/**
	 * Defines additional data for the VL
	 */
	@Valid
	@NotNull
	@JsonProperty("vl_profile")
	private VlProfile vlProfile;

	/**
	 * Describes monitoring parameters applicable to the VL
	 */
	@Valid
	@JsonProperty("monitoring_parameters")
	private Map<String, VirtualLinkMonitoringParameter> monitoringParameters;

	/**
	 * Provides information on the rules to be observed when an instance based on
	 * this VnfVirtualLink is impacted during NFVI operation and maintenance (e.g.
	 * NFVI resource upgrades).
	 */
	@Valid
	@JsonProperty("nfvi_maintenance_info")
	private NfviMaintenanceInfo nfviMaintenanceInfo;

	/**
	 * Provides human-readable information on the purpose of the VL
	 */
	@Valid
	@JsonProperty("description")
	private String description;

	/**
	 * Test access facilities available on the VL
	 */
	@Valid
	@JsonProperty("test_access")
	private List<String> testAccess;

	/**
	 * Specifies the protocol exposed by the VL and the flow pattern supported by
	 * the VL
	 */
	@Valid
	@NotNull
	@JsonProperty("connectivity_type")
	private ConnectivityType connectivityType;

	/**
	 * Caps.
	 */
	private VirtualLinkable virtualLinkable;

	@NotNull
	public VlProfile getVlProfile() {
		return this.vlProfile;
	}

	public void setVlProfile(@NotNull final VlProfile vlProfile) {
		this.vlProfile = vlProfile;
	}

	public Map<String, VirtualLinkMonitoringParameter> getMonitoringParameters() {
		return this.monitoringParameters;
	}

	public void setMonitoringParameters(
			final Map<String, VirtualLinkMonitoringParameter> monitoringParameters) {
		this.monitoringParameters = monitoringParameters;
	}

	public NfviMaintenanceInfo getNfviMaintenanceInfo() {
		return this.nfviMaintenanceInfo;
	}

	public void setNfviMaintenanceInfo(final NfviMaintenanceInfo nfviMaintenanceInfo) {
		this.nfviMaintenanceInfo = nfviMaintenanceInfo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<String> getTestAccess() {
		return this.testAccess;
	}

	public void setTestAccess(final List<String> testAccess) {
		this.testAccess = testAccess;
	}

	@NotNull
	public ConnectivityType getConnectivityType() {
		return this.connectivityType;
	}

	public void setConnectivityType(@NotNull final ConnectivityType connectivityType) {
		this.connectivityType = connectivityType;
	}

	public VirtualLinkable getVirtualLinkable() {
		return this.virtualLinkable;
	}

	public void setVirtualLinkable(final VirtualLinkable virtualLinkable) {
		this.virtualLinkable = virtualLinkable;
	}
}
