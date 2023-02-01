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
 * This type represents the input information related to one or more VNF internal CP instances created based on the same CPD.  NOTE: Cardinality greater than 1 is only applicable for specific cases where more than one network attachment definition  resource is needed to fulfil the connectivity requirements of the VNF internal CP, e.g. to build a link redundant mated  pair in SR-IOV cases. When more than one \&quot;netAttDefResourceId\&quot; is indicated, all shall belong to the same namespace as  defined by the corresponding \&quot;netAttDefResourceNamespace\&quot; attribute in the \&quot;NetAttDefResourceData\&quot;. 
 */
@Schema(description = "This type represents the input information related to one or more VNF internal CP instances created based on the same CPD.  NOTE: Cardinality greater than 1 is only applicable for specific cases where more than one network attachment definition  resource is needed to fulfil the connectivity requirements of the VNF internal CP, e.g. to build a link redundant mated  pair in SR-IOV cases. When more than one \"netAttDefResourceId\" is indicated, all shall belong to the same namespace as  defined by the corresponding \"netAttDefResourceNamespace\" attribute in the \"NetAttDefResourceData\". ")
@Validated


public class IntVnfCpData   {
  @JsonProperty("cpdId")
  private String cpdId = null;

  @JsonProperty("netAttDefResourceId")
  private String netAttDefResourceId = null;

  public IntVnfCpData cpdId(String cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  /**
   * Get cpdId
   * @return cpdId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getCpdId() {
    return cpdId;
  }

  public void setCpdId(String cpdId) {
    this.cpdId = cpdId;
  }

  public IntVnfCpData netAttDefResourceId(String netAttDefResourceId) {
    this.netAttDefResourceId = netAttDefResourceId;
    return this;
  }

  /**
   * Get netAttDefResourceId
   * @return netAttDefResourceId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getNetAttDefResourceId() {
    return netAttDefResourceId;
  }

  public void setNetAttDefResourceId(String netAttDefResourceId) {
    this.netAttDefResourceId = netAttDefResourceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntVnfCpData intVnfCpData = (IntVnfCpData) o;
    return Objects.equals(this.cpdId, intVnfCpData.cpdId) &&
        Objects.equals(this.netAttDefResourceId, intVnfCpData.netAttDefResourceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, netAttDefResourceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IntVnfCpData {\n");
    
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    netAttDefResourceId: ").append(toIndentedString(netAttDefResourceId)).append("\n");
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
