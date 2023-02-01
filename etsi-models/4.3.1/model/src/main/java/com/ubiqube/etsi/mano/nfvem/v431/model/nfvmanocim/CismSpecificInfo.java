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
package com.ubiqube.etsi.mano.nfvem.v431.model.nfvmanocim;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents information attributes specific to a CISM entity, and that  can be relevant to more than one NFV-MANO service offered by a CISM entity. It  shall comply with the provisions defined in table 5.6.3.14-1.  NOTE: No attributes are specified in the present document version. The definition of attributes is left for future specification.  
 */
@Schema(description = "This type represents information attributes specific to a CISM entity, and that  can be relevant to more than one NFV-MANO service offered by a CISM entity. It  shall comply with the provisions defined in table 5.6.3.14-1.  NOTE: No attributes are specified in the present document version. The definition of attributes is left for future specification.  ")
@Validated


public class CismSpecificInfo   {
  @JsonProperty("none")
  private Object none = null;

  public CismSpecificInfo none(Object none) {
    this.none = none;
    return this;
  }

  /**
   * Get none
   * @return none
   **/
  @Schema(description = "")
  
    public Object getNone() {
    return none;
  }

  public void setNone(Object none) {
    this.none = none;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CismSpecificInfo cismSpecificInfo = (CismSpecificInfo) o;
    return Objects.equals(this.none, cismSpecificInfo.none);
  }

  @Override
  public int hashCode() {
    return Objects.hash(none);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CismSpecificInfo {\n");
    
    sb.append("    none: ").append(toIndentedString(none)).append("\n");
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
