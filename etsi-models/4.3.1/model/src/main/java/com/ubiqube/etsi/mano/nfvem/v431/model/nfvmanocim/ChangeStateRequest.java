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

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents request parameters for changing the state of a managed
 * entity. * NOTE 1: In the present document version, a request shall only
 * include an operational state change (attribute
 * \&quot;operationalStateChange\&quot;) or an administrative state change
 * request (attribute \&quot;administrativeStateChange\&quot;), but not both. *
 * NOTE 2: The \&quot;stopType\&quot; shall only be provided when the
 * \&quot;operationalStateAction\&quot; attribute is equal to \&quot;STOP\&quot;
 * or \&quot;RESTART\&quot;. The \&quot;gracefulStopTimeout\&quot; shall be
 * absent when the \&quot;stopType\&quot; attribute is equal to
 * \&quot;FORCEFUL\&quot;, and may be provided otherwise.
 */
@Schema(description = "This type represents request parameters for changing the state of a managed entity. * NOTE 1: In the present document version, a request shall only include an operational state change             (attribute \"operationalStateChange\") or an administrative state change request (attribute             \"administrativeStateChange\"), but not both. * NOTE 2: The \"stopType\" shall only be provided when the \"operationalStateAction\" attribute is equal             to \"STOP\" or \"RESTART\". The \"gracefulStopTimeout\" shall be absent when the \"stopType\" attribute             is equal to \"FORCEFUL\", and may be provided otherwise. ")
@Validated

public class ChangeStateRequest {
	@JsonProperty("operationalStateChange")
	private ChangeStateRequestOperationalStateChange operationalStateChange = null;

	@JsonProperty("administrativeStateChange")
	private ChangeStateRequestAdministrativeStateChange administrativeStateChange = null;

	public ChangeStateRequest operationalStateChange(final ChangeStateRequestOperationalStateChange operationalStateChange) {
		this.operationalStateChange = operationalStateChange;
		return this;
	}

	/**
	 * Get operationalStateChange
	 *
	 * @return operationalStateChange
	 **/
	@Schema(description = "")

	@Valid
	public ChangeStateRequestOperationalStateChange getOperationalStateChange() {
		return operationalStateChange;
	}

	public void setOperationalStateChange(final ChangeStateRequestOperationalStateChange operationalStateChange) {
		this.operationalStateChange = operationalStateChange;
	}

	public ChangeStateRequest administrativeStateChange(final ChangeStateRequestAdministrativeStateChange administrativeStateChange) {
		this.administrativeStateChange = administrativeStateChange;
		return this;
	}

	/**
	 * Get administrativeStateChange
	 *
	 * @return administrativeStateChange
	 **/
	@Schema(description = "")

	@Valid
	public ChangeStateRequestAdministrativeStateChange getAdministrativeStateChange() {
		return administrativeStateChange;
	}

	public void setAdministrativeStateChange(final ChangeStateRequestAdministrativeStateChange administrativeStateChange) {
		this.administrativeStateChange = administrativeStateChange;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ChangeStateRequest changeStateRequest = (ChangeStateRequest) o;
		return Objects.equals(this.operationalStateChange, changeStateRequest.operationalStateChange) &&
				Objects.equals(this.administrativeStateChange, changeStateRequest.administrativeStateChange);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operationalStateChange, administrativeStateChange);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ChangeStateRequest {\n");

		sb.append("    operationalStateChange: ").append(toIndentedString(operationalStateChange)).append("\n");
		sb.append("    administrativeStateChange: ").append(toIndentedString(administrativeStateChange)).append("\n");
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
