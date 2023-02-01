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
 * Information for the NTP based clock synchronization. Shall be present if type &#x3D; \&quot;NTP\&quot;. 
 */
@Schema(description = "Information for the NTP based clock synchronization. Shall be present if type = \"NTP\". ")
@Validated


public class ClockSyncInfoNtpServerInfo   {
  @JsonProperty("ipAddress")
  private String ipAddress = null;

  @JsonProperty("hostname")
  private String hostname = null;

  public ClockSyncInfoNtpServerInfo ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * An IPV4 or IPV6 address. Representation: In case of an IPV4 address, string that consists of four decimal integers separated by dots, each integer ranging from 0 to 255. In case of an IPV6 address, string that consists of groups of zero to four hexadecimal digits, separated by colons. 
   * @return ipAddress
   **/
  @Schema(description = "An IPV4 or IPV6 address. Representation: In case of an IPV4 address, string that consists of four decimal integers separated by dots, each integer ranging from 0 to 255. In case of an IPV6 address, string that consists of groups of zero to four hexadecimal digits, separated by colons. ")
  
    public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public ClockSyncInfoNtpServerInfo hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  /**
   * Indicates the hostname of the NTP server. See note. 
   * @return hostname
   **/
  @Schema(description = "Indicates the hostname of the NTP server. See note. ")
  
    public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClockSyncInfoNtpServerInfo clockSyncInfoNtpServerInfo = (ClockSyncInfoNtpServerInfo) o;
    return Objects.equals(this.ipAddress, clockSyncInfoNtpServerInfo.ipAddress) &&
        Objects.equals(this.hostname, clockSyncInfoNtpServerInfo.hostname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress, hostname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClockSyncInfoNtpServerInfo {\n");
    
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
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
