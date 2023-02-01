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
import com.ubiqube.etsi.mano.vnfm.v431.model.grant.IntVnfCpData;
import com.ubiqube.etsi.mano.vnfm.v431.model.grant.NetAttDefResourceData;
import com.ubiqube.etsi.mano.vnfm.v431.model.grant.VnfLinkPortData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents an externally-managed internal VL. * NOTE 1: It is only applicable if the externally-managed VL is realized by a secondary container cluster network. It shall           not be present otherwise. * NOTE 2: A link port is not needed for a VNFC internal connection point connected to a secondary container cluster           network. * NOTE 3: An example of the network attachment definition resource when the container infrastructure service           management is a Kubernetes® instance is a network attachment definition (NAD). 
 */
@Schema(description = "This type represents an externally-managed internal VL. * NOTE 1: It is only applicable if the externally-managed VL is realized by a secondary container cluster network. It shall           not be present otherwise. * NOTE 2: A link port is not needed for a VNFC internal connection point connected to a secondary container cluster           network. * NOTE 3: An example of the network attachment definition resource when the container infrastructure service           management is a Kubernetes® instance is a network attachment definition (NAD). ")
@Validated


public class ExtManagedVirtualLinkData   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("vnfVirtualLinkDescId")
  private String vnfVirtualLinkDescId = null;

  @JsonProperty("vimConnectionId")
  private String vimConnectionId = null;

  @JsonProperty("resourceProviderId")
  private String resourceProviderId = null;

  @JsonProperty("resourceId")
  private String resourceId = null;

  @JsonProperty("netAttDefResourceData")
  @Valid
  private List<NetAttDefResourceData> netAttDefResourceData = null;

  @JsonProperty("intCp")
  @Valid
  private List<IntVnfCpData> intCp = null;

  @JsonProperty("vnfLinkPort")
  @Valid
  private List<VnfLinkPortData> vnfLinkPort = null;

  @JsonProperty("extManagedMultisiteVirtualLinkId")
  private String extManagedMultisiteVirtualLinkId = null;

  public ExtManagedVirtualLinkData id(String id) {
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

  public ExtManagedVirtualLinkData vnfVirtualLinkDescId(String vnfVirtualLinkDescId) {
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

  public ExtManagedVirtualLinkData vimConnectionId(String vimConnectionId) {
    this.vimConnectionId = vimConnectionId;
    return this;
  }

  /**
   * Get vimConnectionId
   * @return vimConnectionId
   **/
  @Schema(description = "")
  
    public String getVimConnectionId() {
    return vimConnectionId;
  }

  public void setVimConnectionId(String vimConnectionId) {
    this.vimConnectionId = vimConnectionId;
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
  @Schema(required = true, description = "")
      @NotNull

    public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
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
   * Externally provided network attachment definition resource(s) that provide the specification of the interface to attach VNFC connection points to this externallymanaged VL. See notes 1 and 3. 
   * @return netAttDefResourceData
   **/
  @Schema(description = "Externally provided network attachment definition resource(s) that provide the specification of the interface to attach VNFC connection points to this externallymanaged VL. See notes 1 and 3. ")
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
   * Internal CPs of the VNF to be connected to this externally-managed VL. See note 1. 
   * @return intCp
   **/
  @Schema(description = "Internal CPs of the VNF to be connected to this externally-managed VL. See note 1. ")
      @Valid
    public List<IntVnfCpData> getIntCp() {
    return intCp;
  }

  public void setIntCp(List<IntVnfCpData> intCp) {
    this.intCp = intCp;
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
   * Externally provided link ports to be used to connect VNFC connection points to this externally-managed VL on this network resource. If this attribute is not present, the VNFM shall create the link ports on the externally-managed VL. See note 2. 
   * @return vnfLinkPort
   **/
  @Schema(description = "Externally provided link ports to be used to connect VNFC connection points to this externally-managed VL on this network resource. If this attribute is not present, the VNFM shall create the link ports on the externally-managed VL. See note 2. ")
      @Valid
    public List<VnfLinkPortData> getVnfLinkPort() {
    return vnfLinkPort;
  }

  public void setVnfLinkPort(List<VnfLinkPortData> vnfLinkPort) {
    this.vnfLinkPort = vnfLinkPort;
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
    return Objects.equals(this.id, extManagedVirtualLinkData.id) &&
        Objects.equals(this.vnfVirtualLinkDescId, extManagedVirtualLinkData.vnfVirtualLinkDescId) &&
        Objects.equals(this.vimConnectionId, extManagedVirtualLinkData.vimConnectionId) &&
        Objects.equals(this.resourceProviderId, extManagedVirtualLinkData.resourceProviderId) &&
        Objects.equals(this.resourceId, extManagedVirtualLinkData.resourceId) &&
        Objects.equals(this.netAttDefResourceData, extManagedVirtualLinkData.netAttDefResourceData) &&
        Objects.equals(this.intCp, extManagedVirtualLinkData.intCp) &&
        Objects.equals(this.vnfLinkPort, extManagedVirtualLinkData.vnfLinkPort) &&
        Objects.equals(this.extManagedMultisiteVirtualLinkId, extManagedVirtualLinkData.extManagedMultisiteVirtualLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vnfVirtualLinkDescId, vimConnectionId, resourceProviderId, resourceId, netAttDefResourceData, intCp, vnfLinkPort, extManagedMultisiteVirtualLinkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtManagedVirtualLinkData {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vnfVirtualLinkDescId: ").append(toIndentedString(vnfVirtualLinkDescId)).append("\n");
    sb.append("    vimConnectionId: ").append(toIndentedString(vimConnectionId)).append("\n");
    sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    netAttDefResourceData: ").append(toIndentedString(netAttDefResourceData)).append("\n");
    sb.append("    intCp: ").append(toIndentedString(intCp)).append("\n");
    sb.append("    vnfLinkPort: ").append(toIndentedString(vnfLinkPort)).append("\n");
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
