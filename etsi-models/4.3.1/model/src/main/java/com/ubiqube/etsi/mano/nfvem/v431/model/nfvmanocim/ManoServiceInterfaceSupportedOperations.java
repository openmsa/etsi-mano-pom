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
 * ManoServiceInterfaceSupportedOperations
 */
@Validated


public class ManoServiceInterfaceSupportedOperations   {
  @JsonProperty("operationName")
  private String operationName = null;

  @JsonProperty("maxConcurrentOpNumber")
  private Integer maxConcurrentOpNumber = null;

  public ManoServiceInterfaceSupportedOperations operationName(String operationName) {
    this.operationName = operationName;
    return this;
  }

  /**
   * Name of the operation supported on the interface. 
   * @return operationName
   **/
  @Schema(required = true, description = "Name of the operation supported on the interface. ")
      @NotNull

    public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public ManoServiceInterfaceSupportedOperations maxConcurrentOpNumber(Integer maxConcurrentOpNumber) {
    this.maxConcurrentOpNumber = maxConcurrentOpNumber;
    return this;
  }

  /**
   * Maximum number of concurrent requests supported by the interface operation. See note 2. 
   * @return maxConcurrentOpNumber
   **/
  @Schema(description = "Maximum number of concurrent requests supported by the interface operation. See note 2. ")
  
    public Integer getMaxConcurrentOpNumber() {
    return maxConcurrentOpNumber;
  }

  public void setMaxConcurrentOpNumber(Integer maxConcurrentOpNumber) {
    this.maxConcurrentOpNumber = maxConcurrentOpNumber;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ManoServiceInterfaceSupportedOperations manoServiceInterfaceSupportedOperations = (ManoServiceInterfaceSupportedOperations) o;
    return Objects.equals(this.operationName, manoServiceInterfaceSupportedOperations.operationName) &&
        Objects.equals(this.maxConcurrentOpNumber, manoServiceInterfaceSupportedOperations.maxConcurrentOpNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operationName, maxConcurrentOpNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManoServiceInterfaceSupportedOperations {\n");
    
    sb.append("    operationName: ").append(toIndentedString(operationName)).append("\n");
    sb.append("    maxConcurrentOpNumber: ").append(toIndentedString(maxConcurrentOpNumber)).append("\n");
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
