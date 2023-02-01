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
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * VnfmSpecificInfoSupportedVnfdFormats
 */
@Validated


public class VnfmSpecificInfoSupportedVnfdFormats   {
  /**
   * Name of the VNFD format. Permitted values:   - TOSCA: The VNFD follows TOSCA definition, according to ETSI   GS NFV-SOL 001 standard.   - YANG: The VNFD follows YANG definition according to ETSI   GS NFV-SOL 006 standard. 
   */
  public enum VnfdFormatEnum {
    TOSCA("TOSCA"),
    
    YANG("YANG");

    private String value;

    VnfdFormatEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static VnfdFormatEnum fromValue(String text) {
      for (VnfdFormatEnum b : VnfdFormatEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("vnfdFormat")
  private VnfdFormatEnum vnfdFormat = null;

  @JsonProperty("standardVersion")
  private String standardVersion = null;

  public VnfmSpecificInfoSupportedVnfdFormats vnfdFormat(VnfdFormatEnum vnfdFormat) {
    this.vnfdFormat = vnfdFormat;
    return this;
  }

  /**
   * Name of the VNFD format. Permitted values:   - TOSCA: The VNFD follows TOSCA definition, according to ETSI   GS NFV-SOL 001 standard.   - YANG: The VNFD follows YANG definition according to ETSI   GS NFV-SOL 006 standard. 
   * @return vnfdFormat
   **/
  @Schema(required = true, description = "Name of the VNFD format. Permitted values:   - TOSCA: The VNFD follows TOSCA definition, according to ETSI   GS NFV-SOL 001 standard.   - YANG: The VNFD follows YANG definition according to ETSI   GS NFV-SOL 006 standard. ")
      @NotNull

    public VnfdFormatEnum getVnfdFormat() {
    return vnfdFormat;
  }

  public void setVnfdFormat(VnfdFormatEnum vnfdFormat) {
    this.vnfdFormat = vnfdFormat;
  }

  public VnfmSpecificInfoSupportedVnfdFormats standardVersion(String standardVersion) {
    this.standardVersion = standardVersion;
    return this;
  }

  /**
   * A version. 
   * @return standardVersion
   **/
  @Schema(required = true, description = "A version. ")
      @NotNull

    public String getStandardVersion() {
    return standardVersion;
  }

  public void setStandardVersion(String standardVersion) {
    this.standardVersion = standardVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfmSpecificInfoSupportedVnfdFormats vnfmSpecificInfoSupportedVnfdFormats = (VnfmSpecificInfoSupportedVnfdFormats) o;
    return Objects.equals(this.vnfdFormat, vnfmSpecificInfoSupportedVnfdFormats.vnfdFormat) &&
        Objects.equals(this.standardVersion, vnfmSpecificInfoSupportedVnfdFormats.standardVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfdFormat, standardVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfmSpecificInfoSupportedVnfdFormats {\n");
    
    sb.append("    vnfdFormat: ").append(toIndentedString(vnfdFormat)).append("\n");
    sb.append("    standardVersion: ").append(toIndentedString(standardVersion)).append("\n");
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
