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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.ExtLinkPortData;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.NetAttDefResourceData;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.VnfExtCpData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents an external VL. NOTE 1: A link port is not needed for an external CP instance that exposes a VIP CP in the following cases: 1. For a VIP CP directly exposed as extCP:   1.1. no dedicated IP address is allocated as VIP address, as indicated in the VNFD   1.2. a dedicated IP address is allocated as VIP address,   but the NFVO indicates that no port is needed (createExtLinkPort in VnfExtCpConfig set to false). 2. For a VIP CP exposed as extCP via a floating IP address: 2.1. no dedicated IP address is allocated as VIP address, as indicated in the VNFD,   and the VNFC CP associated to the VIP CP is also exposed via a floating IP address. NOTE 2:  An example of the network attachment definition resource when the container infrastructure service is a Kubernetes® instance is a network attachment definition (NAD). 
 */
@Schema(description = "This type represents an external VL. NOTE 1: A link port is not needed for an external CP instance that exposes a VIP CP in the following cases: 1. For a VIP CP directly exposed as extCP:   1.1. no dedicated IP address is allocated as VIP address, as indicated in the VNFD   1.2. a dedicated IP address is allocated as VIP address,   but the NFVO indicates that no port is needed (createExtLinkPort in VnfExtCpConfig set to false). 2. For a VIP CP exposed as extCP via a floating IP address: 2.1. no dedicated IP address is allocated as VIP address, as indicated in the VNFD,   and the VNFC CP associated to the VIP CP is also exposed via a floating IP address. NOTE 2:  An example of the network attachment definition resource when the container infrastructure service is a Kubernetes® instance is a network attachment definition (NAD). ")
@Validated


public class ExtVirtualLinkData   {
  @JsonProperty("extVirtualLinkId")
  private String extVirtualLinkId = null;

  @JsonProperty("vimId")
  private String vimId = null;

  @JsonProperty("resourceProviderId")
  private String resourceProviderId = null;

  @JsonProperty("resourceId")
  private String resourceId = null;

  @JsonProperty("extCps")
  @Valid
  private List<VnfExtCpData> extCps = new ArrayList<>();

  @JsonProperty("extLinkPorts")
  @Valid
  private List<ExtLinkPortData> extLinkPorts = null;

  @JsonProperty("extNetAttDefResourceData")
  @Valid
  private List<NetAttDefResourceData> extNetAttDefResourceData = null;

  public ExtVirtualLinkData extVirtualLinkId(String extVirtualLinkId) {
    this.extVirtualLinkId = extVirtualLinkId;
    return this;
  }

  /**
   * Get extVirtualLinkId
   * @return extVirtualLinkId
   **/
  @Schema(description = "")
  
    public String getExtVirtualLinkId() {
    return extVirtualLinkId;
  }

  public void setExtVirtualLinkId(String extVirtualLinkId) {
    this.extVirtualLinkId = extVirtualLinkId;
  }

  public ExtVirtualLinkData vimId(String vimId) {
    this.vimId = vimId;
    return this;
  }

  /**
   * Get vimId
   * @return vimId
   **/
  @Schema(description = "")
  
    public String getVimId() {
    return vimId;
  }

  public void setVimId(String vimId) {
    this.vimId = vimId;
  }

  public ExtVirtualLinkData resourceProviderId(String resourceProviderId) {
    this.resourceProviderId = resourceProviderId;
    return this;
  }

  /**
   * Get resourceProviderId
   * @return resourceProviderId
   **/
  @Schema(description = "")
  
    public String getResourceProviderId() {
    return resourceProviderId;
  }

  public void setResourceProviderId(String resourceProviderId) {
    this.resourceProviderId = resourceProviderId;
  }

  public ExtVirtualLinkData resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public ExtVirtualLinkData extCps(List<VnfExtCpData> extCps) {
    this.extCps = extCps;
    return this;
  }

  public ExtVirtualLinkData addExtCpsItem(VnfExtCpData extCpsItem) {
    this.extCps.add(extCpsItem);
    return this;
  }

  /**
   * External CPs of the VNF to be connected to this external VL. 
   * @return extCps
   **/
  @Schema(required = true, description = "External CPs of the VNF to be connected to this external VL. ")
      @NotNull
    @Valid
    public List<VnfExtCpData> getExtCps() {
    return extCps;
  }

  public void setExtCps(List<VnfExtCpData> extCps) {
    this.extCps = extCps;
  }

  public ExtVirtualLinkData extLinkPorts(List<ExtLinkPortData> extLinkPorts) {
    this.extLinkPorts = extLinkPorts;
    return this;
  }

  public ExtVirtualLinkData addExtLinkPortsItem(ExtLinkPortData extLinkPortsItem) {
    if (this.extLinkPorts == null) {
      this.extLinkPorts = new ArrayList<>();
    }
    this.extLinkPorts.add(extLinkPortsItem);
    return this;
  }

  /**
   * Externally provided link ports to be used to connect external connection points to this external VL unless the extCp exposes a VIP CP and a link port is not needed for it based on the conditions defined below. See note 1. 
   * @return extLinkPorts
   **/
  @Schema(description = "Externally provided link ports to be used to connect external connection points to this external VL unless the extCp exposes a VIP CP and a link port is not needed for it based on the conditions defined below. See note 1. ")
      @Valid
    public List<ExtLinkPortData> getExtLinkPorts() {
    return extLinkPorts;
  }

  public void setExtLinkPorts(List<ExtLinkPortData> extLinkPorts) {
    this.extLinkPorts = extLinkPorts;
  }

  public ExtVirtualLinkData extNetAttDefResourceData(List<NetAttDefResourceData> extNetAttDefResourceData) {
    this.extNetAttDefResourceData = extNetAttDefResourceData;
    return this;
  }

  public ExtVirtualLinkData addExtNetAttDefResourceDataItem(NetAttDefResourceData extNetAttDefResourceDataItem) {
    if (this.extNetAttDefResourceData == null) {
      this.extNetAttDefResourceData = new ArrayList<>();
    }
    this.extNetAttDefResourceData.add(extNetAttDefResourceDataItem);
    return this;
  }

  /**
   * Externally provided network attachment definition resource(s) that provides the specification of the interface to attach external CPs to this external VL. See note 2.  It is only applicable if the external VL is realized by a secondary container cluster network. It shall not be present otherwise. 
   * @return extNetAttDefResourceData
   **/
  @Schema(description = "Externally provided network attachment definition resource(s) that provides the specification of the interface to attach external CPs to this external VL. See note 2.  It is only applicable if the external VL is realized by a secondary container cluster network. It shall not be present otherwise. ")
      @Valid
    public List<NetAttDefResourceData> getExtNetAttDefResourceData() {
    return extNetAttDefResourceData;
  }

  public void setExtNetAttDefResourceData(List<NetAttDefResourceData> extNetAttDefResourceData) {
    this.extNetAttDefResourceData = extNetAttDefResourceData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtVirtualLinkData extVirtualLinkData = (ExtVirtualLinkData) o;
    return Objects.equals(this.extVirtualLinkId, extVirtualLinkData.extVirtualLinkId) &&
        Objects.equals(this.vimId, extVirtualLinkData.vimId) &&
        Objects.equals(this.resourceProviderId, extVirtualLinkData.resourceProviderId) &&
        Objects.equals(this.resourceId, extVirtualLinkData.resourceId) &&
        Objects.equals(this.extCps, extVirtualLinkData.extCps) &&
        Objects.equals(this.extLinkPorts, extVirtualLinkData.extLinkPorts) &&
        Objects.equals(this.extNetAttDefResourceData, extVirtualLinkData.extNetAttDefResourceData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(extVirtualLinkId, vimId, resourceProviderId, resourceId, extCps, extLinkPorts, extNetAttDefResourceData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtVirtualLinkData {\n");
    
    sb.append("    extVirtualLinkId: ").append(toIndentedString(extVirtualLinkId)).append("\n");
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
    sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    extCps: ").append(toIndentedString(extCps)).append("\n");
    sb.append("    extLinkPorts: ").append(toIndentedString(extLinkPorts)).append("\n");
    sb.append("    extNetAttDefResourceData: ").append(toIndentedString(extNetAttDefResourceData)).append("\n");
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
