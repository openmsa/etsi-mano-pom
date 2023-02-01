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
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type describes the service identifying port properties exposed by the virtual CP instance. 
 */
@Schema(description = "This type describes the service identifying port properties exposed by the virtual CP instance. ")
@Validated


public class ServicePortInfo   {
  @JsonProperty("name")
  private String name = null;

  /**
   * The L4 protocol for this port exposed by the virtual CP instance. Permitted values: - TCP - UDP - SCTP 
   */
  public enum ProtocolEnum {
    TCP("TCP"),
    
    UDP("UDP"),
    
    SCTP("SCTP");

    private String value;

    ProtocolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ProtocolEnum fromValue(String text) {
      for (ProtocolEnum b : ProtocolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("protocol")
  private ProtocolEnum protocol = null;

  @JsonProperty("port")
  private Integer port = null;

  @JsonProperty("portConfigurable")
  private Boolean portConfigurable = null;

  public ServicePortInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the port exposed by the virtual CP instance. 
   * @return name
   **/
  @Schema(required = true, description = "The name of the port exposed by the virtual CP instance. ")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ServicePortInfo protocol(ProtocolEnum protocol) {
    this.protocol = protocol;
    return this;
  }

  /**
   * The L4 protocol for this port exposed by the virtual CP instance. Permitted values: - TCP - UDP - SCTP 
   * @return protocol
   **/
  @Schema(description = "The L4 protocol for this port exposed by the virtual CP instance. Permitted values: - TCP - UDP - SCTP ")
  
    public ProtocolEnum getProtocol() {
    return protocol;
  }

  public void setProtocol(ProtocolEnum protocol) {
    this.protocol = protocol;
  }

  public ServicePortInfo port(Integer port) {
    this.port = port;
    return this;
  }

  /**
   * The L4 port number exposed by the virtual CP instance. 
   * @return port
   **/
  @Schema(required = true, description = "The L4 port number exposed by the virtual CP instance. ")
      @NotNull

    public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public ServicePortInfo portConfigurable(Boolean portConfigurable) {
    this.portConfigurable = portConfigurable;
    return this;
  }

  /**
   * Get portConfigurable
   * @return portConfigurable
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Boolean getPortConfigurable() {
    return portConfigurable;
  }

  public void setPortConfigurable(Boolean portConfigurable) {
    this.portConfigurable = portConfigurable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServicePortInfo servicePortInfo = (ServicePortInfo) o;
    return Objects.equals(this.name, servicePortInfo.name) &&
        Objects.equals(this.protocol, servicePortInfo.protocol) &&
        Objects.equals(this.port, servicePortInfo.port) &&
        Objects.equals(this.portConfigurable, servicePortInfo.portConfigurable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, protocol, port, portConfigurable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServicePortInfo {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    portConfigurable: ").append(toIndentedString(portConfigurable)).append("\n");
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
