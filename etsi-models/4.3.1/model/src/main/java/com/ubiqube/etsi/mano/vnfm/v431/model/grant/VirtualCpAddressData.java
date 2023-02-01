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
package com.ubiqube.etsi.mano.vnfm.v431.model.grant;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents network address data for a virtual CP. * NOTE 1: If the container cluster is set up to be able to configure an external load balancer this address will be used,           otherwise it will be ignored by the CISM.  * NOTE 2: In case the cluster can configure an external load balancer but no loadBalancerIp is provided the container           cluster will assign an IP address. 
 */
@Schema(description = "This type represents network address data for a virtual CP. * NOTE 1: If the container cluster is set up to be able to configure an external load balancer this address will be used,           otherwise it will be ignored by the CISM.  * NOTE 2: In case the cluster can configure an external load balancer but no loadBalancerIp is provided the container           cluster will assign an IP address. ")
@Validated


public class VirtualCpAddressData   {
  /**
   * The type of the IP addresses. Permitted values: IPV4, IPV6. 
   */
  public enum TypeEnum {
    IPV4("IPV4"),
    
    IPV6("IPV6");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("loadBalancerIp")
  private String loadBalancerIp = null;

  public VirtualCpAddressData type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of the IP addresses. Permitted values: IPV4, IPV6. 
   * @return type
   **/
  @Schema(required = true, description = "The type of the IP addresses. Permitted values: IPV4, IPV6. ")
      @NotNull

    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public VirtualCpAddressData loadBalancerIp(String loadBalancerIp) {
    this.loadBalancerIp = loadBalancerIp;
    return this;
  }

  /**
   * Get loadBalancerIp
   * @return loadBalancerIp
   **/
  @Schema(description = "")
  
    public String getLoadBalancerIp() {
    return loadBalancerIp;
  }

  public void setLoadBalancerIp(String loadBalancerIp) {
    this.loadBalancerIp = loadBalancerIp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualCpAddressData virtualCpAddressData = (VirtualCpAddressData) o;
    return Objects.equals(this.type, virtualCpAddressData.type) &&
        Objects.equals(this.loadBalancerIp, virtualCpAddressData.loadBalancerIp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, loadBalancerIp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualCpAddressData {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    loadBalancerIp: ").append(toIndentedString(loadBalancerIp)).append("\n");
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
