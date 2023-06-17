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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanocim.ClockSyncInfoNtpServerInfo;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents parameters for connecting to an NTP server.  
 */
@Schema (description= "This type represents parameters for connecting to an NTP server.  " )
@Validated
public class ClockSyncInfo   {
  @JsonProperty("id")
  private String id = null;

  /**
   * Type of clock synchronization. Permitted values:   - NTP: For Network Time Protocol (NTP) based clock synchronization.   - OTHER: For other types of clock synchronization. 
   */
  public enum TypeEnum {
    NTP("NTP"),
    
    OTHER("OTHER");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("ntpServerInfo")
  private ClockSyncInfoNtpServerInfo ntpServerInfo = null;

  @JsonProperty("otherClockSyncParams")
  private Map<String, String> otherClockSyncParams = null;

  public ClockSyncInfo id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @Schema(required= true ,description= "" )
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ClockSyncInfo type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of clock synchronization. Permitted values:   - NTP: For Network Time Protocol (NTP) based clock synchronization.   - OTHER: For other types of clock synchronization. 
   * @return type
  **/
  @Schema(required= true ,description= "Type of clock synchronization. Permitted values:   - NTP: For Network Time Protocol (NTP) based clock synchronization.   - OTHER: For other types of clock synchronization. " )
      @NotNull

    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ClockSyncInfo ntpServerInfo(ClockSyncInfoNtpServerInfo ntpServerInfo) {
    this.ntpServerInfo = ntpServerInfo;
    return this;
  }

  /**
   * Get ntpServerInfo
   * @return ntpServerInfo
  **/
  @Schema(description= "" )
  
    @Valid
    public ClockSyncInfoNtpServerInfo getNtpServerInfo() {
    return ntpServerInfo;
  }

  public void setNtpServerInfo(ClockSyncInfoNtpServerInfo ntpServerInfo) {
    this.ntpServerInfo = ntpServerInfo;
  }

  public ClockSyncInfo otherClockSyncParams(Map<String, String> otherClockSyncParams) {
    this.otherClockSyncParams = otherClockSyncParams;
    return this;
  }

  /**
   * Get otherClockSyncParams
   * @return otherClockSyncParams
  **/
  @Schema(description= "" )
  
    @Valid
    public Map<String, String> getOtherClockSyncParams() {
    return otherClockSyncParams;
  }

  public void setOtherClockSyncParams(Map<String, String> otherClockSyncParams) {
    this.otherClockSyncParams = otherClockSyncParams;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClockSyncInfo clockSyncInfo = (ClockSyncInfo) o;
    return Objects.equals(this.id, clockSyncInfo.id) &&
        Objects.equals(this.type, clockSyncInfo.type) &&
        Objects.equals(this.ntpServerInfo, clockSyncInfo.ntpServerInfo) &&
        Objects.equals(this.otherClockSyncParams, clockSyncInfo.otherClockSyncParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, ntpServerInfo, otherClockSyncParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClockSyncInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    ntpServerInfo: ").append(toIndentedString(ntpServerInfo)).append("\n");
    sb.append("    otherClockSyncParams: ").append(toIndentedString(otherClockSyncParams)).append("\n");
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
