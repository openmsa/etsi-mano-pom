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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.OverridingVersionDependency;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type specifies an existing VNF instance to be used in the NS instance and if needed, the VNF Profile to use for this VNF instance. It shall comply with the provisions defined in Table 6.5.3.19-1. NOTE 1:  If the overridingVnfdId attribute is present the vnfProfileId attribute shall also be present. NOTE 2:  This attribute allows to use an existing VNF instance based on a different VNFD to the one specified           in the NSD with vnfProfileId, provided both have the same vnfdExtInvariantId. NOTE 3: A VnfProfile, NsProfile or PnfProfile may contain multiple VersionDependencies as it may describe the            version dependencies of the descriptor referenced in the profile or of other descriptors with the same external invariant identifier. NOTE 4: The overridingVersionDependency attribute may only be present if the overridingVnfdId attribute is present. 
 */
@Schema(description = "This type specifies an existing VNF instance to be used in the NS instance and if needed, the VNF Profile to use for this VNF instance. It shall comply with the provisions defined in Table 6.5.3.19-1. NOTE 1:  If the overridingVnfdId attribute is present the vnfProfileId attribute shall also be present. NOTE 2:  This attribute allows to use an existing VNF instance based on a different VNFD to the one specified           in the NSD with vnfProfileId, provided both have the same vnfdExtInvariantId. NOTE 3: A VnfProfile, NsProfile or PnfProfile may contain multiple VersionDependencies as it may describe the            version dependencies of the descriptor referenced in the profile or of other descriptors with the same external invariant identifier. NOTE 4: The overridingVersionDependency attribute may only be present if the overridingVnfdId attribute is present. ")
@Validated


public class VnfInstanceData  implements OneOfVnfInstanceData {
  @JsonProperty("vnfInstanceId")
  private String vnfInstanceId = null;

  @JsonProperty("vnfProfileId")
  private String vnfProfileId = null;

  @JsonProperty("overridingVnfdId")
  private String overridingVnfdId = null;

  @JsonProperty("overridingVersionDependency")
  @Valid
  private List<OverridingVersionDependency> overridingVersionDependency = null;

  public VnfInstanceData vnfInstanceId(String vnfInstanceId) {
    this.vnfInstanceId = vnfInstanceId;
    return this;
  }

  /**
   * Get vnfInstanceId
   * @return vnfInstanceId
   **/
  @Schema(description = "")
  
    public String getVnfInstanceId() {
    return vnfInstanceId;
  }

  public void setVnfInstanceId(String vnfInstanceId) {
    this.vnfInstanceId = vnfInstanceId;
  }

  public VnfInstanceData vnfProfileId(String vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
    return this;
  }

  /**
   * Get vnfProfileId
   * @return vnfProfileId
   **/
  @Schema(description = "")
  
    public String getVnfProfileId() {
    return vnfProfileId;
  }

  public void setVnfProfileId(String vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
  }

  public VnfInstanceData overridingVnfdId(String overridingVnfdId) {
    this.overridingVnfdId = overridingVnfdId;
    return this;
  }

  /**
   * Get overridingVnfdId
   * @return overridingVnfdId
   **/
  @Schema(description = "")
  
    public String getOverridingVnfdId() {
    return overridingVnfdId;
  }

  public void setOverridingVnfdId(String overridingVnfdId) {
    this.overridingVnfdId = overridingVnfdId;
  }

  public VnfInstanceData overridingVersionDependency(List<OverridingVersionDependency> overridingVersionDependency) {
    this.overridingVersionDependency = overridingVersionDependency;
    return this;
  }

  public VnfInstanceData addOverridingVersionDependencyItem(OverridingVersionDependency overridingVersionDependencyItem) {
    if (this.overridingVersionDependency == null) {
      this.overridingVersionDependency = new ArrayList<>();
    }
    this.overridingVersionDependency.add(overridingVersionDependencyItem);
    return this;
  }

  /**
   * If present, information in each overridingVersionDependency replaces the versionDependency in the runtime information that the NFVO keeps about the VnfProfile, NsProfile or PnfProfile indicated in the OverridingVersionDependency. Only the versionDependency in the VnfProfile, NsProfile or PnfProfile with the same dependentConstituentId as in the overridingVersionDependency is replaced. See note 3. If no versionDependency with the dependentConstituentId indicated in the overridingVersionDependency exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency is added to the runtime information that the NFVO keeps about the Profile. See note 4. 
   * @return overridingVersionDependency
   **/
  @Schema(description = "If present, information in each overridingVersionDependency replaces the versionDependency in the runtime information that the NFVO keeps about the VnfProfile, NsProfile or PnfProfile indicated in the OverridingVersionDependency. Only the versionDependency in the VnfProfile, NsProfile or PnfProfile with the same dependentConstituentId as in the overridingVersionDependency is replaced. See note 3. If no versionDependency with the dependentConstituentId indicated in the overridingVersionDependency exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency is added to the runtime information that the NFVO keeps about the Profile. See note 4. ")
      @Valid
    public List<OverridingVersionDependency> getOverridingVersionDependency() {
    return overridingVersionDependency;
  }

  public void setOverridingVersionDependency(List<OverridingVersionDependency> overridingVersionDependency) {
    this.overridingVersionDependency = overridingVersionDependency;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfInstanceData vnfInstanceData = (VnfInstanceData) o;
    return Objects.equals(this.vnfInstanceId, vnfInstanceData.vnfInstanceId) &&
        Objects.equals(this.vnfProfileId, vnfInstanceData.vnfProfileId) &&
        Objects.equals(this.overridingVnfdId, vnfInstanceData.overridingVnfdId) &&
        Objects.equals(this.overridingVersionDependency, vnfInstanceData.overridingVersionDependency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfInstanceId, vnfProfileId, overridingVnfdId, overridingVersionDependency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfInstanceData {\n");
    
    sb.append("    vnfInstanceId: ").append(toIndentedString(vnfInstanceId)).append("\n");
    sb.append("    vnfProfileId: ").append(toIndentedString(vnfProfileId)).append("\n");
    sb.append("    overridingVnfdId: ").append(toIndentedString(overridingVnfdId)).append("\n");
    sb.append("    overridingVersionDependency: ").append(toIndentedString(overridingVersionDependency)).append("\n");
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
