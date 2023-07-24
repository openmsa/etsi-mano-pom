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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TerminateVnfRequest
 */

public class TerminateVnfRequest {
	/**
	 * Indicates whether forceful or graceful termination is requested. Permitted values: * FORCEFUL: The VNFM will shut down the VNF and release the resources immediately after accepting the request. * GRACEFUL: The VNFM will first arrange to take the VNF out of service after accepting the request. Once the operation of taking the VNF out of service finishes (irrespective of whether it has succeeded or failed) or once the timer value specified in the \&quot;gracefulTerminationTimeout\&quot;
	 * attribute expires, the VNFM will shut down the VNF and release the resources.
	 */
	public enum TerminationTypeEnum {
		FORCEFUL("FORCEFUL"),

		GRACEFUL("GRACEFUL");

		private final String value;

		TerminationTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TerminationTypeEnum fromValue(final String text) {
			for (final TerminationTypeEnum b : TerminationTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("terminationType")
	private TerminationTypeEnum terminationType = null;

	@JsonProperty("gracefulTerminationTimeout")
	private Integer gracefulTerminationTimeout = null;

	@JsonProperty("additionalParams")
	private Map<String, String> additionalParams = null;

	public TerminateVnfRequest terminationType(final TerminationTypeEnum terminationType) {
		this.terminationType = terminationType;
		return this;
	}

	/**
	 * Indicates whether forceful or graceful termination is requested. Permitted values: * FORCEFUL: The VNFM will shut down the VNF and release the resources immediately after accepting the request. * GRACEFUL: The VNFM will first arrange to take the VNF out of service after accepting the request. Once the operation of taking the VNF out of service finishes (irrespective of whether it has succeeded or failed) or once the timer value specified in the \&quot;gracefulTerminationTimeout\&quot;
	 * attribute expires, the VNFM will shut down the VNF and release the resources.
	 *
	 * @return terminationType
	 **/
	@JsonProperty("terminationType")
	@Schema(required = true, description = "Indicates whether forceful or graceful termination is requested. Permitted values: * FORCEFUL: The VNFM will shut down the VNF and release the   resources immediately after accepting the request. * GRACEFUL: The VNFM will first arrange to take the VNF out of service after accepting the request. Once the operation of taking the VNF out of service finishes (irrespective of whether it has succeeded or failed) or once the timer value specified in the \"gracefulTerminationTimeout\" attribute expires, the VNFM will shut down the VNF and release the resources. ")
	@Nonnull
	public TerminationTypeEnum getTerminationType() {
		return terminationType;
	}

	public void setTerminationType(final TerminationTypeEnum terminationType) {
		this.terminationType = terminationType;
	}

	public TerminateVnfRequest gracefulTerminationTimeout(final Integer gracefulTerminationTimeout) {
		this.gracefulTerminationTimeout = gracefulTerminationTimeout;
		return this;
	}

	/**
	 * This attribute is only applicable in case of graceful termination. It defines the time to wait for the VNF to be taken out of service before shutting down the VNF and releasing the resources. The unit is seconds. If not given and the \&quot;terminationType\&quot; attribute is set to \&quot;GRACEFUL\&quot;, it is expected that the VNFM waits for the successful taking out of service of the VNF, no matter how long it takes, before shutting down the VNF and releasing the resources.
	 *
	 * @return gracefulTerminationTimeout
	 **/
	@JsonProperty("gracefulTerminationTimeout")
	@Schema(description = "This attribute is only applicable in case of graceful termination.  It defines the time to wait for the VNF to be taken out of service before shutting down the VNF and releasing the resources. The unit is seconds. If not given and the \"terminationType\" attribute is set to \"GRACEFUL\", it is expected that the VNFM waits for the successful taking out of service of the VNF, no matter how long it takes, before shutting down the VNF and releasing the resources. ")
	public Integer getGracefulTerminationTimeout() {
		return gracefulTerminationTimeout;
	}

	public void setGracefulTerminationTimeout(final Integer gracefulTerminationTimeout) {
		this.gracefulTerminationTimeout = gracefulTerminationTimeout;
	}

	public TerminateVnfRequest additionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
		return this;
	}

	/**
	 * Additional parameters passed by the NFVO as input to the termination process, specific to the VNF being terminated, as declared in the VNFD as part of \&quot;TerminateVnfOpConfig\&quot;.
	 *
	 * @return additionalParams
	 **/
	@JsonProperty("additionalParams")
	@Schema(description = "Additional parameters passed by the NFVO as input to the termination process, specific to the VNF being terminated, as declared in the VNFD as part of \"TerminateVnfOpConfig\". ")
	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class TerminateVnfRequest {\n");

		sb.append("    terminationType: ").append(toIndentedString(terminationType)).append("\n");
		sb.append("    gracefulTerminationTimeout: ").append(toIndentedString(gracefulTerminationTimeout)).append("\n");
		sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
