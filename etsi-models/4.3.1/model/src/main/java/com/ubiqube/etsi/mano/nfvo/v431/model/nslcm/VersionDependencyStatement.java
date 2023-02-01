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
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information related to one or more VNF, NS or PNF descriptor identifiers which describe one  single dependency. When more than one descriptor is indicated, they correspond to different versions of the same VNF,  NS or PNF and they represent alternatives to fulfil the dependency. It shall comply with the provisions defined in  table 6.5.3.113-1. 
 */
@Schema(description = "This type represents the information related to one or more VNF, NS or PNF descriptor identifiers which describe one  single dependency. When more than one descriptor is indicated, they correspond to different versions of the same VNF,  NS or PNF and they represent alternatives to fulfil the dependency. It shall comply with the provisions defined in  table 6.5.3.113-1. ")
@Validated


public class VersionDependencyStatement   {
  @JsonProperty("descriptorId")
  @Valid
  private List<String> descriptorId = new ArrayList<>();

  public VersionDependencyStatement descriptorId(List<String> descriptorId) {
    this.descriptorId = descriptorId;
    return this;
  }

  public VersionDependencyStatement addDescriptorIdItem(String descriptorIdItem) {
    this.descriptorId.add(descriptorIdItem);
    return this;
  }

  /**
   * Identifies a VNFD, NSD or PNFD upon which the entity using this information element depends. When more than one descriptor is indicated, they shall correspond to versions of the same VNF, NS or PNF and they represent. alternatives, i.e. the presence of one of them fulfills the dependency.
   * @return descriptorId
   **/
  @Schema(required = true, description = "Identifies a VNFD, NSD or PNFD upon which the entity using this information element depends. When more than one descriptor is indicated, they shall correspond to versions of the same VNF, NS or PNF and they represent. alternatives, i.e. the presence of one of them fulfills the dependency.")
      @NotNull

    public List<String> getDescriptorId() {
    return descriptorId;
  }

  public void setDescriptorId(List<String> descriptorId) {
    this.descriptorId = descriptorId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionDependencyStatement versionDependencyStatement = (VersionDependencyStatement) o;
    return Objects.equals(this.descriptorId, versionDependencyStatement.descriptorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(descriptorId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionDependencyStatement {\n");
    
    sb.append("    descriptorId: ").append(toIndentedString(descriptorId)).append("\n");
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
