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
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents security related information for accessing an NFV-MANO
 * service interface produced by an NFV-MANO functional entity. * NOTE: Provided
 * configuration of the OAuth 2.0 authorization server information and
 * configuration shall be supported, and dynamic configuration may be supported.
 */
@Schema(description = "This type represents security related information for accessing an NFV-MANO service interface produced by an NFV-MANO functional entity. * NOTE: Provided configuration of the OAuth 2.0 authorization server information and configuration         shall be supported, and dynamic configuration may be supported. ")
@Validated

public class ClientInterfaceSecurityInfo {
	/**
	 * Gets or Sets authType
	 */
	public enum AuthTypeEnum {
		TLS_TUNNEL("TLS_TUNNEL"),

		OAUTH2("OAUTH2");

		private final String value;

		AuthTypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static AuthTypeEnum fromValue(final String text) {
			for (final AuthTypeEnum b : AuthTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("authType")
	@Valid
	private List<AuthTypeEnum> authType = new ArrayList<>();

	@JsonProperty("oauthServerInfo")
	private ClientInterfaceSecurityInfoOauthServerInfo oauthServerInfo = null;

	@JsonProperty("tlsTunnelInfo")
	private ClientInterfaceSecurityInfoTlsTunnelInfo tlsTunnelInfo = null;

	public ClientInterfaceSecurityInfo authType(final List<AuthTypeEnum> authType) {
		this.authType = authType;
		return this;
	}

	public ClientInterfaceSecurityInfo addAuthTypeItem(final AuthTypeEnum authTypeItem) {
		this.authType.add(authTypeItem);
		return this;
	}

	/**
	 * Type of API request authorization to be used by the API consumer accessing
	 * the API. The support of authorization methods for the API consumer is
	 * specified in clause 8.3.6 of ETSI GS NFV-SOL 013. Permitted values: -
	 * TLS_TUNNEL: Using TLS tunnel, as defined by TLS 1.2 in IETF RFC 5246. -
	 * OAUTH2: Using access token, as defined by the OAuth 2.0 specification in IETF
	 * RFC 6749.
	 *
	 * @return authType
	 **/
	@Schema(required = true, description = "Type of API request authorization to be used by the API consumer accessing the API. The support of authorization methods for the API consumer is specified in clause 8.3.6 of ETSI GS NFV-SOL 013. Permitted values: - TLS_TUNNEL: Using TLS tunnel, as defined by TLS 1.2 in IETF RFC 5246. - OAUTH2: Using access token, as defined by the OAuth 2.0 specification in IETF RFC 6749. ")
	@NotNull

	@Size(min = 1)
	public List<AuthTypeEnum> getAuthType() {
		return authType;
	}

	public void setAuthType(final List<AuthTypeEnum> authType) {
		this.authType = authType;
	}

	public ClientInterfaceSecurityInfo oauthServerInfo(final ClientInterfaceSecurityInfoOauthServerInfo oauthServerInfo) {
		this.oauthServerInfo = oauthServerInfo;
		return this;
	}

	/**
	 * Get oauthServerInfo
	 *
	 * @return oauthServerInfo
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ClientInterfaceSecurityInfoOauthServerInfo getOauthServerInfo() {
		return oauthServerInfo;
	}

	public void setOauthServerInfo(final ClientInterfaceSecurityInfoOauthServerInfo oauthServerInfo) {
		this.oauthServerInfo = oauthServerInfo;
	}

	public ClientInterfaceSecurityInfo tlsTunnelInfo(final ClientInterfaceSecurityInfoTlsTunnelInfo tlsTunnelInfo) {
		this.tlsTunnelInfo = tlsTunnelInfo;
		return this;
	}

	/**
	 * Get tlsTunnelInfo
	 *
	 * @return tlsTunnelInfo
	 **/
	@Schema(description = "")

	@Valid
	public ClientInterfaceSecurityInfoTlsTunnelInfo getTlsTunnelInfo() {
		return tlsTunnelInfo;
	}

	public void setTlsTunnelInfo(final ClientInterfaceSecurityInfoTlsTunnelInfo tlsTunnelInfo) {
		this.tlsTunnelInfo = tlsTunnelInfo;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final ClientInterfaceSecurityInfo clientInterfaceSecurityInfo = (ClientInterfaceSecurityInfo) o;
		return Objects.equals(this.authType, clientInterfaceSecurityInfo.authType) &&
				Objects.equals(this.oauthServerInfo, clientInterfaceSecurityInfo.oauthServerInfo) &&
				Objects.equals(this.tlsTunnelInfo, clientInterfaceSecurityInfo.tlsTunnelInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authType, oauthServerInfo, tlsTunnelInfo);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ClientInterfaceSecurityInfo {\n");

		sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
		sb.append("    oauthServerInfo: ").append(toIndentedString(oauthServerInfo)).append("\n");
		sb.append("    tlsTunnelInfo: ").append(toIndentedString(tlsTunnelInfo)).append("\n");
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
