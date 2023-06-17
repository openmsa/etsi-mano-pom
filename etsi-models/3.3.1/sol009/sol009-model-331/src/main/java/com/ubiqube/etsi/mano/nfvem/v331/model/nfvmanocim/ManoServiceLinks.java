/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.Link;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Links to resources related to this resource. 
 */
@Schema (description= "Links to resources related to this resource. " )
@Validated
public class ManoServiceLinks   {
  @JsonProperty("manoServiceInterfaces")
  @Valid
  private List<Link> manoServiceInterfaces = new ArrayList<>();

  public ManoServiceLinks manoServiceInterfaces(List<Link> manoServiceInterfaces) {
    this.manoServiceInterfaces = manoServiceInterfaces;
    return this;
  }

  public ManoServiceLinks addManoServiceInterfacesItem(Link manoServiceInterfacesItem) {
    this.manoServiceInterfaces.add(manoServiceInterfacesItem);
    return this;
  }

  /**
   * Link to the \"individual NFV-MANO service interface\" resources with  information about the associated interfaces to the NFV-MANO service. 
   * @return manoServiceInterfaces
  **/
  @Schema(required= true ,description= "Link to the \"individual NFV-MANO service interface\" resources with  information about the associated interfaces to the NFV-MANO service. " )
      @NotNull
    @Valid
  @Size(min=1)   public List<Link> getManoServiceInterfaces() {
    return manoServiceInterfaces;
  }

  public void setManoServiceInterfaces(List<Link> manoServiceInterfaces) {
    this.manoServiceInterfaces = manoServiceInterfaces;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ManoServiceLinks manoServiceLinks = (ManoServiceLinks) o;
    return Objects.equals(this.manoServiceInterfaces, manoServiceLinks.manoServiceInterfaces);
  }

  @Override
  public int hashCode() {
    return Objects.hash(manoServiceInterfaces);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManoServiceLinks {\n");
    
    sb.append("    manoServiceInterfaces: ").append(toIndentedString(manoServiceInterfaces)).append("\n");
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
