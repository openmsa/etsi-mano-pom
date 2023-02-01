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
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.ExtLinkPortInfo;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.NetAttDefResourceInfo;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.ResourceHandle;
import com.ubiqube.etsi.mano.em.v431.model.vnflcm.VnfExtCpData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents information about an external VL. NOTE: This attribute reflects the current configuration information that has resulted from merging into this attribute        the \&quot;VnfExtCpData\&quot; information which was passed as part of the \&quot;ExtVirtualLinkData\&quot; structure in the input of the        most recent VNF LCM operation such as \&quot;InstantiateVnfRequest\&quot;, \&quot;ChangeExtVnfConnectivityRequest\&quot;, \&quot;ChangeVnfFlavourRequest\&quot;        or \&quot;ChangeCurrentVnfPkgRequest\&quot;, or in the Grant response. If applying such change results in an empty list of        \&quot;currentVnfExtCpData\&quot; structure instances, the affected instance of \&quot;ExtVirtualLinkInfo\&quot; shall be removed from its        parent data structure. 
 */
@Schema(description = "This type represents information about an external VL. NOTE: This attribute reflects the current configuration information that has resulted from merging into this attribute        the \"VnfExtCpData\" information which was passed as part of the \"ExtVirtualLinkData\" structure in the input of the        most recent VNF LCM operation such as \"InstantiateVnfRequest\", \"ChangeExtVnfConnectivityRequest\", \"ChangeVnfFlavourRequest\"        or \"ChangeCurrentVnfPkgRequest\", or in the Grant response. If applying such change results in an empty list of        \"currentVnfExtCpData\" structure instances, the affected instance of \"ExtVirtualLinkInfo\" shall be removed from its        parent data structure. ")
@Validated


public class ExtVirtualLinkInfo   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("resourceHandle")
  private ResourceHandle resourceHandle = null;

  @JsonProperty("extLinkPorts")
  @Valid
  private List<ExtLinkPortInfo> extLinkPorts = null;

  @JsonProperty("currentVnfExtCpData")
  @Valid
  private List<VnfExtCpData> currentVnfExtCpData = new ArrayList<>();

  @JsonProperty("extNetAttDefResource")
  @Valid
  private List<NetAttDefResourceInfo> extNetAttDefResource = null;

  public ExtVirtualLinkInfo id(String id) {
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

  public ExtVirtualLinkInfo resourceHandle(ResourceHandle resourceHandle) {
    this.resourceHandle = resourceHandle;
    return this;
  }

  /**
   * Get resourceHandle
   * @return resourceHandle
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ResourceHandle getResourceHandle() {
    return resourceHandle;
  }

  public void setResourceHandle(ResourceHandle resourceHandle) {
    this.resourceHandle = resourceHandle;
  }

  public ExtVirtualLinkInfo extLinkPorts(List<ExtLinkPortInfo> extLinkPorts) {
    this.extLinkPorts = extLinkPorts;
    return this;
  }

  public ExtVirtualLinkInfo addExtLinkPortsItem(ExtLinkPortInfo extLinkPortsItem) {
    if (this.extLinkPorts == null) {
      this.extLinkPorts = new ArrayList<>();
    }
    this.extLinkPorts.add(extLinkPortsItem);
    return this;
  }

  /**
   * Link ports of this VL. 
   * @return extLinkPorts
   **/
  @Schema(description = "Link ports of this VL. ")
      @Valid
    public List<ExtLinkPortInfo> getExtLinkPorts() {
    return extLinkPorts;
  }

  public void setExtLinkPorts(List<ExtLinkPortInfo> extLinkPorts) {
    this.extLinkPorts = extLinkPorts;
  }

  public ExtVirtualLinkInfo currentVnfExtCpData(List<VnfExtCpData> currentVnfExtCpData) {
    this.currentVnfExtCpData = currentVnfExtCpData;
    return this;
  }

  public ExtVirtualLinkInfo addCurrentVnfExtCpDataItem(VnfExtCpData currentVnfExtCpDataItem) {
    this.currentVnfExtCpData.add(currentVnfExtCpDataItem);
    return this;
  }

  /**
   * Allows the API consumer to read the current CP configuration information for the connection of external CPs  to the external virtual link. See note. 
   * @return currentVnfExtCpData
   **/
  @Schema(required = true, description = "Allows the API consumer to read the current CP configuration information for the connection of external CPs  to the external virtual link. See note. ")
      @NotNull
    @Valid
    public List<VnfExtCpData> getCurrentVnfExtCpData() {
    return currentVnfExtCpData;
  }

  public void setCurrentVnfExtCpData(List<VnfExtCpData> currentVnfExtCpData) {
    this.currentVnfExtCpData = currentVnfExtCpData;
  }

  public ExtVirtualLinkInfo extNetAttDefResource(List<NetAttDefResourceInfo> extNetAttDefResource) {
    this.extNetAttDefResource = extNetAttDefResource;
    return this;
  }

  public ExtVirtualLinkInfo addExtNetAttDefResourceItem(NetAttDefResourceInfo extNetAttDefResourceItem) {
    if (this.extNetAttDefResource == null) {
      this.extNetAttDefResource = new ArrayList<>();
    }
    this.extNetAttDefResource.add(extNetAttDefResourceItem);
    return this;
  }

  /**
   * Network attachment definition resources that provide the specification of the interface to attach connection points  to this VL. 
   * @return extNetAttDefResource
   **/
  @Schema(description = "Network attachment definition resources that provide the specification of the interface to attach connection points  to this VL. ")
      @Valid
    public List<NetAttDefResourceInfo> getExtNetAttDefResource() {
    return extNetAttDefResource;
  }

  public void setExtNetAttDefResource(List<NetAttDefResourceInfo> extNetAttDefResource) {
    this.extNetAttDefResource = extNetAttDefResource;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtVirtualLinkInfo extVirtualLinkInfo = (ExtVirtualLinkInfo) o;
    return Objects.equals(this.id, extVirtualLinkInfo.id) &&
        Objects.equals(this.resourceHandle, extVirtualLinkInfo.resourceHandle) &&
        Objects.equals(this.extLinkPorts, extVirtualLinkInfo.extLinkPorts) &&
        Objects.equals(this.currentVnfExtCpData, extVirtualLinkInfo.currentVnfExtCpData) &&
        Objects.equals(this.extNetAttDefResource, extVirtualLinkInfo.extNetAttDefResource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, resourceHandle, extLinkPorts, currentVnfExtCpData, extNetAttDefResource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtVirtualLinkInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resourceHandle: ").append(toIndentedString(resourceHandle)).append("\n");
    sb.append("    extLinkPorts: ").append(toIndentedString(extLinkPorts)).append("\n");
    sb.append("    currentVnfExtCpData: ").append(toIndentedString(currentVnfExtCpData)).append("\n");
    sb.append("    extNetAttDefResource: ").append(toIndentedString(extNetAttDefResource)).append("\n");
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
