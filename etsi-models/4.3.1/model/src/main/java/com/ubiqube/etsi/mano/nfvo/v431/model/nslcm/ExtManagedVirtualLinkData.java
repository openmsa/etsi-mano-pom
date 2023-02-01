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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.IntVnfCpData;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.NetAttDefResourceData;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.VnfLinkPortData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents an externally-managed internal VL. It shall comply with the provisions defined in Table 6.5.3.27-1.        NOTE 1: It is only applicable if the externally-managed VL is realized by a  secondary container cluster network. It shall not be present otherwise. NOTE 2:  An example of the network attachment definition resource when the container infrastructure service is a Kubernetes® instance is a network  attachment definition (NAD). 
 */
@Schema(description = "This type represents an externally-managed internal VL. It shall comply with the provisions defined in Table 6.5.3.27-1.        NOTE 1: It is only applicable if the externally-managed VL is realized by a  secondary container cluster network. It shall not be present otherwise. NOTE 2:  An example of the network attachment definition resource when the container infrastructure service is a Kubernetes® instance is a network  attachment definition (NAD). ")
@Validated


public class ExtManagedVirtualLinkData  implements OneOfExtManagedVirtualLinkData {
  @JsonProperty("extManagedVirtualLinkId")
  private String extManagedVirtualLinkId = null;

  @JsonProperty("vnfVirtualLinkDescId")
  private String vnfVirtualLinkDescId = null;

  @JsonProperty("vimId")
  private String vimId = null;

  @JsonProperty("resourceProviderId")
  private String resourceProviderId = null;

  @JsonProperty("resourceId")
  private String resourceId = null;

  @JsonProperty("vnfLinkPort")
  @Valid
  private List<VnfLinkPortData> vnfLinkPort = null;

  @JsonProperty("netAttDefResourceData")
  @Valid
  private List<NetAttDefResourceData> netAttDefResourceData = null;

  @JsonProperty("intCp")
  @Valid
  private List<IntVnfCpData> intCp = null;

  @JsonProperty("extManagedMultisiteVirtualLinkId")
  private String extManagedMultisiteVirtualLinkId = null;

  public ExtManagedVirtualLinkData extManagedVirtualLinkId(String extManagedVirtualLinkId) {
    this.extManagedVirtualLinkId = extManagedVirtualLinkId;
    return this;
  }

  /**
   * Get extManagedVirtualLinkId
   * @return extManagedVirtualLinkId
   **/
  @Schema(description = "")
  
    public String getExtManagedVirtualLinkId() {
    return extManagedVirtualLinkId;
  }

  public void setExtManagedVirtualLinkId(String extManagedVirtualLinkId) {
    this.extManagedVirtualLinkId = extManagedVirtualLinkId;
  }

  public ExtManagedVirtualLinkData vnfVirtualLinkDescId(String vnfVirtualLinkDescId) {
    this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
    return this;
  }

  /**
   * Get vnfVirtualLinkDescId
   * @return vnfVirtualLinkDescId
   **/
  @Schema(description = "")
  
    public String getVnfVirtualLinkDescId() {
    return vnfVirtualLinkDescId;
  }

  public void setVnfVirtualLinkDescId(String vnfVirtualLinkDescId) {
    this.vnfVirtualLinkDescId = vnfVirtualLinkDescId;
  }

  public ExtManagedVirtualLinkData vimId(String vimId) {
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

  public ExtManagedVirtualLinkData resourceProviderId(String resourceProviderId) {
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

  public ExtManagedVirtualLinkData resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
   **/
  @Schema(description = "")
  
    public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public ExtManagedVirtualLinkData vnfLinkPort(List<VnfLinkPortData> vnfLinkPort) {
    this.vnfLinkPort = vnfLinkPort;
    return this;
  }

  public ExtManagedVirtualLinkData addVnfLinkPortItem(VnfLinkPortData vnfLinkPortItem) {
    if (this.vnfLinkPort == null) {
      this.vnfLinkPort = new ArrayList<>();
    }
    this.vnfLinkPort.add(vnfLinkPortItem);
    return this;
  }

  /**
   * Externally provided link ports to be used to connect VNFC connection points to this externally-managed VL on this network resource. If this attribute is not present, the NFVO or the VNFM shall create the link ports on the externally-managed VL. 
   * @return vnfLinkPort
   **/
  @Schema(description = "Externally provided link ports to be used to connect VNFC connection points to this externally-managed VL on this network resource. If this attribute is not present, the NFVO or the VNFM shall create the link ports on the externally-managed VL. ")
      @Valid
    public List<VnfLinkPortData> getVnfLinkPort() {
    return vnfLinkPort;
  }

  public void setVnfLinkPort(List<VnfLinkPortData> vnfLinkPort) {
    this.vnfLinkPort = vnfLinkPort;
  }

  public ExtManagedVirtualLinkData netAttDefResourceData(List<NetAttDefResourceData> netAttDefResourceData) {
    this.netAttDefResourceData = netAttDefResourceData;
    return this;
  }

  public ExtManagedVirtualLinkData addNetAttDefResourceDataItem(NetAttDefResourceData netAttDefResourceDataItem) {
    if (this.netAttDefResourceData == null) {
      this.netAttDefResourceData = new ArrayList<>();
    }
    this.netAttDefResourceData.add(netAttDefResourceDataItem);
    return this;
  }

  /**
   * Externally provided network attachment definition resource(s) that provides the specification of the interface to attach VNFC connection points to this externally-managed VL. If this attribute is not present, the NFVO shall create the network attachment definition resource(s) for the externally-managed VL.  See notes 1 and 2. 
   * @return netAttDefResourceData
   **/
  @Schema(description = "Externally provided network attachment definition resource(s) that provides the specification of the interface to attach VNFC connection points to this externally-managed VL. If this attribute is not present, the NFVO shall create the network attachment definition resource(s) for the externally-managed VL.  See notes 1 and 2. ")
      @Valid
    public List<NetAttDefResourceData> getNetAttDefResourceData() {
    return netAttDefResourceData;
  }

  public void setNetAttDefResourceData(List<NetAttDefResourceData> netAttDefResourceData) {
    this.netAttDefResourceData = netAttDefResourceData;
  }

  public ExtManagedVirtualLinkData intCp(List<IntVnfCpData> intCp) {
    this.intCp = intCp;
    return this;
  }

  public ExtManagedVirtualLinkData addIntCpItem(IntVnfCpData intCpItem) {
    if (this.intCp == null) {
      this.intCp = new ArrayList<>();
    }
    this.intCp.add(intCpItem);
    return this;
  }

  /**
   * Internal CPs of the VNF to be connected to this externally-managed VL. See note 1. This attribute may only be present if the \"netAttDefResourceData\" is also present. 
   * @return intCp
   **/
  @Schema(description = "Internal CPs of the VNF to be connected to this externally-managed VL. See note 1. This attribute may only be present if the \"netAttDefResourceData\" is also present. ")
      @Valid
    public List<IntVnfCpData> getIntCp() {
    return intCp;
  }

  public void setIntCp(List<IntVnfCpData> intCp) {
    this.intCp = intCp;
  }

  public ExtManagedVirtualLinkData extManagedMultisiteVirtualLinkId(String extManagedMultisiteVirtualLinkId) {
    this.extManagedMultisiteVirtualLinkId = extManagedMultisiteVirtualLinkId;
    return this;
  }

  /**
   * Get extManagedMultisiteVirtualLinkId
   * @return extManagedMultisiteVirtualLinkId
   **/
  @Schema(description = "")
  
    public String getExtManagedMultisiteVirtualLinkId() {
    return extManagedMultisiteVirtualLinkId;
  }

  public void setExtManagedMultisiteVirtualLinkId(String extManagedMultisiteVirtualLinkId) {
    this.extManagedMultisiteVirtualLinkId = extManagedMultisiteVirtualLinkId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtManagedVirtualLinkData extManagedVirtualLinkData = (ExtManagedVirtualLinkData) o;
    return Objects.equals(this.extManagedVirtualLinkId, extManagedVirtualLinkData.extManagedVirtualLinkId) &&
        Objects.equals(this.vnfVirtualLinkDescId, extManagedVirtualLinkData.vnfVirtualLinkDescId) &&
        Objects.equals(this.vimId, extManagedVirtualLinkData.vimId) &&
        Objects.equals(this.resourceProviderId, extManagedVirtualLinkData.resourceProviderId) &&
        Objects.equals(this.resourceId, extManagedVirtualLinkData.resourceId) &&
        Objects.equals(this.vnfLinkPort, extManagedVirtualLinkData.vnfLinkPort) &&
        Objects.equals(this.netAttDefResourceData, extManagedVirtualLinkData.netAttDefResourceData) &&
        Objects.equals(this.intCp, extManagedVirtualLinkData.intCp) &&
        Objects.equals(this.extManagedMultisiteVirtualLinkId, extManagedVirtualLinkData.extManagedMultisiteVirtualLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(extManagedVirtualLinkId, vnfVirtualLinkDescId, vimId, resourceProviderId, resourceId, vnfLinkPort, netAttDefResourceData, intCp, extManagedMultisiteVirtualLinkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtManagedVirtualLinkData {\n");
    
    sb.append("    extManagedVirtualLinkId: ").append(toIndentedString(extManagedVirtualLinkId)).append("\n");
    sb.append("    vnfVirtualLinkDescId: ").append(toIndentedString(vnfVirtualLinkDescId)).append("\n");
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
    sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    vnfLinkPort: ").append(toIndentedString(vnfLinkPort)).append("\n");
    sb.append("    netAttDefResourceData: ").append(toIndentedString(netAttDefResourceData)).append("\n");
    sb.append("    intCp: ").append(toIndentedString(intCp)).append("\n");
    sb.append("    extManagedMultisiteVirtualLinkId: ").append(toIndentedString(extManagedMultisiteVirtualLinkId)).append("\n");
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
