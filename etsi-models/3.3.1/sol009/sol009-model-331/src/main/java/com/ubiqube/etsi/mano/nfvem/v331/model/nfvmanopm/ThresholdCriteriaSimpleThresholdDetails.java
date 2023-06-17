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
package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Details of a simple threshold. Shall be present if thresholdType&#x3D;\&quot;SIMPLE\&quot;. 
 */
@Schema(description = "Details of a simple threshold. Shall be present if thresholdType=\"SIMPLE\". ")
@Validated


public class ThresholdCriteriaSimpleThresholdDetails   {
  @JsonProperty("thresholdValue")
  private BigDecimal thresholdValue = null;

  @JsonProperty("hysteresis")
  private BigDecimal hysteresis = null;

  public ThresholdCriteriaSimpleThresholdDetails thresholdValue(BigDecimal thresholdValue) {
    this.thresholdValue = thresholdValue;
    return this;
  }

  /**
   * Get thresholdValue
   * @return thresholdValue
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getThresholdValue() {
    return thresholdValue;
  }

  public void setThresholdValue(BigDecimal thresholdValue) {
    this.thresholdValue = thresholdValue;
  }

  public ThresholdCriteriaSimpleThresholdDetails hysteresis(BigDecimal hysteresis) {
    this.hysteresis = hysteresis;
    return this;
  }

  /**
   * Get hysteresis
   * @return hysteresis
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getHysteresis() {
    return hysteresis;
  }

  public void setHysteresis(BigDecimal hysteresis) {
    this.hysteresis = hysteresis;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThresholdCriteriaSimpleThresholdDetails thresholdCriteriaSimpleThresholdDetails = (ThresholdCriteriaSimpleThresholdDetails) o;
    return Objects.equals(this.thresholdValue, thresholdCriteriaSimpleThresholdDetails.thresholdValue) &&
        Objects.equals(this.hysteresis, thresholdCriteriaSimpleThresholdDetails.hysteresis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(thresholdValue, hysteresis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ThresholdCriteriaSimpleThresholdDetails {\n");
    
    sb.append("    thresholdValue: ").append(toIndentedString(thresholdValue)).append("\n");
    sb.append("    hysteresis: ").append(toIndentedString(hysteresis)).append("\n");
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
