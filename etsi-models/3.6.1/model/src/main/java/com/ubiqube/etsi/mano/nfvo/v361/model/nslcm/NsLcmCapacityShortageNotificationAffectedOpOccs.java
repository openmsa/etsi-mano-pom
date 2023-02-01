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
package com.ubiqube.etsi.mano.nfvo.v361.model.nslcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvo.v361.model.nslcm.NsLcmCapacityShortageNotificationLinks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * NsLcmCapacityShortageNotificationAffectedOpOccs
 */
@Validated


public class NsLcmCapacityShortageNotificationAffectedOpOccs   {
  @JsonProperty("affectedNsId")
  private String affectedNsId = null;

  @JsonProperty("affectedOpOccId")
  private String affectedOpOccId = null;

  /**
   * This flag indicates in what condition (pre-empted or triggered) the operation occurrence(s) were affected by the resource shortage. Permitted values: - PRE_EMPTED: the operation was pre-empted and the \"operationState\" = \"FAILED_TEMP” of the NsLcmOpOcc     structure identified by \"affectedOpOccId\" attribute. - TRIGGERED: the operation was triggered by NFVO and the “operationState” = \"FAILED_TEMP” of the     NsLcmOpOcc structure identified by \"affectedOpOccId\" attribute.  In case the notification indicates the end of a shortage condition (“status“ = \"LCM_SHORTAGE_END\"), only entries with “affectedCondition“ = \"PRE_EMPTED\" shall be included. See note. 
   */
  public enum AffectedConditionEnum {
    PRE_EMPTED("PRE_EMPTED"),
    
    TRIGGERED("TRIGGERED");

    private String value;

    AffectedConditionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AffectedConditionEnum fromValue(String text) {
      for (AffectedConditionEnum b : AffectedConditionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("affectedCondition")
  private AffectedConditionEnum affectedCondition = null;

  @JsonProperty("_links")
  private NsLcmCapacityShortageNotificationLinks _links = null;

  public NsLcmCapacityShortageNotificationAffectedOpOccs affectedNsId(String affectedNsId) {
    this.affectedNsId = affectedNsId;
    return this;
  }

  /**
   * Get affectedNsId
   * @return affectedNsId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getAffectedNsId() {
    return affectedNsId;
  }

  public void setAffectedNsId(String affectedNsId) {
    this.affectedNsId = affectedNsId;
  }

  public NsLcmCapacityShortageNotificationAffectedOpOccs affectedOpOccId(String affectedOpOccId) {
    this.affectedOpOccId = affectedOpOccId;
    return this;
  }

  /**
   * Get affectedOpOccId
   * @return affectedOpOccId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getAffectedOpOccId() {
    return affectedOpOccId;
  }

  public void setAffectedOpOccId(String affectedOpOccId) {
    this.affectedOpOccId = affectedOpOccId;
  }

  public NsLcmCapacityShortageNotificationAffectedOpOccs affectedCondition(AffectedConditionEnum affectedCondition) {
    this.affectedCondition = affectedCondition;
    return this;
  }

  /**
   * This flag indicates in what condition (pre-empted or triggered) the operation occurrence(s) were affected by the resource shortage. Permitted values: - PRE_EMPTED: the operation was pre-empted and the \"operationState\" = \"FAILED_TEMP” of the NsLcmOpOcc     structure identified by \"affectedOpOccId\" attribute. - TRIGGERED: the operation was triggered by NFVO and the “operationState” = \"FAILED_TEMP” of the     NsLcmOpOcc structure identified by \"affectedOpOccId\" attribute.  In case the notification indicates the end of a shortage condition (“status“ = \"LCM_SHORTAGE_END\"), only entries with “affectedCondition“ = \"PRE_EMPTED\" shall be included. See note. 
   * @return affectedCondition
   **/
  @Schema(required = true, description = "This flag indicates in what condition (pre-empted or triggered) the operation occurrence(s) were affected by the resource shortage. Permitted values: - PRE_EMPTED: the operation was pre-empted and the \"operationState\" = \"FAILED_TEMP” of the NsLcmOpOcc     structure identified by \"affectedOpOccId\" attribute. - TRIGGERED: the operation was triggered by NFVO and the “operationState” = \"FAILED_TEMP” of the     NsLcmOpOcc structure identified by \"affectedOpOccId\" attribute.  In case the notification indicates the end of a shortage condition (“status“ = \"LCM_SHORTAGE_END\"), only entries with “affectedCondition“ = \"PRE_EMPTED\" shall be included. See note. ")
      @NotNull

    public AffectedConditionEnum getAffectedCondition() {
    return affectedCondition;
  }

  public void setAffectedCondition(AffectedConditionEnum affectedCondition) {
    this.affectedCondition = affectedCondition;
  }

  public NsLcmCapacityShortageNotificationAffectedOpOccs _links(NsLcmCapacityShortageNotificationLinks _links) {
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
    public NsLcmCapacityShortageNotificationLinks getLinks() {
    return _links;
  }

  public void setLinks(NsLcmCapacityShortageNotificationLinks _links) {
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
    NsLcmCapacityShortageNotificationAffectedOpOccs nsLcmCapacityShortageNotificationAffectedOpOccs = (NsLcmCapacityShortageNotificationAffectedOpOccs) o;
    return Objects.equals(this.affectedNsId, nsLcmCapacityShortageNotificationAffectedOpOccs.affectedNsId) &&
        Objects.equals(this.affectedOpOccId, nsLcmCapacityShortageNotificationAffectedOpOccs.affectedOpOccId) &&
        Objects.equals(this.affectedCondition, nsLcmCapacityShortageNotificationAffectedOpOccs.affectedCondition) &&
        Objects.equals(this._links, nsLcmCapacityShortageNotificationAffectedOpOccs._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affectedNsId, affectedOpOccId, affectedCondition, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsLcmCapacityShortageNotificationAffectedOpOccs {\n");
    
    sb.append("    affectedNsId: ").append(toIndentedString(affectedNsId)).append("\n");
    sb.append("    affectedOpOccId: ").append(toIndentedString(affectedOpOccId)).append("\n");
    sb.append("    affectedCondition: ").append(toIndentedString(affectedCondition)).append("\n");
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
