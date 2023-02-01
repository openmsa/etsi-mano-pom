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
import com.ubiqube.etsi.mano.vnfm.v431.model.grant.AdditionalResourceInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information that allows addressing a virtualised resource that is used by a VNF instance. Information about the resource is available from the VIM. * NOTE 1: The value set of the \&quot;vimLevelResourceType\&quot; attribute is within the scope of the VIM or CISM or the resource            provider and can be used as information that complements the ResourceHandle. This value set is different from            the value set of the \&quot;type\&quot; attribute in the ResourceDefinition (refer to clause 9.5.3.2). When the container            infrastructure service management is a Kubernetes® instance the vimLevelResourceType is the type of            resource, as would correspond to the &#x27;kind&#x27; field if the resource is declared in its own Kubernetes® manifest,            e.g.: Pod, PersistentVolumeClaim, NetworkAttachmentDefinition.  * NOTE 2: When the container infrastructure service management is a Kubernetes® instance the resourceId shall be            populated in the following way:           - For a compute MCIO, it is the instance identifier that Kubernetes® assigns, which is unique cluster wide            per resource type.           - For a storage MCIO modelled as a persistent volume claim, it is the name of the persistent volume claim,            i.e. the value of the &#x27;claimName&#x27; field in the Kubernetes® manifest, or a compound name built by            Kubernetes® if the persistent volume claim is defined inline in another template instead of in its own            manifest.           - For a network MCIO representing a NetworkAttachmentDefinition, a Service or an Ingress, it is the value of            the &#x27;metadata.name&#x27; field in Kubernetes® manifest. 
 */
@Schema(description = "This type represents the information that allows addressing a virtualised resource that is used by a VNF instance. Information about the resource is available from the VIM. * NOTE 1: The value set of the \"vimLevelResourceType\" attribute is within the scope of the VIM or CISM or the resource            provider and can be used as information that complements the ResourceHandle. This value set is different from            the value set of the \"type\" attribute in the ResourceDefinition (refer to clause 9.5.3.2). When the container            infrastructure service management is a Kubernetes® instance the vimLevelResourceType is the type of            resource, as would correspond to the 'kind' field if the resource is declared in its own Kubernetes® manifest,            e.g.: Pod, PersistentVolumeClaim, NetworkAttachmentDefinition.  * NOTE 2: When the container infrastructure service management is a Kubernetes® instance the resourceId shall be            populated in the following way:           - For a compute MCIO, it is the instance identifier that Kubernetes® assigns, which is unique cluster wide            per resource type.           - For a storage MCIO modelled as a persistent volume claim, it is the name of the persistent volume claim,            i.e. the value of the 'claimName' field in the Kubernetes® manifest, or a compound name built by            Kubernetes® if the persistent volume claim is defined inline in another template instead of in its own            manifest.           - For a network MCIO representing a NetworkAttachmentDefinition, a Service or an Ingress, it is the value of            the 'metadata.name' field in Kubernetes® manifest. ")
@Validated


public class ResourceHandle   {
  @JsonProperty("vimConnectionId")
  private String vimConnectionId = null;

  @JsonProperty("resourceProviderId")
  private String resourceProviderId = null;

  @JsonProperty("resourceId")
  private String resourceId = null;

  @JsonProperty("vimLevelResourceType")
  private String vimLevelResourceType = null;

  @JsonProperty("vimLevelAdditionalResourceInfo")
  private AdditionalResourceInfo vimLevelAdditionalResourceInfo = null;

  @JsonProperty("containerNamespace")
  private String containerNamespace = null;

  public ResourceHandle vimConnectionId(String vimConnectionId) {
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

  public ResourceHandle resourceProviderId(String resourceProviderId) {
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

  public ResourceHandle resourceId(String resourceId) {
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

  public ResourceHandle vimLevelResourceType(String vimLevelResourceType) {
    this.vimLevelResourceType = vimLevelResourceType;
    return this;
  }

  /**
   * Type of the resource in the scope of the VIM or the CISM or the resource provider. See note 1. 
   * @return vimLevelResourceType
   **/
  @Schema(description = "Type of the resource in the scope of the VIM or the CISM or the resource provider. See note 1. ")
  
    public String getVimLevelResourceType() {
    return vimLevelResourceType;
  }

  public void setVimLevelResourceType(String vimLevelResourceType) {
    this.vimLevelResourceType = vimLevelResourceType;
  }

  public ResourceHandle vimLevelAdditionalResourceInfo(AdditionalResourceInfo vimLevelAdditionalResourceInfo) {
    this.vimLevelAdditionalResourceInfo = vimLevelAdditionalResourceInfo;
    return this;
  }

  /**
   * Get vimLevelAdditionalResourceInfo
   * @return vimLevelAdditionalResourceInfo
   **/
  @Schema(description = "")
  
    @Valid
    public AdditionalResourceInfo getVimLevelAdditionalResourceInfo() {
    return vimLevelAdditionalResourceInfo;
  }

  public void setVimLevelAdditionalResourceInfo(AdditionalResourceInfo vimLevelAdditionalResourceInfo) {
    this.vimLevelAdditionalResourceInfo = vimLevelAdditionalResourceInfo;
  }

  public ResourceHandle containerNamespace(String containerNamespace) {
    this.containerNamespace = containerNamespace;
    return this;
  }

  /**
   * The value of the namespace in which the MCIO corresponding to the resource is deployed. This attribute shall be present if the resource is managed by a CISM and it shall be absent otherwise. 
   * @return containerNamespace
   **/
  @Schema(description = "The value of the namespace in which the MCIO corresponding to the resource is deployed. This attribute shall be present if the resource is managed by a CISM and it shall be absent otherwise. ")
  
    public String getContainerNamespace() {
    return containerNamespace;
  }

  public void setContainerNamespace(String containerNamespace) {
    this.containerNamespace = containerNamespace;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceHandle resourceHandle = (ResourceHandle) o;
    return Objects.equals(this.vimConnectionId, resourceHandle.vimConnectionId) &&
        Objects.equals(this.resourceProviderId, resourceHandle.resourceProviderId) &&
        Objects.equals(this.resourceId, resourceHandle.resourceId) &&
        Objects.equals(this.vimLevelResourceType, resourceHandle.vimLevelResourceType) &&
        Objects.equals(this.vimLevelAdditionalResourceInfo, resourceHandle.vimLevelAdditionalResourceInfo) &&
        Objects.equals(this.containerNamespace, resourceHandle.containerNamespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vimConnectionId, resourceProviderId, resourceId, vimLevelResourceType, vimLevelAdditionalResourceInfo, containerNamespace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceHandle {\n");
    
    sb.append("    vimConnectionId: ").append(toIndentedString(vimConnectionId)).append("\n");
    sb.append("    resourceProviderId: ").append(toIndentedString(resourceProviderId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    vimLevelResourceType: ").append(toIndentedString(vimLevelResourceType)).append("\n");
    sb.append("    vimLevelAdditionalResourceInfo: ").append(toIndentedString(vimLevelAdditionalResourceInfo)).append("\n");
    sb.append("    containerNamespace: ").append(toIndentedString(containerNamespace)).append("\n");
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
