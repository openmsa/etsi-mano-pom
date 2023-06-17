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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Configuration data used when performing dynamic discovery of  the authorization server identifier.  NOTE: Provided configuration of the OAuth 2.0 authorization  server information and configuration shall be supported, and  dynamic configuration may be supported. 
 */
@Schema (description= "Configuration data used when performing dynamic discovery of  the authorization server identifier.  NOTE: Provided configuration of the OAuth 2.0 authorization  server information and configuration shall be supported, and  dynamic configuration may be supported. " )
@Validated
public class ServerInterfaceSecurityInfoOauthServerInfoDynamicDiscovery   {
  @JsonProperty("webFingerHost")
  private String webFingerHost = null;

  public ServerInterfaceSecurityInfoOauthServerInfoDynamicDiscovery webFingerHost(String webFingerHost) {
    this.webFingerHost = webFingerHost;
    return this;
  }

  /**
   * Get webFingerHost
   * @return webFingerHost
  **/
  @Schema(required= true ,description= "" )
      @NotNull

    public String getWebFingerHost() {
    return webFingerHost;
  }

  public void setWebFingerHost(String webFingerHost) {
    this.webFingerHost = webFingerHost;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerInterfaceSecurityInfoOauthServerInfoDynamicDiscovery serverInterfaceSecurityInfoOauthServerInfoDynamicDiscovery = (ServerInterfaceSecurityInfoOauthServerInfoDynamicDiscovery) o;
    return Objects.equals(this.webFingerHost, serverInterfaceSecurityInfoOauthServerInfoDynamicDiscovery.webFingerHost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(webFingerHost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServerInterfaceSecurityInfoOauthServerInfoDynamicDiscovery {\n");
    
    sb.append("    webFingerHost: ").append(toIndentedString(webFingerHost)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
