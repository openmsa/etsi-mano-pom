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
import com.ubiqube.parser.tosca.scalar.Time;
import java.lang.Boolean;
import java.lang.String;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ubiqube.parser.tosca.objects.tosca.datatypes.Root;

/**
 * Provides information related to the constraints and rules applicable to virtualised resources and their groups impacted due to NFVI maintenance operations
 */
public class NfviMaintenanceInfo extends Root {
	/**
	 * Specifies the allowed migration types in the order of preference in case of an impact starting with the most preferred type. It is applicable to the Vdu.Compute node and to the VirtualBlockStorage, VirtualObjectStorage and VirtualFileStorage nodes.
	 */
	@Valid
	@JsonProperty("supported_migration_type")
	private List<String> supportedMigrationType;

	/**
	 * Specifies the minimum notification lead time requested for upcoming impact of the virtualised resource or their group (i.e. between the notification and the action causing the impact).
	 */
	@Valid
	@NotNull
	@JsonProperty("impact_notification_lead_time")
	private Time impactNotificationLeadTime;

	/**
	 * Specifies the time required by the group to recover from an impact, thus, the minimum time requested between consecutive impacts of the group..
	 */
	@Valid
	@JsonProperty("min_recovery_time_between_impacts")
	private Time minRecoveryTimeBetweenImpacts;

	/**
	 * Specifies for different group sizes the minimum number of instances which need to be preserved simultaneously within the group of virtualised resources.
	 */
	@Valid
	@JsonProperty("min_number_of_preserved_instances")
	private List<MinNumberOfPreservedInstances> minNumberOfPreservedInstances;

	/**
	 * Specifies for different group sizes the maximum number of instances that can be impacted simultaneously within the group of virtualised resources without losing functionality.
	 */
	@Valid
	@JsonProperty("max_number_of_impacted_instances")
	private List<MaxNumberOfImpactedInstances> maxNumberOfImpactedInstances;

	/**
	 * Indicates whether it is requested that at the time of the notification of an upcoming change that is expected to have an impact on the VNF, virtualised resource(s) of the same characteristics as the impacted ones is/are provided to compensate for the impact (TRUE) or not (FALSE).
	 */
	@Valid
	@NotNull
	@JsonProperty("is_impact_mitigation_requested")
	private Boolean isImpactMitigationRequested = false;

	/**
	 * Specifies the maximum interruption time that can go undetected at the VNF level and therefore which will not trigger VNF-internal recovery during live migration. It is applicable to the Vdu.Compute node and to the VirtualBlockStorage, VirtualObjectStorage and VirtualFileStorage nodes.
	 */
	@Valid
	@JsonProperty("max_undetectable_interruption_time")
	private Time maxUndetectableInterruptionTime;

	public List<String> getSupportedMigrationType() {
		return this.supportedMigrationType;
	}

	public void setSupportedMigrationType(final List<String> supportedMigrationType) {
		this.supportedMigrationType = supportedMigrationType;
	}

	@NotNull
	public Time getImpactNotificationLeadTime() {
		return this.impactNotificationLeadTime;
	}

	public void setImpactNotificationLeadTime(@NotNull final Time impactNotificationLeadTime) {
		this.impactNotificationLeadTime = impactNotificationLeadTime;
	}

	public Time getMinRecoveryTimeBetweenImpacts() {
		return this.minRecoveryTimeBetweenImpacts;
	}

	public void setMinRecoveryTimeBetweenImpacts(final Time minRecoveryTimeBetweenImpacts) {
		this.minRecoveryTimeBetweenImpacts = minRecoveryTimeBetweenImpacts;
	}

	public List<MinNumberOfPreservedInstances> getMinNumberOfPreservedInstances() {
		return this.minNumberOfPreservedInstances;
	}

	public void setMinNumberOfPreservedInstances(
			final List<MinNumberOfPreservedInstances> minNumberOfPreservedInstances) {
		this.minNumberOfPreservedInstances = minNumberOfPreservedInstances;
	}

	public List<MaxNumberOfImpactedInstances> getMaxNumberOfImpactedInstances() {
		return this.maxNumberOfImpactedInstances;
	}

	public void setMaxNumberOfImpactedInstances(
			final List<MaxNumberOfImpactedInstances> maxNumberOfImpactedInstances) {
		this.maxNumberOfImpactedInstances = maxNumberOfImpactedInstances;
	}

	@NotNull
	public Boolean getIsImpactMitigationRequested() {
		return this.isImpactMitigationRequested;
	}

	public void setIsImpactMitigationRequested(@NotNull final Boolean isImpactMitigationRequested) {
		this.isImpactMitigationRequested = isImpactMitigationRequested;
	}

	public Time getMaxUndetectableInterruptionTime() {
		return this.maxUndetectableInterruptionTime;
	}

	public void setMaxUndetectableInterruptionTime(final Time maxUndetectableInterruptionTime) {
		this.maxUndetectableInterruptionTime = maxUndetectableInterruptionTime;
	}
}
