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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnfind;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.vnfm.v431.model.vnfind.SupportedIndicatorsChangeNotificationLinks;
import com.ubiqube.etsi.mano.vnfm.v431.model.vnfind.SupportedIndicatorsChangeNotificationSupportedIndicators;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents a notification to inform the receiver that the set of indicators supported  by a VNF instance has changed. The notification shall be triggered by the VNFM when the set of supported VNF indicators has changed  as a side effect of the \&quot;Change current VNF package\&quot; operation. It may be triggered by the VNFM when  a VNF has been instantiated. NOTE: ETSI GS NFV-SOL 001 specifies the structure and format of the VNFD based on TOSCA specifications. 
 */
@Schema(description = "This type represents a notification to inform the receiver that the set of indicators supported  by a VNF instance has changed. The notification shall be triggered by the VNFM when the set of supported VNF indicators has changed  as a side effect of the \"Change current VNF package\" operation. It may be triggered by the VNFM when  a VNF has been instantiated. NOTE: ETSI GS NFV-SOL 001 specifies the structure and format of the VNFD based on TOSCA specifications. ")
@Validated


public class SupportedIndicatorsChangeNotification   {
  @JsonProperty("id")
  private String id = null;

  /**
   * Discriminator for the different notification types. Shall be set to  \"SupportedIndicatorsChangeNotification\" for this notification type. 
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

  @JsonProperty("vnfInstanceId")
  private String vnfInstanceId = null;

  @JsonProperty("supportedIndicators")
  @Valid
  private List<SupportedIndicatorsChangeNotificationSupportedIndicators> supportedIndicators = null;

  @JsonProperty("_links")
  private SupportedIndicatorsChangeNotificationLinks _links = null;

  public SupportedIndicatorsChangeNotification id(String id) {
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

  public SupportedIndicatorsChangeNotification notificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
    return this;
  }

  /**
   * Discriminator for the different notification types. Shall be set to  \"SupportedIndicatorsChangeNotification\" for this notification type. 
   * @return notificationType
   **/
  @Schema(required = true, description = "Discriminator for the different notification types. Shall be set to  \"SupportedIndicatorsChangeNotification\" for this notification type. ")
      @NotNull

    public NotificationTypeEnum getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
  }

  public SupportedIndicatorsChangeNotification subscriptionId(String subscriptionId) {
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

  public SupportedIndicatorsChangeNotification timeStamp(OffsetDateTime timeStamp) {
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

  public SupportedIndicatorsChangeNotification vnfInstanceId(String vnfInstanceId) {
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

  public SupportedIndicatorsChangeNotification supportedIndicators(List<SupportedIndicatorsChangeNotificationSupportedIndicators> supportedIndicators) {
    this.supportedIndicators = supportedIndicators;
    return this;
  }

  public SupportedIndicatorsChangeNotification addSupportedIndicatorsItem(SupportedIndicatorsChangeNotificationSupportedIndicators supportedIndicatorsItem) {
    if (this.supportedIndicators == null) {
      this.supportedIndicators = new ArrayList<>();
    }
    this.supportedIndicators.add(supportedIndicatorsItem);
    return this;
  }

  /**
   * Set of VNF indicators supported by the VNF instance. 
   * @return supportedIndicators
   **/
  @Schema(description = "Set of VNF indicators supported by the VNF instance. ")
      @Valid
    public List<SupportedIndicatorsChangeNotificationSupportedIndicators> getSupportedIndicators() {
    return supportedIndicators;
  }

  public void setSupportedIndicators(List<SupportedIndicatorsChangeNotificationSupportedIndicators> supportedIndicators) {
    this.supportedIndicators = supportedIndicators;
  }

  public SupportedIndicatorsChangeNotification _links(SupportedIndicatorsChangeNotificationLinks _links) {
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
    public SupportedIndicatorsChangeNotificationLinks getLinks() {
    return _links;
  }

  public void setLinks(SupportedIndicatorsChangeNotificationLinks _links) {
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
    SupportedIndicatorsChangeNotification supportedIndicatorsChangeNotification = (SupportedIndicatorsChangeNotification) o;
    return Objects.equals(this.id, supportedIndicatorsChangeNotification.id) &&
        Objects.equals(this.notificationType, supportedIndicatorsChangeNotification.notificationType) &&
        Objects.equals(this.subscriptionId, supportedIndicatorsChangeNotification.subscriptionId) &&
        Objects.equals(this.timeStamp, supportedIndicatorsChangeNotification.timeStamp) &&
        Objects.equals(this.vnfInstanceId, supportedIndicatorsChangeNotification.vnfInstanceId) &&
        Objects.equals(this.supportedIndicators, supportedIndicatorsChangeNotification.supportedIndicators) &&
        Objects.equals(this._links, supportedIndicatorsChangeNotification._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, notificationType, subscriptionId, timeStamp, vnfInstanceId, supportedIndicators, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportedIndicatorsChangeNotification {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
    sb.append("    vnfInstanceId: ").append(toIndentedString(vnfInstanceId)).append("\n");
    sb.append("    supportedIndicators: ").append(toIndentedString(supportedIndicators)).append("\n");
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
