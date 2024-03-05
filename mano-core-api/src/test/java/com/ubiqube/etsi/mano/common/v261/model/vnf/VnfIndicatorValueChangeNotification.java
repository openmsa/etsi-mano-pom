/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.common.v261.model.vnf;

import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents a VNF indicator value change notification. The notification shall be triggered by the VNFM when the value of an indicator has changed. 
 */
@Schema(description = "This type represents a VNF indicator value change notification. The notification shall be triggered by the VNFM when the value of an indicator has changed. ")
@Validated


public class VnfIndicatorValueChangeNotification   {
  @JsonProperty("id")
  private String id = null;

  /**
   * Discriminator for the different notification types. Shall be set to \"VnfIndicatorValueChangeNotification\" for this notification type. 
   */
  public enum NotificationTypeEnum {
    VNFINDICATORVALUECHANGENOTIFICATION("VnfIndicatorValueChangeNotification");

    private String value;

    NotificationTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static NotificationTypeEnum fromValue(String text) {
      for (NotificationTypeEnum b : NotificationTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("notificationType")
  private NotificationTypeEnum notificationType = null;

  @JsonProperty("subscriptionId")
  private String subscriptionId = null;

  @JsonProperty("timeStamp")
  private OffsetDateTime timeStamp = null;

  @JsonProperty("vnfIndicatorId")
  private String vnfIndicatorId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("value")
  private Object value = null;

  @JsonProperty("vnfInstanceId")
  private String vnfInstanceId = null;
  
  @JsonProperty("vnfdId")
  private String vnfdId = null;

public String getVnfdId() {
	return vnfdId;
  }

  public void setVnfdId(String vnfdId) {
		this.vnfdId = vnfdId;
  }

@JsonProperty("_links")
  private VnfIndicatorValueChangeNotificationLinks _links = null;

  public VnfIndicatorValueChangeNotification id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfIndicatorValueChangeNotification notificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
    return this;
  }

  /**
   * Discriminator for the different notification types. Shall be set to \"VnfIndicatorValueChangeNotification\" for this notification type. 
   * @return notificationType
   **/
  @Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"VnfIndicatorValueChangeNotification\" for this notification type. ")
      @NotNull

    public NotificationTypeEnum getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
  }

  public VnfIndicatorValueChangeNotification subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

  /**
   * Get subscriptionId
   * @return subscriptionId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public VnfIndicatorValueChangeNotification timeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  /**
   * Get timeStamp
   * @return timeStamp
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public VnfIndicatorValueChangeNotification vnfIndicatorId(String vnfIndicatorId) {
    this.vnfIndicatorId = vnfIndicatorId;
    return this;
  }

  /**
   * Get vnfIndicatorId
   * @return vnfIndicatorId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getVnfIndicatorId() {
    return vnfIndicatorId;
  }

  public void setVnfIndicatorId(String vnfIndicatorId) {
    this.vnfIndicatorId = vnfIndicatorId;
  }

  public VnfIndicatorValueChangeNotification name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Human readable name of the VNF indicator. Shall be present if defined in the VNFD. 
   * @return name
   **/
  @Schema(description = "Human readable name of the VNF indicator. Shall be present if defined in the VNFD. ")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VnfIndicatorValueChangeNotification value(Object value) {
    this.value = value;
    return this;
  }

  /**
   * Provides the value of the VNF indicator. The value format is defined in the VNFD. ETSI GS NFV-SOL 001 specifies the structure and format of the VNFD based on TOSCA specifications. 
   * @return value
   **/
  @Schema(required = true, description = "Provides the value of the VNF indicator. The value format is defined in the VNFD. ETSI GS NFV-SOL 001 specifies the structure and format of the VNFD based on TOSCA specifications. ")
      @NotNull

    public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public VnfIndicatorValueChangeNotification vnfInstanceId(String vnfInstanceId) {
    this.vnfInstanceId = vnfInstanceId;
    return this;
  }

  /**
   * Get vnfInstanceId
   * @return vnfInstanceId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getVnfInstanceId() {
    return vnfInstanceId;
  }

  public void setVnfInstanceId(String vnfInstanceId) {
    this.vnfInstanceId = vnfInstanceId;
  }

  public VnfIndicatorValueChangeNotification _links(VnfIndicatorValueChangeNotificationLinks _links) {
    this._links = _links;
    return this;
  }

  /**
   * Get _links
   * @return _links
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public VnfIndicatorValueChangeNotificationLinks getLinks() {
    return _links;
  }

  public void setLinks(VnfIndicatorValueChangeNotificationLinks _links) {
    this._links = _links;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfIndicatorValueChangeNotification vnfIndicatorValueChangeNotification = (VnfIndicatorValueChangeNotification) o;
    return Objects.equals(this.id, vnfIndicatorValueChangeNotification.id) &&
        Objects.equals(this.notificationType, vnfIndicatorValueChangeNotification.notificationType) &&
        Objects.equals(this.subscriptionId, vnfIndicatorValueChangeNotification.subscriptionId) &&
        Objects.equals(this.timeStamp, vnfIndicatorValueChangeNotification.timeStamp) &&
        Objects.equals(this.vnfIndicatorId, vnfIndicatorValueChangeNotification.vnfIndicatorId) &&
        Objects.equals(this.name, vnfIndicatorValueChangeNotification.name) &&
        Objects.equals(this.value, vnfIndicatorValueChangeNotification.value) &&
        Objects.equals(this.vnfInstanceId, vnfIndicatorValueChangeNotification.vnfInstanceId) &&
        Objects.equals(this.vnfdId, vnfIndicatorValueChangeNotification.vnfdId) &&
        Objects.equals(this._links, vnfIndicatorValueChangeNotification._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, notificationType, subscriptionId, timeStamp, vnfIndicatorId, name, value, vnfInstanceId, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfIndicatorValueChangeNotification {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
    sb.append("    vnfIndicatorId: ").append(toIndentedString(vnfIndicatorId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    vnfInstanceId: ").append(toIndentedString(vnfInstanceId)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
