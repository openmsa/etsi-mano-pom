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
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * For the RESTful NFV-MANO APIs, valid values are all values for \&quot;apiName\&quot; as defined  in ETSI GS NFV-SOL 002, ETSI GS NFV-SOL 003, and ETSI GS NFV-SOL 005. For the NFV-MANO service interfaces for which no API is specified by ETSI NFV, valid  values are defined in table 5.6.4.3-1. NOTE: The table is expected to be updated, by removing the corresponding listed entries,  once the interfaces are specified as a RESTful NFV-MANO API.  
 */
@Schema (description= "For the RESTful NFV-MANO APIs, valid values are all values for \"apiName\" as defined  in ETSI GS NFV-SOL 002, ETSI GS NFV-SOL 003, and ETSI GS NFV-SOL 005. For the NFV-MANO service interfaces for which no API is specified by ETSI NFV, valid  values are defined in table 5.6.4.3-1. NOTE: The table is expected to be updated, by removing the corresponding listed entries,  once the interfaces are specified as a RESTful NFV-MANO API.  " )
@Validated
public class ManoServiceInterfaceTypeShortName   {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManoServiceInterfaceTypeShortName {\n");
    
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
