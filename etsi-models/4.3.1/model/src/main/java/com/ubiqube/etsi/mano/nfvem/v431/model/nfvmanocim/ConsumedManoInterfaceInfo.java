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
 * This type represents an interface consumed by the producer NFV MANO
 * functional entity from another peer functional entity. * NOTE 1: The
 * information to be provided in this attribute shall relate to the
 * specification and its version. For instance, \&quot;ETSI GS NFV-SOL 003
 * (V2.4.1)\&quot;. * NOTE 2: Due to the security sensitive information
 * associated to the attribute, based on access control policies, the API
 * consumer might have read only, write only, read/write, or no access at all to
 * the attribute&#x27;s value. In case the API consumer is not allowed to read
 * the value of the security sensitive attribute, the attribute shall be omitted
 * when the information is to be provided in a response message, and shall be
 * provided otherwise. In case the API consumer is not allowed to modify the
 * value of the security sensitive attribute, and the modification request
 * includes new attribute values, the whole modification request shall be
 * rejected, and proper error information returned.
 */
@Schema(description = "This type represents an interface consumed by the producer NFV MANO functional entity from another peer functional entity. * NOTE 1: The information to be provided in this attribute shall relate to the specification and its version.             For instance, \"ETSI GS NFV-SOL 003 (V2.4.1)\". * NOTE 2: Due to the security sensitive information associated to the attribute, based on access control             policies, the API consumer might have read only, write only, read/write, or no access at all to             the attribute's value. In case the API consumer is not allowed to read the value of the security             sensitive attribute, the attribute shall be omitted when the information is to be provided in a             response message, and shall be provided otherwise. In case the API consumer is not allowed to             modify the value of the security sensitive attribute, and the modification request includes new             attribute values, the whole modification request shall be rejected, and proper error information returned. ")
@Validated

public class ConsumedManoInterfaceInfo {
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("standardVersion")
	private String standardVersion = null;

	@JsonProperty("apiVersion")
	private String apiVersion = null;

	@JsonProperty("apiEndpoint")
	private ConsumedManoInterfaceInfoApiEndpoint apiEndpoint = null;

	@JsonProperty("securityInfo")
	private ClientInterfaceSecurityInfo securityInfo = null;

	public ConsumedManoInterfaceInfo name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Human-readable name of the NFV-MANO interface.
	 *
	 * @return name
	 **/
	@Schema(required = true, description = "Human-readable name of the NFV-MANO interface. ")
	@NotNull

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ConsumedManoInterfaceInfo type(final String type) {
		this.type = type;
		return this;
	}

	/**
	 * Type of the NFV-MANO service interface consumed by the NFV-MANO functional
	 * entity. Valid values are defined in clause 5.6.4.3.
	 *
	 * @return type
	 **/
	@Schema(required = true, description = "Type of the NFV-MANO service interface consumed by the NFV-MANO functional entity. Valid values are defined in clause 5.6.4.3. ")
	@NotNull

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public ConsumedManoInterfaceInfo standardVersion(final String standardVersion) {
		this.standardVersion = standardVersion;
		return this;
	}

	/**
	 * A version.
	 *
	 * @return standardVersion
	 **/
	@Schema(required = true, description = "A version. ")
	@NotNull

	public String getStandardVersion() {
		return standardVersion;
	}

	public void setStandardVersion(final String standardVersion) {
		this.standardVersion = standardVersion;
	}

	public ConsumedManoInterfaceInfo apiVersion(final String apiVersion) {
		this.apiVersion = apiVersion;
		return this;
	}

	/**
	 * A version.
	 *
	 * @return apiVersion
	 **/
	@Schema(required = true, description = "A version. ")
	@NotNull

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(final String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public ConsumedManoInterfaceInfo apiEndpoint(final ConsumedManoInterfaceInfoApiEndpoint apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
		return this;
	}

	/**
	 * Get apiEndpoint
	 *
	 * @return apiEndpoint
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ConsumedManoInterfaceInfoApiEndpoint getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(final ConsumedManoInterfaceInfoApiEndpoint apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	public ConsumedManoInterfaceInfo securityInfo(final ClientInterfaceSecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
		return this;
	}

	/**
	 * Get securityInfo
	 *
	 * @return securityInfo
	 **/
	@Schema(description = "")

	@Valid
	public ClientInterfaceSecurityInfo getSecurityInfo() {
		return securityInfo;
	}

	public void setSecurityInfo(final ClientInterfaceSecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ConsumedManoInterfaceInfo consumedManoInterfaceInfo = (ConsumedManoInterfaceInfo) o;
		return Objects.equals(this.name, consumedManoInterfaceInfo.name) &&
				Objects.equals(this.type, consumedManoInterfaceInfo.type) &&
				Objects.equals(this.standardVersion, consumedManoInterfaceInfo.standardVersion) &&
				Objects.equals(this.apiVersion, consumedManoInterfaceInfo.apiVersion) &&
				Objects.equals(this.apiEndpoint, consumedManoInterfaceInfo.apiEndpoint) &&
				Objects.equals(this.securityInfo, consumedManoInterfaceInfo.securityInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type, standardVersion, apiVersion, apiEndpoint, securityInfo);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ConsumedManoInterfaceInfo {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    standardVersion: ").append(toIndentedString(standardVersion)).append("\n");
		sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
		sb.append("    apiEndpoint: ").append(toIndentedString(apiEndpoint)).append("\n");
		sb.append("    securityInfo: ").append(toIndentedString(securityInfo)).append("\n");
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
