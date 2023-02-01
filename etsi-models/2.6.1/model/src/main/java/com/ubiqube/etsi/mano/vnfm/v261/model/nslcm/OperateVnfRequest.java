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

/*
 * SOL003 - VNF Lifecycle Management interface
 * SOL003 - VNF Lifecycle Management interface definition  IMPORTANT: Please note that this file might be not aligned to the current version of the ETSI Group Specification it refers to. In case of discrepancies the published ETSI Group Specification takes precedence.  In clause 4.3.2 of ETSI GS NFV-SOL 003 v2.4.1, an attribute-based filtering mechanism is defined. This mechanism is currently not included in the corresponding OpenAPI design for this GS version. Changes to the attribute-based filtering mechanism are being considered in v2.5.1 of this GS for inclusion in the corresponding future ETSI NFV OpenAPI design. Please report bugs to https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis&list_id=61&product=NFV&resolution=
 *
 * OpenAPI spec version: 1.1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.ubiqube.etsi.mano.vnfm.v261.model.nslcm;

import java.util.Map;

import jakarta.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.common.v261.model.nslcm.VnfOperationalStateType;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type represents request parameters for the \&quot;Operate VNF\&quot;
 * operation.
 */
@Schema(description = "This type represents request parameters for the \"Operate VNF\" operation. ")

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
	 * The desired operational state (i.e. started or stopped) to change the VNF to.
	 *
	 * @return changeStateTo
	 **/
	@JsonProperty("changeStateTo")
	@Schema(required = true, description = "The desired operational state (i.e. started or stopped) to change the VNF to. ")
	@Nonnull
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
	 * It signals whether forceful or graceful stop is requested. The “stopType” and
	 * “gracefulStopTimeout” attributes shall be absent, when the “changeStateTo”
	 * attribute is equal to “STARTED”. The “gracefulStopTimeout” attribute shall be
	 * present, when the “changeStateTo” is equal to “STOPPED” and the “stopType”
	 * attribute is equal to “GRACEFUL”. The “gracefulStopTimeout” attribute shall
	 * be absent, when the “changeStateTo” attribute is equal to “STOPPED” and the
	 * “stopType” attribute is equal to “FORCEFUL”. The request shall be treated as
	 * if the “stopType” attribute was set to ”FORCEFUL”, when the “changeStateTo”
	 * attribute is equal to “STOPPED” and the “stopType” attribute is absent.
	 *
	 * @return stopType
	 **/
	@JsonProperty("stopType")
	@Schema(description = "It signals whether forceful or graceful stop is requested. The “stopType” and “gracefulStopTimeout” attributes shall be absent, when the “changeStateTo” attribute is equal to “STARTED”. The “gracefulStopTimeout” attribute shall be present, when the “changeStateTo” is equal to “STOPPED” and the “stopType” attribute is equal to “GRACEFUL”. The “gracefulStopTimeout” attribute shall be absent, when the “changeStateTo” attribute is equal to “STOPPED” and the “stopType” attribute is equal to “FORCEFUL”. The request shall be treated as if the “stopType” attribute was set to ”FORCEFUL”, when the “changeStateTo” attribute is equal to “STOPPED” and the “stopType” attribute is absent. ")
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
	 * during graceful stop, before stopping the VNF. The “stopType” and
	 * “gracefulStopTimeout” attributes shall be absent, when the “changeStateTo”
	 * attribute is equal to “STARTED”. The “gracefulStopTimeout” attribute shall be
	 * present, when the “changeStateTo” is equal to “STOPPED” and the “stopType”
	 * attribute is equal to “GRACEFUL”. The “gracefulStopTimeout” attribute shall
	 * be absent, when the “changeStateTo” attribute is equal to “STOPPED” and the
	 * “stopType” attribute is equal to “FORCEFUL”. The request shall be treated as
	 * if the “stopType” attribute was set to ”FORCEFUL”, when the “changeStateTo”
	 * attribute is equal to “STOPPED” and the “stopType” attribute is absent.
	 *
	 * @return gracefulStopTimeout
	 **/
	@JsonProperty("gracefulStopTimeout")
	@Schema(description = "The time interval (in seconds) to wait for the VNF to be taken out of service during graceful stop, before stopping the VNF. The “stopType” and “gracefulStopTimeout” attributes shall be absent, when the “changeStateTo” attribute is equal to “STARTED”. The “gracefulStopTimeout” attribute shall be present, when the “changeStateTo” is equal to “STOPPED” and the “stopType” attribute is equal to “GRACEFUL”. The “gracefulStopTimeout” attribute shall be absent, when the “changeStateTo” attribute is equal to “STOPPED” and the “stopType” attribute is equal to “FORCEFUL”. The request shall be treated as if the “stopType” attribute was set to ”FORCEFUL”, when the “changeStateTo” attribute is equal to “STOPPED” and the “stopType” attribute is absent. ")
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
	 * Additional parameters passed by the NFVO as input to the process, specific to
	 * the VNF of which the operation status is changed, as declared in the VNFD as
	 * part of \&quot;OperateVnfOpConfig\&quot;.
	 *
	 * @return additionalParams
	 **/
	@JsonProperty("additionalParams")
	@Schema(description = "Additional parameters passed by the NFVO as input to the process, specific to the VNF of which the operation status is changed, as declared in the VNFD as part of \"OperateVnfOpConfig\". ")
	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
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
