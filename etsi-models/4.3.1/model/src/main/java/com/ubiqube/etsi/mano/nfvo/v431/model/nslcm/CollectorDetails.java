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
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information about where the mirrored flow is to be delivered.  It shall comply with the provisions defined in table 6.5.3.103-1. NOTE: The collector which is attached to this port can be a virtual machine that uses the mirrored      data for analysis purpose. The collector is not managed by VIM. 
 */
@Schema(description = "This type represents the information about where the mirrored flow is to be delivered.  It shall comply with the provisions defined in table 6.5.3.103-1. NOTE: The collector which is attached to this port can be a virtual machine that uses the mirrored      data for analysis purpose. The collector is not managed by VIM. ")
@Validated


public class CollectorDetails   {
  @JsonProperty("collectorId")
  private String collectorId = null;

  @JsonProperty("collectorName")
  private String collectorName = null;

  @JsonProperty("portId")
  private String portId = null;

  public CollectorDetails collectorId(String collectorId) {
    this.collectorId = collectorId;
    return this;
  }

  /**
   * Get collectorId
   * @return collectorId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getCollectorId() {
    return collectorId;
  }

  public void setCollectorId(String collectorId) {
    this.collectorId = collectorId;
  }

  public CollectorDetails collectorName(String collectorName) {
    this.collectorName = collectorName;
    return this;
  }

  /**
   * Name of the collector where the mirrored flow is to be delivered. 
   * @return collectorName
   **/
  @Schema(required = true, description = "Name of the collector where the mirrored flow is to be delivered. ")
      @NotNull

    public String getCollectorName() {
    return collectorName;
  }

  public void setCollectorName(String collectorName) {
    this.collectorName = collectorName;
  }

  public CollectorDetails portId(String portId) {
    this.portId = portId;
    return this;
  }

  /**
   * Get portId
   * @return portId
   **/
  @Schema(description = "")
  
    public String getPortId() {
    return portId;
  }

  public void setPortId(String portId) {
    this.portId = portId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectorDetails collectorDetails = (CollectorDetails) o;
    return Objects.equals(this.collectorId, collectorDetails.collectorId) &&
        Objects.equals(this.collectorName, collectorDetails.collectorName) &&
        Objects.equals(this.portId, collectorDetails.portId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectorId, collectorName, portId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectorDetails {\n");
    
    sb.append("    collectorId: ").append(toIndentedString(collectorId)).append("\n");
    sb.append("    collectorName: ").append(toIndentedString(collectorName)).append("\n");
    sb.append("    portId: ").append(toIndentedString(portId)).append("\n");
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
