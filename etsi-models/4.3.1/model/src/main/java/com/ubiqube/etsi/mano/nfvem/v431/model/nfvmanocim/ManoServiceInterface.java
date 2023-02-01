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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents an individual NFV-MANO service interface produced by an
 * NFV-MANO functional entity. * NOTE 1: The information to be provided in this
 * attribute shall relate to the specification and version of the specification.
 * For instance, \&quot;ETSI GS NFV-SOL 003 (V2.4.1)\&quot;. * NOTE 2: If this
 * attribute is not present, the value of this parameter is undefined. Overload
 * is handled by the error handling schemes defined by the applicable API
 * specification. * NOTE 3: Due to the security sensitive information associated
 * to the attribute, based on access control policies, the API consumer might
 * have read only, write only, read/write, or no access at all to the
 * attribute&#x27;s value. In case the API consumer is not allowed to read the
 * value of the security sensitive attribute, the attribute shall be omitted
 * when the information is to be provided in a response message, and shall be
 * provided otherwise. In case the API consumer is not allowed to modify the
 * value of the security sensitive attribute, and the modification request
 * includes new attribute values, the whole modification request shall be
 * rejected, and proper error information returned.
 */
@Schema(description = "This type represents an individual NFV-MANO service interface produced by an NFV-MANO functional entity. * NOTE 1: The information to be provided in this attribute shall relate to the specification and             version of the specification. For instance, \"ETSI GS NFV-SOL 003 (V2.4.1)\". * NOTE 2: If this attribute is not present, the value of this parameter is undefined. Overload is             handled by the error handling schemes defined by the applicable API specification. * NOTE 3: Due to the security sensitive information associated to the attribute, based on access             control policies, the API consumer might have read only, write only, read/write, or no access             at all to the attribute's value. In case the API consumer is not allowed to read the value of             the security sensitive attribute, the attribute shall be omitted when the information is to be             provided in a response message, and shall be provided otherwise. In case the API consumer is             not allowed to modify the value of the security sensitive attribute, and the modification request             includes new attribute values, the whole modification request shall be rejected, and proper             error information returned. ")
@Validated

public class ManoServiceInterface {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("type")
	private String type = null;

	@JsonProperty("standardVersion")
	private String standardVersion = null;

	@JsonProperty("providerSpecificApiVersion")
	private String providerSpecificApiVersion = null;

	@JsonProperty("apiVersion")
	private String apiVersion = null;

	@JsonProperty("apiEndpoint")
	private ManoServiceInterfaceApiEndpoint apiEndpoint = null;

	@JsonProperty("maxConcurrentIntOpNumber")
	private Integer maxConcurrentIntOpNumber = null;

	@JsonProperty("supportedOperations")
	@Valid
	private List<ManoServiceInterfaceSupportedOperations> supportedOperations = new ArrayList<>();

	@JsonProperty("interfaceState")
	private ManoServiceInterfaceInterfaceState interfaceState = null;

	@JsonProperty("securityInfo")
	private ServerInterfaceSecurityInfo securityInfo = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	public ManoServiceInterface id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * An identifier that is unique for the respective type within a NFV-MANO
	 * functional entity, but that need not be globally unique. Representation:
	 * string of variable length..
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "An identifier that is unique for the respective type within a NFV-MANO functional entity, but that need not be globally unique. Representation: string of variable length.. ")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public ManoServiceInterface name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Human-readable name of the NFV-MANO functional entity interface. This
	 * attribute can be modified with the PATCH method.
	 *
	 * @return name
	 **/
	@Schema(required = true, description = "Human-readable name of the NFV-MANO functional entity interface. This attribute can be modified with the PATCH method. ")
	@NotNull

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ManoServiceInterface type(final String type) {
		this.type = type;
		return this;
	}

	/**
	 * Type of the NFV-MANO service interface produced by the NFV-MANO functional
	 * entity. Valid values are defined in clause 5.6.4.3.
	 *
	 * @return type
	 **/
	@Schema(required = true, description = "Type of the NFV-MANO service interface produced by the NFV-MANO functional entity. Valid values are defined in clause 5.6.4.3. ")
	@NotNull

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public ManoServiceInterface standardVersion(final String standardVersion) {
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

	public ManoServiceInterface providerSpecificApiVersion(final String providerSpecificApiVersion) {
		this.providerSpecificApiVersion = providerSpecificApiVersion;
		return this;
	}

	/**
	 * A version.
	 *
	 * @return providerSpecificApiVersion
	 **/
	@Schema(required = true, description = "A version. ")
	@NotNull

	public String getProviderSpecificApiVersion() {
		return providerSpecificApiVersion;
	}

	public void setProviderSpecificApiVersion(final String providerSpecificApiVersion) {
		this.providerSpecificApiVersion = providerSpecificApiVersion;
	}

	public ManoServiceInterface apiVersion(final String apiVersion) {
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

	public ManoServiceInterface apiEndpoint(final ManoServiceInterfaceApiEndpoint apiEndpoint) {
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
	public ManoServiceInterfaceApiEndpoint getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(final ManoServiceInterfaceApiEndpoint apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	public ManoServiceInterface maxConcurrentIntOpNumber(final Integer maxConcurrentIntOpNumber) {
		this.maxConcurrentIntOpNumber = maxConcurrentIntOpNumber;
		return this;
	}

	/**
	 * Maximum number of concurrent operation requests supported on this interface.
	 * See note 2.
	 *
	 * @return maxConcurrentIntOpNumber
	 **/
	@Schema(description = "Maximum number of concurrent operation requests supported on this interface. See note 2. ")

	public Integer getMaxConcurrentIntOpNumber() {
		return maxConcurrentIntOpNumber;
	}

	public void setMaxConcurrentIntOpNumber(final Integer maxConcurrentIntOpNumber) {
		this.maxConcurrentIntOpNumber = maxConcurrentIntOpNumber;
	}

	public ManoServiceInterface supportedOperations(final List<ManoServiceInterfaceSupportedOperations> supportedOperations) {
		this.supportedOperations = supportedOperations;
		return this;
	}

	public ManoServiceInterface addSupportedOperationsItem(final ManoServiceInterfaceSupportedOperations supportedOperationsItem) {
		this.supportedOperations.add(supportedOperationsItem);
		return this;
	}

	/**
	 * Information about supported operations of this interface.
	 *
	 * @return supportedOperations
	 **/
	@Schema(required = true, description = "Information about supported operations of this interface. ")
	@NotNull
	@Valid
	@Size(min = 1)
	public List<ManoServiceInterfaceSupportedOperations> getSupportedOperations() {
		return supportedOperations;
	}

	public void setSupportedOperations(final List<ManoServiceInterfaceSupportedOperations> supportedOperations) {
		this.supportedOperations = supportedOperations;
	}

	public ManoServiceInterface interfaceState(final ManoServiceInterfaceInterfaceState interfaceState) {
		this.interfaceState = interfaceState;
		return this;
	}

	/**
	 * Get interfaceState
	 *
	 * @return interfaceState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ManoServiceInterfaceInterfaceState getInterfaceState() {
		return interfaceState;
	}

	public void setInterfaceState(final ManoServiceInterfaceInterfaceState interfaceState) {
		this.interfaceState = interfaceState;
	}

	public ManoServiceInterface securityInfo(final ServerInterfaceSecurityInfo securityInfo) {
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
	public ServerInterfaceSecurityInfo getSecurityInfo() {
		return securityInfo;
	}

	public void setSecurityInfo(final ServerInterfaceSecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
	}

	public ManoServiceInterface metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Get metadata
	 *
	 * @return metadata
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ManoServiceInterface manoServiceInterface = (ManoServiceInterface) o;
		return Objects.equals(this.id, manoServiceInterface.id) &&
				Objects.equals(this.name, manoServiceInterface.name) &&
				Objects.equals(this.type, manoServiceInterface.type) &&
				Objects.equals(this.standardVersion, manoServiceInterface.standardVersion) &&
				Objects.equals(this.providerSpecificApiVersion, manoServiceInterface.providerSpecificApiVersion) &&
				Objects.equals(this.apiVersion, manoServiceInterface.apiVersion) &&
				Objects.equals(this.apiEndpoint, manoServiceInterface.apiEndpoint) &&
				Objects.equals(this.maxConcurrentIntOpNumber, manoServiceInterface.maxConcurrentIntOpNumber) &&
				Objects.equals(this.supportedOperations, manoServiceInterface.supportedOperations) &&
				Objects.equals(this.interfaceState, manoServiceInterface.interfaceState) &&
				Objects.equals(this.securityInfo, manoServiceInterface.securityInfo) &&
				Objects.equals(this.metadata, manoServiceInterface.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type, standardVersion, providerSpecificApiVersion, apiVersion, apiEndpoint, maxConcurrentIntOpNumber, supportedOperations, interfaceState, securityInfo, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoServiceInterface {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    standardVersion: ").append(toIndentedString(standardVersion)).append("\n");
		sb.append("    providerSpecificApiVersion: ").append(toIndentedString(providerSpecificApiVersion)).append("\n");
		sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
		sb.append("    apiEndpoint: ").append(toIndentedString(apiEndpoint)).append("\n");
		sb.append("    maxConcurrentIntOpNumber: ").append(toIndentedString(maxConcurrentIntOpNumber)).append("\n");
		sb.append("    supportedOperations: ").append(toIndentedString(supportedOperations)).append("\n");
		sb.append("    interfaceState: ").append(toIndentedString(interfaceState)).append("\n");
		sb.append("    securityInfo: ").append(toIndentedString(securityInfo)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
