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
package com.ubiqube.etsi.mano.em.v431.model.vnflcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents the scale level of a VNF instance related to a scaling aspect. 
 */
@Schema(description = "This type represents the scale level of a VNF instance related to a scaling aspect. ")
@Validated


public class ScaleInfo   {
  @JsonProperty("aspectId")
  private String aspectId = null;

  @JsonProperty("vnfdId")
  private String vnfdId = null;

  @JsonProperty("scaleToLevel")
  private String scaleToLevel = null;

  public ScaleInfo aspectId(String aspectId) {
    this.aspectId = aspectId;
    return this;
  }

  /**
   * Get aspectId
   * @return aspectId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getAspectId() {
    return aspectId;
  }

  public void setAspectId(String aspectId) {
    this.aspectId = aspectId;
  }

  public ScaleInfo vnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  /**
   * Get vnfdId
   * @return vnfdId
   **/
  @Schema(description = "")
  
    public String getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
  }

  public ScaleInfo scaleToLevel(String scaleToLevel) {
    this.scaleToLevel = scaleToLevel;
    return this;
  }

  /**
   * Get scaleToLevel
   * @return scaleToLevel
   **/
  @Schema(description = "")
  
    public String getScaleToLevel() {
    return scaleToLevel;
  }

  public void setScaleToLevel(String scaleToLevel) {
    this.scaleToLevel = scaleToLevel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScaleInfo scaleInfo = (ScaleInfo) o;
    return Objects.equals(this.aspectId, scaleInfo.aspectId) &&
        Objects.equals(this.vnfdId, scaleInfo.vnfdId) &&
        Objects.equals(this.scaleToLevel, scaleInfo.scaleToLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aspectId, vnfdId, scaleToLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScaleInfo {\n");
    
    sb.append("    aspectId: ").append(toIndentedString(aspectId)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    scaleToLevel: ").append(toIndentedString(scaleToLevel)).append("\n");
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
