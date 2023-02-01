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
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.DataFlowDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the information about the data flows to be mirrored.  It shall comply with the provisions defined in table 6.5.3.101-1. NOTE: A value need not be provided at runtime if the APIconsumer does not  intend to refer to a data flow defined in the NSD. 
 */
@Schema(description = "This type represents the information about the data flows to be mirrored.  It shall comply with the provisions defined in table 6.5.3.101-1. NOTE: A value need not be provided at runtime if the APIconsumer does not  intend to refer to a data flow defined in the NSD. ")
@Validated


public class DataFlowData   {
  @JsonProperty("dataFlowInfoId")
  private String dataFlowInfoId = null;

  @JsonProperty("dataFlowDetails")
  private DataFlowDetails dataFlowDetails = null;

  public DataFlowData dataFlowInfoId(String dataFlowInfoId) {
    this.dataFlowInfoId = dataFlowInfoId;
    return this;
  }

  /**
   * Get dataFlowInfoId
   * @return dataFlowInfoId
   **/
  @Schema(description = "")
  
    public String getDataFlowInfoId() {
    return dataFlowInfoId;
  }

  public void setDataFlowInfoId(String dataFlowInfoId) {
    this.dataFlowInfoId = dataFlowInfoId;
  }

  public DataFlowData dataFlowDetails(DataFlowDetails dataFlowDetails) {
    this.dataFlowDetails = dataFlowDetails;
    return this;
  }

  /**
   * Get dataFlowDetails
   * @return dataFlowDetails
   **/
  @Schema(description = "")
  
    @Valid
    public DataFlowDetails getDataFlowDetails() {
    return dataFlowDetails;
  }

  public void setDataFlowDetails(DataFlowDetails dataFlowDetails) {
    this.dataFlowDetails = dataFlowDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataFlowData dataFlowData = (DataFlowData) o;
    return Objects.equals(this.dataFlowInfoId, dataFlowData.dataFlowInfoId) &&
        Objects.equals(this.dataFlowDetails, dataFlowData.dataFlowDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataFlowInfoId, dataFlowDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataFlowData {\n");
    
    sb.append("    dataFlowInfoId: ").append(toIndentedString(dataFlowInfoId)).append("\n");
    sb.append("    dataFlowDetails: ").append(toIndentedString(dataFlowDetails)).append("\n");
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
