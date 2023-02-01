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
package com.ubiqube.etsi.mano.nfvo.v431.model.nsfm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.NotificationLink;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Links to resources related to this notification. 
 */
@Schema(description = "Links to resources related to this notification. ")
@Validated


public class AlarmListRebuiltNotificationLinks   {
  @JsonProperty("subscription")
  private NotificationLink subscription = null;

  @JsonProperty("alarms")
  private NotificationLink alarms = null;

  public AlarmListRebuiltNotificationLinks subscription(NotificationLink subscription) {
    this.subscription = subscription;
    return this;
  }

  /**
   * Get subscription
   * @return subscription
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public NotificationLink getSubscription() {
    return subscription;
  }

  public void setSubscription(NotificationLink subscription) {
    this.subscription = subscription;
  }

  public AlarmListRebuiltNotificationLinks alarms(NotificationLink alarms) {
    this.alarms = alarms;
    return this;
  }

  /**
   * Get alarms
   * @return alarms
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public NotificationLink getAlarms() {
    return alarms;
  }

  public void setAlarms(NotificationLink alarms) {
    this.alarms = alarms;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlarmListRebuiltNotificationLinks alarmListRebuiltNotificationLinks = (AlarmListRebuiltNotificationLinks) o;
    return Objects.equals(this.subscription, alarmListRebuiltNotificationLinks.subscription) &&
        Objects.equals(this.alarms, alarmListRebuiltNotificationLinks.alarms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscription, alarms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlarmListRebuiltNotificationLinks {\n");
    
    sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
    sb.append("    alarms: ").append(toIndentedString(alarms)).append("\n");
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
