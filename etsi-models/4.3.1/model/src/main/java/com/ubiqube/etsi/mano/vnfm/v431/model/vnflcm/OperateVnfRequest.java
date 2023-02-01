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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnflcm;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents request parameters for the \&quot;Operate VNF\&quot;
 * operation. NOTE: The \&quot;stopType\&quot; and
 * \&quot;gracefulStopTimeout\&quot; attributes shall be absent, when the
 * \&quot;changeStateTo\&quot; attribute is equal to \&quot;STARTED\&quot;. The
 * \&quot;gracefulStopTimeout\&quot; attribute shall be present, when the
 * \&quot;changeStateTo\&quot; is equal to \&quot;STOPPED\&quot; and the
 * \&quot;stopType\&quot; attribute is equal to \&quot;GRACEFUL\&quot;. The
 * \&quot;gracefulStopTimeout\&quot; attribute shall be absent, when the
 * \&quot;changeStateTo\&quot; attribute is equal to \&quot;STOPPED\&quot; and
 * the \&quot;stopType\&quot; attribute is equal to \&quot;FORCEFUL\&quot;. The
 * request shall be treated as if the \&quot;stopType\&quot; attribute has been
 * set to \&quot;FORCEFUL\&quot;, when the \&quot;changeStateTo\&quot; attribute
 * is equal to \&quot;STOPPED\&quot; and the \&quot;stopType\&quot; attribute is
 * absent.
 */
@Schema(description = "This type represents request parameters for the \"Operate VNF\" operation. NOTE: The \"stopType\" and \"gracefulStopTimeout\" attributes shall be absent,        when the \"changeStateTo\" attribute is equal to \"STARTED\".        The \"gracefulStopTimeout\" attribute shall be present, when the \"changeStateTo\"        is equal to \"STOPPED\" and the \"stopType\" attribute is equal to \"GRACEFUL\".        The \"gracefulStopTimeout\" attribute shall be absent, when the \"changeStateTo\"        attribute is equal to \"STOPPED\" and the \"stopType\" attribute is equal to \"FORCEFUL\".        The request shall be treated as if the \"stopType\" attribute has been set to \"FORCEFUL\",        when the \"changeStateTo\" attribute is equal to \"STOPPED\" and the \"stopType\" attribute        is absent. ")
@Validated

public class OperateVnfRequest {
	@JsonProperty("changeStateTo")
	private VnfOperationalStateType changeStateTo = null;

	@JsonProperty("stopType")
	private StopType stopType = null;

	@JsonProperty("gracefulStopTimeout")
	private Integer gracefulStopTimeout = null;

	@JsonProperty("additionalParams")
	private Map<String, String> additionalParams = null;

	public OperateVnfRequest changeStateTo(final VnfOperationalStateType changeStateTo) {
		this.changeStateTo = changeStateTo;
		return this;
	}

	/**
	 * Get changeStateTo
	 *
	 * @return changeStateTo
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public VnfOperationalStateType getChangeStateTo() {
		return changeStateTo;
	}

	public void setChangeStateTo(final VnfOperationalStateType changeStateTo) {
		this.changeStateTo = changeStateTo;
	}

	public OperateVnfRequest stopType(final StopType stopType) {
		this.stopType = stopType;
		return this;
	}

	/**
	 * Get stopType
	 *
	 * @return stopType
	 **/
	@Schema(description = "")

	@Valid
	public StopType getStopType() {
		return stopType;
	}

	public void setStopType(final StopType stopType) {
		this.stopType = stopType;
	}

	public OperateVnfRequest gracefulStopTimeout(final Integer gracefulStopTimeout) {
		this.gracefulStopTimeout = gracefulStopTimeout;
		return this;
	}

	/**
	 * The time interval (in seconds) to wait for the VNF to be taken out of service
	 * during graceful stop, before stopping the VNF. See note.
	 *
	 * @return gracefulStopTimeout
	 **/
	@Schema(description = "The time interval (in seconds) to wait for the VNF to be taken out of service  during graceful stop, before stopping the VNF. See note. ")

	public Integer getGracefulStopTimeout() {
		return gracefulStopTimeout;
	}

	public void setGracefulStopTimeout(final Integer gracefulStopTimeout) {
		this.gracefulStopTimeout = gracefulStopTimeout;
	}

	public OperateVnfRequest additionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
		return this;
	}

	/**
	 * Get additionalParams
	 *
	 * @return additionalParams
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final OperateVnfRequest operateVnfRequest = (OperateVnfRequest) o;
		return Objects.equals(this.changeStateTo, operateVnfRequest.changeStateTo) &&
				Objects.equals(this.stopType, operateVnfRequest.stopType) &&
				Objects.equals(this.gracefulStopTimeout, operateVnfRequest.gracefulStopTimeout) &&
				Objects.equals(this.additionalParams, operateVnfRequest.additionalParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(changeStateTo, stopType, gracefulStopTimeout, additionalParams);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class OperateVnfRequest {\n");

		sb.append("    changeStateTo: ").append(toIndentedString(changeStateTo)).append("\n");
		sb.append("    stopType: ").append(toIndentedString(stopType)).append("\n");
		sb.append("    gracefulStopTimeout: ").append(toIndentedString(gracefulStopTimeout)).append("\n");
		sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
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
