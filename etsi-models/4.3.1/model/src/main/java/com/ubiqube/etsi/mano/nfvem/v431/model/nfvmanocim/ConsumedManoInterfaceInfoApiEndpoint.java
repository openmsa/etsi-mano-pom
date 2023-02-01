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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Consumable API endpoint of the interface. It provides the information relevant about the protocol, host and port, and path where the interface API can be accessed. 
 */
@Schema(description = "Consumable API endpoint of the interface. It provides the information relevant about the protocol, host and port, and path where the interface API can be accessed. ")
@Validated


public class ConsumedManoInterfaceInfoApiEndpoint   {
  @JsonProperty("apiRoot")
  private String apiRoot = null;

  @JsonProperty("apiName")
  private String apiName = null;

  @JsonProperty("apiMajorVersion")
  private String apiMajorVersion = null;

  @JsonProperty("apiUri")
  private String apiUri = null;

  public ConsumedManoInterfaceInfoApiEndpoint apiRoot(String apiRoot) {
    this.apiRoot = apiRoot;
    return this;
  }

  /**
   * String formatted according to IETF RFC 3986. 
   * @return apiRoot
   **/
  @Schema(description = "String formatted according to IETF RFC 3986. ")
  
    public String getApiRoot() {
    return apiRoot;
  }

  public void setApiRoot(String apiRoot) {
    this.apiRoot = apiRoot;
  }

  public ConsumedManoInterfaceInfoApiEndpoint apiName(String apiName) {
    this.apiName = apiName;
    return this;
  }

  /**
   * Indicates the interface name in an abbreviated form. Shall be present for ETSI NFV specified RESTful NFV-MANO APIs. The {apiName} of each interface is defined in the standard the interface is compliant to (see also clause 4.1 of ETSI GS NFV-SOL 013). May be present otherwise. 
   * @return apiName
   **/
  @Schema(description = "Indicates the interface name in an abbreviated form. Shall be present for ETSI NFV specified RESTful NFV-MANO APIs. The {apiName} of each interface is defined in the standard the interface is compliant to (see also clause 4.1 of ETSI GS NFV-SOL 013). May be present otherwise. ")
  
    public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public ConsumedManoInterfaceInfoApiEndpoint apiMajorVersion(String apiMajorVersion) {
    this.apiMajorVersion = apiMajorVersion;
    return this;
  }

  /**
   * Indicates the current major version of the API. Shall be present for ETSI NFV specified RESTful NFV-MANO APIs. The major version is defined in the standard the interface is compliant to (see also clause 4.1 of ETSI GS NFV-SOL 013). May be present otherwise. 
   * @return apiMajorVersion
   **/
  @Schema(description = "Indicates the current major version of the API. Shall be present for ETSI NFV specified RESTful NFV-MANO APIs. The major version is defined in the standard the interface is compliant to (see also clause 4.1 of ETSI GS NFV-SOL 013). May be present otherwise. ")
  
    public String getApiMajorVersion() {
    return apiMajorVersion;
  }

  public void setApiMajorVersion(String apiMajorVersion) {
    this.apiMajorVersion = apiMajorVersion;
  }

  public ConsumedManoInterfaceInfoApiEndpoint apiUri(String apiUri) {
    this.apiUri = apiUri;
    return this;
  }

  /**
   * String formatted according to IETF RFC 3986. 
   * @return apiUri
   **/
  @Schema(required = true, description = "String formatted according to IETF RFC 3986. ")
      @NotNull

    public String getApiUri() {
    return apiUri;
  }

  public void setApiUri(String apiUri) {
    this.apiUri = apiUri;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsumedManoInterfaceInfoApiEndpoint consumedManoInterfaceInfoApiEndpoint = (ConsumedManoInterfaceInfoApiEndpoint) o;
    return Objects.equals(this.apiRoot, consumedManoInterfaceInfoApiEndpoint.apiRoot) &&
        Objects.equals(this.apiName, consumedManoInterfaceInfoApiEndpoint.apiName) &&
        Objects.equals(this.apiMajorVersion, consumedManoInterfaceInfoApiEndpoint.apiMajorVersion) &&
        Objects.equals(this.apiUri, consumedManoInterfaceInfoApiEndpoint.apiUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiRoot, apiName, apiMajorVersion, apiUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsumedManoInterfaceInfoApiEndpoint {\n");
    
    sb.append("    apiRoot: ").append(toIndentedString(apiRoot)).append("\n");
    sb.append("    apiName: ").append(toIndentedString(apiName)).append("\n");
    sb.append("    apiMajorVersion: ").append(toIndentedString(apiMajorVersion)).append("\n");
    sb.append("    apiUri: ").append(toIndentedString(apiUri)).append("\n");
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
