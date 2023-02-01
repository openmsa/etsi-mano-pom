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
 * This type specifies an existing nested NS instance to be used in the NS instance  and if needed, the NsProfile to use for this nested NS instance.  It shall comply with the provisions defined in Table 6.5.3.19a-1. NOTE 1:  If the overridingNsdId attribute is present the nsProfileId attribute shall also be present. NOTE 2:  This attribute allows to use as nested NS an existing NS instance based on a different NSD to the one  specified in the composite NSD with nsProfileId, provided they have the same nsdExtInvariantId. NOTE 3: A VnfProfile, NsProfile or PnfProfile may contain multiple VersionDependencies as it may describe the version dependencies of the descriptor referenced in the profile or of other descriptors with the same external invariant identifier. NOTE 4: The overridingVersionDependency attribute may only be present if the overridingNsdId attribute is present. 
 */
@Schema(description = "This type specifies an existing nested NS instance to be used in the NS instance  and if needed, the NsProfile to use for this nested NS instance.  It shall comply with the provisions defined in Table 6.5.3.19a-1. NOTE 1:  If the overridingNsdId attribute is present the nsProfileId attribute shall also be present. NOTE 2:  This attribute allows to use as nested NS an existing NS instance based on a different NSD to the one  specified in the composite NSD with nsProfileId, provided they have the same nsdExtInvariantId. NOTE 3: A VnfProfile, NsProfile or PnfProfile may contain multiple VersionDependencies as it may describe the version dependencies of the descriptor referenced in the profile or of other descriptors with the same external invariant identifier. NOTE 4: The overridingVersionDependency attribute may only be present if the overridingNsdId attribute is present. ")
@Validated


public class NestedNsInstanceData  implements OneOfNestedNsInstanceData {
  @JsonProperty("nestedNsInstanceId")
  private String nestedNsInstanceId = null;

  @JsonProperty("nsProfileId")
  private String nsProfileId = null;

  @JsonProperty("overridingNsddId")
  private String overridingNsddId = null;

  @JsonProperty("overridingVersionDependency")
  @Valid
  private List<OverridingVersionDependency> overridingVersionDependency = null;

  public NestedNsInstanceData nestedNsInstanceId(String nestedNsInstanceId) {
    this.nestedNsInstanceId = nestedNsInstanceId;
    return this;
  }

  /**
   * Get nestedNsInstanceId
   * @return nestedNsInstanceId
   **/
  @Schema(description = "")
  
    public String getNestedNsInstanceId() {
    return nestedNsInstanceId;
  }

  public void setNestedNsInstanceId(String nestedNsInstanceId) {
    this.nestedNsInstanceId = nestedNsInstanceId;
  }

  public NestedNsInstanceData nsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
    return this;
  }

  /**
   * Get nsProfileId
   * @return nsProfileId
   **/
  @Schema(description = "")
  
    public String getNsProfileId() {
    return nsProfileId;
  }

  public void setNsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
  }

  public NestedNsInstanceData overridingNsddId(String overridingNsddId) {
    this.overridingNsddId = overridingNsddId;
    return this;
  }

  /**
   * Get overridingNsddId
   * @return overridingNsddId
   **/
  @Schema(description = "")
  
    public String getOverridingNsddId() {
    return overridingNsddId;
  }

  public void setOverridingNsddId(String overridingNsddId) {
    this.overridingNsddId = overridingNsddId;
  }

  public NestedNsInstanceData overridingVersionDependency(List<OverridingVersionDependency> overridingVersionDependency) {
    this.overridingVersionDependency = overridingVersionDependency;
    return this;
  }

  public NestedNsInstanceData addOverridingVersionDependencyItem(OverridingVersionDependency overridingVersionDependencyItem) {
    if (this.overridingVersionDependency == null) {
      this.overridingVersionDependency = new ArrayList<>();
    }
    this.overridingVersionDependency.add(overridingVersionDependencyItem);
    return this;
  }

  /**
   * If present, information in each overridingVersionDependency replaces the versionDependency in the runtime information that the NFVO keeps about the VnfProfile, NsProfile or PnfProfile indicated in the OverridingVersionDependency. Only the versionDependency in the VnfProfile, NsProfile or PnfProfile with the same dependentConstituentId as in the overridingVersionDependency is replaced. See note 3. If no versionDependency with the dependentConstituentId indicated in the overridingVersionDependency exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency is added to the runtime information that the NFVO keeps about the profile. See note 4. 
   * @return overridingVersionDependency
   **/
  @Schema(description = "If present, information in each overridingVersionDependency replaces the versionDependency in the runtime information that the NFVO keeps about the VnfProfile, NsProfile or PnfProfile indicated in the OverridingVersionDependency. Only the versionDependency in the VnfProfile, NsProfile or PnfProfile with the same dependentConstituentId as in the overridingVersionDependency is replaced. See note 3. If no versionDependency with the dependentConstituentId indicated in the overridingVersionDependency exists in the VnfProfile, NsProfile or PnfProfile, the new versionDependency is added to the runtime information that the NFVO keeps about the profile. See note 4. ")
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
    NestedNsInstanceData nestedNsInstanceData = (NestedNsInstanceData) o;
    return Objects.equals(this.nestedNsInstanceId, nestedNsInstanceData.nestedNsInstanceId) &&
        Objects.equals(this.nsProfileId, nestedNsInstanceData.nsProfileId) &&
        Objects.equals(this.overridingNsddId, nestedNsInstanceData.overridingNsddId) &&
        Objects.equals(this.overridingVersionDependency, nestedNsInstanceData.overridingVersionDependency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nestedNsInstanceId, nsProfileId, overridingNsddId, overridingVersionDependency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NestedNsInstanceData {\n");
    
    sb.append("    nestedNsInstanceId: ").append(toIndentedString(nestedNsInstanceId)).append("\n");
    sb.append("    nsProfileId: ").append(toIndentedString(nsProfileId)).append("\n");
    sb.append("    overridingNsddId: ").append(toIndentedString(overridingNsddId)).append("\n");
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
