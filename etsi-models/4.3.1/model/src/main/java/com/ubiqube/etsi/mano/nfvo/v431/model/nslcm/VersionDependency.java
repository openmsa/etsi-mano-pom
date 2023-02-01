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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.VersionDependencyStatement;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information related to all dependencies that an NSD constituent has on the versions of other NSD constituents.  It shall comply with the provisions defined in table 6.5.3.112-1. 
 */
@Schema(description = "This type represents the information related to all dependencies that an NSD constituent has on the versions of other NSD constituents.  It shall comply with the provisions defined in table 6.5.3.112-1. ")
@Validated


public class VersionDependency   {
  @JsonProperty("dependentConstituentId")
  private String dependentConstituentId = null;

  @JsonProperty("versionDependencyStatement")
  @Valid
  private List<VersionDependencyStatement> versionDependencyStatement = null;

  public VersionDependency dependentConstituentId(String dependentConstituentId) {
    this.dependentConstituentId = dependentConstituentId;
    return this;
  }

  /**
   * Get dependentConstituentId
   * @return dependentConstituentId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getDependentConstituentId() {
    return dependentConstituentId;
  }

  public void setDependentConstituentId(String dependentConstituentId) {
    this.dependentConstituentId = dependentConstituentId;
  }

  public VersionDependency versionDependencyStatement(List<VersionDependencyStatement> versionDependencyStatement) {
    this.versionDependencyStatement = versionDependencyStatement;
    return this;
  }

  public VersionDependency addVersionDependencyStatementItem(VersionDependencyStatement versionDependencyStatementItem) {
    if (this.versionDependencyStatement == null) {
      this.versionDependencyStatement = new ArrayList<>();
    }
    this.versionDependencyStatement.add(versionDependencyStatementItem);
    return this;
  }

  /**
   * Identifies one or multiple versions of an NSD constituents upon which the dependent constituent identified by  dependentConstituentId has a dependency. Cardinality 0 is used to remove an existing version dependency in a profile. 
   * @return versionDependencyStatement
   **/
  @Schema(description = "Identifies one or multiple versions of an NSD constituents upon which the dependent constituent identified by  dependentConstituentId has a dependency. Cardinality 0 is used to remove an existing version dependency in a profile. ")
      @Valid
    public List<VersionDependencyStatement> getVersionDependencyStatement() {
    return versionDependencyStatement;
  }

  public void setVersionDependencyStatement(List<VersionDependencyStatement> versionDependencyStatement) {
    this.versionDependencyStatement = versionDependencyStatement;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionDependency versionDependency = (VersionDependency) o;
    return Objects.equals(this.dependentConstituentId, versionDependency.dependentConstituentId) &&
        Objects.equals(this.versionDependencyStatement, versionDependency.versionDependencyStatement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dependentConstituentId, versionDependencyStatement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionDependency {\n");
    
    sb.append("    dependentConstituentId: ").append(toIndentedString(dependentConstituentId)).append("\n");
    sb.append("    versionDependencyStatement: ").append(toIndentedString(versionDependencyStatement)).append("\n");
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
