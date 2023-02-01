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
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.NsLcmCapacityShortageNotificationAffectedOpOccs;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.NsLcmCapacityShortageNotificationCapacityInformation;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.NsLcmCapacityShortageNotificationLinks1;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents an NS LCM capacity shortage notification, which informs the receiver about resource shortage conditions during the execution of NS LCM operations. The notifications are triggered by the NFVO when a capacity shortage condition occurs during the execution of an NS LCM operation, which fails due to the resource shortage, or which succeeds despite the resource shortage because the NFVO has reduced the resource consumption of other NSs by requesting these NSs to be scaled in or terminated. The notification shall comply with the provisions defined in Table 6.5.2.19-1. The support of the notification is mandatory. This notification shall be triggered by the NFVO when there is a capacity shortage condition during the execution of an NS LCM operation which will cause the LCM operation to be not successfully completed, or which will trigger the automatic executing of an LCM operation to reduce the resource consumption of one or more NS instances to resolve a resource shortage situation. The shortage conditions include: • Necessary resources could not be allocated during an LCM operation because of resource shortage which causes   the LCM operation to fail. • An LCM operation on an NS instance with higher priority pre-empted an LCM operation on NS instance with lower   priority because of resource shortage. • An LCM operation on an NS instance with higher priority pre-empted an existing NS instance. Resources were   de-allocated from the lower priority NS instance to allow the LCM operation on a higher priority NS instance. • The resource capacity shortage situation has ended, and it can be expected that an LCM operation that had   failed could succeed now if retried.  NOTE: ETSI GS NFV-IFA 013 [x] defines further shortage situations.       These are not supported by the present version of the present document.  This notification shall also be triggered by the NFVO when a shortage condition has ended that has previously led to NS LCM operation occurrences failing. The notification shall be sent to all API consumers (OSS/BSS) that have subscribed to notifications related to capacity shortage and meeting the filter conditions of all pre-empted (low priority) and all pre-empting (high priority NS instance(s)). See ETSI GS NFV-IFA 010 [2], Annex D.2 for the use cases. The notification about the result of an unsuccessful LCM operation occurrence shall include appropriate information about the resource shortage when the cause for failure is a resource shortage. The notification where a pre-emption occurred due to e.g. a higher priority LCM operation during resource shortage shall include appropriate information about the pre-emption. NOTE:     Not all operation occurrences that are in \&quot;FAILED_TEMP\&quot; have been pre-empted by a resource shortage.           When the operation occurrences were pre-empted, the NS instances affected by the resource shortage end           (“status“ &#x3D; \&quot;LCM_SHORTAGE_END\&quot;) which are pointed by \&quot;affectedNsId\&quot; in the list of \&quot;affectedOpOccs\&quot; structure           can have the operations retried again (which needs a request by the OSS/BSS).  NOTE 1: The present version of the present document supports only the resource shortage status enumeration           values “LCM_RESOURCES_NOT_AVAILABLE” and “LCM_SHORTAGE_END” which represent a subset of the trigger conditions           defined in clause 8.3.5.2  of ETSI GS NFV-IFA 013 [x]. 
 */
@Schema(description = "This type represents an NS LCM capacity shortage notification, which informs the receiver about resource shortage conditions during the execution of NS LCM operations. The notifications are triggered by the NFVO when a capacity shortage condition occurs during the execution of an NS LCM operation, which fails due to the resource shortage, or which succeeds despite the resource shortage because the NFVO has reduced the resource consumption of other NSs by requesting these NSs to be scaled in or terminated. The notification shall comply with the provisions defined in Table 6.5.2.19-1. The support of the notification is mandatory. This notification shall be triggered by the NFVO when there is a capacity shortage condition during the execution of an NS LCM operation which will cause the LCM operation to be not successfully completed, or which will trigger the automatic executing of an LCM operation to reduce the resource consumption of one or more NS instances to resolve a resource shortage situation. The shortage conditions include: • Necessary resources could not be allocated during an LCM operation because of resource shortage which causes   the LCM operation to fail. • An LCM operation on an NS instance with higher priority pre-empted an LCM operation on NS instance with lower   priority because of resource shortage. • An LCM operation on an NS instance with higher priority pre-empted an existing NS instance. Resources were   de-allocated from the lower priority NS instance to allow the LCM operation on a higher priority NS instance. • The resource capacity shortage situation has ended, and it can be expected that an LCM operation that had   failed could succeed now if retried.  NOTE: ETSI GS NFV-IFA 013 [x] defines further shortage situations.       These are not supported by the present version of the present document.  This notification shall also be triggered by the NFVO when a shortage condition has ended that has previously led to NS LCM operation occurrences failing. The notification shall be sent to all API consumers (OSS/BSS) that have subscribed to notifications related to capacity shortage and meeting the filter conditions of all pre-empted (low priority) and all pre-empting (high priority NS instance(s)). See ETSI GS NFV-IFA 010 [2], Annex D.2 for the use cases. The notification about the result of an unsuccessful LCM operation occurrence shall include appropriate information about the resource shortage when the cause for failure is a resource shortage. The notification where a pre-emption occurred due to e.g. a higher priority LCM operation during resource shortage shall include appropriate information about the pre-emption. NOTE:     Not all operation occurrences that are in \"FAILED_TEMP\" have been pre-empted by a resource shortage.           When the operation occurrences were pre-empted, the NS instances affected by the resource shortage end           (“status“ = \"LCM_SHORTAGE_END\") which are pointed by \"affectedNsId\" in the list of \"affectedOpOccs\" structure           can have the operations retried again (which needs a request by the OSS/BSS).  NOTE 1: The present version of the present document supports only the resource shortage status enumeration           values “LCM_RESOURCES_NOT_AVAILABLE” and “LCM_SHORTAGE_END” which represent a subset of the trigger conditions           defined in clause 8.3.5.2  of ETSI GS NFV-IFA 013 [x]. ")
@Validated


public class NsLcmCapacityShortageNotification   {
  @JsonProperty("id")
  private String id = null;

  /**
   * Discriminator for the different notification types. Shall be set to \"NsLcmCapacityShortageNotification\" for this notification type. 
   */
  public enum NotificationTypeEnum {
    NSLCMCAPACITYSHORTAGENOTIFICATION("NsLcmCapacityShortageNotification");

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

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  @JsonProperty("preemptingNsLcmOpOccId")
  private String preemptingNsLcmOpOccId = null;

  @JsonProperty("highPrioNsInstanceId")
  private String highPrioNsInstanceId = null;

  /**
   * Indicates the situation of capacity shortage. Permitted values: - LCM_RESOURCES_NOT_AVAILABLE: the lifecycle operation identified by the nsLcmOpOccId attribute could not   be completed because necessary resources were not available. - LCM_SHORTAGE_END: the shortage situation which has caused the lifecycle management operation identified   by the nsLcmOpOccId attribute to fail has ended. 
   */
  public enum StatusEnum {
    RESOURCES_NOT_AVAILABLE("LCM_RESOURCES_NOT_AVAILABLE"),
    
    SHORTAGE_END("LCM_SHORTAGE_END");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  /**
   * Indicates whether this notification reports about a resource shortage or NFV-MANO capacity or performance shortage. Permitted values: - RESOURCE_SHORTAGE: the notification reports a resource shortage. Shall be present when a resources shortage situation has been identified starts and the notification is sent with “status“ = “LCM_RESOURCES_NOT_AVAILABLE“ and shall be absent otherwise. 
   */
  public enum ShortageTypeEnum {
    RESOURCE_SHORTAGE("RESOURCE_SHORTAGE");

    private String value;

    ShortageTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ShortageTypeEnum fromValue(String text) {
      for (ShortageTypeEnum b : ShortageTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("shortageType")
  private ShortageTypeEnum shortageType = null;

  @JsonProperty("affectedOpOccs")
  @Valid
  private List<NsLcmCapacityShortageNotificationAffectedOpOccs> affectedOpOccs = null;

  @JsonProperty("capacityInformation")
  @Valid
  private List<NsLcmCapacityShortageNotificationCapacityInformation> capacityInformation = null;

  @JsonProperty("_links")
  private NsLcmCapacityShortageNotificationLinks1 _links = null;

  public NsLcmCapacityShortageNotification id(String id) {
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

  public NsLcmCapacityShortageNotification notificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
    return this;
  }

  /**
   * Discriminator for the different notification types. Shall be set to \"NsLcmCapacityShortageNotification\" for this notification type. 
   * @return notificationType
   **/
  @Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"NsLcmCapacityShortageNotification\" for this notification type. ")
      @NotNull

    public NotificationTypeEnum getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(NotificationTypeEnum notificationType) {
    this.notificationType = notificationType;
  }

  public NsLcmCapacityShortageNotification subscriptionId(String subscriptionId) {
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

  public NsLcmCapacityShortageNotification timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public NsLcmCapacityShortageNotification preemptingNsLcmOpOccId(String preemptingNsLcmOpOccId) {
    this.preemptingNsLcmOpOccId = preemptingNsLcmOpOccId;
    return this;
  }

  /**
   * Get preemptingNsLcmOpOccId
   * @return preemptingNsLcmOpOccId
   **/
  @Schema(description = "")
  
    public String getPreemptingNsLcmOpOccId() {
    return preemptingNsLcmOpOccId;
  }

  public void setPreemptingNsLcmOpOccId(String preemptingNsLcmOpOccId) {
    this.preemptingNsLcmOpOccId = preemptingNsLcmOpOccId;
  }

  public NsLcmCapacityShortageNotification highPrioNsInstanceId(String highPrioNsInstanceId) {
    this.highPrioNsInstanceId = highPrioNsInstanceId;
    return this;
  }

  /**
   * Get highPrioNsInstanceId
   * @return highPrioNsInstanceId
   **/
  @Schema(description = "")
  
    public String getHighPrioNsInstanceId() {
    return highPrioNsInstanceId;
  }

  public void setHighPrioNsInstanceId(String highPrioNsInstanceId) {
    this.highPrioNsInstanceId = highPrioNsInstanceId;
  }

  public NsLcmCapacityShortageNotification status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Indicates the situation of capacity shortage. Permitted values: - LCM_RESOURCES_NOT_AVAILABLE: the lifecycle operation identified by the nsLcmOpOccId attribute could not   be completed because necessary resources were not available. - LCM_SHORTAGE_END: the shortage situation which has caused the lifecycle management operation identified   by the nsLcmOpOccId attribute to fail has ended. 
   * @return status
   **/
  @Schema(required = true, description = "Indicates the situation of capacity shortage. Permitted values: - LCM_RESOURCES_NOT_AVAILABLE: the lifecycle operation identified by the nsLcmOpOccId attribute could not   be completed because necessary resources were not available. - LCM_SHORTAGE_END: the shortage situation which has caused the lifecycle management operation identified   by the nsLcmOpOccId attribute to fail has ended. ")
      @NotNull

    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public NsLcmCapacityShortageNotification shortageType(ShortageTypeEnum shortageType) {
    this.shortageType = shortageType;
    return this;
  }

  /**
   * Indicates whether this notification reports about a resource shortage or NFV-MANO capacity or performance shortage. Permitted values: - RESOURCE_SHORTAGE: the notification reports a resource shortage. Shall be present when a resources shortage situation has been identified starts and the notification is sent with “status“ = “LCM_RESOURCES_NOT_AVAILABLE“ and shall be absent otherwise. 
   * @return shortageType
   **/
  @Schema(description = "Indicates whether this notification reports about a resource shortage or NFV-MANO capacity or performance shortage. Permitted values: - RESOURCE_SHORTAGE: the notification reports a resource shortage. Shall be present when a resources shortage situation has been identified starts and the notification is sent with “status“ = “LCM_RESOURCES_NOT_AVAILABLE“ and shall be absent otherwise. ")
  
    public ShortageTypeEnum getShortageType() {
    return shortageType;
  }

  public void setShortageType(ShortageTypeEnum shortageType) {
    this.shortageType = shortageType;
  }

  public NsLcmCapacityShortageNotification affectedOpOccs(List<NsLcmCapacityShortageNotificationAffectedOpOccs> affectedOpOccs) {
    this.affectedOpOccs = affectedOpOccs;
    return this;
  }

  public NsLcmCapacityShortageNotification addAffectedOpOccsItem(NsLcmCapacityShortageNotificationAffectedOpOccs affectedOpOccsItem) {
    if (this.affectedOpOccs == null) {
      this.affectedOpOccs = new ArrayList<>();
    }
    this.affectedOpOccs.add(affectedOpOccsItem);
    return this;
  }

  /**
   * List of NS LCM operation occurrence(s) that were affected by the resource shortage. 
   * @return affectedOpOccs
   **/
  @Schema(description = "List of NS LCM operation occurrence(s) that were affected by the resource shortage. ")
      @Valid
    public List<NsLcmCapacityShortageNotificationAffectedOpOccs> getAffectedOpOccs() {
    return affectedOpOccs;
  }

  public void setAffectedOpOccs(List<NsLcmCapacityShortageNotificationAffectedOpOccs> affectedOpOccs) {
    this.affectedOpOccs = affectedOpOccs;
  }

  public NsLcmCapacityShortageNotification capacityInformation(List<NsLcmCapacityShortageNotificationCapacityInformation> capacityInformation) {
    this.capacityInformation = capacityInformation;
    return this;
  }

  public NsLcmCapacityShortageNotification addCapacityInformationItem(NsLcmCapacityShortageNotificationCapacityInformation capacityInformationItem) {
    if (this.capacityInformation == null) {
      this.capacityInformation = new ArrayList<>();
    }
    this.capacityInformation.add(capacityInformationItem);
    return this;
  }

  /**
   * References to NFVI capacity information related to the shortage. 
   * @return capacityInformation
   **/
  @Schema(description = "References to NFVI capacity information related to the shortage. ")
      @Valid
    public List<NsLcmCapacityShortageNotificationCapacityInformation> getCapacityInformation() {
    return capacityInformation;
  }

  public void setCapacityInformation(List<NsLcmCapacityShortageNotificationCapacityInformation> capacityInformation) {
    this.capacityInformation = capacityInformation;
  }

  public NsLcmCapacityShortageNotification _links(NsLcmCapacityShortageNotificationLinks1 _links) {
    this._links = _links;
    return this;
  }

  /**
   * Get _links
   * @return _links
   **/
  @Schema(description = "")
  
    @Valid
    public NsLcmCapacityShortageNotificationLinks1 getLinks() {
    return _links;
  }

  public void setLinks(NsLcmCapacityShortageNotificationLinks1 _links) {
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
    NsLcmCapacityShortageNotification nsLcmCapacityShortageNotification = (NsLcmCapacityShortageNotification) o;
    return Objects.equals(this.id, nsLcmCapacityShortageNotification.id) &&
        Objects.equals(this.notificationType, nsLcmCapacityShortageNotification.notificationType) &&
        Objects.equals(this.subscriptionId, nsLcmCapacityShortageNotification.subscriptionId) &&
        Objects.equals(this.timestamp, nsLcmCapacityShortageNotification.timestamp) &&
        Objects.equals(this.preemptingNsLcmOpOccId, nsLcmCapacityShortageNotification.preemptingNsLcmOpOccId) &&
        Objects.equals(this.highPrioNsInstanceId, nsLcmCapacityShortageNotification.highPrioNsInstanceId) &&
        Objects.equals(this.status, nsLcmCapacityShortageNotification.status) &&
        Objects.equals(this.shortageType, nsLcmCapacityShortageNotification.shortageType) &&
        Objects.equals(this.affectedOpOccs, nsLcmCapacityShortageNotification.affectedOpOccs) &&
        Objects.equals(this.capacityInformation, nsLcmCapacityShortageNotification.capacityInformation) &&
        Objects.equals(this._links, nsLcmCapacityShortageNotification._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, notificationType, subscriptionId, timestamp, preemptingNsLcmOpOccId, highPrioNsInstanceId, status, shortageType, affectedOpOccs, capacityInformation, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsLcmCapacityShortageNotification {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    preemptingNsLcmOpOccId: ").append(toIndentedString(preemptingNsLcmOpOccId)).append("\n");
    sb.append("    highPrioNsInstanceId: ").append(toIndentedString(highPrioNsInstanceId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    shortageType: ").append(toIndentedString(shortageType)).append("\n");
    sb.append("    affectedOpOccs: ").append(toIndentedString(affectedOpOccs)).append("\n");
    sb.append("    capacityInformation: ").append(toIndentedString(capacityInformation)).append("\n");
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
