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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Integer;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * describes additional instantiation data for a given Vdu.Compute (for VM based VDU) or Vdu.OsContainerDeployableUnit node (for Oscontainer based VDU) used in a specific deployment flavour.
 */
public class VduProfile extends Root {
	/**
	 * Provides information on the impact tolerance and rules to be observed when instance(s) of the Vdu.Compute (for VM based VDU) are impacted during NFVI operation and maintenance (e.g. NFVI resource upgrades).
	 */
	@Valid
	@JsonProperty("nfvi_maintenance_info")
	private NfviMaintenanceInfo nfviMaintenanceInfo;

	/**
	 * Minimum number of instances of the VNFC based on this Vdu.Compute (for VM based VDU) or Vdu.OsContainerDeployableUnit node (for Oscontainer based VDU) that is permitted to exist for a particular VNF deployment flavour.
	 */
	@Valid
	@NotNull
	@JsonProperty("min_number_of_instances")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer minNumberOfInstances;

	/**
	 * Maximum number of instances of the VNFC based on this Vdu.Compute (for VM based VDU) or Vdu.OsContainerDeployableUnit node (for Oscontainer based VDU) that is permitted to exist for a particular VNF deployment flavour.
	 */
	@Valid
	@NotNull
	@JsonProperty("max_number_of_instances")
	@DecimalMin(
			value = "0",
			inclusive = true
	)
	private Integer maxNumberOfInstances;

	public NfviMaintenanceInfo getNfviMaintenanceInfo() {
		return this.nfviMaintenanceInfo;
	}

	public void setNfviMaintenanceInfo(final NfviMaintenanceInfo nfviMaintenanceInfo) {
		this.nfviMaintenanceInfo = nfviMaintenanceInfo;
	}

	@NotNull
	public Integer getMinNumberOfInstances() {
		return this.minNumberOfInstances;
	}

	public void setMinNumberOfInstances(@NotNull final Integer minNumberOfInstances) {
		this.minNumberOfInstances = minNumberOfInstances;
	}

	@NotNull
	public Integer getMaxNumberOfInstances() {
		return this.maxNumberOfInstances;
	}

	public void setMaxNumberOfInstances(@NotNull final Integer maxNumberOfInstances) {
		this.maxNumberOfInstances = maxNumberOfInstances;
	}
}
