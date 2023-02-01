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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Information and current values of the NFV-MANO functional entity’s
 * application state.
 */
@Schema(description = "Information and current values of the NFV-MANO functional entity’s application state. ")
@Validated

public class ManoEntityManoApplicationState {
	@JsonProperty("operationalState")
	private OperationalStateEnumType operationalState = null;

	@JsonProperty("administrativeState")
	private AdministrativeStateEnumType administrativeState = null;

	@JsonProperty("usageState")
	private UsageStateEnumType usageState = null;

	public ManoEntityManoApplicationState operationalState(final OperationalStateEnumType operationalState) {
		this.operationalState = operationalState;
		return this;
	}

	/**
	 * Get operationalState
	 *
	 * @return operationalState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public OperationalStateEnumType getOperationalState() {
		return operationalState;
	}

	public void setOperationalState(final OperationalStateEnumType operationalState) {
		this.operationalState = operationalState;
	}

	public ManoEntityManoApplicationState administrativeState(final AdministrativeStateEnumType administrativeState) {
		this.administrativeState = administrativeState;
		return this;
	}

	/**
	 * Get administrativeState
	 *
	 * @return administrativeState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public AdministrativeStateEnumType getAdministrativeState() {
		return administrativeState;
	}

	public void setAdministrativeState(final AdministrativeStateEnumType administrativeState) {
		this.administrativeState = administrativeState;
	}

	public ManoEntityManoApplicationState usageState(final UsageStateEnumType usageState) {
		this.usageState = usageState;
		return this;
	}

	/**
	 * Get usageState
	 *
	 * @return usageState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public UsageStateEnumType getUsageState() {
		return usageState;
	}

	public void setUsageState(final UsageStateEnumType usageState) {
		this.usageState = usageState;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ManoEntityManoApplicationState manoEntityManoApplicationState = (ManoEntityManoApplicationState) o;
		return Objects.equals(this.operationalState, manoEntityManoApplicationState.operationalState) &&
				Objects.equals(this.administrativeState, manoEntityManoApplicationState.administrativeState) &&
				Objects.equals(this.usageState, manoEntityManoApplicationState.usageState);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operationalState, administrativeState, usageState);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoEntityManoApplicationState {\n");

		sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
		sb.append("    administrativeState: ").append(toIndentedString(administrativeState)).append("\n");
		sb.append("    usageState: ").append(toIndentedString(usageState)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
