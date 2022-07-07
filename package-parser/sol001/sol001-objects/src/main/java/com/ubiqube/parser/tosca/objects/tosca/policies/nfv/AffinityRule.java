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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.nfv.NfviMaintenanceInfo;
import com.ubiqube.parser.tosca.objects.tosca.policies.Placement;

/**
 * The AffinityRule describes the affinity rules applicable for the defined targetstosca.nodes.nfv.Vdu.Computetosca.nodes.nfv.VnfVirtualLinktosca.groups.nfv.PlacementGrouptosca.nodes.nfv.Mcioptosca.nodes.nfv.Vdu.OsContainerDeployableUnit
 */
public class AffinityRule extends Placement {
	/**
	 * scope of the rule is an NFVI_node, an NFVI_PoP, etc.
	 */
	@Valid
	@NotNull
	@JsonProperty("scope")
	private String scope;

	/**
	 * Provides information on the impact tolerance and rules to be observed when a group of instances based on the same Vdu.Compute (for VM based VDU) node is impacted during NFVI operation and maintenance (e.g. NFVI resource upgrades).
	 */
	@Valid
	@JsonProperty("nfvi_maintenance_group_info")
	private NfviMaintenanceInfo nfviMaintenanceGroupInfo;

	@Valid
	private List<String> targets;

	@NotNull
	public String getScope() {
		return this.scope;
	}

	public void setScope(@NotNull final String scope) {
		this.scope = scope;
	}

	public NfviMaintenanceInfo getNfviMaintenanceGroupInfo() {
		return this.nfviMaintenanceGroupInfo;
	}

	public void setNfviMaintenanceGroupInfo(final NfviMaintenanceInfo nfviMaintenanceGroupInfo) {
		this.nfviMaintenanceGroupInfo = nfviMaintenanceGroupInfo;
	}

	public List<String> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}
}
