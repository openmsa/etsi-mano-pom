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
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.NfvoSpecificInfoSupportedNsdFormats;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.NfvoSpecificInfoSupportedVnfdFormats;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents information attributes specific to an NFVO entity,  and that can be relevant to more than one NFV-MANO service offered by an  NFVO entity.  
 */
@Schema (description= "This type represents information attributes specific to an NFVO entity,  and that can be relevant to more than one NFV-MANO service offered by an  NFVO entity.  " )
@Validated
public class NfvoSpecificInfo   {
  @JsonProperty("maxOnboardedNsdNum")
  private Integer maxOnboardedNsdNum = null;

  @JsonProperty("maxOnboardedVnfPkgNum")
  private Integer maxOnboardedVnfPkgNum = null;

  @JsonProperty("supportedVnfdFormats")
  private NfvoSpecificInfoSupportedVnfdFormats supportedVnfdFormats = null;

  @JsonProperty("supportedNsdFormats")
  private NfvoSpecificInfoSupportedNsdFormats supportedNsdFormats = null;

  public NfvoSpecificInfo maxOnboardedNsdNum(Integer maxOnboardedNsdNum) {
    this.maxOnboardedNsdNum = maxOnboardedNsdNum;
    return this;
  }

  /**
   * Maximum number of NSDs that can be on-boarded on the NFVO.  NOTE: If this attribute is not present, the value of this parameter  is undefined. 
   * @return maxOnboardedNsdNum
  **/
  @Schema(description= "Maximum number of NSDs that can be on-boarded on the NFVO.  NOTE: If this attribute is not present, the value of this parameter  is undefined. " )
  
    public Integer getMaxOnboardedNsdNum() {
    return maxOnboardedNsdNum;
  }

  public void setMaxOnboardedNsdNum(Integer maxOnboardedNsdNum) {
    this.maxOnboardedNsdNum = maxOnboardedNsdNum;
  }

  public NfvoSpecificInfo maxOnboardedVnfPkgNum(Integer maxOnboardedVnfPkgNum) {
    this.maxOnboardedVnfPkgNum = maxOnboardedVnfPkgNum;
    return this;
  }

  /**
   * Maximum number of VNF Packages that can be on-boarded on the NFVO.  NOTE: If this attribute is not present, the value of this parameter  is undefined. 
   * @return maxOnboardedVnfPkgNum
  **/
  @Schema(description= "Maximum number of VNF Packages that can be on-boarded on the NFVO.  NOTE: If this attribute is not present, the value of this parameter  is undefined. " )
  
    public Integer getMaxOnboardedVnfPkgNum() {
    return maxOnboardedVnfPkgNum;
  }

  public void setMaxOnboardedVnfPkgNum(Integer maxOnboardedVnfPkgNum) {
    this.maxOnboardedVnfPkgNum = maxOnboardedVnfPkgNum;
  }

  public NfvoSpecificInfo supportedVnfdFormats(NfvoSpecificInfoSupportedVnfdFormats supportedVnfdFormats) {
    this.supportedVnfdFormats = supportedVnfdFormats;
    return this;
  }

  /**
   * Get supportedVnfdFormats
   * @return supportedVnfdFormats
  **/
  @Schema(required= true ,description= "" )
      @NotNull

    @Valid
    public NfvoSpecificInfoSupportedVnfdFormats getSupportedVnfdFormats() {
    return supportedVnfdFormats;
  }

  public void setSupportedVnfdFormats(NfvoSpecificInfoSupportedVnfdFormats supportedVnfdFormats) {
    this.supportedVnfdFormats = supportedVnfdFormats;
  }

  public NfvoSpecificInfo supportedNsdFormats(NfvoSpecificInfoSupportedNsdFormats supportedNsdFormats) {
    this.supportedNsdFormats = supportedNsdFormats;
    return this;
  }

  /**
   * Get supportedNsdFormats
   * @return supportedNsdFormats
  **/
  @Schema(required= true ,description= "" )
      @NotNull

    @Valid
    public NfvoSpecificInfoSupportedNsdFormats getSupportedNsdFormats() {
    return supportedNsdFormats;
  }

  public void setSupportedNsdFormats(NfvoSpecificInfoSupportedNsdFormats supportedNsdFormats) {
    this.supportedNsdFormats = supportedNsdFormats;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfvoSpecificInfo nfvoSpecificInfo = (NfvoSpecificInfo) o;
    return Objects.equals(this.maxOnboardedNsdNum, nfvoSpecificInfo.maxOnboardedNsdNum) &&
        Objects.equals(this.maxOnboardedVnfPkgNum, nfvoSpecificInfo.maxOnboardedVnfPkgNum) &&
        Objects.equals(this.supportedVnfdFormats, nfvoSpecificInfo.supportedVnfdFormats) &&
        Objects.equals(this.supportedNsdFormats, nfvoSpecificInfo.supportedNsdFormats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxOnboardedNsdNum, maxOnboardedVnfPkgNum, supportedVnfdFormats, supportedNsdFormats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfvoSpecificInfo {\n");
    
    sb.append("    maxOnboardedNsdNum: ").append(toIndentedString(maxOnboardedNsdNum)).append("\n");
    sb.append("    maxOnboardedVnfPkgNum: ").append(toIndentedString(maxOnboardedVnfPkgNum)).append("\n");
    sb.append("    supportedVnfdFormats: ").append(toIndentedString(supportedVnfdFormats)).append("\n");
    sb.append("    supportedNsdFormats: ").append(toIndentedString(supportedNsdFormats)).append("\n");
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
