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

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents attribute modifications that were performed on an
 * Individual NFV-MANO service interface\&quot; resource. The attributes that
 * can be included consist of those requested to be modified explicitly in the
 * \&quot;ManoServiceInterfaceModificationRequest\&quot; data structure. If
 * applicable, additional attributes of the \&quot;ManoServiceInterface\&quot;
 * data structure that were modified implicitly shall also be provided. * NOTE:
 * Due to the security sensitive information associated to the attribute, based
 * on access control policies, the API consumer might have read only, write
 * only, read/write, or no access at all to the attribute&#x27;s value. In case
 * the API consumer is not allowed to read the value of the security sensitive
 * attribute, the attribute shall be omitted when the information is to be
 * provided in a response message.
 */
@Schema(description = "This type represents attribute modifications that were performed on an Individual NFV-MANO service interface\" resource. The attributes that can be included consist of those requested to be modified explicitly in the \"ManoServiceInterfaceModificationRequest\" data structure. If applicable, additional attributes of the \"ManoServiceInterface\" data structure that were modified implicitly shall also be provided. * NOTE: Due to the security sensitive information associated to the attribute, based on access control         policies, the API consumer might have read only, write only, read/write, or no access at all         to the attribute's value. In case the API consumer is not allowed to read the value of the security         sensitive attribute, the attribute shall be omitted when the information is to be provided         in a response message. ")
@Validated

public class ManoServiceInterfaceModifications {
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("apiRoot")
	private String apiRoot = null;

	@JsonProperty("apiUri")
	private String apiUri = null;

	@JsonProperty("securityInfo")
	private ServerInterfaceSecurityInfo securityInfo = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	public ManoServiceInterfaceModifications name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * If present, this attribute signals modifications of the \"name\" attribute in
	 * \"ManoServiceInterface\", as defined in clause 5.6.2.11.
	 *
	 * @return name
	 **/
	@Schema(description = "If present, this attribute signals modifications of the \"name\" attribute in \"ManoServiceInterface\", as defined in clause 5.6.2.11. ")

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ManoServiceInterfaceModifications apiRoot(final String apiRoot) {
		this.apiRoot = apiRoot;
		return this;
	}

	/**
	 * String formatted according to IETF RFC 3986.
	 *
	 * @return apiRoot
	 **/
	@Schema(description = "String formatted according to IETF RFC 3986. ")

	public String getApiRoot() {
		return apiRoot;
	}

	public void setApiRoot(final String apiRoot) {
		this.apiRoot = apiRoot;
	}

	public ManoServiceInterfaceModifications apiUri(final String apiUri) {
		this.apiUri = apiUri;
		return this;
	}

	/**
	 * String formatted according to IETF RFC 3986.
	 *
	 * @return apiUri
	 **/
	@Schema(description = "String formatted according to IETF RFC 3986. ")

	public String getApiUri() {
		return apiUri;
	}

	public void setApiUri(final String apiUri) {
		this.apiUri = apiUri;
	}

	public ManoServiceInterfaceModifications securityInfo(final ServerInterfaceSecurityInfo securityInfo) {
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

	public ManoServiceInterfaceModifications metadata(final Map<String, String> metadata) {
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
		final ManoServiceInterfaceModifications manoServiceInterfaceModifications = (ManoServiceInterfaceModifications) o;
		return Objects.equals(this.name, manoServiceInterfaceModifications.name) &&
				Objects.equals(this.apiRoot, manoServiceInterfaceModifications.apiRoot) &&
				Objects.equals(this.apiUri, manoServiceInterfaceModifications.apiUri) &&
				Objects.equals(this.securityInfo, manoServiceInterfaceModifications.securityInfo) &&
				Objects.equals(this.metadata, manoServiceInterfaceModifications.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, apiRoot, apiUri, securityInfo, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ManoServiceInterfaceModifications {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    apiRoot: ").append(toIndentedString(apiRoot)).append("\n");
		sb.append("    apiUri: ").append(toIndentedString(apiUri)).append("\n");
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
