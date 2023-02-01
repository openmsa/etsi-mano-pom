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
package com.ubiqube.etsi.mano.em.v431.model.vnflcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.ResourceHandle;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type contains information related to a network attachment definition resource that provides the  specification of the interface used to connect one or multiple connection points to a secondary container  cluster network. 
 */
@Schema(description = "This type contains information related to a network attachment definition resource that provides the  specification of the interface used to connect one or multiple connection points to a secondary container  cluster network. ")
@Validated


public class NetAttDefResourceInfo   {
  @JsonProperty("netAttDefResourceInfoId")
  private String netAttDefResourceInfoId = null;

  @JsonProperty("netAttDefResource")
  private ResourceHandle netAttDefResource = null;

  @JsonProperty("associatedExtCpId")
  @Valid
  private List<String> associatedExtCpId = null;

  @JsonProperty("associatedVnfcCpId")
  @Valid
  private List<String> associatedVnfcCpId = null;

  public NetAttDefResourceInfo netAttDefResourceInfoId(String netAttDefResourceInfoId) {
    this.netAttDefResourceInfoId = netAttDefResourceInfoId;
    return this;
  }

  /**
   * Get netAttDefResourceInfoId
   * @return netAttDefResourceInfoId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getNetAttDefResourceInfoId() {
    return netAttDefResourceInfoId;
  }

  public void setNetAttDefResourceInfoId(String netAttDefResourceInfoId) {
    this.netAttDefResourceInfoId = netAttDefResourceInfoId;
  }

  public NetAttDefResourceInfo netAttDefResource(ResourceHandle netAttDefResource) {
    this.netAttDefResource = netAttDefResource;
    return this;
  }

  /**
   * Get netAttDefResource
   * @return netAttDefResource
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ResourceHandle getNetAttDefResource() {
    return netAttDefResource;
  }

  public void setNetAttDefResource(ResourceHandle netAttDefResource) {
    this.netAttDefResource = netAttDefResource;
  }

  public NetAttDefResourceInfo associatedExtCpId(List<String> associatedExtCpId) {
    this.associatedExtCpId = associatedExtCpId;
    return this;
  }

  public NetAttDefResourceInfo addAssociatedExtCpIdItem(String associatedExtCpIdItem) {
    if (this.associatedExtCpId == null) {
      this.associatedExtCpId = new ArrayList<>();
    }
    this.associatedExtCpId.add(associatedExtCpIdItem);
    return this;
  }

  /**
   * Identifier of the external CP associated to this network attachment definition resource. Shall be present  when the network attachment definition resource is used for external connectivity by the VNF. 
   * @return associatedExtCpId
   **/
  @Schema(description = "Identifier of the external CP associated to this network attachment definition resource. Shall be present  when the network attachment definition resource is used for external connectivity by the VNF. ")
  
    public List<String> getAssociatedExtCpId() {
    return associatedExtCpId;
  }

  public void setAssociatedExtCpId(List<String> associatedExtCpId) {
    this.associatedExtCpId = associatedExtCpId;
  }

  public NetAttDefResourceInfo associatedVnfcCpId(List<String> associatedVnfcCpId) {
    this.associatedVnfcCpId = associatedVnfcCpId;
    return this;
  }

  public NetAttDefResourceInfo addAssociatedVnfcCpIdItem(String associatedVnfcCpIdItem) {
    if (this.associatedVnfcCpId == null) {
      this.associatedVnfcCpId = new ArrayList<>();
    }
    this.associatedVnfcCpId.add(associatedVnfcCpIdItem);
    return this;
  }

  /**
   * Identifier of the VNFC CP associated to this network attachment definition resource. May be present when  the network attachment definition resource is used for internal connectivity by the VNF. 
   * @return associatedVnfcCpId
   **/
  @Schema(description = "Identifier of the VNFC CP associated to this network attachment definition resource. May be present when  the network attachment definition resource is used for internal connectivity by the VNF. ")
  
    public List<String> getAssociatedVnfcCpId() {
    return associatedVnfcCpId;
  }

  public void setAssociatedVnfcCpId(List<String> associatedVnfcCpId) {
    this.associatedVnfcCpId = associatedVnfcCpId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NetAttDefResourceInfo netAttDefResourceInfo = (NetAttDefResourceInfo) o;
    return Objects.equals(this.netAttDefResourceInfoId, netAttDefResourceInfo.netAttDefResourceInfoId) &&
        Objects.equals(this.netAttDefResource, netAttDefResourceInfo.netAttDefResource) &&
        Objects.equals(this.associatedExtCpId, netAttDefResourceInfo.associatedExtCpId) &&
        Objects.equals(this.associatedVnfcCpId, netAttDefResourceInfo.associatedVnfcCpId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(netAttDefResourceInfoId, netAttDefResource, associatedExtCpId, associatedVnfcCpId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetAttDefResourceInfo {\n");
    
    sb.append("    netAttDefResourceInfoId: ").append(toIndentedString(netAttDefResourceInfoId)).append("\n");
    sb.append("    netAttDefResource: ").append(toIndentedString(netAttDefResource)).append("\n");
    sb.append("    associatedExtCpId: ").append(toIndentedString(associatedExtCpId)).append("\n");
    sb.append("    associatedVnfcCpId: ").append(toIndentedString(associatedVnfcCpId)).append("\n");
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
