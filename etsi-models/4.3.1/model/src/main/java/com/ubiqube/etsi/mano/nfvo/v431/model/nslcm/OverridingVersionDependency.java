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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.VersionDependency;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information to override or add a version dependency in the runtime information that the NFVO keeps about a profile of a NSD constituent. It shall comply with the provisions defined in table 6.5.3.111-1. 
 */
@Schema(description = "This type represents the information to override or add a version dependency in the runtime information that the NFVO keeps about a profile of a NSD constituent. It shall comply with the provisions defined in table 6.5.3.111-1. ")
@Validated


public class OverridingVersionDependency   {
  @JsonProperty("profileId")
  private String profileId = null;

  @JsonProperty("versionDependency")
  private VersionDependency versionDependency = null;

  public OverridingVersionDependency profileId(String profileId) {
    this.profileId = profileId;
    return this;
  }

  /**
   * Get profileId
   * @return profileId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getProfileId() {
    return profileId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public OverridingVersionDependency versionDependency(VersionDependency versionDependency) {
    this.versionDependency = versionDependency;
    return this;
  }

  /**
   * Get versionDependency
   * @return versionDependency
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public VersionDependency getVersionDependency() {
    return versionDependency;
  }

  public void setVersionDependency(VersionDependency versionDependency) {
    this.versionDependency = versionDependency;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OverridingVersionDependency overridingVersionDependency = (OverridingVersionDependency) o;
    return Objects.equals(this.profileId, overridingVersionDependency.profileId) &&
        Objects.equals(this.versionDependency, overridingVersionDependency.versionDependency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profileId, versionDependency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OverridingVersionDependency {\n");
    
    sb.append("    profileId: ").append(toIndentedString(profileId)).append("\n");
    sb.append("    versionDependency: ").append(toIndentedString(versionDependency)).append("\n");
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
