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
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ChangeStateRequestAdministrativeStateChange;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ChangeStateRequestOperationalStateChange;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents request parameters for changing the state of a managed entity.  
 */
@Schema (description= "This type represents request parameters for changing the state of a managed entity.  " )
@Validated
public class ChangeStateRequest   {
  @JsonProperty("operationalStateChange")
  private ChangeStateRequestOperationalStateChange operationalStateChange = null;

  @JsonProperty("administrativeStateChange")
  private ChangeStateRequestAdministrativeStateChange administrativeStateChange = null;

  public ChangeStateRequest operationalStateChange(ChangeStateRequestOperationalStateChange operationalStateChange) {
    this.operationalStateChange = operationalStateChange;
    return this;
  }

  /**
   * Get operationalStateChange
   * @return operationalStateChange
  **/
  @Schema(description= "" )
  
    @Valid
    public ChangeStateRequestOperationalStateChange getOperationalStateChange() {
    return operationalStateChange;
  }

  public void setOperationalStateChange(ChangeStateRequestOperationalStateChange operationalStateChange) {
    this.operationalStateChange = operationalStateChange;
  }

  public ChangeStateRequest administrativeStateChange(ChangeStateRequestAdministrativeStateChange administrativeStateChange) {
    this.administrativeStateChange = administrativeStateChange;
    return this;
  }

  /**
   * Get administrativeStateChange
   * @return administrativeStateChange
  **/
  @Schema(description= "" )
  
    @Valid
    public ChangeStateRequestAdministrativeStateChange getAdministrativeStateChange() {
    return administrativeStateChange;
  }

  public void setAdministrativeStateChange(ChangeStateRequestAdministrativeStateChange administrativeStateChange) {
    this.administrativeStateChange = administrativeStateChange;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChangeStateRequest changeStateRequest = (ChangeStateRequest) o;
    return Objects.equals(this.operationalStateChange, changeStateRequest.operationalStateChange) &&
        Objects.equals(this.administrativeStateChange, changeStateRequest.administrativeStateChange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operationalStateChange, administrativeStateChange);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangeStateRequest {\n");
    
    sb.append("    operationalStateChange: ").append(toIndentedString(operationalStateChange)).append("\n");
    sb.append("    administrativeStateChange: ").append(toIndentedString(administrativeStateChange)).append("\n");
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
