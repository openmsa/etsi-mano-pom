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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.DirectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the detailed information about the data flows that are requested to be mirrored. It shall comply with the provisions defined in table 6.5.3.102-1. NOTE 1: It shall be present when dataFlowInfoId in DataFlowData is not provided. NOTE 2: Depends on the value of the direction of the data flow to be mirrored, this attribute defines whether the data flow from the connection point to the target IP address or the data flow from the target IP address to the connection point or both shall be mirrored. 
 */
@Schema(description = "This type represents the detailed information about the data flows that are requested to be mirrored. It shall comply with the provisions defined in table 6.5.3.102-1. NOTE 1: It shall be present when dataFlowInfoId in DataFlowData is not provided. NOTE 2: Depends on the value of the direction of the data flow to be mirrored, this attribute defines whether the data flow from the connection point to the target IP address or the data flow from the target IP address to the connection point or both shall be mirrored. ")
@Validated


public class DataFlowDetails   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("cpdId")
  private String cpdId = null;

  @JsonProperty("direction")
  private DirectionType direction = null;

  @JsonProperty("targetIpAddress")
  private String targetIpAddress = null;

  public DataFlowDetails description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Information description of the data flow. 
   * @return description
   **/
  @Schema(description = "Information description of the data flow. ")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DataFlowDetails cpdId(String cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  /**
   * Get cpdId
   * @return cpdId
   **/
  @Schema(description = "")
  
    public String getCpdId() {
    return cpdId;
  }

  public void setCpdId(String cpdId) {
    this.cpdId = cpdId;
  }

  public DataFlowDetails direction(DirectionType direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Get direction
   * @return direction
   **/
  @Schema(description = "")
  
    @Valid
    public DirectionType getDirection() {
    return direction;
  }

  public void setDirection(DirectionType direction) {
    this.direction = direction;
  }

  public DataFlowDetails targetIpAddress(String targetIpAddress) {
    this.targetIpAddress = targetIpAddress;
    return this;
  }

  /**
   * Get targetIpAddress
   * @return targetIpAddress
   **/
  @Schema(description = "")
  
    public String getTargetIpAddress() {
    return targetIpAddress;
  }

  public void setTargetIpAddress(String targetIpAddress) {
    this.targetIpAddress = targetIpAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataFlowDetails dataFlowDetails = (DataFlowDetails) o;
    return Objects.equals(this.description, dataFlowDetails.description) &&
        Objects.equals(this.cpdId, dataFlowDetails.cpdId) &&
        Objects.equals(this.direction, dataFlowDetails.direction) &&
        Objects.equals(this.targetIpAddress, dataFlowDetails.targetIpAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, cpdId, direction, targetIpAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataFlowDetails {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    targetIpAddress: ").append(toIndentedString(targetIpAddress)).append("\n");
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
