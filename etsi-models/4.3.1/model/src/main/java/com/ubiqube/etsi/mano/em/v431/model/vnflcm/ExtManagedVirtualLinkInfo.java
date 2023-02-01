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
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.NetAttDefResourceInfo;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.ResourceHandle;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.VnfLinkPortInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type provides information about an externally-managed virtual link. 
 */
@Schema(description = "This type provides information about an externally-managed virtual link. ")
@Validated


public class ExtManagedVirtualLinkInfo   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("vnfVirtualLinkDescId")
  private String vnfVirtualLinkDescId = null;

  @JsonProperty("vnfdId")
  private String vnfdId = null;

  @JsonProperty("networkResource")
  private ResourceHandle networkResource = null;

  @JsonProperty("vnfLinkPorts")
  @Valid
  private List<VnfLinkPortInfo> vnfLinkPorts = null;

  @JsonProperty("vnfNetAttDefResource")
  @Valid
  private List<NetAttDefResourceInfo> vnfNetAttDefResource = null;

  public ExtManagedVirtualLinkInfo id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ExtManagedVirtualLinkInfo vnfVirtualLinkDescId(String vnfVirtualLinkDescId) {
    this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
    return this;
  }

  /**
   * Get vnfVirtualLinkDescId
   * @return vnfVirtualLinkDescId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getVnfVirtualLinkDescId() {
    return vnfVirtualLinkDescId;
  }

  public void setVnfVirtualLinkDescId(String vnfVirtualLinkDescId) {
    this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
  }

  public ExtManagedVirtualLinkInfo vnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  /**
   * Get vnfdId
   * @return vnfdId
   **/
  @Schema(description = "")
  
    public String getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
  }

  public ExtManagedVirtualLinkInfo networkResource(ResourceHandle networkResource) {
    this.networkResource = networkResource;
    return this;
  }

  /**
   * Get networkResource
   * @return networkResource
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ResourceHandle getNetworkResource() {
    return networkResource;
  }

  public void setNetworkResource(ResourceHandle networkResource) {
    this.networkResource = networkResource;
  }

  public ExtManagedVirtualLinkInfo vnfLinkPorts(List<VnfLinkPortInfo> vnfLinkPorts) {
    this.vnfLinkPorts = vnfLinkPorts;
    return this;
  }

  public ExtManagedVirtualLinkInfo addVnfLinkPortsItem(VnfLinkPortInfo vnfLinkPortsItem) {
    if (this.vnfLinkPorts == null) {
      this.vnfLinkPorts = new ArrayList<>();
    }
    this.vnfLinkPorts.add(vnfLinkPortsItem);
    return this;
  }

  /**
   * Link ports of this VL. 
   * @return vnfLinkPorts
   **/
  @Schema(description = "Link ports of this VL. ")
      @Valid
    public List<VnfLinkPortInfo> getVnfLinkPorts() {
    return vnfLinkPorts;
  }

  public void setVnfLinkPorts(List<VnfLinkPortInfo> vnfLinkPorts) {
    this.vnfLinkPorts = vnfLinkPorts;
  }

  public ExtManagedVirtualLinkInfo vnfNetAttDefResource(List<NetAttDefResourceInfo> vnfNetAttDefResource) {
    this.vnfNetAttDefResource = vnfNetAttDefResource;
    return this;
  }

  public ExtManagedVirtualLinkInfo addVnfNetAttDefResourceItem(NetAttDefResourceInfo vnfNetAttDefResourceItem) {
    if (this.vnfNetAttDefResource == null) {
      this.vnfNetAttDefResource = new ArrayList<>();
    }
    this.vnfNetAttDefResource.add(vnfNetAttDefResourceItem);
    return this;
  }

  /**
   * Network attachment definition resources that provide the specification of the interface  to attach connection points to this VL. 
   * @return vnfNetAttDefResource
   **/
  @Schema(description = "Network attachment definition resources that provide the specification of the interface  to attach connection points to this VL. ")
      @Valid
    public List<NetAttDefResourceInfo> getVnfNetAttDefResource() {
    return vnfNetAttDefResource;
  }

  public void setVnfNetAttDefResource(List<NetAttDefResourceInfo> vnfNetAttDefResource) {
    this.vnfNetAttDefResource = vnfNetAttDefResource;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtManagedVirtualLinkInfo extManagedVirtualLinkInfo = (ExtManagedVirtualLinkInfo) o;
    return Objects.equals(this.id, extManagedVirtualLinkInfo.id) &&
        Objects.equals(this.vnfVirtualLinkDescId, extManagedVirtualLinkInfo.vnfVirtualLinkDescId) &&
        Objects.equals(this.vnfdId, extManagedVirtualLinkInfo.vnfdId) &&
        Objects.equals(this.networkResource, extManagedVirtualLinkInfo.networkResource) &&
        Objects.equals(this.vnfLinkPorts, extManagedVirtualLinkInfo.vnfLinkPorts) &&
        Objects.equals(this.vnfNetAttDefResource, extManagedVirtualLinkInfo.vnfNetAttDefResource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vnfVirtualLinkDescId, vnfdId, networkResource, vnfLinkPorts, vnfNetAttDefResource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtManagedVirtualLinkInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vnfVirtualLinkDescId: ").append(toIndentedString(vnfVirtualLinkDescId)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    networkResource: ").append(toIndentedString(networkResource)).append("\n");
    sb.append("    vnfLinkPorts: ").append(toIndentedString(vnfLinkPorts)).append("\n");
    sb.append("    vnfNetAttDefResource: ").append(toIndentedString(vnfNetAttDefResource)).append("\n");
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
